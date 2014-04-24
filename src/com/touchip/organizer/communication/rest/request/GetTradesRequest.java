package com.touchip.organizer.communication.rest.request;

import java.util.HashMap;
import java.util.Map;

import com.octo.android.robospice.request.springandroid.SpringAndroidSpiceRequest;
import com.touchip.organizer.communication.rest.model.HotspotsList;
import com.touchip.organizer.utils.DataAccess;
import com.touchip.organizer.utils.GlobalConstants;
import com.touchip.organizer.utils.Utils;

public class GetTradesRequest extends SpringAndroidSpiceRequest <HotspotsList> {

     // Variable array for POST request
     private static Map <String, String> vars = new HashMap <String, String>();

     public GetTradesRequest () {
          super(HotspotsList.class);
          Utils.logw("Post....." + RestAddresses.GET_TRADES);
     }

     @Override public HotspotsList loadDataFromNetwork() throws Exception {
          // if map exist and has elements clear it, instead of creating a new one
          if ( null != vars && !vars.isEmpty() ) {
               vars.clear();
          }
          vars.put("companyId", String.valueOf(DataAccess.LAST_CLICKED_COMPANY.companyId));
          vars.put("date", GlobalConstants.SITE_PLAN_IMAGE_NAME);
          return getRestTemplate().postForObject(RestAddresses.GET_TRADES, vars, HotspotsList.class);
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
