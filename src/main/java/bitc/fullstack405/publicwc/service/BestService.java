package bitc.fullstack405.publicwc.service;

import bitc.fullstack405.publicwc.entity.Best;
import bitc.fullstack405.publicwc.entity.Favorite;
import bitc.fullstack405.publicwc.entity.WcInfo;
import bitc.fullstack405.publicwc.repository.BestRepository;
import bitc.fullstack405.publicwc.repository.UsersRepository;
import bitc.fullstack405.publicwc.repository.WcInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class BestService {

    @Autowired
    private BestRepository bestRepository;

    @Autowired
    private WcInfoRepository wcInfoRepository;

    @Autowired
    private UsersRepository usersRepository;

    public int getLikeCount(int wcId) {
        return bestRepository.getLikeCount(wcId);
    }

    public int getHateCount(int wcId) {
        return bestRepository.getHateCount(wcId);
    }

    public void likeCountUp(String userId, int wcId) {
        Best best = new Best();
        best.setBestUsers(usersRepository.findById(userId).get());
        best.setBestWc(wcInfoRepository.findById(wcId).get());
        best.setGood(1);

        bestRepository.save(best);
    }

    public void hateCountUp(String userId, int wcId) {
        Best best = new Best();
        best.setBestUsers(usersRepository.findById(userId).get());
        best.setBestWc(wcInfoRepository.findById(wcId).get());
        best.setGood(0);

        bestRepository.save(best);
    }

    public void updateToBad(Best best) {
        best.setGood(0);
        bestRepository.save(best);
    }

    public void updateToGood(Best best) {
        best.setGood(1);
        bestRepository.save(best);
    }

    public List<Map<String, Integer>> getLikeCountList(List<Integer> wcIdList) throws Exception {
        List<Map<String, Integer>> likeList = bestRepository.getLikeCountList(wcIdList);


        return likeList;
    }

    public List<Map<String, Integer>> getHateCountList(List<Integer> wcIdList) throws Exception{
        List<Map<String, Integer>> hateList = bestRepository.getHateCountList(wcIdList);

        return hateList;
    }

    public Best findByUserAndWcInfo(String userId, int wcInfo) {
        return bestRepository.findByUserAndWcInfo(userId, wcInfo);
    }

    public void delete(Best best){
        bestRepository.delete(best);
    }


}
