package by.agat.system.domain;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class DocumentDTO {
    private Long uuid;
    private String name;
    private int bank;
    private String path;
    private String status;
    private String typeDoc;

    @Override
    public String toString() {
        return "DocumentDTO{}";
    }
}
