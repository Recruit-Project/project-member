package com.kyj.fmk.common.controller;

import com.kyj.fmk.common.model.req.ReqCommonCdDTO;
import com.kyj.fmk.common.model.res.ResCommonCdDTO;
import com.kyj.fmk.common.service.CommonCdService;
import com.kyj.fmk.core.model.dto.ResApiDTO;
import com.kyj.fmk.sec.annotation.PublicEndpoint;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 2025-08-10
 * @author 김용준
 * 공통 내용에 대한 컨트롤러
 */
@RestController
@RequestMapping("/api/v1/member/cm")
@RequiredArgsConstructor
@Slf4j
public class CommonController {

    private final CommonCdService commonCdService;

    /**
     * 공통코드를 조회하는 컨트롤러
     * CM_CD(팀스타일,회의스타일 등 )
     * CM_CD_VAL(친화적인, 협력적인 등 실질적인 코드값)
     * 에 따라 null이면 전체를 조회하여 코드값과 코드값의명 을 리턴해주고,  조건이 있으면 그에 해당하는 리스트를 코드값과 코드값의 명을 json으로 리턴해준다.
     * @param reqCommonCdDTO
     * @return
     */
    @GetMapping("cmCdList")
    @PublicEndpoint
    public ResponseEntity<ResApiDTO<List<ResCommonCdDTO>>> selectCmCdList(ReqCommonCdDTO reqCommonCdDTO){
        log.info("tes={}",reqCommonCdDTO.getCmCd());
        return commonCdService.selectCmCdList(reqCommonCdDTO);
    }

}
