package com.touchip.organizer.communication.rest.request;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.util.MultiValueMap;

import quickutils.core.QUFactory.QLog;

import com.octo.android.robospice.request.springandroid.SpringAndroidSpiceRequest;

public class UploadCapturedPhotoRequest extends SpringAndroidSpiceRequest <String> {

     // Variable array for POST request
     private static MultiValueMap <String, Object> vars;

     public UploadCapturedPhotoRequest ( MultiValueMap <String, Object> parts ) {
          super(String.class);
          vars = parts;
          QLog.debug("Post....." + RestAddresses.UPLOAD_CAPTURED_PHOTO);
     }

     @Override public String loadDataFromNetwork() throws Exception {
          HttpHeaders headers = new HttpHeaders();
          headers.setContentType(MediaType.MULTIPART_FORM_DATA);
          getRestTemplate().getMessageConverters().add(new FormHttpMessageConverter());
          return getRestTemplate().exchange(RestAddresses.UPLOAD_CAPTURED_PHOTO, HttpMethod.POST, new HttpEntity <MultiValueMap <String, Object>>(vars, headers), String.class).getBody();
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
