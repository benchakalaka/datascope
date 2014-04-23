package com.touchip.organizer.communication.rest.request;

import java.nio.charset.Charset;

import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.octo.android.robospice.request.springandroid.SpringAndroidSpiceRequest;
import com.touchip.organizer.utils.GlobalConstants;
import com.touchip.organizer.utils.Utils;

public class SaveDrawingPathsRequest extends SpringAndroidSpiceRequest <String> {

     // Variable array for POST request
     private static MultiValueMap <String, Object> vars = new LinkedMultiValueMap <String, Object>();
     private final FileSystemResource              file;

     public SaveDrawingPathsRequest ( FileSystemResource file ) {
          super(String.class);
          this.file = file;
          Utils.logw("Post....." + RestAddresses.SAVE_DRAWING_PATHES);
     }

     /**
      * Network request
      */
     @Override public String loadDataFromNetwork() throws Exception {
          // if map exist and has elements clear it, instead of creating a new one
          if ( null != vars && !vars.isEmpty() ) {
               vars.clear();
          }
          vars.add("markerId", GlobalConstants.LAST_CLICKED_MARKER_ID);
          vars.add("drawingData", file);
          vars.add("type", GlobalConstants.DrawingType.SITE_PLAN_DRAWING);
          vars.add("date", GlobalConstants.SITE_PLAN_IMAGE_NAME);
          vars.add("floor", GlobalConstants.CURRENT_FLOOR);

          HttpHeaders headers = new HttpHeaders();
          headers.setContentType(MediaType.MULTIPART_FORM_DATA);
          headers.add("Accept", "text/html;charset=utf-8");

          getRestTemplate().getMessageConverters().add(new StringHttpMessageConverter(Charset.forName("utf-8")));

          return getRestTemplate()
                    .exchange(RestAddresses.SAVE_DRAWING_PATHES, HttpMethod.POST, new HttpEntity <MultiValueMap <String, Object>>(vars, headers), String.class).getBody();

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
