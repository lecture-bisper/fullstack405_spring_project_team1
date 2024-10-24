package bitc.fullstack405.publicwc.repository;


import bitc.fullstack405.publicwc.entity.Best;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Map;

public interface BestRepository extends JpaRepository<Best, Integer> {

    @Query("select count(*) as cnt from Best b where b.bestWc.id = :wcId and b.good = 1")
    int getLikeCount(int wcId);

    @Query("select count(*) as cnt from Best b where b.bestWc.id = :wcId and b.good = 0")
    int getHateCount(int wcId);

    @Query("select count(*) as cnt, b.bestWc.id as wcId from Best b where b.bestWc.id in (:wcIdList) and b.good = 1 group by b.bestWc.id")
    List<Map<String, Integer>> getLikeCountList(List<Integer> wcIdList);

    @Query("select count(*) as cnt, b.bestWc.id as wcId from Best b where b.bestWc.id in (:wcIdList) and b.good = 0 group by b.bestWc.id")
    List<Map<String, Integer>> getHateCountList(List<Integer> wcIdList);

    @Query("SELECT b FROM Best b WHERE b.bestUsers.id = :userId AND b.bestWc.id = :wcInfoId")
    Best findByUserAndWcInfo(@Param("userId") String userId, @Param("wcInfoId") int wcInfoId);
}
