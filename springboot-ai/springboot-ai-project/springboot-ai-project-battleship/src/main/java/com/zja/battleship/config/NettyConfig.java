package com.zja.battleship.config;

import com.zja.battleship.handler.GameWebSocketHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import jakarta.annotation.PreDestroy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: zhengja
 * @Date: 2025-07-14 14:28
 */
@Configuration
public class NettyConfig {

    @Value("${netty.port:8081}")
    private int nettyPort;

    @Autowired
    private GameWebSocketHandler gameWebSocketHandler;

    private EventLoopGroup bossGroup;
    private EventLoopGroup workerGroup;

    @Bean
    public void startNetty() throws InterruptedException {
        bossGroup = new NioEventLoopGroup();
        workerGroup = new NioEventLoopGroup();

        ServerBootstrap bootstrap = new ServerBootstrap();
        bootstrap.group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel.class)
                .childHandler(gameWebSocketHandler);

        Channel channel = bootstrap.bind(nettyPort).sync().channel();
        System.out.println("Netty WebSocket server started on port: " + nettyPort);
        channel.closeFuture().sync();
    }

    @PreDestroy
    public void stopNetty() {
        if (bossGroup != null) {
            bossGroup.shutdownGracefully();
        }
        if (workerGroup != null) {
            workerGroup.shutdownGracefully();
        }
        System.out.println("Netty WebSocket server stopped");
    }
}