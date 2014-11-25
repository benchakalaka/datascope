package com.touchip.organizer.communication.rest.request;

import java.util.HashMap;
import java.util.Map;

import quickutils.core.QUFactory.QLog;

import com.octo.android.robospice.request.springandroid.SpringAndroidSpiceRequest;
import com.touchip.organizer.communication.rest.model.ModelDelivery;

public class GetDeliveriesListRequest extends SpringAndroidSpiceRequest <ModelDelivery> {

     // Variable array for POST request
     private final Map <String, String> vars;

     public GetDeliveriesListRequest ( HashMap <String, String> params ) {
          super(ModelDelivery.class);
          this.vars = params;
          QLog.debug("Post....." + RestAddresses.GET_DELIVERIES_LIST + ".............................................................");
     }

     /**
      * Network request
      */
     @Override public ModelDelivery loadDataFromNetwork() throws Exception {
          return getRestTemplate().postForObject(RestAddresses.GET_DELIVERIES_LIST, vars, ModelDelivery.class);
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
