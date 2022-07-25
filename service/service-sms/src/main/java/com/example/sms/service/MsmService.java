package com.example.sms.service;

public interface MsmService {
    boolean send(String phone, String code);
}
