package com.touchip.organizer.activities.custom.components;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

import android.content.ClipData;
import android.content.ClipDescription;
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
import com.touchip.organizer.activities.custom.components.TradesView.TradesListViewAdapter.MyDragShadowBuilder;
import com.touchip.organizer.communication.rest.model.ModelAssetsList;
import com.touchip.organizer.communication.rest.model.ModelAssetsList.POJORoboSingleAsset;
import com.touchip.organizer.utils.GlobalConstants;

@EViewGroup ( R.layout.asset_view ) public class AssetsView extends RelativeLayout {

     @ViewById TextView          twAssetDescription;
     @ViewById TextView          twAssetTagId;
     @ViewById ImageView         ivAsset;
     private final SuperActivity activity;

     public AssetsView ( SuperActivity act ) {
          super(act);
          this.activity = act;
     }

     public static class AssetsListViewAdapter extends ArrayAdapter <POJORoboSingleAsset> {
          private final ModelAssetsList   data;
          private final ADrawingCompanies activity;

          public AssetsListViewAdapter ( ADrawingCompanies act , ModelAssetsList list ) {
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

          public ModelAssetsList getData() {
               return data;
          }

          @Override public View getView(final int position, View convertView, ViewGroup parent) {
               final AssetsView rowView = AssetsView_.build(this.activity);

               rowView.twAssetDescription.setText(data.get(position).description);
               rowView.twAssetTagId.setText(String.format("TAG: %s", data.get(position).tagId));
               rowView.ivAsset.setTag(data.get(position).description + " , " + rowView.twAssetTagId.getText().toString() + "_asset");

               rowView.ivAsset.setOnTouchListener(new OnTouchListener() {

                    @Override public boolean onTouch(View v, MotionEvent event) {
                         if ( event.getAction() == MotionEvent.ACTION_DOWN ) {
                              String[] mimeTypes = { ClipDescription.MIMETYPE_TEXT_PLAIN };
                              ClipData dragData = new ClipData(rowView.ivAsset.getTag().toString(), mimeTypes, new ClipData.Item(rowView.ivAsset.getTag().toString()));
                              TradesView.TradesListViewAdapter.MyDragShadowBuilder mdsb = new MyDragShadowBuilder(v);
                              v.startDrag(dragData, mdsb, null, 0);
                         }
                         return false;
                    }
               });

               rowView.setTag(data.get(position).description + " , " + rowView.twAssetTagId.getText().toString() + "_asset");

               rowView.setOnTouchListener(new OnTouchListener() {

                    @Override public boolean onTouch(View v, MotionEvent event) {
                         if ( event.getAction() == MotionEvent.ACTION_DOWN ) {
                              GlobalConstants.LAST_CLICKED_ASSET = ((AssetsListViewAdapter) activity.getLwAssets().getAdapter()).getData().get(position);
                         }
                         return false;
                    }
               });

               return rowView;
          }

          /*
           * static OnSwipeTouchListener swipeAndClose = new OnSwipeTouchListener(ADrawingCompanies.INSTANCE) {
           * @Override public void onSwipeRight() {
           * AnimationManager.animateMenu(ADrawingCompanies.getLlAssets(), View.GONE, R.anim.disappear, 500);
           * }
           * };
           */
     }

}
