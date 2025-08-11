package com.kyj.fmk.common.service;


import com.kyj.fmk.core.model.cmcd.req.ReqCommonCdDTO;
import com.kyj.fmk.core.model.cmcd.res.ResCommonCdDTO;
import com.kyj.fmk.core.model.dto.ResApiDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;

/**
 * 2025-08-10
 * @author 김용준
 * 공통코드, 기술스택코드 , 직무코드에 관하여 셀렉트하고 , 쓰기작업을 수행하는 서비스
 */
public interface CommonCdService {

    public ResponseEntity<ResApiDTO<List<ResCommonCdDTO>>> selectCmCdList(ReqCommonCdDTO reqCommonCdDTO);
}
