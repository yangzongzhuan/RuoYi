package com.ruoyi.framework.config.properties;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import org.springframework.aop.framework.Advised;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.ruoyi.common.annotation.Anonymous;

/**
 * 设置Anonymous注解允许匿名访问的url
 * 
 * @author ruoyi
 */
@Configuration
public class PermitAllUrlProperties implements InitializingBean, ApplicationContextAware
{
    private List<String> urls = new ArrayList<>();

    private ApplicationContext applicationContext;

    @Override
    public void afterPropertiesSet() throws Exception
    {
        Map<String, Object> controllers = applicationContext.getBeansWithAnnotation(Controller.class);
        for (Object bean : controllers.values())
        {
            if (!(bean instanceof Advised))
            {
                continue;
            }
            Class<?> beanClass = ((Advised) bean).getTargetSource().getTarget().getClass();
            RequestMapping base = beanClass.getAnnotation(RequestMapping.class);
            String[] baseUrl = {};
            if (Objects.nonNull(base))
            {
                baseUrl = base.value();
            }
            Method[] methods = beanClass.getDeclaredMethods();
            for (Method method : methods)
            {
                if (method.isAnnotationPresent(Anonymous.class) && method.isAnnotationPresent(RequestMapping.class))
                {
                    RequestMapping requestMapping = method.getAnnotation(RequestMapping.class);
                    String[] uri = requestMapping.value();
                    urls.addAll(rebuildUrl(baseUrl, uri));
                }
                else if (method.isAnnotationPresent(Anonymous.class) && method.isAnnotationPresent(GetMapping.class))
                {
                    GetMapping requestMapping = method.getAnnotation(GetMapping.class);
                    String[] uri = requestMapping.value();
                    urls.addAll(rebuildUrl(baseUrl, uri));
                }
                else if (method.isAnnotationPresent(Anonymous.class) && method.isAnnotationPresent(PostMapping.class))
                {
                    PostMapping requestMapping = method.getAnnotation(PostMapping.class);
                    String[] uri = requestMapping.value();
                    urls.addAll(rebuildUrl(baseUrl, uri));
                }
                else if (method.isAnnotationPresent(Anonymous.class) && method.isAnnotationPresent(PutMapping.class))
                {
                    PutMapping requestMapping = method.getAnnotation(PutMapping.class);
                    String[] uri = requestMapping.value();
                    urls.addAll(rebuildUrl(baseUrl, uri));
                }
                else if (method.isAnnotationPresent(Anonymous.class) && method.isAnnotationPresent(DeleteMapping.class))
                {
                    DeleteMapping requestMapping = method.getAnnotation(DeleteMapping.class);
                    String[] uri = requestMapping.value();
                    urls.addAll(rebuildUrl(baseUrl, uri));
                }
            }

        }
    }

    private List<String> rebuildUrl(String[] bases, String[] uris)
    {
        List<String> urls = new ArrayList<>();
        for (String base : bases)
        {
            for (String uri : uris)
            {
                urls.add(prefix(base) + prefix(uri));
            }
        }
        return urls;
    }

    private String prefix(String seg)
    {
        return seg.startsWith("/") ? seg : "/" + seg;
    }

    @Override
    public void setApplicationContext(ApplicationContext context) throws BeansException
    {
        this.applicationContext = context;
    }

    public List<String> getUrls()
    {
        return urls;
    }

    public void setUrls(List<String> urls)
    {
        this.urls = urls;
    }
}
