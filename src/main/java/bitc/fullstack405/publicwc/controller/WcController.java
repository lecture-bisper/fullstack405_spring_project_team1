package bitc.fullstack405.publicwc.controller;

import bitc.fullstack405.publicwc.entity.WcInfo;
import bitc.fullstack405.publicwc.service.ToiletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/wcs")
public class WcController {

    @Autowired
    private ToiletService toiletService;

    @PostMapping
    public ResponseEntity<WcInfo> addWc(@RequestBody WcInfo wcInfo) {
        WcInfo createdWc = toiletService.addWcInfo(wcInfo);
        return new ResponseEntity<>(createdWc, HttpStatus.CREATED);
    }

    // 나머지 메서드들도 필요한 경우 추가하실 수 있습니다.
}


