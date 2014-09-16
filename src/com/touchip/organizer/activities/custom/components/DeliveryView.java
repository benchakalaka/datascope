package com.touchip.organizer.activities.custom.components;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

import android.content.Context;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.timessquare.sample.R;

@EViewGroup ( R.layout.delivery_view ) public class DeliveryView extends RelativeLayout {

     @ViewById public TextView  twDeliveryDescription;
     @ViewById public TextView  twDeliveryGate;
     @ViewById public ImageView ivCompanyColor;

     public DeliveryView ( Context context ) {
          super(context);
     }

}
