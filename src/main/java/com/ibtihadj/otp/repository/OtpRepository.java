package com.ibtihadj.otp.repository;


import com.ibtihadj.otp.models.Otp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface OtpRepository extends JpaRepository<Otp, Long> {
    @Query("select o FROM  Otp o where o.telephone=:X and o.code=:Y")
    Optional<Otp> findByTelephoneAndCode(@Param("X") String telephone, @Param("Y") String code);
}
