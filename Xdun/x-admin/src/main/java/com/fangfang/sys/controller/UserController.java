package com.fangfang.sys.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fangfang.common.vo.Result;
import com.fangfang.sys.entity.User;
import com.fangfang.sys.service.IUserService;
import io.netty.util.internal.SocketUtils;
import io.netty.util.internal.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author fangfang
 * @since 2024-08-25
 */
@Controller
//@Controller 注解会默认输出试图，而前后端对接需要输出json对象，应该使用 @RestController
//@RestController
@RequestMapping("/user")
public class UserController {
    // 暴露接口，测试service
    @Autowired
    private IUserService userService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/all")
    @ResponseBody
    public Result<List<User>> getAll() {
        return Result.success("查询成功", userService.list());
    }

    @PostMapping("/login")
    @ResponseBody
    public Result<Map<String, Object>> login(@RequestBody User user) {
        Map<String, Object> data = userService.login(user);
        if (data != null) {
            return Result.success(data);
        }
        return Result.fail(20002, "用户名或密码错误");
    }

    @GetMapping("/info")
    @ResponseBody
    public Result<Map<String, Object>> getUserInfo(@RequestParam("token") String token) {
//        根据token获取用户信息，redis
        Map<String, Object> data = userService.getUserInfo(token);

        if (data != null) {
            return Result.success(data);
        }
        return Result.fail(20003, "用户信息获取失败");
    }

    @PostMapping("/logout")
    @ResponseBody
    public Result<?> logout(@RequestHeader("X-Token") String token) {
        userService.logout(token);
        return Result.success("注销成功");
    }

    @GetMapping("/list")
    @ResponseBody
    public Result<?> getUserList(@RequestParam(value = "username", required = false) String username,
                                 @RequestParam(value = "phone", required = false) String phone,
                                 @RequestParam("pageNo") Long pageNo,
                                 @RequestParam("pageSize") Long pageSize) {
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(StringUtils.hasLength(username), User::getUsername, username);
        wrapper.eq(StringUtils.hasLength(phone), User::getPhone, phone);
        // 降序排序
        wrapper.orderByDesc(User::getId);

        // baomidou里的Page
        // 还需要分页拦截器的配置 https://baomidou.com/plugins/
        Page<User> page = new Page<>(pageNo, pageSize);
        userService.page(page, wrapper);

        Map<String, Object> data = new HashMap<>();
        data.put("total", page.getTotal());
        data.put("rows", page.getRecords());

        System.out.println(data);
        return Result.success(data);
    }

    @PostMapping
    @ResponseBody
    public Result<?> addUser(@RequestBody User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userService.save(user);
        return Result.success("新增用户成功");
    }

    @PutMapping
    @ResponseBody
    public Result<?> updateUser(@RequestBody User user) {
        user.setPassword(null);
        // updateById方法默认：如果该字段为空不更新，password不更新
        userService.updateById(user);
        return Result.success("修改用户成功");
    }

    @GetMapping("/{id}")
    @ResponseBody
    public Result<User> getUserById(@PathVariable("id") Integer id) {
        return Result.success(userService.getById(id));
    }

    @DeleteMapping("/{id}")
    @ResponseBody
    public Result<User> deleteUserById(@PathVariable("id") Integer id){
        userService.removeById(id);
        return Result.success("删除用户成功");
    }
}
