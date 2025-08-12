package com.kyj.fmk.common.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kyj.fmk.core.model.cmcd.req.ReqCommonCdDTO;
import com.kyj.fmk.core.model.cmcd.req.ReqDtyCdDTO;
import com.kyj.fmk.core.model.cmcd.req.ReqSkillCdDTO;
import com.kyj.fmk.core.model.cmcd.res.ResCommonCdDTO;
import com.kyj.fmk.core.model.cmcd.res.ResDtyCdDTO;
import com.kyj.fmk.core.model.cmcd.res.ResSkillCdDTO;
import com.kyj.fmk.common.repository.CommonRepository;
import com.kyj.fmk.core.model.CmCdConst;
import com.kyj.fmk.core.model.OutPutConst;
import com.kyj.fmk.core.redis.RedisKey;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 2025-08-10
 * @author 김용준
 * 애플리케이션 시작시 공통코드,기술스택코드,직무코드를 레디스에 캐싱하기위한 서비스 구현체
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class CommonCdLoadServiceImpl implements CommonCdLoadService{
    private final RedisTemplate<String, String> redisTemplate;
    private final CommonRepository commonRepository;

    /**
     * 공통코드 로드
     */
    @PostConstruct
    @Override
    public void loadCmCd() {
        HashOperations<String, String, String> hashOps = redisTemplate.opsForHash();
        //공통코드 리스트
        List<ResCommonCdDTO> list = commonRepository.cmCdList(null);
        log.info("--------공통코드 적재시작----------");
        int cnt = 0;
        //공통 코드별 분기 저장
        for (ResCommonCdDTO dto : list){

            log.info("공통코드적재 값={}",dto.getCmCdVal());
            log.info("공통코드적재 이름={}",dto.getCmCdValNm());

           if(dto.getCmCd().equals(CmCdConst.APY_ST_CD)){
               hashOps.put(RedisKey.CM_APY_ST_CD,dto.getCmCdVal(),dto.getCmCdValNm());
           } else if (dto.getCmCd().equals(CmCdConst.CMC_TONE_CD)) {
               hashOps.put(RedisKey.CM_CMC_TONE_CD,dto.getCmCdVal(),dto.getCmCdValNm());
           } else if (dto.getCmCd().equals(CmCdConst.GRP_ST_CD)) {
               hashOps.put(RedisKey.CM_GRP_ST_CD,dto.getCmCdVal(),dto.getCmCdValNm());
           } else if (dto.getCmCd().equals(CmCdConst.MT_STY_CD)) {
               hashOps.put(RedisKey.CM_MT_STY_CD,dto.getCmCdVal(),dto.getCmCdValNm());
           } else if (dto.getCmCd().equals(CmCdConst.RECRUIT_ST_CD)) {
               hashOps.put(RedisKey.CM_RECRUIT_ST_CD,dto.getCmCdVal(),dto.getCmCdValNm());
           } else if (dto.getCmCd().equals(CmCdConst.TEAM_STY_CD)) {
               hashOps.put(RedisKey.CM_TEAM_STY_CD,dto.getCmCdVal(),dto.getCmCdValNm());
           }
                cnt++;
        }

        log.info("--------공통코드 적재종료----------");
        log.info("공통코드 적재건수={}",cnt);
    }

    /**
     * 스킬코드 로드
     */
    @PostConstruct
    @Override
    public void loadSkillCd() {

        Map<String,String> skillMap = new HashMap<>();
        ObjectMapper objectMapper = new ObjectMapper();

        HashOperations<String, String, String> hashOps = redisTemplate.opsForHash();
        //기술스택 리스트 조회
        List<ResSkillCdDTO> list = commonRepository.skillCdList(null);
        log.info("--------기술코드 적재시작----------");

        int cnt =0;

        for(ResSkillCdDTO dto : list){
            log.info("기술코드 적재 값={}",dto.getSkillCd());
            log.info("기술코드 적재 이름={}",dto.getSkillNm());
            log.info("기술코드 적재 이미지={}",dto.getSkillCdImg());
            //해시조회를 위한 개별저장
            hashOps.put(RedisKey.SKILL_CD_KEY+dto.getSkillCd(),RedisKey.SUFFIX_SKILL_NM_KEY,dto.getSkillNm());

            if(dto.getSkillCdImg() == null){
                //기본 이미지 세팅
                dto.setSkillCdImg(OutPutConst.basicImgUrl);
            }

            hashOps.put(RedisKey.SKILL_CD_KEY+dto.getSkillCd(),RedisKey.SUFFIX_SKILL_CD_IMG_KEY,dto.getSkillCdImg());

            // dto → JSON 문자열
            //전체조회를 위한 저장
            String json = null;
            try {
                json = objectMapper.writeValueAsString(dto);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }

            skillMap.put(dto.getSkillCd(), json);

            cnt ++;
        }

        hashOps.putAll(RedisKey.SKILL_CD_KEY_ALL, skillMap);

        log.info("--------기술코드 적재종료----------");
        log.info("기술코드 적재건수={}",cnt);
    }

    /**
     * 직무코드 업로드
     */
    @PostConstruct
    @Override
    public void loadDtyCd() {
        HashOperations<String, String, String> hashOps = redisTemplate.opsForHash();


        //기술스택 리스트 조회
        List<ResDtyCdDTO> list = commonRepository.dtyCdList(null);
        log.info("--------직무코드 적재시작----------");

        int cnt =0;

        for(ResDtyCdDTO dto : list){
            log.info("직무코드 적재 값={}",dto.getDtyCd());
            log.info("직무코드 적재 이름={}",dto.getDtyNm());
            log.info("직무코드 적재 이미지={}",dto.getDtyCdImg());
            //해시조회를 위한 개별저장
            hashOps.put(RedisKey.SKILL_CD_KEY+dto.getDtyCd(),RedisKey.SUFFIX_DTY_NM_KEY,dto.getDtyNm());
            if(dto.getDtyCdImg() == null){
                //기본 이미지 세팅
                dto.setDtyCdImg(OutPutConst.basicImgUrl);
            }
            hashOps.put(RedisKey.SKILL_CD_KEY+dto.getDtyCd(),RedisKey.SUFFIX_DTY_CD_IMG_KEY,dto.getDtyCdImg());

            //전체조회를 위한 저장
            redisTemplate.opsForList().rightPushAll(RedisKey.DTY_CD_KEY_ALL, dto.getDtyCd());
            cnt ++;
        }

        log.info("--------직무코드 적재종료----------");
        log.info("직무코드 적재건수={}",cnt);
    }
}
