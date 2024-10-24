package bitc.fullstack405.publicwc.service;

import bitc.fullstack405.publicwc.entity.Users;
import bitc.fullstack405.publicwc.entity.WcInfo;
import bitc.fullstack405.publicwc.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

public interface UserService {

    void writeWc(WcInfo wcInfo);
    Optional<Users> findById(String userId); // ID로 사용자 조회
    void saveUser(Users user); // 사용자 저장
    boolean checkPassword(Users user, String rawPassword); // 비밀번호 확인
    void deleteUser(String userId); // 사용자 삭제

    boolean useKeyForLevel3(String userId);
//    void addFavorite(String userId, int wcId); // 즐겨찾기 추가
}
