package net.leludo.gtrchamp.jersey;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;

@SpringBootApplication
public class GtrChampJerseyApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        new GtrChampJerseyApplication()
                .configure(new SpringApplicationBuilder(GtrChampJerseyApplication.class)).run(args);

    }

}
