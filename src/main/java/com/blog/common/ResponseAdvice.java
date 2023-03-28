package com.blog.common;

import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

// 如果引入了swagger或knife4j的文档生成组件，这里需要仅扫描自己项目的包，否则文档无法正常生成
@RestControllerAdvice(basePackages = "com.blog.controller")
public class ResponseAdvice implements ResponseBodyAdvice<Object> {
    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        // 如果不需要进行封装的，可以添加一些校验手段，比如添加标记排除的注解
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        // 提供一定的灵活度，如果body已经被包装了，就不进行包装
        // 这里的判断可以根据自己的业务进行调整

        //这里的判断是为了防止重复包装
        if (body instanceof Result) {
            return body;
        }

        // 这里是对以前代码的兼容吧？比如以前的方法并没有返回Result类型的值，而是返回一个对象。
        //这样，可以在这里包装一下。

        //也就是说，service里是可以直接返回result类型的。

        // 那这里为何直接返回success？
        // 如果返回了一个null，或者0，1，什么的。前端却会看到一个成功的消息。
        // emm，这个成功是指请求成功，不是指业务成功。？
        // 不可能。error也是一样的。是指业务的成功和错误。
        // 所有这里的 success 和 error 都是指业务的成功和错误。
        // 这里有问题的。如果是一个空对象，那么前端会看到一个成功的消息。

        return Result.success(body);

    }
}