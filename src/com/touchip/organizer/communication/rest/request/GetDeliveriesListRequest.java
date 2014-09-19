package com.touchip.organizer.communication.rest.request;

import java.util.HashMap;
import java.util.Map;

import com.octo.android.robospice.request.springandroid.SpringAndroidSpiceRequest;
import com.touchip.organizer.communication.rest.model.Delivery;
import com.touchip.organizer.constants.RestAddresses;
import com.touchip.organizer.utils.Utils;

public class GetDeliveriesListRequest extends SpringAndroidSpiceRequest <Delivery> {

     // Variable array for POST request
     private final Map <String, String> vars;

     public GetDeliveriesListRequest ( HashMap <String, String> params ) {
          super(Delivery.class);
          this.vars = params;
          Utils.logw("Post....." + RestAddresses.GET_DELIVERIES_LIST + ".............................................................");
     }

     /**
      * Network request
      */
     @Override public Delivery loadDataFromNetwork() throws Exception {
          return getRestTemplate().postForObject(RestAddresses.GET_DELIVERIES_LIST, vars, Delivery.class);
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
