package com.kyj.fmk.common.mapper;


import com.kyj.fmk.core.model.cmcd.req.ReqCommonCdDTO;
import com.kyj.fmk.core.model.cmcd.req.ReqDtyCdDTO;
import com.kyj.fmk.core.model.cmcd.req.ReqSkillCdDTO;
import com.kyj.fmk.core.model.cmcd.res.ResCommonCdDTO;
import com.kyj.fmk.core.model.cmcd.res.ResDtyCdDTO;
import com.kyj.fmk.core.model.cmcd.res.ResSkillCdDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 2025-08-10
 * @author 김용준
 * 공통매퍼
 */
@Mapper
public interface CommonMapper {

    public List<ResCommonCdDTO> cmCdList(ReqCommonCdDTO commonCdDTO);
    public List<ResSkillCdDTO> skillCdList(ReqSkillCdDTO skillCdDTO);
    public List<ResDtyCdDTO> dtyCdList(ReqDtyCdDTO dtyCdDTO);

}
