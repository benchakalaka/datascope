package com.touchip.organizer.communication.rest.request;

import java.util.HashMap;
import java.util.Map;

import com.octo.android.robospice.request.springandroid.SpringAndroidSpiceRequest;
import com.touchip.organizer.communication.rest.model.CompaniesAndHotspots;
import com.touchip.organizer.utils.GlobalConstants;
import com.touchip.organizer.utils.HTTP_PARAMS;
import com.touchip.organizer.utils.Utils;

public class CompaniesAndHotspotsRequest extends SpringAndroidSpiceRequest <CompaniesAndHotspots> {

     // Variable array for POST request
     private static Map <String, String> vars = new HashMap <String, String>();

     public CompaniesAndHotspotsRequest () {
          super(CompaniesAndHotspots.class);
          Utils.logw("Post....." + RestAddresses.GET_ALL_COMPANIES);
     }

     /**
      * Network request
      */
     @Override public CompaniesAndHotspots loadDataFromNetwork() throws Exception {
          // if map exist and has elements clear it, instead of creating a new one
          if ( null != vars && !vars.isEmpty() ) {
               vars.clear();
          }

          vars.put(HTTP_PARAMS.DATE, GlobalConstants.SITE_PLAN_IMAGE_NAME);
          vars.put(HTTP_PARAMS.FLOOR, GlobalConstants.CURRENT_FLOOR);
          vars.put(/* HTTP_PARAMS.SITE_ID */"markerId", GlobalConstants.LAST_CLICKED_MARKER_ID);
          return getRestTemplate().postForObject(RestAddresses.GET_ALL_COMPANIES, vars, CompaniesAndHotspots.class);
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
