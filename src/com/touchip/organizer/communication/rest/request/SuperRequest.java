package com.touchip.organizer.communication.rest.request;

import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.util.MultiValueMap;

import com.octo.android.robospice.request.springandroid.SpringAndroidSpiceRequest;
import com.touchip.organizer.utils.Utils;

public class SuperRequest<T> extends SpringAndroidSpiceRequest <T> {

     // Request URL
     protected String                   requestUrl;
     // Addition convertors (if needed)
     protected HttpMessageConverter <?> converter = null;
     // Parent class
     protected Class <?>                transferingClassType;
     // Variable array for request
     private Object                     httpParams;
     // headers (optional)
     private HttpHeaders                headers;
     MultiValueMap <String, Object>     vars;

     /**
      * @param clazz
      * @param reqestUrl
      * @param converter
      * @param vars
      */
     public SuperRequest ( Class <T> clazz , String reqestUrl , HttpMessageConverter <?> converter , Object vars ) {
          super(clazz);
          this.requestUrl = reqestUrl;
          this.transferingClassType = clazz;
          httpParams = vars;
     }

     /**
      * @param clazz
      * @param reqestUrl
      * @param converter
      * @param vars
      * @param headers
      */
     public SuperRequest ( Class <T> clazz , String reqestUrl , HttpMessageConverter <?> converter , MultiValueMap <String, Object> vars , HttpHeaders headers ) {
          super(clazz);
          this.requestUrl = reqestUrl;
          this.transferingClassType = clazz;
          this.vars = vars;
          this.headers = headers;
          this.converter = converter;
     }

     /**
      * Base loading data from network (Background UI)
      */
     @SuppressWarnings ( "unchecked" ) @Override public T loadDataFromNetwork() throws Exception {
          Utils.logw("POST ASK FOR OBJECT " + transferingClassType.getSimpleName() + " URL == (" + this.requestUrl + ")");
          Utils.printMap((Map) httpParams);
          if ( null != vars ) {
               Utils.printMap(vars);
          }
          if ( null != this.converter ) {
               getRestTemplate().getMessageConverters().add(converter);
          }

          if ( null != headers ) {
               return (T) getRestTemplate().postForEntity(this.requestUrl, new HttpEntity <MultiValueMap <String, Object>>(vars, headers), transferingClassType).getBody();
          } else {
               return (T) getRestTemplate().postForEntity(this.requestUrl, httpParams, transferingClassType).getBody();
          }
     }

     /**
      * This method generates a unique cache key for this request.
      * 
      * @return string representation of cache word
      */
     public String createCacheKey() {
          return getClass().getSimpleName() + ".cache";
     }
}