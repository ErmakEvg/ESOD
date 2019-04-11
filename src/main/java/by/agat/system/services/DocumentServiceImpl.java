package by.agat.system.services;

import by.agat.system.domain.Document;
import by.agat.system.repository.DocumentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DocumentServiceImpl implements DocumentService{

    @Autowired
    private DocumentRepository documentRepository;

    @Override
    public void save(Document document) {
        documentRepository.save(document);
    }
}
