package com.touchip.organizer.communication.rest.request;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.converter.ByteArrayHttpMessageConverter;

import com.octo.android.robospice.request.springandroid.SpringAndroidSpiceRequest;
import com.touchip.organizer.constants.GlobalConstants;
import com.touchip.organizer.constants.HTTP_PARAMS;
import com.touchip.organizer.constants.RestAddresses;
import com.touchip.organizer.utils.Utils;

public class DownloadSitePlanWithFloorRequest extends SpringAndroidSpiceRequest <byte[]> {

     // Variable array for POST request
     private static Map <String, String> vars = new HashMap <String, String>();

     public DownloadSitePlanWithFloorRequest () {
          super(byte[].class);
          Utils.logw("Post....." + RestAddresses.DOWNLOAD_SITE_PLAN);
     }

     @Override public byte[] loadDataFromNetwork() throws Exception {

          // if map exist and has elements clear it, instead of creating a new one
          if ( null != vars && !vars.isEmpty() ) {
               vars.clear();
          }
          vars.put(HTTP_PARAMS.DATE, GlobalConstants.SITE_PLAN_IMAGE_NAME + "_" + GlobalConstants.SITE_PLAN_FULL_INFO.currentArea);
          vars.put(/* HTTP_PARAMS.SITE_ID */"markerId", GlobalConstants.SITE_ID);
          getRestTemplate().getMessageConverters().add(new ByteArrayHttpMessageConverter());
          return getRestTemplate().postForObject(RestAddresses.DOWNLOAD_SITE_PLAN, vars, byte[].class);
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
