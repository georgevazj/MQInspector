package com.bbva.mmap;

import com.bbva.mmap.conf.ApplicationContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Created by jorge on 15/03/2016.
 */

@SpringBootApplication
public class Launcher {

    private static final Logger logger = LoggerFactory.getLogger(Launcher.class);

    public static void main(String[] args){
        SpringApplication.run(ApplicationContext.class,args);
    }
}