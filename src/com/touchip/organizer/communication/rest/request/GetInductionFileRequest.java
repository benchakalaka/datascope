package com.touchip.organizer.communication.rest.request;

import java.util.Map;

import org.springframework.http.converter.ByteArrayHttpMessageConverter;

import quickutils.core.QUFactory.QCollection;
import quickutils.core.QUFactory.QLog;

import com.octo.android.robospice.request.springandroid.SpringAndroidSpiceRequest;
import com.touchip.organizer.utils.GlobalConstants;
import com.touchip.organizer.utils.HTTP_PARAMS;

public class GetInductionFileRequest extends SpringAndroidSpiceRequest <byte[]> {

     // Variable array for POST request
     private final Map <String, String> vars = QCollection.newHashMap();
     private final String               filename;

     public GetInductionFileRequest ( String fName ) {
          super(byte[].class);
          this.filename = fName;
          QLog.debug("Post....." + RestAddresses.GET_INDUCTION_FILE_BY_NAME);
     }

     @Override public byte[] loadDataFromNetwork() throws Exception {
          vars.put(HTTP_PARAMS.SITE_ID, String.valueOf(GlobalConstants.LAST_CLICKED_MARKER_ID));
          vars.put(HTTP_PARAMS.FILE_NAME, this.filename);
          getRestTemplate().getMessageConverters().add(new ByteArrayHttpMessageConverter());
          return getRestTemplate().postForObject(RestAddresses.GET_INDUCTION_FILE_BY_NAME, vars, byte[].class);
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
