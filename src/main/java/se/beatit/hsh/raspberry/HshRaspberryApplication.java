package se.beatit.hsh.raspberry;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;


/**
 *
 * @author Stefan Nilsson
 */
@SpringBootApplication
@ComponentScan()
public class HshRaspberryApplication extends SpringBootServletInitializer {

    public static void main(String args[]) {
        ApplicationContext ctx = SpringApplication.run(HshRaspberryApplication.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(HshRaspberryApplication.class);
    }
}
