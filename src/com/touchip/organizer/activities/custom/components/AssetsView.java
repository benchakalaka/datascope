package com.touchip.organizer.activities.custom.components;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

import android.content.ClipData;
import android.content.ClipDescription;
import android.content.Context;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.timessquare.sample.R;
import com.touchip.organizer.activities.DrawingCompaniesActivity;
import com.touchip.organizer.communication.rest.model.AssetsList;
import com.touchip.organizer.communication.rest.model.AssetsList.POJORoboSingleAsset;
import com.touchip.organizer.utils.DataAccess;

@EViewGroup ( R.layout.asset_view ) public class AssetsView extends RelativeLayout implements android.view.View.OnLongClickListener {

     @ViewById TextView twAssetDescription;
     @ViewById TextView twAssetTagId;

     public AssetsView ( Context context ) {
          super(context);
          setOnLongClickListener(this);
     }

     @Override public boolean onLongClick(View v) {
          String[] mimeTypes = { ClipDescription.MIMETYPE_TEXT_PLAIN };
          ClipData dragData = new ClipData(getTag().toString(), mimeTypes, new ClipData.Item(getTag().toString()));
          View.DragShadowBuilder shadow = new DragShadowBuilder(v);
          v.startDrag(dragData, shadow, null, 0);
          return false;
     }

     public static class AssetsListViewAdapter extends ArrayAdapter <POJORoboSingleAsset> {
          private final AssetsList               data;
          private final DrawingCompaniesActivity activity;

          public AssetsListViewAdapter ( DrawingCompaniesActivity act , AssetsList list ) {
               super(act.getApplicationContext(), R.layout.listview_asset_list_item);
               this.data = list;
               this.activity = act;
          }

          @Override public POJORoboSingleAsset getItem(int position) {
               return data.get(position);
          }

          @Override public int getCount() {
               return (null == data) ? 0 : data.size();
          }

          public AssetsList getData() {
               return data;
          }

          @Override public View getView(final int position, View convertView, ViewGroup parent) {
               AssetsView rowView = AssetsView_.build(this.activity);

               rowView.twAssetDescription.setText(data.get(position).description);
               rowView.twAssetTagId.setText(String.format("TAG: %s", data.get(position).tagId));

               rowView.setTag(data.get(position).description + " , " + rowView.twAssetTagId.getText().toString() + "_asset");

               rowView.setOnTouchListener(new OnTouchListener() {

                    @Override public boolean onTouch(View v, MotionEvent event) {
                         if ( event.getAction() == MotionEvent.ACTION_DOWN ) {
                              DataAccess.LAST_CLICKED_ASSET = ((AssetsListViewAdapter) DrawingCompaniesActivity.getLwAssets().getAdapter()).getData().get(position);
                         }
                         return false;
                    }
               });

               return rowView;
          }

          /*
           * static OnSwipeTouchListener swipeAndClose = new OnSwipeTouchListener(DrawingCompaniesActivity.INSTANCE) {
           * @Override public void onSwipeRight() {
           * AnimationManager.animateMenu(DrawingCompaniesActivity.getLlAssets(), View.GONE, R.anim.disappear, 500);
           * }
           * };
           */
     }

}
