package com.touchip.organizer.communication.rest.request;

import java.util.Map;

import org.springframework.http.converter.ByteArrayHttpMessageConverter;

import com.octo.android.robospice.request.springandroid.SpringAndroidSpiceRequest;
import com.touchip.organizer.utils.Utils;

public class DownloadOrCreateNewWhiteboardRequest extends SpringAndroidSpiceRequest <byte[]> {

     // Variables array for POST request
     private final Map <String, String> vars;
     private final String               requestUrl;

     public DownloadOrCreateNewWhiteboardRequest ( Map <String, String> params , String requestUrl ) {
          super(byte[].class);
          this.requestUrl = requestUrl;
          vars = params;
          Utils.logw("Post....." + requestUrl);
     }

     @Override public byte[] loadDataFromNetwork() throws Exception {
          getRestTemplate().getMessageConverters().add(new ByteArrayHttpMessageConverter());
          return getRestTemplate().postForObject(requestUrl, vars, byte[].class);
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
