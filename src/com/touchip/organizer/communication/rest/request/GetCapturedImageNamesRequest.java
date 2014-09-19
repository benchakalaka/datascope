package com.touchip.organizer.communication.rest.request;

import java.util.HashMap;
import java.util.Map;

import com.octo.android.robospice.request.springandroid.SpringAndroidSpiceRequest;
import com.touchip.organizer.communication.rest.model.CapturedImagesUrlList;
import com.touchip.organizer.constants.GlobalConstants;
import com.touchip.organizer.constants.RestAddresses;
import com.touchip.organizer.utils.Utils;

public class GetCapturedImageNamesRequest extends SpringAndroidSpiceRequest <CapturedImagesUrlList> {

     // Variables array for POST request
     private final Map <String, String> vars = new HashMap <String, String>();

     public GetCapturedImageNamesRequest () {
          super(CapturedImagesUrlList.class);
          Utils.logw("Post....." + RestAddresses.GET_CAPTURED_IMAGES_LIST);
     }

     @Override public CapturedImagesUrlList loadDataFromNetwork() throws Exception {
          vars.put("id", String.valueOf(GlobalConstants.LAST_CLICKED_HOTSPOT.id));
          return getRestTemplate().postForObject(RestAddresses.GET_CAPTURED_IMAGES_LIST, vars, CapturedImagesUrlList.class);
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
