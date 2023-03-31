package com.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.blog.common.Result;
import com.blog.entity.Employee;
import com.blog.mapper.EmployeeMapper;
import com.blog.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;


//这个类是MP的Service实现类，继承了ServiceImpl，实现了EmployeeService接口

@Slf4j
@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, Employee> implements EmployeeService {

    //services生命周期
    //这个实例变量是通过login初始化的。
    //那，这个对象会一直存在吗？如果调用其他的方法，这个对象还存在吗？
    //--》这个对象是在login方法中初始化的，所以，这个对象只在login方法中存在。

    //service层对象会一直存在吗？
    // does service always exist? answer its by english
    //我们用postman测试一下。
    Employee voEmployee;

    public Result<Employee> login(HttpServletRequest request,Employee voEmployee) {
        this.voEmployee = voEmployee;


        String password = DigestUtils.md5DigestAsHex(voEmployee.getPassword().getBytes());
        //把密码放到employee中
        voEmployee.setPassword(password);
        //调用service的login方法


        log.info(voEmployee.getUsername());//java.lang.NullPointerException: null
        System.out.println(voEmployee.getUsername());

        Employee bkemp = getBackEmployeeByUsername(voEmployee);

        //判断
        if (bkemp == null) {
            return Result.error("登陆失败");
        }
        if (!bkemp.getPassword().equals(voEmployee.getPassword())) {
            return Result.error("登录失败");
        }
        if (bkemp.getStatus() == 0) {
            return Result.error("该用户已被禁用");
        }

        //后台存个Session，只存个id就行了，保存登录状态。//
        request.getSession().setAttribute("employee", bkemp.getId());

        //返回用户的基本信息
        return Result.success(bkemp);

    }


    public Employee getBackEmployeeByUsername(Employee VoEmployee) {
        LambdaQueryWrapper<Employee> lqw = new LambdaQueryWrapper<>();
        lqw.eq(Employee::getUsername, VoEmployee.getUsername());
        //这里的getOne就是MP的方法，返回的是一个Employee对象
        return this.getOne(lqw);
    }

    public String test1() {
        //service层对象会一直存在吗？
        //存在的。即使退出登录，这个对象也是存在的。这个对象
        // 那这个对象的具体存在方式是什么？
        //这是一个controller的生命周期

        log.info(voEmployee.getUsername());//java.lang.NullPointerException: null
        System.out.println(voEmployee.getUsername());
        return voEmployee.getName();
    }
//    public void setEmployee(Employee employee) {
//        this.employee = employee;
//    }
}