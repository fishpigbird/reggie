package com.blog.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.blog.common.Result;
import com.blog.entity.Employee;
import com.blog.service.EmployeeService;
import com.blog.service.impl.EmployeeServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestController
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired//自动装配
    private EmployeeServiceImpl employeeService;//这里是接口，不是实现类

    //private AccountServiceImpl accountService;
    //不行，user的密码和employee的密码虽然都是账户密码，但是他们确实不同的table。所以不能用同一个service。
    //也就是说，在mapper中获取密码时，需要用不同的mapper，也就是不同的service。
    //所以user和employee不能用同一个获取密码的方法。
    //那么，获取密码的方法就得分别由他们自身来实现。


    /**
     * 登入功能
     *
     * @param request
     * @param employee
     * @return
     */
    //发送post请求
    @PostMapping("/login")
    public Result<Employee> login(HttpServletRequest request, @RequestBody Employee employee) {

        log.info("登陆的员工信息：{}", employee.toString());

        //前端应该对密码进行加密吗？https://www.zhihu.com/question/25539382
        /*
        一些人认为前端加密密码是有意义的，因为它可以增加破解的难度，保护知识产权，防止模拟登录，遏制第三方应用和机器人等²⁴。
        另一些人认为前端加密密码是没有意义的，因为它不能阻止重放攻击，中间人攻击，服务器窃取等¹⁶，而且可以通过格式化，调试，逆向等手段还原代码。
        还有一些人认为前端加密密码是有一定意义的，但是不能完全依赖它，需要结合后端验证，HTTPS传输，TLS层保护等措施来提高安全性¹³⁵。
        总之，Web前端是否需要加密密码取决于具体的场景和需求，没有一个统一的答案。
         */

        //md加密密码的操作应该放在controller层吗？
        // 不要。

        //加密密码，放到一个String中.

        return employeeService.login(request,employee);
        //idea如何查看当前代码的历史版本//   https://blog.csdn.net/qq_41855420/article/details/107201100
    }
    //TODO 去完善登录功能，添加拦截。

    //测试Controller生命周期
    @GetMapping("/test1")
    //getmappin
    public Result<String> test1(HttpServletRequest request) {
        log.info("test1，测试impl方法是否存在.");
        employeeService.test1();
        return Result.success("退出成功");
    }


    /**
     * 登出功能
     *
     * @param request
     * @return
     */
    @PostMapping("/logout")
    public Result<String> logout(HttpServletRequest request) {
        log.info("登出");
        request.getSession().removeAttribute("employee");
        return Result.success("退出成功");
    }

    /**
     * 新增员工
     *
     * @param employee
     * @return
     */
    @PostMapping
    public Result<String> save(HttpServletRequest request, @RequestBody Employee employee) {
        log.info("新增的员工信息：{}", employee.toString());
        //设置默认密码为123456，并采用MD5加密
        employee.setPassword(DigestUtils.md5DigestAsHex("123456".getBytes()));
        //MP
        //存入数据库
        employeeService.save(employee);
        return Result.success("添加员工成功");
    }



    @GetMapping("/page")
    public Result<Page> page(int page, int pageSize, String name) {
        log.info("page={},pageSize={},name={}", page, pageSize, name);
        //构造分页构造器
        Page<Employee> pageInfo = new Page<>(page, pageSize);
        //构造条件构造器
        LambdaQueryWrapper<Employee> wrapper = new LambdaQueryWrapper<>();
        //添加过滤条件（当我们没有输入name时，就相当于查询所有了）
        wrapper.like(!(name == null || "".equals(name)), Employee::getName, name);
        //并对查询的结果进行降序排序，根据更新时间
        wrapper.orderByDesc(Employee::getUpdateTime);
        //执行查询
        employeeService.page(pageInfo, wrapper);
        return Result.success(pageInfo);
    }


    /**
     * 通用的修改员工信息
     *
     * @param employee
     * @param request
     * @return
     */
    @PutMapping
    public Result<String> update(@RequestBody Employee employee, HttpServletRequest request) {
        log.info(employee.toString());
        //获取线程id 为什么要获取线程id？
        long id = Thread.currentThread().getId();
        log.info("update的线程id为：{}", id); //这里就打个线程日志，
        employeeService.updateById(employee);//这更更新方法会被MyMetaObjectHanddler接手。
        return Result.success("员工信息修改成功");
    }

    @GetMapping("/{id}")
    public Result<Employee> getById(@PathVariable Long id) {
        log.info("根据id查询员工信息..");
        Employee employee = employeeService.getById(id);
        if (employee != null) {
            return Result.success(employee);
        }
        return Result.error("未查询到该员工信息");
    }
}