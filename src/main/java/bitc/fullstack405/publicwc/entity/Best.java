package bitc.fullstack405.publicwc.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "best")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Best {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = true)
    private int good;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "userId")
    @ToString.Exclude
    private Users bestUsers;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "wcId")
    @ToString.Exclude
    private WcInfo bestWc;
}