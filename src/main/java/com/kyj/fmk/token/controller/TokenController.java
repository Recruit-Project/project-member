package com.kyj.fmk.token.controller;

import com.kyj.fmk.core.model.dto.ResApiDTO;
import com.kyj.fmk.sec.service.TokenService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
/**
 * 2025-08-10
 * @author 김용준
 * 토큰을 관리하고 쓰는 컨트롤러
 */
@RestController
@RequiredArgsConstructor
public class TokenController {
    private final TokenService tokenService;

    /**
     * 리프레시 토큰 재발행
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/api/v1/member/reissue")
    public ResponseEntity<ResApiDTO<Void>> reissueToken(HttpServletRequest request, HttpServletResponse response){
        return tokenService.reissueToken(request,response);
    }
}
