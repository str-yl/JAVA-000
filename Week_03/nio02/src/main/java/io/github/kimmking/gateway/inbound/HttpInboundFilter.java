package io.github.kimmking.gateway.inbound;

import io.github.kimmking.gateway.filter.HttpRequestFilter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;

public class HttpInboundFilter implements HttpRequestFilter {

    public void filter(FullHttpRequest fullRequest, ChannelHandlerContext ctx){

        fullRequest.headers().add("nio","yanglei");

    }
}
