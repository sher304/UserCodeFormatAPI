package org.example.codeformater;

import com.google.googlejavaformat.java.JavaFormatterOptions;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import com.google.googlejavaformat.java.Formatter;

@SpringBootApplication
public class CodeFormaterApplication {

    public static void main(String[] args) {
        SpringApplication.run(CodeFormaterApplication.class, args);
    }

    @Bean
    public Formatter formatter() {
        JavaFormatterOptions options = JavaFormatterOptions.builder()
                .style(JavaFormatterOptions.Style.GOOGLE).build();
        Formatter formatter = new Formatter(options);
        return formatter;
    }
}
