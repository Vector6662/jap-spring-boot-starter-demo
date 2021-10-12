package xyz.dong6662.japspringbootstarterdemo.controller;

import com.fujieid.jap.core.JapUser;
import com.fujieid.jap.core.result.JapResponse;
import com.fujieid.jap.core.store.JapUserStore;
import com.fujieid.jap.oauth2.Oauth2Strategy;
import com.fujieid.jap.spring.boot.starter.JapTemplate;
import com.fujieid.spring.boot.japoauth2springbootstarter.autoconfigure.Oauth2Properties;
import com.xkcoding.json.util.Kv;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/oauth")
public class Oauth2Controller {
    @Autowired
    JapTemplate japTemplate;
    @Autowired
    JapUserStore japUserStore;

    @Autowired
    Oauth2Strategy oauth2Strategy;
    @Autowired
    Oauth2Properties oauth2Properties;

    /**
     * gitee AuthorizationCode授权方式
     */
    @RequestMapping("/gitee/authorization-code") //callback-url
    public JapResponse authorizationCode(){
        return japTemplate.opsForOauth2().authenticateByAuthorizationCode("gitee");
    }

    /**
     * gitee password授权方式
     */
    @RequestMapping("/gitee/password")
    public JapResponse passwordGrantType(String username, String password) {
        return japTemplate.opsForOauth2().authenticateByPassword("gitee", username, password);
    }

    @RequestMapping("/gitee/refresh-token")
    public JapResponse refreshToken(HttpServletRequest request, HttpServletResponse response){
        // 这是获取JapUser的一种方式
        JapUser japUser = japUserStore.get(request, response);
        // FIXME: 2021/9/25 gitee平台不会在授权信息中返回refresh_token
        String refreshToken = ((Kv) japUser.getAdditional()).getString("refresh-token");
        return japTemplate.opsForOauth2().refreshToken("gitee",refreshToken);
    }

    /**
     * github AuthorizationCode授权方式
     * @return
     */
    @RequestMapping("/github/authorization-code")
    public JapResponse github(){
        // FIXME: 2021/9/25 GitHub的授权方式似乎并不是严格的oauth2，查看：https://docs.github.com/en/developers/apps/building-oauth-apps/authorizing-oauth-apps#response
        //  会发现默认情况下返回的授权信息不是json格式，而是类似这样：access_token=XXX&token_type=bearer
        return japTemplate.opsForOauth2().authenticateByAuthorizationCode("github");
    }


    /**
     * 阿里云 AuthorizationCode授权方式
     * @return
     */
    @RequestMapping("/aliyun/authorization-code")
    public JapResponse aliyunAuthorizationCode(HttpServletRequest request, HttpServletResponse response){
        return oauth2Strategy.authenticate(oauth2Properties.getOauth2().get(3), request, response);
    }

    @RequestMapping("/aliyun/refresh-token")
    public JapResponse aliyunRefreshToken(HttpServletRequest request, HttpServletResponse response) {
        // 这是获取JapUser的一种方式
        JapUser japUser = japUserStore.get(request, response);
        // FIXME: aliyun平台不会在授权信息中返回refresh_token，因此这里refreshToken的是为null
        String refreshToken = ((Kv) japUser.getAdditional()).getString("refresh-token");
        return oauth2Strategy.refreshToken(oauth2Properties.getOauth2().get(3), refreshToken);
    }

}
