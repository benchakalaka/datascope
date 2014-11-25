package com.touchip.organizer.activities.custom.components.dialogs;

import java.util.HashMap;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

import quickutils.core.QUFactory.QCollection;
import android.app.Dialog;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.squareup.timessquare.sample.R;
import com.touchip.organizer.activities.SuperActivity;
import com.touchip.organizer.communication.rest.model.ModelCompaniesAndHotspots;
import com.touchip.organizer.communication.rest.request.RestAddresses;
import com.touchip.organizer.communication.rest.request.SuperRequest;
import com.touchip.organizer.communication.rest.request.listener.ResponseCompleteHotspot;
import com.touchip.organizer.utils.GlobalConstants;
import com.touchip.organizer.utils.HTTP_PARAMS;

@EViewGroup ( R.layout.dialog_complete_hotspot ) public class CDialogCompleteHotspot extends RelativeLayout {

     // ===================== views
     @ViewById Button            btnCancel , btnOk;

     // ====================== variable
     private final SuperActivity activity;
     private final Dialog        dialog , hsDetailDialog;

     private final int           hotspotId;

     public CDialogCompleteHotspot ( SuperActivity act , Dialog hostDialog , int hotspotId , Dialog hotspotDetailHostDialog ) {
          super(act);
          this.activity = act;
          this.dialog = hostDialog;
          this.hotspotId = hotspotId;
          this.hsDetailDialog = hotspotDetailHostDialog;
     }

     @Click void btnOk() {
          HashMap <String, String> requestParams = QCollection.newHashMap();
          requestParams.put(HTTP_PARAMS.HOTSPOT_ID, String.valueOf(hotspotId));
          requestParams.put(HTTP_PARAMS.IS_COMPLETED, String.valueOf(true));
          requestParams.put(HTTP_PARAMS.DATE, GlobalConstants.SITE_PLAN_IMAGE_NAME);
          requestParams.put(HTTP_PARAMS.FLOOR, GlobalConstants.CURRENT_FLOOR);
          requestParams.put(HTTP_PARAMS.USER_ID, String.valueOf(GlobalConstants.CURRENT_USER.userId));

          SuperRequest <ModelCompaniesAndHotspots> requestGetDatesToHighlight = new SuperRequest <ModelCompaniesAndHotspots>(activity, ModelCompaniesAndHotspots.class, RestAddresses.COMPLETE_HOTSPOT, requestParams);
          activity.execute(requestGetDatesToHighlight, new ResponseCompleteHotspot(activity, hsDetailDialog));
          dialog.dismiss();

     }

     @Click void btnCancel() {
          dialog.dismiss();
     }

}