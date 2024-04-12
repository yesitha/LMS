package com.itgura;

import com.itgura.util.BootMain;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;


@SpringBootApplication(exclude={DataSourceAutoConfiguration.class, HibernateJpaAutoConfiguration.class})
public class DmsMediatorMain {

    public static void main(String[] args) {
        BootMain.main(args);
        SpringApplication.run(DmsMediatorMain.class, args);
    }

}
