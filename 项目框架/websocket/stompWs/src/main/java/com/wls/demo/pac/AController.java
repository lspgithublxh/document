package com.wls.demo.pac;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * js订阅不是显式的。并不需要后端显式明确每个主题。
 * 生产者则是显式的，调用下面的http接口就可以了。后端就发送给了其他方
 * @Classname AController
 * @Description TODO
 * @Date 2020/12/14 15:15
 * @Created by lisp-b
 */
@Controller
public class AController {

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;


    @GetMapping("refresh/{groupId}")
    public void refresh(@PathVariable Long groupId) {///topic/echoTest/price
        //将会向 订阅了/user/{groupId}/refresh的客户端发送"refresh"字符串。
        simpMessagingTemplate.convertAndSendToUser("echoTest", "/price", "refresh");
    }
}
