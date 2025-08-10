package com.kyj.fmk.common.service;

import com.kyj.fmk.common.model.req.ReqCommonCdDTO;
import com.kyj.fmk.common.model.res.ResCommonCdDTO;
import com.kyj.fmk.common.repository.CommonRepository;
import com.kyj.fmk.core.exception.custom.KyjBizException;
import com.kyj.fmk.core.model.CmCdConst;
import com.kyj.fmk.core.model.dto.ResApiDTO;
import com.kyj.fmk.core.model.enm.CmErrCode;
import com.kyj.fmk.core.redis.RedisKey;
import com.kyj.fmk.core.util.CmSelector;
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
    private final CmSelector cmSelector;

    //레디스
    private final RedisTemplate<String,String> redisTemplate;

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
       try {
           if(reqCommonCdDTO.getCmCd() != null && reqCommonCdDTO.getCmCdVal() == null){
               HashOperations<String, String, String> hashOps = redisTemplate.opsForHash();
               Map<String, String> grpStCdMap =  null;

               String redisKey = null;

               if(reqCommonCdDTO.getCmCd().equals(CmCdConst.TEAM_STY_CD)){
                   grpStCdMap = hashOps.entries(RedisKey.CM_TEAM_STY_CD);
                   redisKey = RedisKey.CM_TEAM_STY_CD;

               } else if (reqCommonCdDTO.getCmCd().equals(CmCdConst.MT_STY_CD)) {
                   grpStCdMap = hashOps.entries(RedisKey.CM_MT_STY_CD);
                   redisKey = RedisKey.CM_MT_STY_CD;

               } else if (reqCommonCdDTO.getCmCd().equals(CmCdConst.RECRUIT_ST_CD)) {
                   grpStCdMap = hashOps.entries(RedisKey.CM_RECRUIT_ST_CD);
                   redisKey = RedisKey.CM_RECRUIT_ST_CD;

               } else if (reqCommonCdDTO.getCmCd().equals(CmCdConst.GRP_ST_CD)) {
                   grpStCdMap = hashOps.entries(RedisKey.CM_GRP_ST_CD);
                   redisKey = RedisKey.CM_GRP_ST_CD;

               } else if (reqCommonCdDTO.getCmCd().equals(CmCdConst.CMC_TONE_CD)) {
                   grpStCdMap = hashOps.entries(RedisKey.CM_CMC_TONE_CD);
                   redisKey = RedisKey.CM_CMC_TONE_CD;

               } else if (reqCommonCdDTO.getCmCd().equals(CmCdConst.APY_ST_CD)) {
                   grpStCdMap = hashOps.entries(RedisKey.CM_APY_ST_CD);
                   redisKey = RedisKey.CM_APY_ST_CD;

               }
                //레디스 조회내용없을 시 대비
               if(grpStCdMap == null){
                   list = commonRepository.cmCdList(reqCommonCdDTO);
               }

               List<String> keys = new ArrayList<>(grpStCdMap.keySet());

               //코드 이름 매핑
               ResCommonCdDTO resCommonCdDTO = new ResCommonCdDTO();

               for(String key: keys){
                   //매핑 및 리스트 추가
                   String cdNm= cmSelector.getCdName(redisKey,key);
                   resCommonCdDTO.setCmCd(reqCommonCdDTO.getCmCd());
                   resCommonCdDTO.setCmCdVal(key);
                   resCommonCdDTO.setCmCdValNm(cdNm);
                   list.add(resCommonCdDTO);
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
