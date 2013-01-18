package com.dianping.dpsf.component;

import java.util.List;
import java.util.Map;

import com.google.common.collect.Maps;

/**
 * Created with IntelliJ IDEA.
 * User: lichenq
 * Date: 13-1-16
 * Time: 下午3:34
 * To change this template use File | Settings | File Templates.
 */
public class MockMeta {

    private String mockClass;
    private List<String> mockMethods;

    public String getMockClass() {
        return mockClass;
    }

    public void setMockClass(String mockClass) {
        this.mockClass = mockClass;
    }

    public List<String> getMockMethods() {
        return mockMethods;
    }

    public void setMockMethods(List<String> mockMethods) {
        this.mockMethods = mockMethods;
    }

    @Override
    public String toString() {
        return "MockMeta{" +
                "mockClass='" + mockClass + '\'' +
                ", mockMethods=" + mockMethods +
                '}';
    }
}
