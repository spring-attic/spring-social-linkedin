package org.springframework.social.linkedin.api.impl;

import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.xml.MarshallingHttpMessageConverter;
import org.springframework.oxm.xstream.XStreamMarshaller;
import org.springframework.social.linkedin.api.CompanyShare;

/**
 * Created by yanikb on 2014-11-27.
 */
public class LinkedInXStreamModule {

    private HttpMessageConverter<Object> httpMessageConverter;

    public LinkedInXStreamModule() {
        XStreamMarshaller xStreamMarshaller = new XStreamMarshaller();
        registerClassesForAliasing(xStreamMarshaller);
        httpMessageConverter = new MarshallingHttpMessageConverter(xStreamMarshaller, xStreamMarshaller);
    }

    private void registerClassesForAliasing(XStreamMarshaller xStreamMarshaller) {
        xStreamMarshaller.getXStream().processAnnotations(CompanyShare.class);
        xStreamMarshaller.getXStream().processAnnotations(CompanyShare.ShareTargetReach.class);
        xStreamMarshaller.getXStream().processAnnotations(CompanyShare.ShareTarget.class);
        xStreamMarshaller.getXStream().processAnnotations(CompanyShare.TValues.class);
    }

    public HttpMessageConverter<Object> getHttpMessageConverter() {
        return httpMessageConverter;
    }
}
