package com.prj.controller;


import com.prj.common.lang.Result;
import com.prj.entity.User;
import com.prj.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author
 * @since 2020-08-14
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("/index")

    public Result index(){
        User user=userService.getById(1L);
        return Result.succ(user);
    }

    @PostMapping("/save")

    public Result save(@Validated @RequestBody User user) {
        return Result.succ(user);
    }
}
