package com.touchip.organizer.communication.rest.request;

import java.util.Map;

import com.octo.android.robospice.request.springandroid.SpringAndroidSpiceRequest;
import com.touchip.organizer.communication.rest.model.ModelFullSiteInfo;
import com.touchip.organizer.constants.RestAddresses;
import com.touchip.organizer.utils.Utils;

public class CreateAssetHotspotRequest extends SpringAndroidSpiceRequest <ModelFullSiteInfo> {

     // Variable array for POST request
     private static Map <String, String> vars;

     public CreateAssetHotspotRequest ( Map <String, String> variables ) {
          super(ModelFullSiteInfo.class);
          vars = variables;
          Utils.logw("Post....." + RestAddresses.CREATE_HOTSPOT);
     }

     @Override public ModelFullSiteInfo loadDataFromNetwork() throws Exception {
          return getRestTemplate().postForObject(RestAddresses.CREATE_HOTSPOT, vars, ModelFullSiteInfo.class);
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
