package com.example.canal;


import com.example.canal.Client.CanalClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.Resource;

@SpringBootApplication
public class CanalApplication  {

    public static void main(String[] args) {
        SpringApplication.run(CanalApplication.class,args);
    }


}
