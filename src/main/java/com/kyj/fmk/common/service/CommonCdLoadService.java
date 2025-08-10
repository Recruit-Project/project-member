package com.kyj.fmk.common.service;
/**
 * 2025-08-10
 * @author 김용준
 * 애플리케이션 시작시 공통코드,기술스택코드,직무코드를 레디스에 캐싱하기위한 서비스
 */
public interface CommonCdLoadService {

    public void loadCmCd();
    public void loadSkillCd();
    public void loadDtyCd();
}

