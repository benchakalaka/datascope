package com.touchip.organizer.activities.custom.components;

import java.util.Map;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

import android.text.TextUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.octo.android.robospice.persistence.DurationInMillis;
import com.squareup.timessquare.sample.R;
import com.touchip.organizer.activities.SpiceFragmentActivity;
import com.touchip.organizer.communication.rest.model.ModelHotspotsAndTrades;
import com.touchip.organizer.communication.rest.request.SuperRequest;
import com.touchip.organizer.communication.rest.request.listener.CreateHotspotRequestListener;
import com.touchip.organizer.constants.HTTP_PARAMS;
import com.touchip.organizer.constants.RestAddresses;
import com.touchip.organizer.utils.Utils;

@EViewGroup ( R.layout.dialog_create_hotspot_whiteboard ) public class CDialogCreateHotspotWhiteboard extends RelativeLayout {

     // ===================== views
     @ViewById EditText                  etName;
     @ViewById ImageView                 ivOk;

     // ====================== variable
     private final SpiceFragmentActivity activity;
     private Map <String, String>        params;

     public CDialogCreateHotspotWhiteboard ( SpiceFragmentActivity act ) {
          super(act.getApplicationContext());
          this.activity = act;
     }

     public void setHttpParams(Map <String, String> vars) {
          this.params = vars;
     }

     @Click void ivOk() {
          if ( !TextUtils.isEmpty(etName.getText().toString()) ) {
               this.params.put(HTTP_PARAMS.DESCRIPTION, etName.getText().toString());
               this.activity.showProgressDialog();

               SuperRequest <ModelHotspotsAndTrades> request = new SuperRequest <ModelHotspotsAndTrades>(ModelHotspotsAndTrades.class, RestAddresses.CREATE_HOTSPOT, null, this.params);
               this.activity.getSpiceManager().execute(request, request.createCacheKey(), DurationInMillis.ALWAYS_EXPIRED, new CreateHotspotRequestListener(this.activity));
          } else {
               Utils.showToast(activity, R.string.field_cannot_be_empty, true);
          }
     }

}