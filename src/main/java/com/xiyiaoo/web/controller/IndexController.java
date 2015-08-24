/**
 * Copyright © Foresee Science & Technology Ltd. 
 */
package com.xiyiaoo.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * User: xiyiaoo@gmail.com
 * Date: 15-4-28 下午4:20
 */
@Controller
public class IndexController {
    @RequestMapping("/")
    public String index(){
        return "index";
    }

    @RequestMapping("/login")
    public String login(HttpServletRequest request){
        if (request.getAttribute("shiroLoginFailure") != null) {
            request.setAttribute("hasError", true);
        }
        return "login";
    }

    @RequestMapping("/template/{dir}/{name}")
    public String template(@PathVariable("name") String name, @PathVariable("dir") String dir){
        return "template/" + dir + "/" + name;
    }
}
