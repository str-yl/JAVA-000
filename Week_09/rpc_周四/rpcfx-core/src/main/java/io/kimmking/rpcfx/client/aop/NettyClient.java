package io.kimmking.rpcfx.client.aop;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.HttpRequestEncoder;
import io.netty.handler.codec.http.HttpResponseDecoder;
import io.netty.util.CharsetUtil;

import java.net.InetSocketAddress;

public class NettyClient {

    private final String host;
    private final int port;
    private String request;
    public NettyClient(String h,int p,String request){
        this.host = h;
        this.port = p;
        this.request = request;
    }

    public String start() throws Exception {
        EventLoopGroup group = new NioEventLoopGroup();
        NettyClientHandler nc = new NettyClientHandler();
        try{
            Bootstrap b = new Bootstrap();
            b.group(group)
                    .channel(NioSocketChannel.class)
                    .remoteAddress(new InetSocketAddress(host,port))
                    .handler(new ChannelInitializer<SocketChannel>() {
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline().addLast(new HttpResponseDecoder());
                            // 客户端发送的是httprequest，所以要使用HttpRequestEncoder进行编码
                            socketChannel.pipeline().addLast(new HttpRequestEncoder());
                            socketChannel.pipeline().addLast(nc);
                        }
                    });
            ChannelFuture f = b.connect().sync();
            f.channel().writeAndFlush(request);
            f.channel().closeFuture().sync();
            return nc.getResponseResult().toString(CharsetUtil.UTF_8);
        }finally {
            group.shutdownGracefully().sync();
        }
    }
}
