package bitc.fullstack405.publicwc.repository;

import bitc.fullstack405.publicwc.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface UsersRepository extends JpaRepository<Users, String> {

    @Query("SELECT count(u) FROM Users u WHERE u.id = :id AND u.password = :password")
    int correctLogin(@Param("id") String id, @Param("password") String password);

    @Query("SELECT count(u) FROM Users u WHERE u.id = :id")
    int loginIdCheck(@Param("id") String id);

    @Query("SELECT count(u) FROM Users u WHERE u.email = :email")
    int emailCheck(@Param("email") String email);

    @Modifying
    @Transactional
    @Query("UPDATE Users u SET u.password = :password WHERE u.id = :id")
    int updatePassword(@Param("id") String id, @Param("password") String password);

    @Modifying
    @Transactional
    @Query("UPDATE Users u SET u.email = :email WHERE u.id = :id")
    int updateEmail(@Param("id") String id, @Param("email") String email);

    @Modifying
    @Transactional
    @Query("DELETE FROM Users u WHERE u.id = :id")
    void deleteUsers(@Param("id") String id);

}
