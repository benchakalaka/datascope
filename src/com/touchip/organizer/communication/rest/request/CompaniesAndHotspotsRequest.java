package com.touchip.organizer.communication.rest.request;

import java.util.Map;

import quickutils.core.QUFactory.QCollection;
import quickutils.core.QUFactory.QLog;

import com.octo.android.robospice.request.springandroid.SpringAndroidSpiceRequest;
import com.touchip.organizer.communication.rest.model.ModelCompaniesAndHotspots;
import com.touchip.organizer.utils.GlobalConstants;
import com.touchip.organizer.utils.HTTP_PARAMS;

public class CompaniesAndHotspotsRequest extends SpringAndroidSpiceRequest <ModelCompaniesAndHotspots> {

     // Variable array for POST request
     private static Map <String, String> vars = QCollection.newHashMap();

     public CompaniesAndHotspotsRequest () {
          super(ModelCompaniesAndHotspots.class);
          QLog.debug("Post....." + RestAddresses.GET_ALL_COMPANIES);
     }

     /**
      * Network request
      */
     @Override public ModelCompaniesAndHotspots loadDataFromNetwork() throws Exception {
          // if map exist and has elements clear it, instead of creating a new one
          if ( null != vars && !vars.isEmpty() ) {
               vars.clear();
          }
          vars.put(HTTP_PARAMS.USER_ID, String.valueOf(GlobalConstants.CURRENT_USER.userId));
          vars.put(HTTP_PARAMS.COMPANY_ID, String.valueOf(GlobalConstants.CURRENT_USER.companyId));
          vars.put(HTTP_PARAMS.DATE, GlobalConstants.SITE_PLAN_IMAGE_NAME);
          vars.put(HTTP_PARAMS.FLOOR, GlobalConstants.CURRENT_FLOOR);
          vars.put(HTTP_PARAMS.MARKER_ID, GlobalConstants.LAST_CLICKED_MARKER_ID);
          return getRestTemplate().postForObject(RestAddresses.GET_ALL_COMPANIES, vars, ModelCompaniesAndHotspots.class);
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
