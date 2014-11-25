package com.touchip.organizer.communication.rest.request.listener;

import quickutils.core.QUFactory.QLog;
import quickutils.core.QUFactory.QNotifications;
import android.content.Intent;

import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;
import com.squareup.timessquare.sample.R;
import com.touchip.organizer.activities.ADrawingCompanies_;
import com.touchip.organizer.activities.SuperActivity;
import com.touchip.organizer.activities.fragments.FragmentCompaniesList;
import com.touchip.organizer.activities.fragments.FragmentCompaniesListOffSite;
import com.touchip.organizer.communication.rest.model.ModelCompaniesAndHotspots;
import com.touchip.organizer.communication.rest.model.ModelHotspotsList;
import com.touchip.organizer.utils.FilterManager;
import com.touchip.organizer.utils.GlobalConstants;

public class CompaniesAndHotspotsRequestListener implements RequestListener <ModelCompaniesAndHotspots> {

     // Reference to activity, for updating ui components
     protected SuperActivity activity;

     // Constructor that accept activity
     public CompaniesAndHotspotsRequestListener ( SuperActivity act ) {
          activity = act;
     }

     // Have got error from server or problem with internet connection (there is no cash available)
     @Override public void onRequestFailure(SpiceException e) {
          // update your UI
          QLog.debug(e.getMessage());
          QNotifications.showShortToast(activity, R.string.connection_problem);
          this.activity.dissmissProgressDialog();
     }

     // Request succesfull, update UI
     @Override public void onRequestSuccess(ModelCompaniesAndHotspots modelCompaniesAndHotspots) {

          FragmentCompaniesList.COMPANIES_LIST = modelCompaniesAndHotspots.companyWrappersList;
          FragmentCompaniesListOffSite.COMPANIES_LIST = modelCompaniesAndHotspots.companyWrappersList;

          GlobalConstants.SIGNED_HOTSPOTS_ALL = modelCompaniesAndHotspots.hotSpotWrapperList;
          GlobalConstants.UNASSIGNED_HOTSPOTS_ALL = modelCompaniesAndHotspots.unassignHotspotsWrapperList;

          GlobalConstants.SIGNED_HOTSPOTS = new ModelHotspotsList();
          GlobalConstants.UNASSIGNED_HOTSPOTS = new ModelHotspotsList();

          for ( int i = 0; i < modelCompaniesAndHotspots.hotSpotWrapperList.size(); i++ ) {
               if ( FilterManager.displayCompleted == modelCompaniesAndHotspots.hotSpotWrapperList.get(i).isCompleted ) {
                    GlobalConstants.SIGNED_HOTSPOTS.add(modelCompaniesAndHotspots.hotSpotWrapperList.get(i));
               }
          }

          for ( int i = 0; i < modelCompaniesAndHotspots.unassignHotspotsWrapperList.size(); i++ ) {
               if ( FilterManager.displayCompleted == modelCompaniesAndHotspots.unassignHotspotsWrapperList.get(i).isCompleted ) {
                    GlobalConstants.UNASSIGNED_HOTSPOTS.add(modelCompaniesAndHotspots.unassignHotspotsWrapperList.get(i));
               }
          }

          GlobalConstants.TODAY_FROM_SERVER = modelCompaniesAndHotspots.today;

          activity.dissmissProgressDialog();
          Intent i = new Intent(activity, ADrawingCompanies_.class);
          i.addFlags(Intent.FLAG_ACTIVITY_TASK_ON_HOME);
          this.activity.dissmissProgressDialog();
          activity.startActivity(i);
     }
}
