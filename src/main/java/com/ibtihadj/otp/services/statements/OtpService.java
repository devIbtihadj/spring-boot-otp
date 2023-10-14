package com.ibtihadj.otp.services.statements;

public interface OtpService {
    boolean sendMessage(String telephone);

    boolean confirmOtp(String telephone, String otp);
}
