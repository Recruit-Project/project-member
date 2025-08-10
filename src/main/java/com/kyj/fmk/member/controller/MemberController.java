package com.kyj.fmk.member.controller;

import com.kyj.fmk.core.util.CookieUtil;
import com.kyj.fmk.sec.annotation.PublicEndpoint;
import com.kyj.fmk.sec.jwt.JWTUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

/**
 * 2025-08-10
 * @author 김용준
 * 회원정보를 관리하고 쓰는 컨트롤러
 */
@RestController
@RequestMapping("/api/v1/member/")
@RequiredArgsConstructor
public class MemberController {
    /**
     * 추가정보 입력후 리다이렉트 될 페이지
     */
    @Value("${spring.security.oauth2.login.success-url}")
    private String successUrl;
    
    private final JWTUtil jwtUtil;

    /**
     * 추가정보 입력을 위한페이지에서 회원가입 요청
     * @param request
     * @param response
     * @throws IOException
     */
    @GetMapping("join")
    @PublicEndpoint
    public void join(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String joinJwt =(String)CookieUtil.getCookie("joinJwt", request);
        String usrId = jwtUtil.getUsrId(joinJwt);
        response.sendRedirect(successUrl);
    }
}
