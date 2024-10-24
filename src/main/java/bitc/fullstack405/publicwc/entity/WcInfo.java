package bitc.fullstack405.publicwc.entity;

import bitc.fullstack405.publicwc.entity.Best;
import bitc.fullstack405.publicwc.entity.Favorite;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.ColumnDefault;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "wcinfo")
@Data
@NoArgsConstructor
public class WcInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id; // 번호

    @Column(nullable = true)
    @ColumnDefault("1")
    private int level; // 화장실 등급

    @Column(nullable = false)
    private String addr1; // 도로명 주소

    private String addr2; // 지번 주소

    @Column(nullable = false)
    private String detailAddr; // 기본값 설정

    private String time; // 개방 시간

    private String comment; // 상세글

    @Column(nullable = false)
    private String latitude; // 위도

    @Column(nullable = false)
    private String longitude;  // 경도

    private String wcpass;

    private String point;

    @Column(nullable = false)
    private String createUserId;

    @JsonManagedReference
    @OneToMany(mappedBy = "favoriteWc")
    @ToString.Exclude
    private List<Favorite> wcFavoriteList = new ArrayList<>();

    @JsonManagedReference
    @OneToMany(mappedBy = "bestWc", cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<Best> wcBestList = new ArrayList<>();

}
