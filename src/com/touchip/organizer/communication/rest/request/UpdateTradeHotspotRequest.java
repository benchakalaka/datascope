package com.touchip.organizer.communication.rest.request;

import java.util.Map;

import com.octo.android.robospice.request.springandroid.SpringAndroidSpiceRequest;
import com.touchip.organizer.communication.rest.model.HotspotsList;
import com.touchip.organizer.constants.RestAddresses;
import com.touchip.organizer.utils.Utils;

public class UpdateTradeHotspotRequest extends SpringAndroidSpiceRequest <HotspotsList> {

     // Variable array for POST request
     private static Map <String, Integer> vars;

     public UpdateTradeHotspotRequest ( Map <String, Integer> variables ) {
          super(HotspotsList.class);
          vars = variables;
          Utils.logw("Post....." + RestAddresses.UPDATE_TRADE_HOTSPOT);
     }

     @Override public HotspotsList loadDataFromNetwork() throws Exception {
          return getRestTemplate().postForObject(RestAddresses.UPDATE_TRADE_HOTSPOT, vars, HotspotsList.class);
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
