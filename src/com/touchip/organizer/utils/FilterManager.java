package com.touchip.organizer.utils;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import quickutils.core.QUFactory.QPreconditions;
import android.graphics.Color;

import com.touchip.organizer.activities.fragments.FragmentCompaniesList;
import com.touchip.organizer.communication.rest.model.ModelHotspotsList;
import com.touchip.organizer.utils.GlobalConstants.Hotspots;

public class FilterManager {

     public static boolean               displayCompleted     = false;

     /**
      * Display hotspot id on canvas
      */
     public static boolean               displayHSId          = false;
     /**
      * Set which contains companies colors which should be displayed on canvas
      */
     public static Set <Integer>         activeCompaniesColor = new HashSet <Integer>();

     /**
      * Hotspots asotiated array: <"Waste", true> - means that waste hotspot shopuld be displayed
      * 0 - NOTE
      * 1 - SAFETY
      * 2 - WASTE
      * 3 - PERMIT
      * 4 - WB
      * 5 - TRADE
      * 6 - ASSET
      * 7 - CCTV
      */
     public static Map <String, Boolean> activeHotspots       = new TreeMap <String, Boolean>(String.CASE_INSENSITIVE_ORDER);

     /**
      * Set state all hotspots on canvas
      */
     public static void setFilterHotstpotsState(boolean value) {
          activeHotspots.put(Hotspots.NOTE_HOTSPOT, value);
          activeHotspots.put(Hotspots.SAFETY_HOTSPOT, value);
          activeHotspots.put(Hotspots.WASTE_HOTSPOT, value);
          activeHotspots.put(Hotspots.PERMITS_HOTSPOT, value);
          activeHotspots.put(Hotspots.WHITEBOARD_HOTSPOT, value);
          activeHotspots.put(Hotspots.TRADE_HOTSPOT, value);
          activeHotspots.put(Hotspots.ASSET_HOTSPOT, value);
          activeHotspots.put(Hotspots.CAMERA_HOTSPOT, value);
          activeHotspots.put(Hotspots.QUICK_NOTE_HOTSPOT, value);
          activeHotspots.put(Hotspots.HIGH_RISK, value);
          activeHotspots.put(Hotspots.ON_THE_FLY, value);
     }

     public static ModelHotspotsList getFilteredAssignedHotspots() {
          ModelHotspotsList retHotspots = new ModelHotspotsList();
          for ( int i = 0; i < GlobalConstants.SIGNED_HOTSPOTS.size(); i++ ) {

               int companyId = GlobalConstants.SIGNED_HOTSPOTS.get(i).companyId;
               int companyColor = FragmentCompaniesList.getCompanyColorById(companyId);
               if ( FilterManager.activeCompaniesColor.contains(companyColor) ) {
                    retHotspots.add(GlobalConstants.SIGNED_HOTSPOTS.get(i));
               }
          }
          return retHotspots;
     }

     public static void deactivateAllCompaniesDrawing() {
          if ( !QPreconditions.isNull(FragmentCompaniesList.COMPANIES_LIST) ) {
               // clear set if we have colours
               if ( !QPreconditions.isNullOrEmpty(FilterManager.activeCompaniesColor) ) {
                    FilterManager.activeCompaniesColor.clear();
               }
          }
     }

     public static void activateAllCompaniesDrawing() {
          // if companies list is not emptu
          if ( !QPreconditions.isNull(FragmentCompaniesList.COMPANIES_LIST) ) {
               // clear set if we have colours set
               if ( !QPreconditions.isNullOrEmpty(FilterManager.activeCompaniesColor) ) {
                    FilterManager.activeCompaniesColor.clear();
               }
               for ( int i = 0; i < FragmentCompaniesList.COMPANIES_LIST.size(); i++ ) {
                    FilterManager.activeCompaniesColor.add(Integer.valueOf(Color.parseColor(FragmentCompaniesList.COMPANIES_LIST.get(i).colour)));
               }
          }
     }
}
