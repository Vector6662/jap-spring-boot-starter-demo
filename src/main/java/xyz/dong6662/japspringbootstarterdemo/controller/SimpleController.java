package xyz.dong6662.japspringbootstarterdemo.controller;

import com.fujieid.jap.core.result.JapResponse;
import com.fujieid.jap.simple.SimpleStrategy;
import com.fujieid.jap.spring.boot.japsimplespringbootstarter.autoconfigure.SimpleProperties;
import com.fujieid.jap.spring.boot.starter.JapTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@RestController
@RequestMapping("/simple")
public class SimpleController {
    @Autowired
    JapTemplate japTemplate;

    @Autowired
    SimpleStrategy simpleStrategy;
    @Autowired
    SimpleProperties simpleProperties;

    @RequestMapping(method = RequestMethod.GET, path = "/1")
    public JapResponse simple1() {
        return japTemplate.opsForSimple().authenticate();
    }


    @RequestMapping(method = RequestMethod.GET, path = "/2")
    public JapResponse simple2(HttpServletRequest request, HttpServletResponse response){
        return simpleStrategy.authenticate(simpleProperties.getSimple(),request,response);
    }

}
