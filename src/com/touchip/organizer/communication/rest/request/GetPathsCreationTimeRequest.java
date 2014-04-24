package com.touchip.organizer.communication.rest.request;

import java.util.HashMap;
import java.util.Map;

import com.octo.android.robospice.request.springandroid.SpringAndroidSpiceRequest;
import com.touchip.organizer.communication.rest.model.PathsCreationTimeList;
import com.touchip.organizer.utils.GlobalConstants;
import com.touchip.organizer.utils.Utils;

public class GetPathsCreationTimeRequest extends SpringAndroidSpiceRequest <PathsCreationTimeList> {

     // Variables array for POST request
     private final Map <String, String> vars = new HashMap <String, String>();

     public GetPathsCreationTimeRequest () {
          super(PathsCreationTimeList.class);
          Utils.logw("Post....." + RestAddresses.GET_TIMES_FOR_PATHS);
     }

     @Override public PathsCreationTimeList loadDataFromNetwork() throws Exception {

          vars.put("floor", GlobalConstants.CURRENT_FLOOR);
          vars.put("date", GlobalConstants.SITE_PLAN_IMAGE_NAME);
          vars.put("markerId", GlobalConstants.LAST_CLICKED_MARKER_ID);

          return getRestTemplate().postForObject(RestAddresses.GET_TIMES_FOR_PATHS, vars, PathsCreationTimeList.class);
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
