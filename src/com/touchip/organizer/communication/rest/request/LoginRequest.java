package com.touchip.organizer.communication.rest.request;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import com.octo.android.robospice.request.springandroid.SpringAndroidSpiceRequest;
import com.touchip.organizer.activities.TvActivity;
import com.touchip.organizer.communication.rest.model.User;
import com.touchip.organizer.utils.Utils;

public class LoginRequest extends SpringAndroidSpiceRequest <User> {

     // Variable array for POST request
     private final Map <String, String> vars;

     int                                WEBSERVICES_TIMEOUT = 5000;

     public LoginRequest ( HashMap <String, String> params ) {
          super(User.class);
          Utils.logw("Post....." + RestAddresses.LOGIN);
          this.vars = params;
          if ( null != TvActivity.progressDialog ) {
               TvActivity.progressDialog.show();
          }
     }

     @Override public User loadDataFromNetwork() throws Exception {
          return getRestTemplate().postForObject(/* "http://10.1.254.154:39064/api/Login/Login" */RestAddresses.LOGIN, vars, User.class);
     }

     /**
      * This method generates a unique cache key for this request.
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
