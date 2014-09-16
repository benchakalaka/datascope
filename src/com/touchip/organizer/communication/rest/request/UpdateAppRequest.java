package com.touchip.organizer.communication.rest.request;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.converter.ByteArrayHttpMessageConverter;

import com.octo.android.robospice.request.springandroid.SpringAndroidSpiceRequest;
import com.touchip.organizer.activities.StartActivity;
import com.touchip.organizer.utils.HTTP_PARAMS;
import com.touchip.organizer.utils.Utils;

public class UpdateAppRequest extends SpringAndroidSpiceRequest <byte[]> {

     // Variable array for POST request
     private static Map <String, String> vars = new HashMap <String, String>();

     public UpdateAppRequest () {
          super(byte[].class);
          Utils.logw("Post....." + RestAddresses.UPDATE_APPLICATION);
     }

     @Override public byte[] loadDataFromNetwork() throws Exception {

          // if map exist and has elements clear it, instead of creating a new one
          if ( null != vars && !vars.isEmpty() ) {
               vars.clear();
          }

          vars.put(HTTP_PARAMS.VERSION, StartActivity.APP_VERSION);
          getRestTemplate().getMessageConverters().add(new ByteArrayHttpMessageConverter());

          return getRestTemplate().postForObject(RestAddresses.UPDATE_APPLICATION, vars, byte[].class);
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
