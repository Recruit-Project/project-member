package com.kyj.fmk.common.repository;

import com.kyj.fmk.common.mapper.CommonMapper;
import com.kyj.fmk.common.model.req.ReqCommonCdDTO;
import com.kyj.fmk.common.model.req.ReqDtyCdDTO;
import com.kyj.fmk.common.model.req.ReqSkillCdDTO;
import com.kyj.fmk.common.model.res.ResCommonCdDTO;
import com.kyj.fmk.common.model.res.ResDtyCdDTO;
import com.kyj.fmk.common.model.res.ResSkillCdDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 2025-08-10
 * @author 김용준
 * 공통 레포지토리
 */
@Repository
@RequiredArgsConstructor
public class CommonRepository {

    private final CommonMapper commonMapper;

    /**
     * 공통코드 셀렉트 리스트
     * @return
     */
    public List<ResCommonCdDTO> cmCdList(ReqCommonCdDTO commonCdDTO){
        return commonMapper.cmCdList(commonCdDTO);
    }

    /**
     * 스킬코드 셀렉트 리스트
     * @return
     */
    public List<ResSkillCdDTO> skillCdList(ReqSkillCdDTO skillCdDTO){
        return commonMapper.skillCdList(skillCdDTO);
    }

    /**
     * 직무코드 리스트
     * @return
     */
    public List<ResDtyCdDTO> dtyCdList(ReqDtyCdDTO dtyCdDTO){
        return commonMapper.dtyCdList(dtyCdDTO);
    }

}
