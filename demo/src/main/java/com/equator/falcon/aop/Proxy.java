package com.equator.falcon.aop;

/**
 * 代理接口，其实现类中会提供相应的横切逻辑，并调用doProxyChain方法，随后将再次调用当前ProxyChain对象的doProxyChain方法，直到proxyIndex到达proxyList的上限
 *
 * @Author: Equator
 * @Date: 2020/2/12 11:37
 **/

public interface Proxy {
    /**
     * 执行链式代理，执行多个代理。执行顺序取决于添加到执行链上面的顺序
     *
     * @param proxyChain
     * @return
     * @throws Throwable
     */
    Object doProxy(ProxyChain proxyChain) throws Throwable;
}
