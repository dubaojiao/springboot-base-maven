package com.duke.util;

import com.duke.annotation.Verification;
import com.duke.exception.ApiException;

import java.lang.reflect.Field;

public class ParamVerification {

    public static void valid(Object object)throws Exception{
        Class objectClass = object.getClass();
        //获取所有的属性
        Field[] fields = objectClass.getDeclaredFields();

        for(Field field:fields){
            //对于private私有化的成员变量，通过setAccessible来修改器访问权限
            field.setAccessible(true);
            validate(field,object);
            //重新设置会私有权限
            field.setAccessible(false);
        }
    }

    private static void validate(Field field,Object object) throws Exception {
        Verification verification = field.getAnnotation(Verification.class);
        if (verification == null) {
            return ;
        }
        String desc = verification.desc().equals("")?field.getName():verification.desc();
        Object value = field.get(object);
        if(!verification.nullable()){
            if(value == null || CheckUtil.objectIsNull(value.toString())){
                throw  new  ApiException(500,desc+"不能为空");
            }
        }
        if (verification.maxLength() > 0) {
            if(value.toString().length() > verification.maxLength()){
                throw  new ApiException(500,desc+"最大长度为"+verification.maxLength());
            }
        }
        if (verification.maxLength() > 0) {
            if(value.toString().length() > verification.maxLength()){
                throw  new ApiException(500,desc+"最大长度为"+verification.maxLength());
            }
        }
        if(verification.minLength() > 0){
            if(value.toString().length() < verification.minLength()){
                throw  new  ApiException(500,desc+"最小长度为"+verification.minLength());
            }
        }
    }
}
