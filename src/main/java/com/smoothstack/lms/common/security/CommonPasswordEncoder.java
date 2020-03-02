package com.smoothstack.lms.common.security;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class CommonPasswordEncoder extends BCryptPasswordEncoder implements PasswordEncoder {
    // We can implement our own Password Encoder here, but BCryot is good enough.

}
