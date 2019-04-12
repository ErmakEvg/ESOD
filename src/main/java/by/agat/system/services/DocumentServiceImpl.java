package by.agat.system.services;

import by.agat.system.domain.Document;
import by.agat.system.domain.User;
import by.agat.system.repository.DocumentRepo;
import by.agat.system.repository.DocumentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class DocumentServiceImpl implements DocumentService{

    @Autowired
    private DocumentRepository documentRepository;

    @Autowired
    private DocumentRepo documentRepo;

    @Override
    public void save(Document document) {
        documentRepository.save(document);
    }

    @Override
    public List<Document> getDocumentsByUserAndCurrDate(int userId) {
        return documentRepo.getDocumentsByCurrentDate(userId);
    }


}
