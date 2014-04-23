package com.touchip.organizer.activities.custom.components;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

import android.content.ClipData;
import android.content.ClipDescription;
import android.content.Context;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.timessquare.sample.R;
import com.touchip.organizer.activities.DrawingCompaniesActivity;
import com.touchip.organizer.communication.rest.model.HotspotsList.POJORoboHotspot;
import com.touchip.organizer.utils.Utils;

@EViewGroup ( R.layout.trades_view ) public class TradesView extends RelativeLayout implements android.view.View.OnLongClickListener {

     // Load views
     @ViewById public TextView              twTradeAmount;
     @ViewById public TextView              twTradeDescription;
     private final DrawingCompaniesActivity activity;

     public TradesView ( Context context ) {
          super(context);
          this.activity = (DrawingCompaniesActivity) context;
     }

     public void setTrade(POJORoboHotspot trade) {
          twTradeDescription.setText(trade.description);
          twTradeAmount.setText(String.valueOf(trade.amount));
          setTag(String.valueOf(trade.id) + "_trade");
          setOnLongClickListener(this);
     }

     @Override public boolean onLongClick(View v) {
          if ( Integer.valueOf(twTradeAmount.getText().toString()) == 0 ) {
               Utils.showCustomToast(activity, "There is no available resources. You cannot drag this item.", R.drawable.hide_hotspot);
               return false;
          }

          String[] mimeTypes = { ClipDescription.MIMETYPE_TEXT_PLAIN };
          ClipData dragData = new ClipData(getTag().toString(), mimeTypes, new ClipData.Item(getTag().toString()));
          View.DragShadowBuilder shadow = new DragShadowBuilder(v);
          v.startDrag(dragData, shadow, null, 0);

          return false;
     }
}
