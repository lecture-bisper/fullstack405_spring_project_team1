package bitc.fullstack405.publicwc.service;

import bitc.fullstack405.publicwc.entity.Users;
import bitc.fullstack405.publicwc.entity.WcInfo;
import bitc.fullstack405.publicwc.repository.BestRepository;
import bitc.fullstack405.publicwc.repository.UsersRepository;
import bitc.fullstack405.publicwc.repository.WcInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ToiletServiceImpl implements ToiletService {

    @Autowired
    private WcInfoRepository wcInfoRepository;

    @Autowired
    private UsersRepository usersRepository;
    @Autowired
    private BestRepository bestRepository;

    @Override
    public List<WcInfo> getAllToilets() {
        return wcInfoRepository.findAll(); // 모든 화장실 조회
    }

    @Override
    public WcInfo addWcInfo(WcInfo wcInfo) {
        return wcInfoRepository.save(wcInfo); // 새 화장실 정보 추가
    }

    @Override
    public WcInfo updateWcInfo(int id, WcInfo wcInfo) {
        if (wcInfoRepository.existsById(id)) {
            wcInfo.setId(id); // ID를 설정하여 업데이트 수행
            return wcInfoRepository.save(wcInfo);
        }
        return null; // ID가 존재하지 않으면 null 반환
    }

    @Override
    public WcInfo findWcInfoById(int id) {
        return wcInfoRepository.findById(id).orElse(null); // ID로 화장실 정보 조회
    }

    @Override
    public boolean deleteWcInfo(int id) {
        if (wcInfoRepository.existsById(id)) {
            wcInfoRepository.deleteById(id); // ID로 화장실 정보 삭제
            return true;
        }
        return false; // ID가 존재하지 않으면 false 반환
    }

    @Override
    public List<WcInfo> parsingWc(String juso) {
        switch (juso) {
            case "해운대":
                return wcInfoRepository.pointWc("해운대");
            case "부전역":
                return wcInfoRepository.pointWc("부전역");
            default:
                return wcInfoRepository.pointWc("내위치%");
        }
    }

    @Override
    public void usePasskey(String userId) {
        Optional<Users> userOptional = usersRepository.findById(userId);
        if (userOptional.isPresent()) {
            Users user = userOptional.get();
            int currentPasskey = user.getPasskey();
            if (currentPasskey > 0) {
                user.setPasskey(currentPasskey - 1);
                usersRepository.save(user);
            }
        }
    }
}
