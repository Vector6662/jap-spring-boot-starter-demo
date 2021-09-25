package my.dong6662.japspringbootstarterdemo.controller;

import com.fujieid.jap.core.JapUser;
import com.fujieid.jap.core.context.JapAuthentication;
import com.fujieid.jap.core.result.JapResponse;
import com.fujieid.jap.core.store.JapUserStore;
import com.fujieid.jap.core.util.JapUtil;
import com.fujieid.jap.spring.boot.starter.JapTemplate;
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
        // AuthGiteeRequest中没有实现refresh方法，故会报错。
        return japTemplate.opsForSocial().refreshToken("gitee",token);
    }

    // 同样AuthGiteeRequest没有实现revoke，故暂不测试revokeToken
}
