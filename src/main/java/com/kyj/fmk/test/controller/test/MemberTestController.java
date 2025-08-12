package com.kyj.fmk.test.controller.test;

import com.kyj.fmk.core.exception.custom.KyjBizException;
import com.kyj.fmk.core.file.FileService;
import com.kyj.fmk.core.model.dto.ResApiDTO;
import com.kyj.fmk.core.model.enm.CmErrCode;

import com.kyj.fmk.core.redis.RedisKey;
import com.kyj.fmk.sec.annotation.PublicEndpoint;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/member")
public class MemberTestController {


    private final RedisTemplate<String,Object> redisTemplate;




    @PublicEndpoint
    @GetMapping("/devtest2")
    public ResponseEntity<Object> test2(){
        ResApiDTO resApiDTO = new ResApiDTO(null);
        return ResponseEntity.ok(resApiDTO);
    }


    @PublicEndpoint
    @GetMapping("/devtest3")
    public String test3(){
        HashOperations<String, String, String> hashOps = redisTemplate.opsForHash();
        Map<String, String> grpStCdMap = hashOps.entries(RedisKey.CM_CMC_TONE_CD);
        List<String> keys = new ArrayList<>(grpStCdMap.keySet());

        for(String key2 : keys){
            System.out.println("key2 = " + key2);

        }
        return null;
    }

    @PublicEndpoint
    @GetMapping("/devtest4")
    public ResponseEntity<Object> test4(){
        throw new KyjBizException(CmErrCode.SEC010);

    }

    @PublicEndpoint
    @GetMapping("/devtest5")
    public ResponseEntity<Object> test5(int i){
       return null;

    }
}
