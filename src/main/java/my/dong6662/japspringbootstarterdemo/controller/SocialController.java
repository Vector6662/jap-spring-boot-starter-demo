package my.dong6662.japspringbootstarterdemo.controller;

import com.fujieid.jap.core.result.JapResponse;
import com.fujieid.jap.spring.boot.starter.JapTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/social")
public class SocialController {
    @Autowired
    JapTemplate japTemplate;

    @RequestMapping(method = RequestMethod.GET, path = "/gitee")
    public JapResponse gitee() {
        // FIXME: 2021/9/12 gitee不会返回state参数，即使访问gitee.com/oauth/authorize时带上了state参数。
        //  https://gitee.com/oauth/authorize?response_type=code&client_id=228d103043840b9706f04ad165726a0079c7e0263bf7c11f1205b4054ff094a9&redirect_uri=http%3A%2F%2Flocalhost%3A8080%2Fsocial%2Fgitee&state=3242vregv&scope=user_info
        return japTemplate.opsForSocial().authenticate("gitee");
    }
}
