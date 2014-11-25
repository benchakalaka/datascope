package com.touchip.organizer.activities.custom.components;

import java.util.HashMap;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;

import quickutils.core.QUFactory.QCollection;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.timessquare.sample.R;
import com.touchip.organizer.activities.SuperActivity;
import com.touchip.organizer.communication.rest.request.RestAddresses;
import com.touchip.organizer.communication.rest.request.SuperRequest;
import com.touchip.organizer.communication.rest.request.listener.ResponseDownloadSitePlanWithFloor;
import com.touchip.organizer.utils.GlobalConstants;
import com.touchip.organizer.utils.HTTP_PARAMS;
import com.touchip.organizer.utils.Utils;

@EViewGroup ( R.layout.image_and_text_below ) public class CustomImageWithTextBelowView extends RelativeLayout implements android.view.View.OnClickListener {

     @ViewById public TextView   text;
     @ViewById public ImageView  image;
     private final SuperActivity activity;
     private String              floor;

     public CustomImageWithTextBelowView ( SuperActivity context , String floor ) {
          super(context);
          this.activity = context;
          this.floor = floor;
          setOnClickListener(this);
     }

     @Override public void onClick(View v) {
          this.floor = text.getText().toString();
          Utils.showCustomToast(activity, String.valueOf("Loading ... " + floor), R.drawable.floorss);
          this.activity.showProgressDialog();
          // ((ADrawingCompanies) activity).saveAndSendDrawing(false, GlobalConstants.CURRENT_FLOOR);
          GlobalConstants.CURRENT_FLOOR = floor;

          HashMap <String, String> params = QCollection.newHashMap();

          params.put(HTTP_PARAMS.DATE, GlobalConstants.SITE_PLAN_IMAGE_NAME);
          params.put(HTTP_PARAMS.MARKER_ID, GlobalConstants.LAST_CLICKED_MARKER_ID);
          params.put(HTTP_PARAMS.FLOOR, GlobalConstants.CURRENT_FLOOR);

          SuperRequest <byte[]> request = new SuperRequest <byte[]>(this.activity, byte[].class, RestAddresses.DOWNLOAD_SITE_PLAN, new ByteArrayHttpMessageConverter(), params);
          this.activity.execute(request, new ResponseDownloadSitePlanWithFloor(this.activity));

     }
}
