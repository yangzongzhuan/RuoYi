package com.ruoyi.framework.config.properties;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import org.apache.commons.lang3.RegExUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.boot.autoconfigure.AutoConfigurationPackages;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.stereotype.Controller;
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
    private static final Pattern PATTERN = Pattern.compile("\\{(.*?)\\}");

    private static final String ASTERISK = "*";

    private ApplicationContext applicationContext;

    private List<String> urls = new ArrayList<>();

    @Override
    public void afterPropertiesSet() throws Exception
    {
        String basePackage = AutoConfigurationPackages.get(applicationContext.getAutowireCapableBeanFactory()).get(0);

        ClassPathScanningCandidateComponentProvider scanner = new ClassPathScanningCandidateComponentProvider(false);
        scanner.addIncludeFilter(new AnnotationTypeFilter(Controller.class));
        for (BeanDefinition bd : scanner.findCandidateComponents(basePackage))
        {
            Class<?> beanClass = Class.forName(bd.getBeanClassName());
            RequestMapping base = beanClass.getAnnotation(RequestMapping.class);
            String[] baseUrl = base != null ? base.value() : new String[] {};
            boolean classAnonymous = beanClass.isAnnotationPresent(Anonymous.class);
            for (Method method : beanClass.getDeclaredMethods())
            {
                boolean methodAnonymous = method.isAnnotationPresent(Anonymous.class);
                if (!classAnonymous && !methodAnonymous)
                {
                    continue;
                }
                RequestMapping mapping = AnnotatedElementUtils.findMergedAnnotation(method, RequestMapping.class);
                if (mapping != null && mapping.value().length > 0)
                {
                    urls.addAll(rebuildUrl(baseUrl, mapping.value()));
                }
            }
        }
    }

    @Override
    public void setApplicationContext(ApplicationContext context) throws BeansException
    {
        this.applicationContext = context;
    }

    private List<String> rebuildUrl(String[] bases, String[] uris)
    {
        List<String> urls = new ArrayList<>();
        for (String base : bases)
        {
            for (String uri : uris)
            {
                urls.add(prefix(base) + prefix(RegExUtils.replaceAll(uri, PATTERN, ASTERISK)));
            }
        }
        return urls;
    }

    private String prefix(String seg)
    {
        return seg.startsWith("/") ? seg : "/" + seg;
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
