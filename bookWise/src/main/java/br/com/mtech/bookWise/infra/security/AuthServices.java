package br.com.mtech.bookWise.infra.security;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServices {

    public String encodePassword(String password){
        return new BCryptPasswordEncoder().encode(password);
    }
}
