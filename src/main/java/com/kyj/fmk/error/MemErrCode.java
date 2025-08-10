package com.kyj.fmk.error;


import com.kyj.fmk.core.model.ErrCode;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;
/**
 * 2025-05-29
 * @author 김용준
 * Restful Api에서 사용하는 에러응답 공통 Enum
 */
@Getter
public enum MemErrCode implements ErrCode {

    MEM001("MEM001","코드값이 존재하지 않거나, 잘못된 코드값입니다.");






    private final String code;
    private final String msg;

    /**
     * 에러코드 생성자
     * @param code
     * @param msg
     */
    MemErrCode(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }




}

