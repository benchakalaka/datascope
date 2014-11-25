package com.touchip.organizer.communication.rest.request;

import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import quickutils.core.QUFactory.QLog;

import com.octo.android.robospice.request.springandroid.SpringAndroidSpiceRequest;
import com.touchip.organizer.activities.SuperActivity;

public class SuperRequest<T> extends SpringAndroidSpiceRequest <T> {
     // Request URL
     private final String                   requestUrl;
     // Addition convertors (if needed)
     protected HttpMessageConverter <?>     converter           = null;
     // Parent class
     protected Class <?>                    transferingClassType;
     // Variable array for request
     private Object                         httpParams;
     // headers (optional)
     private HttpHeaders                    headers;
     private MultiValueMap <String, Object> vars;
     // time in millis to configure request timeout
     int                                    WEBSERVICES_TIMEOUT = 5000;
     private final SuperActivity            activity;

     /**
      * Constructor which helps to build request to server with params
      * 
      * @param act
      *             basic activity, using for showing progressdialog
      * @param clazz
      *             requested class
      * @param reqestUrl
      *             string representation of url
      * @param vars
      *             list of variables to send
      * @param headers
      *             accepted headers for server side
      */
     public SuperRequest ( SuperActivity act , Class <T> clazz , String reqestUrl , Object vars ) {
          super(clazz);
          this.requestUrl = reqestUrl;
          this.transferingClassType = clazz;
          this.httpParams = vars;
          this.activity = act;
          this.activity.showProgressDialog();
     }

     /**
      * Constructor which helps to build request to server with params
      * 
      * @param act
      *             basic activity, using for showing progressdialog
      * @param clazz
      *             requested class
      * @param reqestUrl
      *             string representation of url
      * @param converter
      *             message converter in case of receiving byte array
      * @param vars
      *             list of variables to send
      * @param headers
      *             accepted headers for server side
      */
     public SuperRequest ( SuperActivity act , Class <T> clazz , String reqestUrl , HttpMessageConverter <?> converter , Object vars ) {
          super(clazz);
          this.requestUrl = reqestUrl;
          this.transferingClassType = clazz;
          this.httpParams = vars;
          this.activity = act;
          this.converter = converter;
          this.activity.showProgressDialog();
     }

     /**
      * Constructor which helps to build request to server with params
      * 
      * @param act
      *             basic activity, using for showing progressdialog
      * @param clazz
      *             requested class
      * @param reqestUrl
      *             string representation of url
      * @param converter
      *             message converter in case of receiving byte array
      * @param vars
      *             list of variables to send
      * @param headers
      *             accepted headers for server side
      */
     public SuperRequest ( SuperActivity act , Class <T> clazz , String reqestUrl , HttpMessageConverter <?> converter , MultiValueMap <String, Object> vars , HttpHeaders headers ) {
          super(clazz);
          this.requestUrl = reqestUrl;
          this.transferingClassType = clazz;
          this.vars = vars;
          this.headers = headers;
          this.converter = converter;
          this.activity = act;
          this.activity.showProgressDialog();
     }

     /**
      * Base loading data from network (Background UI)
      */
     @SuppressWarnings ( "unchecked" ) @Override public T loadDataFromNetwork() throws Exception {
          QLog.info("POST ASK FOR OBJECT " + transferingClassType.getSimpleName() + " URL == (" + this.requestUrl + ")");

          QLog.printMap((Map) httpParams);
          if ( null != vars ) {
               QLog.printMap(vars);
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
      * Basically this key is used by rest template in order to cache response
      * 
      * @return string representation of cache word
      */
     public String createCacheKey() {
          return getClass().getSimpleName() + ".cache";
     }

     private void manageTimeOuts(RestTemplate restTemplate) {
          // set timeout for requests
          ClientHttpRequestFactory factory = restTemplate.getRequestFactory();
          if ( factory instanceof HttpComponentsClientHttpRequestFactory ) {
               HttpComponentsClientHttpRequestFactory advancedFactory = (HttpComponentsClientHttpRequestFactory) factory;
               advancedFactory.setConnectTimeout(WEBSERVICES_TIMEOUT);
               advancedFactory.setReadTimeout(WEBSERVICES_TIMEOUT);
          } else if ( factory instanceof SimpleClientHttpRequestFactory ) {
               SimpleClientHttpRequestFactory advancedFactory = (SimpleClientHttpRequestFactory) factory;
               advancedFactory.setConnectTimeout(WEBSERVICES_TIMEOUT);
               advancedFactory.setReadTimeout(WEBSERVICES_TIMEOUT);
          }
     }
}