package bitc.fullstack405.publicwc.service;

import bitc.fullstack405.publicwc.entity.WcInfo;
import java.util.List;

public interface ToiletService {
    List<WcInfo> getAllToilets();
    WcInfo addWcInfo(WcInfo wcInfo);
    WcInfo updateWcInfo(int id, WcInfo wcInfo);
    WcInfo findWcInfoById(int id);
    boolean deleteWcInfo(int id);
    List<WcInfo> parsingWc(String juso);
    void usePasskey(String userId); // 사용자 passkey 감소 메서드
}