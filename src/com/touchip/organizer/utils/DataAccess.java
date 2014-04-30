package com.touchip.organizer.utils;

import java.util.ArrayList;
import java.util.List;

import com.touchip.organizer.activities.custom.components.TradesView;
import com.touchip.organizer.communication.rest.model.CompaniesList.POJORoboCompany;
import com.touchip.organizer.communication.rest.model.DatesToHighlightList;
import com.touchip.organizer.communication.rest.model.HotspotsList;
import com.touchip.organizer.communication.rest.model.HotspotsList.POJORoboHotspot;

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
     public static HotspotsList         TRADES_ON_SITE;
     public static List <TradesView>    ARRAY_TRADES_ITEMS = new ArrayList <TradesView>();
     public static HotspotsList         SIGNED_HOTSPOTS;
<<<<<<< HEAD
     public static HotspotsList         UNASSIGNED_HOTSPOTS;
=======
>>>>>>> 78b3b7a5c2e64f02d5ae56556b9490c6f58c1ad9

     private DataAccess () {

     }

     public static POJORoboHotspot findTradeHotspotById(int id) {
          for ( int i = 0; i < DataAccess.TRADES_ON_SITE.size(); i++ ) {
               if ( DataAccess.TRADES_ON_SITE.get(i).id == id ) { return DataAccess.TRADES_ON_SITE.get(i); }
          }
          return null;
     }

     public static POJORoboHotspot findTradeHotspotByDescription(String description) {
          for ( int i = 0; i < DataAccess.SIGNED_HOTSPOTS.size(); i++ ) {
               if ( DataAccess.SIGNED_HOTSPOTS.get(i).description.equals(description) ) { return DataAccess.SIGNED_HOTSPOTS.get(i); }
          }
          return null;
     }

     public static TradesView findTradeViewByDescription(String description) {
          for ( int i = 0; i < ARRAY_TRADES_ITEMS.size(); i++ ) {
               if ( ARRAY_TRADES_ITEMS.get(i).twTradeDescription.getText().toString().contains(description) ) { return ARRAY_TRADES_ITEMS.get(i); }
          }
          return null;
     }
}
