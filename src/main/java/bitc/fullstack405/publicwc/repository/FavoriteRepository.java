package bitc.fullstack405.publicwc.repository;

import bitc.fullstack405.publicwc.entity.Favorite;
import bitc.fullstack405.publicwc.entity.Users;
import bitc.fullstack405.publicwc.entity.WcInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FavoriteRepository extends JpaRepository<Favorite, Integer> {

//    @Query("SELECT fa FROM Favorite fa WHERE fa.favoriteUsers == :user")
//    Favorite findByUser(@Param("user")Users users);


    @Query("SELECT fa FROM Favorite fa WHERE fa.favoriteUsers = :user")
    List<Favorite> findByUser(@Param("user") Users user);

    @Query("SELECT fa FROM Favorite fa WHERE fa.favoriteUsers = :user AND fa.favoriteWc = :wcInfo")
    Favorite findByUserAndWcInfo(Users user, WcInfo wcInfo);
}
