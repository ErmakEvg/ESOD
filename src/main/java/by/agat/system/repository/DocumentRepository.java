package by.agat.system.repository;

import by.agat.system.domain.Document;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface DocumentRepository extends JpaRepository<Document, Integer> {

    List<Document> getDocumentsByBank(int bank);
    Document getDocumentByUuid(Long uuid);
}
