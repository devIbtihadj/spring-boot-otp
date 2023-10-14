package com.ibtihadj.otp.controllers;

import com.ibtihadj.otp.services.statements.OtpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/otp")
@CrossOrigin(origins = "*")
public class OtpController {
    private final String ENTITY_PATH = "otp";

    private final OtpService otpService;

    @Autowired
    public OtpController(OtpService otpService) {
        this.otpService = otpService;
    }


    @GetMapping(value = ENTITY_PATH+"/send/{num}")
    public boolean send(@PathVariable("num") String telephone){
        return otpService.sendMessage(telephone);
    }


    @GetMapping(value = ENTITY_PATH+"/send/{num}/{otp}")
    public boolean confirmOtp(@PathVariable("num") String telephone, @PathVariable("otp") String otp){
        return otpService.confirmOtp(telephone, otp);
    }



}
