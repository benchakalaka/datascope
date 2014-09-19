package com.touchip.organizer.communication.rest.request;

import java.util.HashMap;
import java.util.Map;

import com.octo.android.robospice.request.springandroid.SpringAndroidSpiceRequest;
import com.touchip.organizer.communication.rest.model.SuitableOperativesList;
import com.touchip.organizer.constants.RestAddresses;
import com.touchip.organizer.utils.Utils;

public class GetSuitableOperativesListRequest extends SpringAndroidSpiceRequest <SuitableOperativesList> {
     // Variable array for POST request
     private static Map <String, String> vars;

     public GetSuitableOperativesListRequest ( HashMap <String, String> reqValues ) {
          super(SuitableOperativesList.class);
          vars = reqValues;
          Utils.logw("Post....." + RestAddresses.GET_SUITABLE_OPERATIVES + ".............................................................");
     }

     /**
      * Network request
      */
     @Override public SuitableOperativesList loadDataFromNetwork() throws Exception {
          return getRestTemplate().postForObject(RestAddresses.GET_SUITABLE_OPERATIVES, vars, SuitableOperativesList.class);
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
