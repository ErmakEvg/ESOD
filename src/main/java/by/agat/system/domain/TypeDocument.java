package by.agat.system.domain;

import lombok.Data;

import javax.persistence.*;
import java.util.Set;

@Data
@Entity
@Table(name = "TYPE_DOCUMENT")
public class TypeDocument {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private int type_id;

    @Column(name = "TYPE")
    private String type;

    @Override
    public String toString() {
        return "TypeDocument{" +
                "id=" + type_id +
                ", type='" + type + '\'' +
                '}';
    }

    @OneToMany(mappedBy="type")
    private Set<Document> documents;
}
