package by.agat.system.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import javax.print.Doc;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "DOCUMENT")
public class Document {

    @Id
    @Column(name = "UUID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long uuid;

    @Column(name = "BANK")
    private int bank;

    @Column(name = "NAME")
    private String name;

    @Column(name = "DATE_UPLOAD")
    private Date dateUpload;

    @Column(name = "PATH")
    private String path;

    @JsonIgnore
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="USER_ID", nullable=false)
    private User user;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="TYPE_ID", nullable=false)
    private TypeDocument type;

    @OneToMany(mappedBy = "document", fetch = FetchType.LAZY)
    private List<DocumentStatus> statuses;

    @Override
    public String toString() {
        return "Document{}";
    }

    public List<DocumentStatus> getStatuses() {
        statuses.sort(Comparator.comparing(DocumentStatus::getDateChange));
        return statuses;
    }
}
