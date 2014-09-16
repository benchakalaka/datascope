package com.touchip.organizer.communication.rest.request.listener;

import java.io.IOException;

import org.springframework.util.support.Base64;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;
import com.squareup.timessquare.sample.R;
import com.touchip.organizer.activities.DrawingCompaniesActivity;
import com.touchip.organizer.activities.DrawingCompaniesActivity_;
import com.touchip.organizer.activities.TvActivity;
import com.touchip.organizer.activities.custom.components.CompaniesDrawingView;
import com.touchip.organizer.activities.fragments.FragmentCompaniesList;
import com.touchip.organizer.activities.fragments.FragmentCompaniesListOffSite;
import com.touchip.organizer.communication.rest.model.CompaniesAndHotspots;
import com.touchip.organizer.utils.DataAccess;
import com.touchip.organizer.utils.GlobalConstants;
import com.touchip.organizer.utils.Utils;

public class ResponseFullSitePlanInfo implements RequestListener <CompaniesAndHotspots> {

     // Reference to activity, for updating ui components
     protected Activity activity;

     // Constructor that accept activity
     public ResponseFullSitePlanInfo ( Activity act ) {
          activity = act;
     }

     // Have got error from server or problem with internet connection (there is no cash available)
     @Override public void onRequestFailure(SpiceException e) {
          // update your UI
          Utils.logw(e.getMessage());
          Utils.showToast(activity, R.string.connection_problem, true);
          TvActivity.dissmissProgressDialog();
     }

     // Request succesfull, update UI
     @Override public void onRequestSuccess(CompaniesAndHotspots companiesAndHotspots) {
          if ( null != companiesAndHotspots ) {
               FragmentCompaniesList.COMPANIES_LIST = companiesAndHotspots.companyWrappersList;
               FragmentCompaniesListOffSite.COMPANIES_LIST = companiesAndHotspots.companyWrappersList;
               DataAccess.SIGNED_HOTSPOTS = companiesAndHotspots.hotSpotWrapperList;
               DataAccess.UNASSIGNED_HOTSPOTS = companiesAndHotspots.unassignHotspotsWrapperList;
               GlobalConstants.TODAY_FROM_SERVER = companiesAndHotspots.today;

               byte[] imageByteArray = null;
               try {
                    imageByteArray = Base64.decode(companiesAndHotspots.sitePlanImage);
                    if ( null != imageByteArray ) {
                         Bitmap bitmap = BitmapFactory.decodeByteArray(imageByteArray, 0, imageByteArray.length);
                         CompaniesDrawingView.canvasBitmap = bitmap;
                         CompaniesDrawingView.startBitmap = bitmap;
                    }

               } catch (IOException e) {
                    e.printStackTrace();
               }

               // TvActivity.dissmissProgressDialog();
               Intent i = new Intent(activity, DrawingCompaniesActivity_.class);
               i.addFlags(Intent.FLAG_ACTIVITY_TASK_ON_HOME);
               DrawingCompaniesActivity.dissmissProgressDialog();
               activity.startActivity(i);
          } else {
               Utils.showCustomToast(activity, "error occured", R.drawable.hide_hotspot);
          }
     }
}
