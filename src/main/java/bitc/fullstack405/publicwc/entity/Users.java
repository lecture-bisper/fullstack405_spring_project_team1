package bitc.fullstack405.publicwc.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
public class Users {

    @Id
    private String id;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String gender;

    @Column(nullable = false)
    @ColumnDefault("false")
    private String handicap;

    @Column(nullable = false)
    @ColumnDefault("5")
    private int passkey = 5;

    @JsonManagedReference
    @OneToMany(mappedBy = "favoriteUsers", cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<Favorite> userFavoriteList = new ArrayList<>();

    @JsonManagedReference
    @OneToMany(mappedBy = "bestUsers", cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<Best> userBestList = new ArrayList<>();
}
