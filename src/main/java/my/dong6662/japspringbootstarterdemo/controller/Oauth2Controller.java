package my.dong6662.japspringbootstarterdemo.controller;

import com.fujieid.jap.core.result.JapResponse;
import com.fujieid.jap.spring.boot.starter.JapTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/oauth")
public class Oauth2Controller {
    @Autowired
    JapTemplate japTemplate;

    // FIXME: 2021/9/12 测试通过了gitee平台的authorization-code和authorization-code授权方式
    @RequestMapping("/gitee/authorization-code")
    public JapResponse authorizationCode(){
        return japTemplate.opsForOauth2().authenticateByAuthorizationCode("gitee");
    }

    @RequestMapping("/gitee/password")
    public JapResponse passwordGrantType(String username, String password) {
        return japTemplate.opsForOauth2().authenticateByPassword("gitee", username, password);
    }



    @RequestMapping("/github/authorization-code")
    public JapResponse github(){
        return japTemplate.opsForOauth2().authenticateByAuthorizationCode("github");
    }



    @RequestMapping("/weibo/authorization-code")
    public JapResponse weibo(String code){
        return japTemplate.opsForOauth2().authenticateByAuthorizationCode("weibo");

    }
}
