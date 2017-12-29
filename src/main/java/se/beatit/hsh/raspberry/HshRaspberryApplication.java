package se.beatit.hsh.raspberry;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;


/**
 *
 * @author Stefan Nilsson
 */
@SpringBootApplication
@ComponentScan()
public class HshRaspberryApplication  {

    public static void main(String args[]) {
        ApplicationContext ctx = SpringApplication.run(HshRaspberryApplication.class, args);
    }
}
