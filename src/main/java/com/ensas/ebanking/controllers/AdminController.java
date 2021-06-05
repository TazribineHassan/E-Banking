package com.ensas.ebanking.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminController {
    @GetMapping("/indexx")
    public String afficher(){

        return "Views/test";

    }
    @GetMapping("/indexx/test")
    public String afficher2(){

        return "Views/hassan";

    }

}