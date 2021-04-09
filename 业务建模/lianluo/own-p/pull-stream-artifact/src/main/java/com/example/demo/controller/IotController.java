package com.example.demo.controller;

import com.example.demo.service.IotService;
import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * (Iot)表服务控制层
 *
 * @author liuyzh
 * @since 2021-03-22 18:21:01
 */
@Api(tags = "(Iot)")
@Validated
@RestController
@AllArgsConstructor
@RequestMapping("iot")
public class IotController {
    @Resource
    private final IotService iotService;

    public static void main(String[] args) {
        String s = "{    \"currentVersion\": \"1.0.0\",    \"targetVersion\": \"1.0.1\",    \"timestamp\": 1545120307,    \"addInfo\": \"addInfo\",    \"upgradeStatusEventType\": 5,    \"upgradeTriggerType\": 0,    \"failEcuName\": \"HU\",    \"errorType\": -1,    \"source\": 1,    \"txId\": \"1234\"}";
        System.out.printf(s.replaceAll("\n", ""));
    }
}
