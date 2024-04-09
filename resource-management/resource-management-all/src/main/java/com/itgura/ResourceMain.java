package com.itgura;
import com.itgura.util.BootMain;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ResourceMain {
    public static void main(String[] args) {

        BootMain.main(args);
        SpringApplication.run(ResourceMain.class);
    }
}