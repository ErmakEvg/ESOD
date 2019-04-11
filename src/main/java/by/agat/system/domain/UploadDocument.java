package by.agat.system.domain;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class UploadDocument {
    private MultipartFile[] file;
}
