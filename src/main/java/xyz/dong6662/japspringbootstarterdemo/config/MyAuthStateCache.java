package xyz.dong6662.japspringbootstarterdemo.config;

import com.fujieid.jap.core.cache.JapCache;
import lombok.extern.slf4j.Slf4j;
import me.zhyd.oauth.cache.AuthDefaultStateCache;
import me.zhyd.oauth.cache.AuthStateCache;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


//@Configuration
@Slf4j
public class MyAuthStateCache {
    @Bean
    public AuthStateCache authStateCache(){
        log.info("custom AuthStateCache");
        return new AuthStateCache() {
            @Override
            public void cache(String key, String value) {

            }

            @Override
            public void cache(String key, String value, long timeout) {

            }

            @Override
            public String get(String key) {
                return null;
            }

            @Override
            public boolean containsKey(String key) {
                return false;
            }
        };

    }

}
