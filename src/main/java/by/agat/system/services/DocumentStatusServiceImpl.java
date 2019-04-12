package by.agat.system.services;

import by.agat.system.domain.Document;
import by.agat.system.domain.DocumentStatus;
import by.agat.system.repository.DocumentStatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DocumentStatusServiceImpl implements DocumentStatusService {

    @Autowired
    private DocumentStatusRepository documentStatusRepository;

    @Override
    public void save(DocumentStatus documentStatus) {
        documentStatusRepository.save(documentStatus);
    }
}
