package by.agat.system.services;

import by.agat.system.domain.Document;
import by.agat.system.domain.User;

import java.util.Date;
import java.util.List;

public interface DocumentService {

    List<Document> getDocumentsByBankAndStatus(int bank, int status);
    void save(Document document);
    List<Document> getDocumentsByUserAndCurrDate(int userId);
    Document getDocumentByUUID(Long uuid);
}
