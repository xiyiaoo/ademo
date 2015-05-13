/**
 * Copyright © Foresee Science & Technology Ltd. 
 */
package com.xiyiaoo.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

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
    public String login(){
        return "login";
    }
}
