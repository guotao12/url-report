package com.guo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 32688
 */

@Controller
public class ReportHtmlController {

    @RequestMapping(path = {"index", "/"})
    public String login() {
        return "forward:index.html";
    }

}
