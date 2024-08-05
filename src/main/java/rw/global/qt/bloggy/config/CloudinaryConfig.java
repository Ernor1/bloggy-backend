package rw.global.qt.bloggy.config;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CloudinaryConfig {

    @Bean
    public Cloudinary cloudinary() {
        return new Cloudinary(ObjectUtils.asMap(
                "cloud_name", "stractora",
                "api_key", "475586369571686",
                "api_secret", "9voyRoxmtGuJ4U26b0C5Yfg2dAI"));
    }
}
