package com.imooc.girl.esign;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class GeipSignatureServiceImpl implements  GeipSignatureService{
    private static Map<Integer,String> services = new ConcurrentHashMap();
    static {
        services.put(1,"com.imooc.girl.esign.FDDSignatureServiceImpl");
        services.put(2,"com.imooc.girl.esign.EsignSignatureServiceImpl");
    }
    @Override
    @Transactional
    public CertificationResultDTO personCertification(PersonCertificationDTO certification) {
        SignatureService signatureService = null;
       // 1 从租户配置中获取到租户开通的电子签服务商
        String serviceName = getSignatureServiceName(certification.getType());
        try {
            //2 根据服务实现类找到具体签字签实现Service
            signatureService = SignatureServiceFactory.getSignatureService(serviceName);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        // 3 执行具体电签平台的方法
        CertificationResultDTO resultDTO = signatureService.personCertification(certification);
        return resultDTO;
    }

    private String getSignatureServiceName(Integer type){
     return services.get(type);
    }
}
