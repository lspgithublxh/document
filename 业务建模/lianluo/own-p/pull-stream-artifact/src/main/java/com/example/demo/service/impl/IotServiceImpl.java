package com.example.demo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.mapper.IotMapper;
import com.example.demo.entity.Iot;
import com.example.demo.service.IotService;
import org.springframework.stereotype.Service;

/**
 * (Iot)表服务实现类
 *
 * @author liuyzh
 * @since 2021-03-22 18:21:00
 */
@Service
public class IotServiceImpl extends ServiceImpl<IotMapper, Iot> implements IotService {

}
