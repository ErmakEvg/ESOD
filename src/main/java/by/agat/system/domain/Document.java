package by.agat.system.domain;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

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

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="USER_ID", nullable=false)
    private User user;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="TYPE_ID", nullable=false)
    private TypeDocument type;

    @Override
    public String toString() {
        return "Document{" +
                "uuid=" + uuid +
                ", bank=" + bank +
                ", name='" + name + '\'' +
                ", dateUpload=" + dateUpload +
                ", path='" + path + '\'' +
                ", user=" + user +
                ", type=" + type +
                '}';
    }
}
