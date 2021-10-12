package xyz.dong6662.japspringbootstarterdemo.controller;

import com.fujieid.jap.core.result.JapResponse;
import com.fujieid.jap.oidc.OidcStrategy;
import com.fujieid.jap.spring.boot.starter.JapTemplate;
import com.fujieid.spring.boot.japoidcspringbootstarter.autoconfigure.OidcProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/oidc")
public class OidcController {
    @Autowired
    JapTemplate japTemplate;

    @Autowired
    OidcStrategy oidcStrategy;
    @Autowired
    OidcProperties oidcProperties;

    @RequestMapping("/aliyun")
    public JapResponse aliyunCallback(){
        return japTemplate.opsForOidc().authenticate("aliyun");
    }


    @RequestMapping("/aliyunXXXX")
    public JapResponse aliyunCallback(HttpServletRequest request, HttpServletResponse response){
        return oidcStrategy.authenticate(oidcProperties.getOidc().get("aliyun"),request,response);
    }
}
