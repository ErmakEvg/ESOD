package by.agat.system.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

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

    @JsonIgnore
    @OneToMany(mappedBy="type", fetch = FetchType.LAZY)
    private List<Document> documents;

    @Override
    public String toString() {
        return "TypeDocument{}";
    }
}
