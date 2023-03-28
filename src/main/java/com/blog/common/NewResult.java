package com.blog.common;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NewResult<T> {
    private Integer code;
    private String message;
    private T data;



    public static <T> NewResult<T> success(T data) {
        return new NewResult<>(ResultEnum.SUCCESS.getCode(), ResultEnum.SUCCESS.getMessage(), data);
    }
    public static <T> NewResult<T> success(String message, T data) {
        return new NewResult<>(ResultEnum.SUCCESS.getCode(), message, data);
    }


    public static NewResult<?> failed() {
        return new NewResult<>(ResultEnum.COMMON_FAILED.getCode(), ResultEnum.COMMON_FAILED.getMessage(), null);
    }
    public static NewResult<?> failed(String message) {
        return new NewResult<>(ResultEnum.COMMON_FAILED.getCode(), message, null);
    }

    // 这里提供了几个静态方法来返回一个Result对象
    // 之前是直接用构造器的set方法也可以实现同样的功能/
    // 不过此种方式的复用性更高/
    // 这里的ResultEnum实现了IResult接口/
    // 这里是一些常用的状态码/
    // 比如200表示成功，400表示失败/

    public static NewResult<?> failed(IResult errorNewResult) {//这个方法是干什么的？
        // 这个方法是干什么的？/
        // 这个方法是用来返回一个错误的NewResult对象的/
        // 这个方法的参数是一个IResult接口的实现类/ //可能是其他的类，但是这个类必须实现了IResult接口//
        // 这个方法的返回值是一个NewResult对象/
        // 这个方法的返回值的泛型是不确定的/

        //为什么这里要用NewResult<?>而不是NewResult<T>？/


        return new NewResult<>(errorNewResult.getCode(), errorNewResult.getMessage(), null);
    }




    // 这里提供了几个静态方法来返回一个Result对象
    public static <T> NewResult<T> instance(Integer code, String message, T data) {
        NewResult<T> NewResult = new NewResult<>();
        NewResult.setCode(code);
        NewResult.setMessage(message);
        NewResult.setData(data);
        return NewResult;
    }

}