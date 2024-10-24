package bitc.fullstack405.publicwc.controller;

import bitc.fullstack405.publicwc.entity.Best;
import bitc.fullstack405.publicwc.entity.Favorite;
import bitc.fullstack405.publicwc.repository.BestRepository;
import bitc.fullstack405.publicwc.service.BestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/best")
public class WcBestController {

    @Autowired
    BestService bestService;
    @Autowired
    private BestRepository bestRepository;

    @ResponseBody
    @GetMapping("/getCountList")
    public Object getCount(@RequestParam("wcIdList") List<Integer> wcIdList) throws Exception {

        List<Map<String, Integer>> likeCountList = bestService.getLikeCountList(wcIdList);
        List<Map<String, Integer>> hateCountList = bestService.getHateCountList(wcIdList);

        Map<String, List<Map<String, Integer>>> map = new HashMap<>();
        map.put("likeList", likeCountList);
        map.put("hateList", hateCountList);

        return map;
    }


    @ResponseBody
    @PostMapping("/countChange")
    public Integer countChange(@RequestParam("userId") String userId, @RequestParam("wcId") int wcId, boolean kind) {

        Best best = bestService.findByUserAndWcInfo(userId, wcId);
        var result = 0;

        // 상태 none
        if (best == null) {
            if (kind) {
                bestService.likeCountUp(userId, wcId);
                result = 1;
            } else {
                bestService.hateCountUp(userId, wcId);
                result = 2;
            }
        } else {
            // 상태 good
            if (best.getGood() == 1) {
                if (kind) {
                    bestService.delete(best);
                    result = 3;
                } else {
                    bestService.updateToBad(best);
                    result = 4;
                }

                // 상태 bad
            } else {
                if (kind) {
                    bestService.updateToGood(best);
                    result = 5;
                } else {
                    bestService.delete(best);
                    result = 6;
                }
            }
        }

        return result;

////        카운트 업
//        if (kind.equals("like")) {
//            bestService.likeCountUp(userId, wcId);
//        } else {
//            bestService.hateCountUp(userId, wcId);
//        }
//
////        현재 카운트 수 가져오기
//        int likeCount = bestService.getLikeCount(wcId);
//        int hateCount = bestService.getHateCount(wcId);

//        Map<String, Integer> map = new HashMap<>();
//        map.put("likeCount", likeCount);
//        map.put("hateCount", hateCount);
//        가져온 카운트 수 클라이언트로 반환
//        return map;
    }


}
