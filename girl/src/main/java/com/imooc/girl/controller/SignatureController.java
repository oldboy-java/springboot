package com.imooc.girl.controller;

import com.imooc.girl.esign.CertificationResultDTO;
import com.imooc.girl.esign.GeipSignatureService;
import com.imooc.girl.esign.PersonCertificationDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/signature")
@Slf4j
public class SignatureController {
    @Autowired
    private GeipSignatureService signatureService;


    @GetMapping("/v4/certification/person/url")
    public CertificationResultDTO  personCertification(@RequestBody(required = false) PersonCertificationDTO certification){
        System.err.println(signatureService);
        return null;
    }
}
