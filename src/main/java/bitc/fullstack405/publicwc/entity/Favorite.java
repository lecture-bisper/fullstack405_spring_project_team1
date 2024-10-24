package bitc.fullstack405.publicwc.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "favorite")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Favorite {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "userId")
    @ToString.Exclude
    private Users favoriteUsers;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "wcId")
    @ToString.Exclude
    private WcInfo favoriteWc;

}