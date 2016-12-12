package com.biometric.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HomepPageController {

	private static final Logger log = LoggerFactory.getLogger(HomepPageController.class);

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView firstPage(Model model) {
        log.info("Main URL");
        return new ModelAndView("index");
    }
}
