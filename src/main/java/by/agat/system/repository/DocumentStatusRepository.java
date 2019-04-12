package by.agat.system.repository;

import by.agat.system.domain.DocumentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DocumentStatusRepository extends JpaRepository<DocumentStatus, Integer> {

}
