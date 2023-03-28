package com.blog.common;


import lombok.Data;//

//@Data//
public enum ResultEnum implements IResult {//这里的ResultEnum实现了IResult接口//
    //
    //这里是一些常用的状态码
    //比如200表示成功，400表示失败
    //这里的状态码可以自定义
    //比如2001表示成功，2002表示参数校验失败，2003表示接口调用失败，2004表示没有权限访问资源
    //这样的状态码可以让前端更好的处理
    //比如前端可以根据状态码2001来判断接口调用成功，然后根据data里的数据来渲染页面
    //根据状态码2002来判断参数校验失败，然后根据message里的数据来提示用户
    //根据状态码2003来判断接口调用失败，然后根据message里的数据来提示用户
    //根据状态码2004来判断没有权限访问资源，然后根据message里的数据来提示用户

    SUCCESS(2001, "接口调用成功"),
    VALIDATE_FAILED(2002, "参数校验失败"),
    COMMON_FAILED(2003, "接口调用失败"),
    FORBIDDEN(2004, "没有权限访问资源");
    //省略get、set方法和构造方法

    private Integer code;
    private String message;

    ResultEnum(int i, String 接口调用成功) {
    }

    @Override
    public Integer getCode() {
        return null;
    }

    @Override
    public String getMessage() {
        return null;
    }
}
