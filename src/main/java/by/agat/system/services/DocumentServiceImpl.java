package by.agat.system.services;

import by.agat.system.domain.Document;
import by.agat.system.domain.User;
import by.agat.system.repository.DocumentRepo;
import by.agat.system.repository.DocumentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DocumentServiceImpl implements DocumentService{

    @Autowired
    private DocumentRepository documentRepository;

    @Autowired
    private DocumentRepo documentRepo;

    @Override
    public List<Document> getDocumentsByBankAndStatus(int bank, int status) {
        List<Document> documents = documentRepository.getDocumentsByBank(bank).stream().filter(document -> document.getStatuses().get(document.getStatuses().size() - 1).getStatus().getId() == status).sorted(Comparator.comparing(Document::getDateUpload)).collect(Collectors.toList());
        return documents;

    }

    @Override
    public void save(Document document) {
        documentRepository.save(document);
    }

    @Override
    public List<Document> getDocumentsByUserAndCurrDate(int userId) {
        return documentRepo.getDocumentsByCurrentDate(userId);
    }

    @Override
    public Document getDocumentByUUID(Long uuid) {
        return documentRepository.getDocumentByUuid(uuid);
    }


}
