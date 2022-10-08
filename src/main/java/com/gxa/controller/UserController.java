package com.gxa.controller;

import com.gxa.entity.User;
import com.gxa.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/user/login")
    public String login(User user, HttpSession session){
        System.out.println(user);
        //1.获取用户登录的subject
        Subject subject = SecurityUtils.getSubject();
        //2.调用shiro的登录方法进行页面的登录,此登陆需要传递token
            //2.1将来自前端传递的登录信息转化为token
        UsernamePasswordToken upt = new UsernamePasswordToken(user.getUserName(),user.getPwd());
        try {
            //由于realm是通过抛出异常的方式来确定用户是否登录成功
            subject.login(upt);
            //且由于使用了session,则user对象不再需要往session中放置
            session.setAttribute("user",user);
            System.out.println("to main");
            return "redirect:/main.html";

        }catch (Exception e){
            e.printStackTrace();
            System.out.println("to index");
            return "redirect:/index.html";
        }
//       User u  = this.userService.login(user.getUserName(), user.getPwd());
//        if(u != null){
//            //登录成功 ，将用户信息放在session中
//            session.setAttribute("user",u);
//            return "redirect:/main.html";
//        }else{
//            return "redirect:/index.html";
//        }

    }


    @GetMapping("/user/logout")
    public String logout(HttpSession session){
        session.invalidate();
        return "redirect:/index.html";
    }
}
