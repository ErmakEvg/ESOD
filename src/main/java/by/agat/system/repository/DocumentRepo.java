package by.agat.system.repository;

import by.agat.system.domain.Document;
import by.agat.system.domain.Status;
import by.agat.system.domain.TypeDocument;
import by.agat.system.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.print.Doc;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class DocumentRepo {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<Document> getDocumentsByCurrentDate(int userId) {
        List<Document> documents = new ArrayList<>();
        String query = "SELECT  d.*, td.*  FROM DOCUMENT d inner join TYPE_DOCUMENT TD on d.TYPE_ID = TD.ID where d.USER_ID = " + userId +" AND TO_DATE(d.DATE_UPLOAD, 'yyyy-MM-dd')=TO_DATE(CURRENT_DATE, 'yyyy-MM-dd')";
        documents = jdbcTemplate.query(query, (resultSet, i) -> {
            Document document = new Document();
            document.setName(resultSet.getString("NAME"));
            document.setUuid(resultSet.getLong("UUID"));
            document.setDateUpload(resultSet.getDate("DATE_UPLOAD"));
            document.setBank(resultSet.getInt("BANK"));
            User user = new User();
            user.setUser_id(resultSet.getInt("USER_ID"));
            document.setUser(user);
            TypeDocument typeDocument = new TypeDocument();
            typeDocument.setType_id(resultSet.getInt("TYPE_ID"));
            typeDocument.setType(resultSet.getString("TYPE"));
            document.setType(typeDocument);
            document.setPath(resultSet.getString("PATH"));
            return document;
        });

        return documents;
    }
}
