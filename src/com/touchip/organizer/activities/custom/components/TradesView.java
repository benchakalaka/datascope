package com.touchip.organizer.activities.custom.components;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

import android.content.ClipData;
import android.content.ClipDescription;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Typeface;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.timessquare.sample.R;
import com.touchip.organizer.activities.ADrawingCompanies;
import com.touchip.organizer.activities.SuperActivity;
import com.touchip.organizer.communication.rest.model.ModelHotspotsList;
import com.touchip.organizer.communication.rest.model.ModelHotspotsList.POJORoboHotspot;
import com.touchip.organizer.utils.GlobalConstants;
import com.touchip.organizer.utils.Utils;

@EViewGroup ( R.layout.trades_view ) public class TradesView extends RelativeLayout {

     // Load views
     @ViewById public TextView       twTradeAmount;
     @ViewById public TextView       twTradeDescription;
     @ViewById public ImageView      ivTrade;
     private final ADrawingCompanies activity;

     public TradesView ( Context context ) {
          super(context);
          this.activity = (ADrawingCompanies) context;
     }

     // ////////////////////////////////////////////////////////////////////////////////////////////
     // ////////////////////////////////////////////////////////////////////////////////////////////
     // //////////////////// ADAPTER FOR TRADEVIEW /////////////////////////////////////////////////
     // ////////////////////////////////////////////////////////////////////////////////////////////
     // ////////////////////////////////////////////////////////////////////////////////////////////
     public static class TradesListViewAdapter extends ArrayAdapter <POJORoboHotspot> {
          public static ModelHotspotsList tradesHotspots;
          private final SuperActivity     activity;

          public TradesListViewAdapter ( SuperActivity act , ModelHotspotsList list ) {
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
               final TradesView rowView = TradesView_.build(activity);
               rowView.twTradeDescription.setText(tradesHotspots.get(position).description);
               rowView.twTradeAmount.setText(String.valueOf(tradesHotspots.get(position).amount));
               rowView.setTag(String.valueOf(tradesHotspots.get(position).description) + "_trade");
               rowView.ivTrade.setTag(String.valueOf(tradesHotspots.get(position).description) + "_trade");
               // rowView.setOnLongClickListener(rowView);
               rowView.ivTrade.setOnTouchListener(new OnTouchListener() {

                    @Override public boolean onTouch(View v, MotionEvent event) {
                         switch (event.getAction()) {
                              case MotionEvent.ACTION_DOWN:
                                   if ( Integer.valueOf(rowView.twTradeAmount.getText().toString()) == 0 ) {
                                        Utils.showCustomToast(activity, R.string.there_is_no_resources_available, R.drawable.hide_hotspot);
                                        return false;
                                   }

                                   String[] mimeTypes = { ClipDescription.MIMETYPE_TEXT_PLAIN };
                                   ClipData dragData = new ClipData(rowView.ivTrade.getTag().toString(), mimeTypes, new ClipData.Item(rowView.ivTrade.getTag().toString()));

                                   MyDragShadowBuilder shadow = new MyDragShadowBuilder(v);
                                   v.startDrag(dragData, shadow, null, 0);
                                   break;
                         }
                         return false;
                    }
               });
               return rowView;
          }

          public static class MyDragShadowBuilder extends View.DragShadowBuilder {

               private Paint p;

               public MyDragShadowBuilder ( View v ) {
                    super(v);
                    try {
                         initPaint();
                    } catch (Exception e) {
                         e.printStackTrace();
                    }
               }

               private void initPaint() throws Exception {
                    p = new Paint();
                    p.setColor(Color.parseColor(GlobalConstants.LAST_CLICKED_COMPANY.colour));
                    p.setTextSize(8);
                    p.setAntiAlias(true);
                    p.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD_ITALIC));
               }

               @Override public void onDrawShadow(Canvas canvas) {
                    super.onDrawShadow(canvas);
                    try {
                         canvas.drawText(GlobalConstants.LAST_CLICKED_COMPANY.companyName, 0, 60, p);
                    } catch (Exception e) {
                         e.printStackTrace();
                    }
               }

               @Override public void onProvideShadowMetrics(Point shadowSize, Point touchPoint) {
                    shadowSize.set(70, 60);
               }
          }
     }

}
