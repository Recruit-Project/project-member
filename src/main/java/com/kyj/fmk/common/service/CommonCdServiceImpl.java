package com.kyj.fmk.common.service;

import com.kyj.fmk.core.model.cmcd.req.ReqCommonCdDTO;
import com.kyj.fmk.core.model.cmcd.req.ReqDtyCdDTO;
import com.kyj.fmk.core.model.cmcd.req.ReqSkillCdDTO;
import com.kyj.fmk.core.model.cmcd.res.ResCommonCdDTO;
import com.kyj.fmk.core.model.cmcd.res.ResDtyCdDTO;
import com.kyj.fmk.core.model.cmcd.res.ResSkillCdDTO;
import com.kyj.fmk.common.repository.CommonRepository;
import com.kyj.fmk.core.exception.custom.KyjBizException;
import com.kyj.fmk.core.model.CmCdConst;
import com.kyj.fmk.core.model.dto.ResApiDTO;
import com.kyj.fmk.core.model.enm.CmErrCode;
import com.kyj.fmk.core.redis.RedisKey;
import com.kyj.fmk.core.service.cmcd.CmCdRedisService;
import com.kyj.fmk.error.MemErrCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 2025-08-10
 * @author 김용준
 * 공통코드, 기술스택코드 , 직무코드에 관하여 셀렉트하고 , 쓰기작업을 수행하는 서비스 구현체
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class CommonCdServiceImpl implements CommonCdService {

    //공통코드를 간편하게 셀렉트하는 서비스
    private final CmCdRedisService cmCdRedisService;

    //repo
    private final CommonRepository commonRepository;


    /**
     * 공통코드를 조회하는 메소드
     * CM_CD(팀스타일,회의스타일 등 )
     * CM_CD_VAL(친화적인, 협력적인 등 실질적인 코드값)
     * 에 따라 null이면 전체를 조회하여 코드값과 코드값의명 을 리턴해주고,  조건이 있으면 그에 해당하는 리스트를 코드값과 코드값의 명을 json으로 리턴해준다.
     * @param reqCommonCdDTO
     * @return
     */
    public ResponseEntity<ResApiDTO<List<ResCommonCdDTO>>> selectCmCdList(ReqCommonCdDTO reqCommonCdDTO){
        //CM_CD_VAL조회를 위해선 CM_CD가 있어야함
        if(reqCommonCdDTO.getCmCd() ==null && reqCommonCdDTO.getCmCdVal() != null){

            throw new KyjBizException(MemErrCode.MEM001);
        }

        //정상코드값 검증
        if(reqCommonCdDTO.getCmCd()!=null){

            switch (reqCommonCdDTO.getCmCd()) {
                case CmCdConst.APY_ST_CD:
                    break;
                case CmCdConst.CMC_TONE_CD:
                    break;
                case CmCdConst.MT_STY_CD:
                    break;
                case CmCdConst.GRP_ST_CD:
                    break;
                case CmCdConst.TEAM_STY_CD:
                    break;
                case CmCdConst.RECRUIT_ST_CD:
                    break;
                default:
                    throw new KyjBizException(MemErrCode.MEM001);
            }
        }

        List<ResCommonCdDTO> list = new ArrayList<>();
        


        //전체조회
        if(reqCommonCdDTO.getCmCd() == null){
            list = commonRepository.cmCdList(null);
        }


        //CM_CD기준 조회 (팀스타일,회의스타일코드인지)
        //레디스에서 조회한다.
       try {
           if(reqCommonCdDTO.getCmCd() != null && reqCommonCdDTO.getCmCdVal() == null){

               //레디스조회
               Map<String ,String> map = cmCdRedisService.selectRedisCmCdMap(reqCommonCdDTO);


               for (Map.Entry<String, String> entry : map.entrySet()) {
                   ResCommonCdDTO resCommonCdDTO = new ResCommonCdDTO();
                   resCommonCdDTO.setCmCd(reqCommonCdDTO.getCmCd());
                   resCommonCdDTO.setCmCdVal(entry.getKey());

                   resCommonCdDTO.setCmCdValNm(entry.getValue());
                   list.add(resCommonCdDTO);
               }
               //레디스 조회내용없을 시 대비
               if(list == null){
                   list = commonRepository.cmCdList(reqCommonCdDTO);
               }
           }
       } catch (Exception e) {
           //레디스 예외발생시 rdb조회
           list = commonRepository.cmCdList(reqCommonCdDTO);
       }


        //CM_CD + CM_CD_VAL 기준조회 (MYBATIS)
        if(!(reqCommonCdDTO.getCmCd() == null && reqCommonCdDTO.getCmCdVal() == null)){

            list=commonRepository.cmCdList(reqCommonCdDTO);

        }

        //조회내용이 없음
        if(list == null || list.isEmpty()){
            throw new KyjBizException(CmErrCode.CM015);
        }

        return ResponseEntity
                .ok()
                .body(new ResApiDTO<>(list));
    }

}
