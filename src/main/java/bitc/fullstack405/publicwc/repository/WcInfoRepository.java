package bitc.fullstack405.publicwc.repository;

import bitc.fullstack405.publicwc.entity.WcInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface WcInfoRepository extends JpaRepository<WcInfo, Integer> {

    // 주소를 포함하는 모든 화장실 리스트 조회
    @Query("SELECT wi FROM WcInfo wi WHERE wi.addr1 LIKE %:addr1%")
    List<WcInfo> findByAddressContaining(@Param("addr1") String addr1);

    // 특정 등급의 모든 화장실 리스트 조회
    @Query("SELECT wi FROM WcInfo wi WHERE wi.level = :level")
    List<WcInfo> findByLevel(@Param("level") int level);

    // 등급이 1인 모든 화장실 리스트 조회
    @Query("SELECT wi FROM WcInfo wi WHERE wi.level = 1")
    List<WcInfo> findAllLevel1();

    // 등급이 2인 모든 화장실 리스트 조회
    @Query("SELECT wi FROM WcInfo wi WHERE wi.level = 2")
    List<WcInfo> findAllLevel2();

    // 등급이 3인 모든 화장실 리스트 조회
    @Query("SELECT wi FROM WcInfo wi WHERE wi.level = 3")
    List<WcInfo> findAllLevel3();

    // 포인트가 '내위치'인 화장실 리스트 조회
    @Query(value = "SELECT * FROM wcinfo WHERE point LIKE :juso ORDER BY CAST(SUBSTRING(point, 4) AS UNSIGNED)", nativeQuery = true)
    List<WcInfo> pointWc(String juso);

//    회원이 찜한 즐겨찾기 리스트 조회
    @Query("SELECT wi FROM WcInfo wi")
    List<WcInfo> favoriteWcLists();

}
