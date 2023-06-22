package com.tian.controller;

import com.tian.pojo.User;
import com.tian.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;

@Controller
public class UserController {
    @Autowired
    UserService userService;

    @GetMapping(value = {"/", "login"})
    public String loginPage() {
        return "login";
    }

    @PostMapping("login2")
    public String loginSuccess(User user, HttpSession session, Model model) {
        try {
            // 先查找一下有没有该账号
            User userName = userService.findUserName(user.getUsername());
            if (userName != null) {
                // 如果有账号则判断账号密码是否正确
                String password = userService.findPassword(user.getUsername());
                if (password.equals(user.getPassword())) {
                    // 添加到session保存起来
                    session.setAttribute("loginUser", user);
                    return "redirect:/success.html";
                } else {
                    // 如果密码错误，则提示输入有误
                    model.addAttribute("msg", "账号或者密码有误");
                    return "login";
                }
            } else {
                model.addAttribute("msg", "账号或者密码有误");
                return "login";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return e.getMessage();
        }
    }

    @GetMapping("success.html")
    public String successPage(HttpSession session, Model model) {
        Object loginUser = session.getAttribute("loginUser");
        if (loginUser != null) {
            return "success";
        } else {
            model.addAttribute("msg", "请登录");
            return "login";
        }
    }

    // 注册用户
    @PostMapping("register")
    public String RegisterUser(User user, Model model) {
        try {
            User userName = userService.findUserName(user.getUsername());
            // 没有用户可以进行注册
            if (userName == null) {
                if (user.getPassword().equals("") || user.getPassword1().equals("") || user.getUsername().equals("") || user.getEmail().equals("")) { //错误一:输入框有空值
                    model.addAttribute("tip", "请填写好信息");
                    return "login";
                } else if (!user.getPassword().equals(user.getPassword1())) {
                    // 错误二:输入两次密码前后不一致
                    model.addAttribute("tip", "密码前后不一致");
                    return "login";
                } else {
                    int i = userService.addUser(user);
                    if (i > 0) {
                        model.addAttribute("tip", "注册成功");
                    }
                    return "login";
                }
            } else {
                model.addAttribute("tip", "用户已存在,请登录");
                return "login";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return e.getMessage();
        }
    }
}
