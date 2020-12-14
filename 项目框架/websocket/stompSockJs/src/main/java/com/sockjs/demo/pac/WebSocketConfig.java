package com.sockjs.demo.pac;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.AbstractWebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

/**
 * @Classname AC
 * @Description TODO
 * @Date 2020/12/14 9:43
 * @Created by lisp-b
 */
@Configuration
@EnableWebSocketMessageBroker
@Slf4j
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

//    @Autowired
//    private SimpMessagingTemplate simpMessagingTemplate;

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        //添加一个/websocket端点，客户端就可以通过这个端点来进行连接；withSockJS作用是添加SockJS支持
        registry.addEndpoint("/websocket")
//                .setAllowedOrigins("*")
                .setAllowedOriginPatterns("*")
                .withSockJS()
        ;//.withSockJS()
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        //定义了两个客户端订阅地址的前缀信息，也就是客户端接收服务端发送消息的前缀信息
        registry.enableSimpleBroker("/topic","/queue");
        //定义了服务端接收地址的前缀，也即客户端给服务端发消息的地址前缀
        registry.setUserDestinationPrefix("/topic");
//        registry.setApplicationDestinationPrefixes("/app");
    }

//    @Scheduled(cron = "*/10 * * * * ?")//运行周期时间可配
//    public void publicMarketValueMessage() {
//        //这里的nowDate 暂且作为订阅的内容--可更换为具体的业务数据
//        String nowDate = "ttt";
//        //这里的destination 是 订阅的地址
//        String destination = "/topic/echoTest/price";
//        log.info("websocket 【{}】定时任务发布消息==>【开始】", destination);
//        try {
//            this.simpMessagingTemplate.convertAndSend(destination, nowDate);
//        } catch (Exception e) {
//            log.error("websocket ={} 定时任务发布消息==>【异常】", destination, e);
//        }
//    }
}
