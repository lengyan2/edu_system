package com.example.canal.Runner;

import com.example.canal.Client.CanalClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class myCommandRunner implements CommandLineRunner {
    @Autowired
    CanalClient canalClient;
    @Override
    public void run(String... args) throws Exception {
        new Thread(() -> {
            canalClient.run();
            }
        ).start();
    }
}
