package com.component;


import org.apache.tomcat.util.descriptor.LocalResolver;
import org.springframework.web.servlet.LocaleResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;

public class MyLocaleResolver implements LocaleResolver {

    @Override
    public Locale resolveLocale(HttpServletRequest httpServletRequest) {
        String language = httpServletRequest.getParameter("language");
        if (language!=null){
            String[] strings = language.split("_");
            Locale locale=new Locale(strings[0],strings[1]);
            return locale;
        }
        return new Locale("en","US");
    }

    @Override
    public void setLocale(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Locale locale) {

    }
}
