package ua.com.lsd25.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

/**
 * @author vzagnitko
 */
@Configuration
public class UploadConfig {

    private static final int ONE_MB = 1024 * 1024;

    @Value("${max.file.size}")
    private Integer maxFileSize;

    @Bean
    public CommonsMultipartResolver fileUpload() {
        CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver();
        commonsMultipartResolver.setMaxUploadSize(maxFileSize * ONE_MB);
        return commonsMultipartResolver;
    }

}
