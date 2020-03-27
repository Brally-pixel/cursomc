package com.jorgesoares.cursomc.services;

public interface AuthService {

    void sendNewPassword(String email);

    String newPassword();

    char randomChar();
}
