package com.touchip.organizer.communication.rest.request;

import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.octo.android.robospice.request.springandroid.SpringAndroidSpiceRequest;
import com.touchip.organizer.utils.GlobalConstants;
import com.touchip.organizer.utils.HTTP_PARAMS;
import com.touchip.organizer.utils.Utils;

public class SaveDrawingPathsRequest extends SpringAndroidSpiceRequest <String> {

     // Variable array for POST request
     private static MultiValueMap <String, Object> vars = new LinkedMultiValueMap <String, Object>();
     private final FileSystemResource              file;
     private final String                          currentFloor;
     private final FileSystemResource              snapshot;

     public SaveDrawingPathsRequest ( FileSystemResource file , FileSystemResource snapshot , String currFloor ) {
          super(String.class);
          this.file = file;
          this.snapshot = snapshot;
          Utils.logw("Post....." + RestAddresses.SAVE_DRAWING_PATHES);
          this.currentFloor = currFloor;
     }

     /**
      * Network request
      */
     @Override public String loadDataFromNetwork() throws Exception {
          // if map exist and has elements clear it, instead of creating a new one
          if ( null != vars && !vars.isEmpty() ) {
               vars.clear();
          }

          vars.add(/* HTTP_PARAMS.SITE_ID */"markerId", GlobalConstants.LAST_CLICKED_MARKER_ID);
          vars.add(HTTP_PARAMS.DRAWING_DATA, file);
          vars.add(HTTP_PARAMS.TYPE, GlobalConstants.DrawingType.SITE_PLAN_DRAWING);
          vars.add(HTTP_PARAMS.DATE, GlobalConstants.SITE_PLAN_IMAGE_NAME);
          vars.add(HTTP_PARAMS.FLOOR, this.currentFloor);
          vars.add(HTTP_PARAMS.SNAPSHOT, snapshot);

          HttpHeaders headers = new HttpHeaders();
          headers.setContentType(MediaType.MULTIPART_FORM_DATA);
          getRestTemplate().getMessageConverters().add(new FormHttpMessageConverter());
          return getRestTemplate().exchange(RestAddresses.SAVE_DRAWING_PATHES, HttpMethod.POST, new HttpEntity <MultiValueMap <String, Object>>(vars, headers), String.class).getBody();
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
