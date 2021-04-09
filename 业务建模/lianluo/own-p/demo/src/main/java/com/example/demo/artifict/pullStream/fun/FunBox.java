package com.example.demo.artifict.pullStream.fun;

import java.util.Objects;

/**
 * Description 简单函数实现
 *
 * @author lishaoping
 * @ClassName FunBox
 * @date 2021.04.07 19:00
 */
public class FunBox {

    public static double intToDouble(Integer data){
        if(Objects.isNull(data)){
            return 0.0;
        }
        return data + 0.000001;
    }

    public static double longToDouble(Long data){
        if(Objects.isNull(data)){
            return 0.0;
        }
        return data.doubleValue();
    }

    public static int doubleToInt(Double data){
        return data.intValue();
    }
}
