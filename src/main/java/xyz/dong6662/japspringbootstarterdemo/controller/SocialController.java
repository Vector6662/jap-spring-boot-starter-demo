package xyz.dong6662.japspringbootstarterdemo.controller;

import com.fujieid.jap.core.JapUser;
import com.fujieid.jap.core.context.JapAuthentication;
import com.fujieid.jap.core.result.JapResponse;
import com.fujieid.jap.core.store.JapUserStore;
import com.fujieid.jap.core.util.JapUtil;
import com.fujieid.jap.social.SocialStrategy;
import com.fujieid.jap.spring.boot.starter.JapTemplate;
import com.fujieid.spring.boot.japsocialspringbootstarter.autoconfigure.SocialProperties;
import me.zhyd.oauth.model.AuthToken;
import me.zhyd.oauth.model.AuthUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@RestController
@RequestMapping("/social")
public class SocialController {
    @Autowired
    JapTemplate japTemplate;
    @Autowired
    JapUserStore japUserStore;

    @Autowired
    SocialStrategy socialStrategy;
    @Autowired
    SocialProperties socialProperties;

    @RequestMapping(method = RequestMethod.GET, path = "/gitee")
    public JapResponse giteeCallback() {
        return japTemplate.opsForSocial().authenticate("gitee");
    }

    @RequestMapping("/gitee/user-info")
    public JapResponse userInfo(HttpServletRequest request, HttpServletResponse response){
        // japUserStore实现类为SessionJapUserStore时，获取的是当前会话保存的AuthUser
        AuthUser authUser = (AuthUser) japUserStore.get(request, response).getAdditional();
        AuthToken token = authUser.getToken();
        return japTemplate.opsForSocial().getUserInfo("gitee", token);
    }

    @RequestMapping("/gitee/refresh-token")
    public JapResponse refreshToken(HttpServletRequest request, HttpServletResponse response){
        // 当前会话保存的AuthUser
        AuthUser authUser = (AuthUser) japUserStore.get(request, response).getAdditional();
        AuthToken token = authUser.getToken();
        // FIXME: 2021/10/10 AuthGiteeRequest中没有实现refresh方法，此处暂无法测试
        return japTemplate.opsForSocial().refreshToken("gitee",token);
    }

    // FIXME: 2021/10/10 同refreshToken， AuthGiteeRequest（me.zhyd.oauth.request包下）中也没有实现revoke，故暂不测试revokeToken


    @RequestMapping("/aliyun")
    public JapResponse authenticate(HttpServletRequest request, HttpServletResponse response){
        return socialStrategy.authenticate(socialProperties.getSocial().get("aliyun"),request,response);
    }

    @RequestMapping("/aliyun/userInfo")
    public JapResponse getUserInfo(HttpServletRequest request, HttpServletResponse response) {
        // 当前会话保存的AuthUser
        AuthUser authUser = (AuthUser) japUserStore.get(request, response).getAdditional();
        AuthToken token = authUser.getToken();
        return socialStrategy.getUserInfo(socialProperties.getSocial().get("aliyun"),token);
    }

}
