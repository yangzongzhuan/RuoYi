package com.ruoyi.framework.config.properties;

import com.ruoyi.common.annotation.Anonymous;
import org.springframework.aop.framework.Advised;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * 设置Anonymous注解允许匿名访问的url
 *
 * @author ruoyi
 */
@Configuration
public class PermitAllUrlProperties implements InitializingBean, ApplicationContextAware {
    private List<String> urls = new ArrayList<>();

    private ApplicationContext applicationContext;

    @Override
    public void afterPropertiesSet() throws Exception {
        Map<String, Object> controllers = applicationContext.getBeansWithAnnotation(Controller.class);
        for (Object bean : controllers.values()) {
            if (!(bean instanceof Advised)) {
                continue;
            }
            Class<?> beanClass = ((Advised) bean).getTargetSource().getTarget().getClass();
            // 在之前的代码下，如果这里不是直接使用RequestMapping注解，而是使用其他带有RequestMapping注解标注的注解（PostMapping）就不会生效
            RequestMapping base = AnnotationUtils.getAnnotation(beanClass, RequestMapping.class);
            String[] baseUrl = {};
            if (Objects.nonNull(base)) {
                baseUrl = base.value();
            }
            Method[] methods = beanClass.getDeclaredMethods();
            for (Method method : methods) {
                // 这里判断次数重复，最差的情况下效率较低，同时采用SpringbootAOP中提供的注解解析工具，提高鲁棒性
                if (AnnotatedElementUtils.hasAnnotation(method, Anonymous.class)) {
                    RequestMapping mergedAnnotation = AnnotatedElementUtils.getMergedAnnotation(method, RequestMapping.class);
                    if (Objects.nonNull(mergedAnnotation)) {
                        String[] uri = mergedAnnotation.value();
                        urls.addAll(rebuildUrl(baseUrl, uri));
                    }
                }
            }

        }
    }

    private List<String> rebuildUrl(String[] bases, String[] uris) {
        List<String> urls = new ArrayList<>();
        for (String base : bases) {
            for (String uri : uris) {
                urls.add(prefix(base) + prefix(uri));
            }
        }
        return urls;
    }

    private String prefix(String seg) {
        return seg.startsWith("/") ? seg : "/" + seg;
    }

    @Override
    public void setApplicationContext(ApplicationContext context) throws BeansException {
        this.applicationContext = context;
    }

    public List<String> getUrls() {
        return urls;
    }

    public void setUrls(List<String> urls) {
        this.urls = urls;
    }
}
