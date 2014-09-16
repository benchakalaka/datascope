package com.touchip.organizer.utils;

import com.touchip.organizer.communication.rest.model.AssetsList.POJORoboSingleAsset;
import com.touchip.organizer.communication.rest.model.CompaniesList.POJORoboCompany;
import com.touchip.organizer.communication.rest.model.DatesToHighlightList;
import com.touchip.organizer.communication.rest.model.HotspotsList;

public class DataAccess {
     /**
      * FragmentCompaniesList data
      */
     public static POJORoboCompany      LAST_CLICKED_COMPANY;

     /**
      * MapActivity Data
      */
     public static DatesToHighlightList datestoHighlight;

     /**
      * DrawingCompaniesData Data
      */
     public static HotspotsList         SIGNED_HOTSPOTS;
     public static HotspotsList         UNASSIGNED_HOTSPOTS;

     public static POJORoboSingleAsset  LAST_CLICKED_ASSET;

     private DataAccess () {
     }
}
