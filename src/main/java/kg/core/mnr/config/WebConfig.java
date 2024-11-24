package kg.core.mnr.config;

import kg.core.mnr.utils.StringToLocalDateTimeConverter;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new StringToLocalDateTimeConverter());
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // Настраиваем обработку запросов к файлам
        registry.addResourceHandler("/uploads/files/**") // URL, по которому доступны файлы
                .addResourceLocations("file:uploads/files/"); // Абсолютный путь к директории
    }
}
