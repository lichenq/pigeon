package com.dianping.dpsf.component;

import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: jian.liu
 * Date: 13-1-11
 * Time: 下午2:15
 * To change this template use File | Settings | File Templates.
 */
public interface ConfigMetaParser<T> {

    T parse(Map<String, String> configMap);

}
