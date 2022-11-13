package com.springbootvaadin.application;

import com.vaadin.flow.server.auth.AnonymousAllowed;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RedirectController {

    @GetMapping("/logged-out")
    public String logged_out() {
//        model.addAttribute("pass", 50);
//        model.addAttribute("fail", 50);
        return "logged-out";
    }

    @GetMapping("/session-expired")
    public String session_expired() {
//        model.addAttribute("pass", 50);
//        model.addAttribute("fail", 50);
        return "session-expired";
    }

    @AnonymousAllowed
    @GetMapping("/external")
    public String external(){
        return "external";
    }

    @AnonymousAllowed
    @GetMapping("/wallet-card")
    public String wallet_card(){
        return "wallet-card";
    }

    @AnonymousAllowed
    @GetMapping("/bootstrapEg")
    public String bootstrapEg(){
        return "bootstrapEg";
    }

}
