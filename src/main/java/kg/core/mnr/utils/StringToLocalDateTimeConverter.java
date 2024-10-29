package kg.core.mnr.utils;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class StringToLocalDateTimeConverter implements Converter<String, LocalDateTime> {

    @Override
    public LocalDateTime convert(String source) {
        // Попробуем преобразовать строку с форматом "yyyy-MM-dd"
        try {
            return LocalDateTime.parse(source, DateTimeFormatter.ISO_LOCAL_DATE);
        } catch (Exception e) {
            // Если не удается преобразовать, возвращаем null или обрабатываем ошибку
            throw new IllegalArgumentException("Неверный формат даты");
        }
    }
}
