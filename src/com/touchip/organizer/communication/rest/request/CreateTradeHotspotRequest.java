package com.touchip.organizer.communication.rest.request;

import java.util.Map;

import com.octo.android.robospice.request.springandroid.SpringAndroidSpiceRequest;
import com.touchip.organizer.communication.rest.model.HotspotsList;
import com.touchip.organizer.utils.Utils;

public class CreateTradeHotspotRequest extends SpringAndroidSpiceRequest <HotspotsList> {

     // Variable array for POST request
     private static Map <String, String> vars;

     public CreateTradeHotspotRequest ( Map <String, String> variables ) {
          super(HotspotsList.class);
          vars = variables;
          Utils.logw("Post....." + RestAddresses.CREATE_TRADE_HOTSPOT);
     }

     @Override public HotspotsList loadDataFromNetwork() throws Exception {
          return getRestTemplate().postForObject(RestAddresses.CREATE_TRADE_HOTSPOT, vars, HotspotsList.class);
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
