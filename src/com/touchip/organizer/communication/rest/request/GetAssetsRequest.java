package com.touchip.organizer.communication.rest.request;

import java.util.HashMap;
import java.util.Map;

import com.octo.android.robospice.request.springandroid.SpringAndroidSpiceRequest;
import com.touchip.organizer.communication.rest.model.AssetsList;
import com.touchip.organizer.utils.DataAccess;
import com.touchip.organizer.utils.GlobalConstants;
import com.touchip.organizer.utils.HTTP_PARAMS;
import com.touchip.organizer.utils.Utils;

public class GetAssetsRequest extends SpringAndroidSpiceRequest <AssetsList> {

     // Variable array for POST request
     private static Map <String, String> vars = new HashMap <String, String>();

     public GetAssetsRequest () {
          super(AssetsList.class);
          Utils.logw("Post....." + RestAddresses.GET_ASSETS_LIST + ".............................................................");
     }

     /**
      * Network request
      */
     @Override public AssetsList loadDataFromNetwork() throws Exception {
          // if map exist and has elements clear it, instead of creating a new one
          if ( null != vars && !vars.isEmpty() ) {
               vars.clear();
          }

          vars.put(HTTP_PARAMS.COMPANY_ID, String.valueOf(DataAccess.LAST_CLICKED_COMPANY.companyId));
          vars.put(/* HTTP_PARAMS.SITE_ID */"markerId", String.valueOf(GlobalConstants.LAST_CLICKED_MARKER_ID));

          return getRestTemplate().postForObject(RestAddresses.GET_ASSETS_LIST, vars, AssetsList.class);
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
