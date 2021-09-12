package com.imooc.girl.esign;

import org.springframework.stereotype.Service;

@Service
public class EsignSignatureServiceImpl implements  SignatureService{

    @Override
    public CertificationResultDTO personCertification(PersonCertificationDTO certification) {
        CertificationResultDTO dto = new CertificationResultDTO();
        dto.setId("88888888888");
        dto.setUrl("https://open.esign.cn");
        return dto;
    }
}
