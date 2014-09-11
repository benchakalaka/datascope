package com.touchip.organizer.activities.custom.components;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

import android.content.ClipData;
import android.content.ClipDescription;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.timessquare.sample.R;
import com.touchip.organizer.activities.DrawingCompaniesActivity;
import com.touchip.organizer.activities.DrawingCompaniesActivity_;
import com.touchip.organizer.communication.rest.model.HotspotsList;
import com.touchip.organizer.communication.rest.model.HotspotsList.POJORoboHotspot;
import com.touchip.organizer.utils.OnSwipeTouchListener;
import com.touchip.organizer.utils.Utils;
import com.touchip.organizer.utils.Utils.AnimationManager;

@EViewGroup ( R.layout.trades_view ) public class TradesView extends RelativeLayout implements android.view.View.OnLongClickListener {

     // Load views
     @ViewById public TextView              twTradeAmount;
     @ViewById public TextView              twTradeDescription;
     private final DrawingCompaniesActivity activity;

     public TradesView ( Context context ) {
          super(context);
          this.activity = (DrawingCompaniesActivity) context;
     }

     @Override public boolean onLongClick(View v) {
          if ( Integer.valueOf(twTradeAmount.getText().toString()) == 0 ) {
               Utils.showCustomToast(activity, R.string.there_is_no_resources_available, R.drawable.hide_hotspot);
               return false;
          }

          String[] mimeTypes = { ClipDescription.MIMETYPE_TEXT_PLAIN };
          ClipData dragData = new ClipData(getTag().toString(), mimeTypes, new ClipData.Item(getTag().toString()));

          View.DragShadowBuilder shadow = new DragShadowBuilder(v);
          v.startDrag(dragData, shadow, null, 0);

          return false;
     }

     // ////////////////////////////////////////////////////////////////////////////////////////////
     // ////////////////////////////////////////////////////////////////////////////////////////////
     // //////////////////// ADAPTER FOR TRADEVIEW /////////////////////////////////////////////////
     // ////////////////////////////////////////////////////////////////////////////////////////////
     // ////////////////////////////////////////////////////////////////////////////////////////////
     public static class TradesListViewAdapter extends ArrayAdapter <POJORoboHotspot> {
          public static HotspotsList              tradesHotspots;
          private final DrawingCompaniesActivity_ activity;

          public TradesListViewAdapter ( DrawingCompaniesActivity_ act , HotspotsList list ) {
               super(act.getApplicationContext(), R.layout.listview_asset_list_item);
               TradesListViewAdapter.tradesHotspots = list;
               this.activity = act;
          }

          @Override public POJORoboHotspot getItem(int position) {
               return tradesHotspots.get(position);
          }

          @Override public int getCount() {
               return (null == tradesHotspots) ? 0 : tradesHotspots.size();
          }

          @Override public View getView(final int position, View convertView, ViewGroup parent) {
               TradesView rowView = TradesView_.build(activity);
               rowView.setOnTouchListener(swipeAndClose);
               rowView.twTradeDescription.setText(tradesHotspots.get(position).description);
               rowView.twTradeAmount.setText(String.valueOf(tradesHotspots.get(position).amount));
               rowView.setTag(String.valueOf(tradesHotspots.get(position).description) + "_trade");
               rowView.setOnLongClickListener(rowView);
               return rowView;
          }
     }

     static OnSwipeTouchListener swipeAndClose = new OnSwipeTouchListener(DrawingCompaniesActivity.INSTANCE) {

                                                    @Override public void onSwipeRight() {
                                                         AnimationManager.animateMenu(DrawingCompaniesActivity.getLlTrades(), View.GONE, R.anim.disappear, 500);
                                                    }
                                               };
}
