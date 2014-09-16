package com.touchip.organizer.activities.custom.components;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

import android.app.Activity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.octo.android.robospice.persistence.DurationInMillis;
import com.squareup.timessquare.sample.R;
import com.touchip.organizer.activities.DrawingCompaniesActivity;
import com.touchip.organizer.communication.rest.request.DownloadSitePlanWithFloorRequest;
import com.touchip.organizer.communication.rest.request.listener.DownloadSitePlanWithFloorRequestListener;
import com.touchip.organizer.utils.GlobalConstants;
import com.touchip.organizer.utils.Utils;

@EViewGroup ( R.layout.image_and_text_below ) public class CustomImageWithTextBelowView extends RelativeLayout implements android.view.View.OnClickListener {

     @ViewById public TextView  text;
     @ViewById public ImageView image;
     private final Activity     activity;
     private String             floor;

     public CustomImageWithTextBelowView ( Activity context , String floor ) {
          super(context);
          this.activity = context;
          this.floor = floor;
          setOnClickListener(this);
     }

     @Override public void onClick(View v) {
          this.floor = text.getText().toString();
          Utils.showCustomToast(activity, String.valueOf("Loading ... " + floor), R.drawable.floors);
          DrawingCompaniesActivity.showProgressDialog();
          // ((DrawingCompaniesActivity) activity).saveAndSendDrawing(false, GlobalConstants.CURRENT_FLOOR);
          GlobalConstants.CURRENT_FLOOR = floor;
          DownloadSitePlanWithFloorRequest request = new DownloadSitePlanWithFloorRequest();
          ((DrawingCompaniesActivity) activity).getSpiceManager().execute(request, request.createCacheKey(), DurationInMillis.ALWAYS_EXPIRED, new DownloadSitePlanWithFloorRequestListener(this.activity));
     }
}
