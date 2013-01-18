package com.dianping.dpsf.component;

import java.util.Arrays;
import java.util.Map;

import com.google.common.collect.Maps;

/**
 * Created with IntelliJ IDEA.
 * User: lichenq
 * Date: 13-1-16
 * Time: 下午3:51
 * To change this template use File | Settings | File Templates.
 */
public class MockMetaParser implements ConfigMetaParser<MockMeta>{
    @Override
    public MockMeta parse(Map<String, String> configMap) {
        if (configMap != null && !configMap.isEmpty()) {
            for (Map.Entry<String, String> entry : configMap.entrySet()) {
                MockMeta mockMeta = new MockMeta();
                mockMeta.setMockClass(entry.getKey());
                mockMeta.setMockMethods(Arrays.asList(entry.getValue().split(",")));
                return mockMeta;
            }
        }
        return null;
    }
}
