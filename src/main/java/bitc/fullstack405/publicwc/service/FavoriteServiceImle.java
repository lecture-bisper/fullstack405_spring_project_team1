package bitc.fullstack405.publicwc.service;

import bitc.fullstack405.publicwc.entity.Favorite;
import bitc.fullstack405.publicwc.entity.Users;
import bitc.fullstack405.publicwc.entity.WcInfo;
import bitc.fullstack405.publicwc.repository.FavoriteRepository;
import bitc.fullstack405.publicwc.repository.UsersRepository;
import bitc.fullstack405.publicwc.repository.WcInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FavoriteServiceImle implements FavoriteService {

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private WcInfoRepository wcInfoRepository;

    @Autowired
    private FavoriteRepository favoriteRepository;
    private List<bitc.fullstack405.publicwc.entity.Favorite> Favorite;


    @Override
    public boolean removeFavorite(Users user, WcInfo wcInfo) {
        var result = favoriteRepository.findByUserAndWcInfo(user, wcInfo);

        // not null 이면 기존 즐겨찾기 정보 있음
        // 기존 정보 삭제
        // 컨트롤러로 정보 없을을 전달 (off 이미지)
        if (result != null) {
            favoriteRepository.delete(result);
            return false;
        }
        // null 이면 기존 즐겨찾기 정보 없음
        // 데이터 추가
        // 컨트롤러로 정보가 있음을 전달 (on 이미지)
        else {
            Favorite favorite = new Favorite();
            favorite.setFavoriteUsers(user);
            favorite.setFavoriteWc(wcInfo);

            favoriteRepository.save(favorite);
            return true;
        }
    }
    @Override
    public void updateFavorite(String userId, int wcId) {
    }

    @Override
    public List<Favorite> selectFavoriteList(Users user) {
        return favoriteRepository.findByUser(user);
    }

    @Override
    public boolean isFavorite(Users user, WcInfo wcInfo) {

        var favorite = favoriteRepository.findByUserAndWcInfo(user, wcInfo);
        if (favorite != null) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Favorite addFavorite(Users user, WcInfo wcInfo) {

        Favorite favorite = new Favorite();

        favorite.setFavoriteUsers(user);
        favorite.setFavoriteWc(wcInfo);

        Favorite result = favoriteRepository.save(favorite);

        return result;
    }

    @Override
    public Optional<Users> getUserById(String userId) {
        return usersRepository.findById(userId);
    }

    @Override
    public Optional<WcInfo> getWcInfoById(int wcId) {
        return wcInfoRepository.findById(wcId);
    }

}

//// userId로 사용자를 찾고, 없으면 예외 발생
//Users user = usersRepository.findById(userId)
//        .orElseThrow(() -> new RuntimeException("User not found"));
//
//// wcId로 화장실을 찾고, 없으면 예외 발생
//WcInfo wcInfo = wcInfoRepository.findById(wcId)
//        .orElseThrow(() -> new RuntimeException("WcInfo not found"));
//
//var newFavorite = new Favorite();
//        newFavorite.setFavoriteUsers(user);
//        newFavorite.setFavoriteWc(wcInfo);
//
//        favoriteRepository.save(newFavorite);