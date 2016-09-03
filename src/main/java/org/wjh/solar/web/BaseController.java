package org.wjh.solar.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.wjh.solar.service.UserService;

public class BaseController {
    @Autowired
    protected UserService userService;
}
