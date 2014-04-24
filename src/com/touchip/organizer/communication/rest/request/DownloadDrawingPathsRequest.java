package com.touchip.organizer.communication.rest.request;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.converter.ByteArrayHttpMessageConverter;

import com.octo.android.robospice.request.springandroid.SpringAndroidSpiceRequest;
import com.touchip.organizer.utils.GlobalConstants;
import com.touchip.organizer.utils.Utils;

public class DownloadDrawingPathsRequest extends SpringAndroidSpiceRequest <byte[]> {

     // Variable array for POST request
     private static Map <String, String> vars = new HashMap <String, String>();

     public DownloadDrawingPathsRequest () {
          super(byte[].class);
          Utils.logw("Post....." + RestAddresses.DOWNLOAD_DRAWING_PATHES);
     }

     @Override public byte[] loadDataFromNetwork() throws Exception {

          // if map exist and has elements clear it, instead of creating a new one
          if ( null != vars && !vars.isEmpty() ) {
               vars.clear();
          }

          vars.put("markerId", GlobalConstants.LAST_CLICKED_MARKER_ID);
          vars.put("date", GlobalConstants.SITE_PLAN_IMAGE_NAME);
          vars.put("floor", GlobalConstants.CURRENT_FLOOR);
          vars.put("type", GlobalConstants.DrawingType.SITE_PLAN_DRAWING);
          getRestTemplate().getMessageConverters().add(new ByteArrayHttpMessageConverter());

          return getRestTemplate().postForObject(RestAddresses.DOWNLOAD_DRAWING_PATHES, vars, byte[].class);
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
