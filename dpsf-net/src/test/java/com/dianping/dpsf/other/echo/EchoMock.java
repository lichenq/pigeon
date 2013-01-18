package com.dianping.dpsf.other.echo;

/**
 * Created with IntelliJ IDEA.
 * User: lichenq
 * Date: 13-1-16
 * Time: 下午3:33
 * To change this template use File | Settings | File Templates.
 */
public class EchoMock implements  IEcho{
    @Override
    public String echo(String input) {
        return "this is mock!";  //To change body of implemented methods use File | Settings | File Templates.
    }
}
