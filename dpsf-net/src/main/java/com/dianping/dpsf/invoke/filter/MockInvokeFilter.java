/**
 * File Created at 12-12-29
 *
 * Copyright 2010 dianping.com.
 * All rights reserved.
 *
 * This software is the confidential and proprietary information of
 * Dianping Company. ("Confidential Information").  You shall not
 * disclose such Confidential Information and shall use it only in
 * accordance with the terms of the license agreement you entered into
 * with dianping.com.
 */
package com.dianping.dpsf.invoke.filter;

import java.lang.reflect.Method;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ReflectionUtils;

import com.dianping.dpsf.Constants;
import com.dianping.dpsf.component.DPSFResponse;
import com.dianping.dpsf.component.InvocationInvokeContext;
import com.dianping.dpsf.component.MockMeta;
import com.dianping.dpsf.exception.DPSFException;
import com.dianping.dpsf.invoke.RemoteInvocationHandler;
import com.dianping.dpsf.protocol.DefaultResponse;
import com.google.common.collect.Maps;

/**
 * 用于对一些弱依赖的Service接口方法进行MOCK，一旦调用该接口方法出错时，返回mock对象，不影响
 * 主业务的流程
 *
 * @author danson.liu
 * @author chenqing.li
 */
public class MockInvokeFilter extends InvocationInvokeFilter {

    private static Map<String, Class> mockClassMap = Maps.newHashMap();

    public MockInvokeFilter(int order) {
        super(order);
    }

    @Override
    public DPSFResponse invoke(RemoteInvocationHandler handler, InvocationInvokeContext invocationContext) throws Throwable {
        MockMeta mockMeta = invocationContext.getMetaData().getMockMeta();
        try {
            DPSFResponse response = handler.handle(invocationContext);
            if (response.getMessageType() == Constants.MESSAGE_TYPE_SERVICE_EXCEPTION
                    || response.getMessageType() == Constants.MESSAGE_TYPE_EXCEPTION) {
                if (mockMeta != null && StringUtils.isNotBlank(mockMeta.getMockClass())
                        && !CollectionUtils.isEmpty(mockMeta.getMockMethods())) {
                    return createMockResponse(invocationContext, mockMeta);
                }
            }
            return response;
        } catch (Exception e) {
            return createMockResponse(invocationContext, mockMeta);
        }
    }

    private DPSFResponse createMockResponse(InvocationInvokeContext invocationContext, MockMeta mockMeta) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
        String mockClassName = mockMeta.getMockClass();
        Class mockClass = mockClassMap.get(mockClassName);
        if (mockClass == null) {
            mockClass = Class.forName(mockClassName);
            mockClassMap.put(mockClassName, mockClass);
        }
        Method invokeMethod = invocationContext.getMethod();
        if (mockClass != null && mockMeta.getMockMethods() != null
            && mockMeta.getMockMethods().contains(invokeMethod.getName())) {
            Object result = ReflectionUtils.invokeMethod(invokeMethod, mockClass.newInstance(), invocationContext.getArguments());
            DPSFResponse dpsfResponse = new DefaultResponse(Constants.MESSAGE_TYPE_SERVICE_MOCK, invocationContext.getRequest().getSerializ());
            dpsfResponse.setReturn(result);
            return dpsfResponse;
        } else {
            return new DefaultResponse(invocationContext.getRequest().getSerializ(),
                    invocationContext.getRequest().getSequence(), Constants.MESSAGE_TYPE_EXCEPTION,
                    new DPSFException("mock class or method config error.please check 'mockConfig'.mockConfig=" + mockMeta));
        }
    }

}
