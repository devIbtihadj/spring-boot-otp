package com.ibtihadj.otp.services.implementations;

import com.ibtihadj.otp.constants.OtpConstants;
import com.ibtihadj.otp.models.Otp;
import com.ibtihadj.otp.repository.OtpRepository;
import com.ibtihadj.otp.services.statements.OtpService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Calendar;
import java.util.Date;
import java.util.Optional;

@Service
@Transactional
public class OtpServiceImpl implements OtpService {

    private final OtpRepository otpRepository;

    @Autowired
    public OtpServiceImpl(OtpRepository otpRepository) {
        this.otpRepository = otpRepository;
    }


    @Override
    public boolean sendMessage(String telephone) {
        Date currentDate = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(currentDate);
        calendar.add(Calendar.SECOND, OtpConstants.OTP_LIFE_TIME_IN_SECONDS);
        Date newDate = calendar.getTime();
        String generatedOtp = generateRandomChar(4);
        Otp otp = new Otp(null, telephone, generatedOtp, newDate, Boolean.FALSE, Boolean.FALSE);
        Otp saved = otpRepository.save(otp);
        if(callAPIMessage(telephone, generatedOtp)){
            saved.setSent(Boolean.TRUE);
            otpRepository.save(saved);
            return true;
        }else {
            return false;
        }
    }

    @Override
    public boolean confirmOtp(String telephone, String otp) {
        Optional<Otp> otpSaved = otpRepository.findByTelephoneAndCode(telephone, otp);
        if(otpSaved.isPresent()){
            if(otpSaved.get().getDateExpiraion().getTime()>new Date().getTime()){
               if (otpSaved.get().isConfirmed()){
                   throw new RuntimeException("Otp déjà confirmé");
               }else {
                   otpSaved.get().setConfirmed(Boolean.TRUE);
                   otpRepository.save(otpSaved.get());
               }
                return true;
            }else {
                throw new RuntimeException("Otp expiré");
            }
        }else {
            throw new RuntimeException("Otp Inexisant");
        }
    }

    boolean callAPIMessage(String telephone, String code) {
        return true;
    }

    public static String generateRandomChar(int length) {
        String characters = "0123456789"; // Caractères autorisés pour l'OTP
        SecureRandom random = new SecureRandom();
        StringBuilder otp = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int randomIndex = random.nextInt(characters.length());
            char randomChar = characters.charAt(randomIndex);
            otp.append(randomChar);
        }
        return otp.toString();
    }

}
