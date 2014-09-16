package com.touchip.organizer.communication.rest.request;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.util.MultiValueMap;

import com.octo.android.robospice.request.springandroid.SpringAndroidSpiceRequest;
import com.touchip.organizer.utils.Utils;

public class SaveHSWhiteboardRequest extends SpringAndroidSpiceRequest <String> {

     // Variables array for POST request
     MultiValueMap <String, Object> params;
     private final String           requestUrl;

     public SaveHSWhiteboardRequest ( MultiValueMap <String, Object> vars , String requestUrl ) {
          super(String.class);
          this.requestUrl = requestUrl;
          this.params = vars;
          Utils.logw("Post....." + requestUrl);
     }

     @Override public String loadDataFromNetwork() throws Exception {
          HttpHeaders headers = new HttpHeaders();
          headers.setContentType(MediaType.MULTIPART_FORM_DATA);
          getRestTemplate().getMessageConverters().add(new FormHttpMessageConverter());
          return getRestTemplate().exchange(requestUrl, HttpMethod.POST, new HttpEntity <MultiValueMap <String, Object>>(params, headers), String.class).getBody();
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