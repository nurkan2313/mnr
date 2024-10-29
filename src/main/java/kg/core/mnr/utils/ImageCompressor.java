package kg.core.mnr.utils;

import net.coobird.thumbnailator.Thumbnails;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class ImageCompressor {

    public static byte[] compressImage(MultipartFile file, int width, int height, float quality) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        Thumbnails.of(file.getInputStream())
                .size(width, height) // Изменение размера
                .outputQuality(quality) // Сжатие (0.0 - сильное сжатие, 1.0 - без потери качества)
                .toOutputStream(outputStream);
        return outputStream.toByteArray();
    }
}
