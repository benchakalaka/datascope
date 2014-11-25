package com.touchip.organizer.activities.custom.components.dialogs;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.timessquare.sample.R;
import com.touchip.organizer.activities.ADrawingCompanies;
import com.touchip.organizer.activities.SuperActivity;
import com.touchip.organizer.activities.fragments.FragmentCompaniesList;
import com.touchip.organizer.activities.fragments.FragmentHotspotsList;
import com.touchip.organizer.activities.fragments.FragmentUnsignedHotspotsList;
import com.touchip.organizer.communication.rest.model.ModelHotspotsList;
import com.touchip.organizer.utils.FilterManager;
import com.touchip.organizer.utils.GlobalConstants;
import com.touchip.organizer.utils.GlobalConstants.Hotspots;
import com.touchip.organizer.utils.Utils.AnimationManager;

@EViewGroup ( R.layout.dialog_smart_filter ) public class CDialogSmartFilter extends RelativeLayout {

     // ----------------------------------------- VIEWS
     @ViewById RelativeLayout               rlOnTheFly , rlCompleted , rlHighRisk , rlQuickNote , rlLabels , rlNote , rlSafety , rlWaste , rlPermission , rlWb , rlTrade , rlAsset , rlCamera , rlShowAll , rlHideAll;
     @ViewById CheckBox                     cbOnTheFly , cbCompleted , cbHighRisk , cbDisplayAll , cbQuickNote , cbLabels , cbNote , cbSafety , cbWaste , cbPermission , cbWb , cbTrade , cbAsset , cbCamera , cbShowAll , cbHideAll;
     @ViewById ListView                     lvCompanies;

     // ----------------------------------------- VARIABLES
     // represents animation which should be played on click
     private final int                      animationId = R.anim.bounce;
     private final SuperActivity            activity;
     private ListViewCompaniesFilterAdapter ADAPTER;

     /**
      * Constructor
      * 
      * @param context
      *             activity context or activity itself
      */
     public CDialogSmartFilter ( SuperActivity context ) {
          super(context);
          this.activity = context;
     }

     void twHideAll() {
          FilterManager.deactivateAllCompaniesDrawing();
          ADrawingCompanies.DRAW_VIEW.setCompanyColourFilter(3);
          ADAPTER.notifyDataSetChanged();
     }

     void twShowAll() {
          FilterManager.activateAllCompaniesDrawing();
          ADrawingCompanies.DRAW_VIEW.setCompanyColourFilter(3);
          ADAPTER.notifyDataSetChanged();
     }

     @Click void btnOk() {
          ADrawingCompanies.DRAW_VIEW.setIsNeedToStopOnDrawMethod(true);
          ADrawingCompanies.DRAW_VIEW.invalidate();

          GlobalConstants.SIGNED_HOTSPOTS = new ModelHotspotsList();
          GlobalConstants.UNASSIGNED_HOTSPOTS = new ModelHotspotsList();

          for ( int i = 0; i < GlobalConstants.SIGNED_HOTSPOTS_ALL.size(); i++ ) {
               if ( FilterManager.displayCompleted == GlobalConstants.SIGNED_HOTSPOTS_ALL.get(i).isCompleted ) {
                    GlobalConstants.SIGNED_HOTSPOTS.add(GlobalConstants.SIGNED_HOTSPOTS_ALL.get(i));
               }
          }

          for ( int i = 0; i < GlobalConstants.UNASSIGNED_HOTSPOTS_ALL.size(); i++ ) {
               if ( FilterManager.displayCompleted == GlobalConstants.UNASSIGNED_HOTSPOTS_ALL.get(i).isCompleted ) {
                    GlobalConstants.UNASSIGNED_HOTSPOTS.add(GlobalConstants.UNASSIGNED_HOTSPOTS_ALL.get(i));
               }
          }

          FragmentHotspotsList.ADAPTER.notifyDataSetChanged();
          FragmentUnsignedHotspotsList.ADAPTER.notifyDataSetChanged();
          FragmentHotspotsList.ADAPTER.updateHotspotsButtonsList();
          ((ADrawingCompanies) this.activity).dialog.dismiss();
     }

     @AfterViews void afterViews() {
          ADAPTER = new ListViewCompaniesFilterAdapter(this.activity);
          lvCompanies.setAdapter(ADAPTER);
          cbLabels.setChecked(FilterManager.displayHSId);
          cbNote.setChecked(FilterManager.activeHotspots.get(Hotspots.NOTE_HOTSPOT));
          cbSafety.setChecked(FilterManager.activeHotspots.get(Hotspots.SAFETY_HOTSPOT));
          cbWaste.setChecked(FilterManager.activeHotspots.get(Hotspots.WASTE_HOTSPOT));
          cbPermission.setChecked(FilterManager.activeHotspots.get(Hotspots.PERMITS_HOTSPOT));
          cbWb.setChecked(FilterManager.activeHotspots.get(Hotspots.WHITEBOARD_HOTSPOT));
          cbTrade.setChecked(FilterManager.activeHotspots.get(Hotspots.TRADE_HOTSPOT));
          cbAsset.setChecked(FilterManager.activeHotspots.get(Hotspots.ASSET_HOTSPOT));
          cbCamera.setChecked(FilterManager.activeHotspots.get(Hotspots.CAMERA_HOTSPOT));
          cbQuickNote.setChecked(FilterManager.activeHotspots.get(Hotspots.QUICK_NOTE_HOTSPOT));
          cbOnTheFly.setChecked(FilterManager.activeHotspots.get(Hotspots.ON_THE_FLY));
          cbHighRisk.setChecked(FilterManager.activeHotspots.get(Hotspots.HIGH_RISK));
          cbCompleted.setChecked(FilterManager.displayCompleted);
     }

     @Click void cbDisplayAll() {
          cbDisplayAll.startAnimation(AnimationManager.load(animationId));
          if ( cbDisplayAll.isChecked() ) {
               twShowAll();
               cbDisplayAll.setText("Untick");
          } else {
               twHideAll();
               cbDisplayAll.setText("Tick");
          }
     }

     @Click void cbCompleted() {
          FilterManager.displayCompleted = cbCompleted.isChecked();
          cbLabels.startAnimation(AnimationManager.load(animationId));
     }

     @Click void cbLabels() {
          FilterManager.displayHSId = cbLabels.isChecked();
          cbLabels.startAnimation(AnimationManager.load(animationId));
     }

     @Click void cbQuickNote() {
          setShowHideCheckedState(false);
          cbQuickNote.startAnimation(AnimationManager.load(animationId));
          FilterManager.activeHotspots.put(Hotspots.QUICK_NOTE_HOTSPOT, cbQuickNote.isChecked());
     }

     @Click void cbOnTheFly() {
          setShowHideCheckedState(false);
          cbOnTheFly.startAnimation(AnimationManager.load(animationId));
          FilterManager.activeHotspots.put(Hotspots.ON_THE_FLY, cbOnTheFly.isChecked());
     }

     @Click void cbHighRisk() {
          setShowHideCheckedState(false);
          cbHighRisk.startAnimation(AnimationManager.load(animationId));
          FilterManager.activeHotspots.put(Hotspots.HIGH_RISK, cbHighRisk.isChecked());
     }

     @Click void cbNote() {
          setShowHideCheckedState(false);
          cbNote.startAnimation(AnimationManager.load(animationId));
          FilterManager.activeHotspots.put(Hotspots.NOTE_HOTSPOT, cbNote.isChecked());
     }

     @Click void cbSafety() {
          setShowHideCheckedState(false);
          cbSafety.startAnimation(AnimationManager.load(animationId));
          FilterManager.activeHotspots.put(Hotspots.SAFETY_HOTSPOT, cbSafety.isChecked());
     }

     @Click void cbWaste() {
          setShowHideCheckedState(false);
          cbWaste.startAnimation(AnimationManager.load(animationId));
          FilterManager.activeHotspots.put(Hotspots.WASTE_HOTSPOT, cbWaste.isChecked());
     }

     @Click void cbPermission() {
          setShowHideCheckedState(false);
          cbPermission.startAnimation(AnimationManager.load(animationId));
          FilterManager.activeHotspots.put(Hotspots.PERMITS_HOTSPOT, cbPermission.isChecked());
     }

     @Click void cbWb() {
          setShowHideCheckedState(false);
          cbWb.startAnimation(AnimationManager.load(animationId));
          FilterManager.activeHotspots.put(Hotspots.WHITEBOARD_HOTSPOT, cbWb.isChecked());
     }

     @Click void cbTrade() {
          setShowHideCheckedState(false);
          cbTrade.startAnimation(AnimationManager.load(animationId));
          FilterManager.activeHotspots.put(Hotspots.TRADE_HOTSPOT, cbTrade.isChecked());
     }

     @Click void cbAsset() {
          setShowHideCheckedState(false);
          cbAsset.startAnimation(AnimationManager.load(animationId));
          FilterManager.activeHotspots.put(Hotspots.ASSET_HOTSPOT, cbAsset.isChecked());
     }

     @Click void cbCamera() {
          setShowHideCheckedState(false);
          cbCamera.startAnimation(AnimationManager.load(animationId));
          FilterManager.activeHotspots.put(Hotspots.CAMERA_HOTSPOT, cbCamera.isChecked());
     }

     @Click void cbShowAll() {
          cbShowAll.startAnimation(AnimationManager.load(animationId));
          cbHideAll.setChecked(false);
          setAllButtonsCheckedStates(true);
          FilterManager.setFilterHotstpotsState(true);
     }

     @Click void cbHideAll() {
          cbHideAll.startAnimation(AnimationManager.load(animationId));
          setAllButtonsCheckedStates(false);
          FilterManager.setFilterHotstpotsState(false);
     }

     @Click void rlLabels() {
          setShowHideCheckedState(false);
          cbLabels.performClick();
          FilterManager.displayHSId = cbLabels.isChecked();
          cbLabels.startAnimation(AnimationManager.load(animationId));
     }

     @Click void rlOnTheFly() {
          setShowHideCheckedState(false);
          cbOnTheFly.performClick();
          cbOnTheFly.startAnimation(AnimationManager.load(animationId));
     }

     @Click void rlQuickNote() {
          setShowHideCheckedState(false);
          cbQuickNote.performClick();
          cbQuickNote.startAnimation(AnimationManager.load(animationId));
     }

     @Click void rlCompleted() {
          setShowHideCheckedState(false);
          cbCompleted.performClick();
          cbCompleted.startAnimation(AnimationManager.load(animationId));
     }

     @Click void rlHighRisk() {
          setShowHideCheckedState(false);
          cbHighRisk.performClick();
          cbHighRisk.startAnimation(AnimationManager.load(animationId));
     }

     @Click void rlNote() {
          setShowHideCheckedState(false);
          cbNote.performClick();
          cbNote.startAnimation(AnimationManager.load(animationId));
     }

     @Click void rlSafety() {
          setShowHideCheckedState(false);
          cbSafety.performClick();
          cbSafety.startAnimation(AnimationManager.load(animationId));
     }

     @Click void rlWaste() {
          setShowHideCheckedState(false);
          cbWaste.performClick();
          cbWaste.startAnimation(AnimationManager.load(animationId));
     }

     @Click void rlPermission() {
          setShowHideCheckedState(false);
          cbPermission.performClick();
          cbPermission.startAnimation(AnimationManager.load(animationId));
     }

     @Click void rlWb() {
          setShowHideCheckedState(false);
          cbWb.performClick();
          cbWb.startAnimation(AnimationManager.load(animationId));
     }

     @Click void rlTrade() {
          setShowHideCheckedState(false);
          cbTrade.performClick();
          cbTrade.startAnimation(AnimationManager.load(animationId));
     }

     @Click void rlAsset() {
          setShowHideCheckedState(false);
          cbAsset.performClick();
          cbAsset.startAnimation(AnimationManager.load(animationId));
     }

     @Click void rlCamera() {
          setShowHideCheckedState(false);
          cbCamera.performClick();
          cbCamera.startAnimation(AnimationManager.load(animationId));
     }

     @Click void rlShowAll() {
          cbShowAll.performClick();
          cbShowAll.startAnimation(AnimationManager.load(animationId));
          cbHideAll.setChecked(false);
          setAllButtonsCheckedStates(true);
     }

     @Click void rlHideAll() {
          cbHideAll.performClick();
          cbHideAll.startAnimation(AnimationManager.load(animationId));
          setAllButtonsCheckedStates(false);
     }

     @Click void btnCancel() {
          ((ADrawingCompanies) this.activity).dialog.dismiss();
     }

     /**
      * Set state of checkboxes
      * 
      * @param value
      *             checked if true, unchecked otherwise
      */
     private void setAllButtonsCheckedStates(boolean value) {
          cbQuickNote.setChecked(value);
          cbNote.setChecked(value);
          cbSafety.setChecked(value);
          cbWaste.setChecked(value);
          cbPermission.setChecked(value);
          cbWb.setChecked(value);
          cbTrade.setChecked(value);
          cbAsset.setChecked(value);
          cbCamera.setChecked(value);
          cbShowAll.setChecked(value);
          cbHighRisk.setChecked(value);
     }

     /**
      * Set state of show all and hide all buttons
      * 
      * @param state
      */
     private void setShowHideCheckedState(boolean state) {
          cbShowAll.setChecked(state);
          cbHideAll.setChecked(state);
     }

     // --------------------------------- COMPANIES LIST ADAPTER
     /**
      * Description: adapter, which represents list of companies and colours for list view
      * 
      * @author Ihor Karpachev
      *         Copyright (c) 2013-2014 Datascope Ltd. All Rights Reserved.
      *         Date: 24 Sep 2014
      *         Time: 15:05:27
      */
     public class ListViewCompaniesFilterAdapter extends ArrayAdapter <String> {

          private final Activity activity;
          private final Paint    paint;

          public ListViewCompaniesFilterAdapter ( Activity act ) {
               super(act, R.layout.listview_company_list_item);
               paint = new Paint();
               paint.setAntiAlias(true);
               activity = act;
               notifyDataSetChanged();
          }

          @Override public int getCount() {
               return FragmentCompaniesList.COMPANIES_LIST.size();
          }

          @Override public String getItem(int position) {
               return FragmentCompaniesList.COMPANIES_LIST.get(position).companyName;
          }

          @Override public View getView(final int position, View view, ViewGroup parent) {
               final View rowView = activity.getLayoutInflater().inflate(R.layout.listview_filter_company_list_item, null, true);
               TextView txtTitle = (TextView) rowView.findViewById(R.id.twCompanyTitle);
               ImageView imageView = (ImageView) rowView.findViewById(R.id.ivCompanyColor);
               final CheckBox cbChecked = (CheckBox) rowView.findViewById(R.id.cbChecked);

               cbChecked.setChecked(FilterManager.activeCompaniesColor.contains(Integer.valueOf(Color.parseColor(FragmentCompaniesList.COMPANIES_LIST.get(position).colour))));

               txtTitle.setText(FragmentCompaniesList.COMPANIES_LIST.get(position).companyName);
               paint.setColor(Color.parseColor(FragmentCompaniesList.COMPANIES_LIST.get(position).colour));
               imageView.setImageBitmap(createRoundImage(paint));

               rowView.setOnClickListener(new OnClickListener() {

                    @Override public void onClick(View v) {

                         cbChecked.performClick();
                         if ( cbChecked.isChecked() ) {
                              ADrawingCompanies.DRAW_VIEW.setCompanyColourFilter(3);
                              FilterManager.activeCompaniesColor.add(Integer.valueOf(Color.parseColor(FragmentCompaniesList.COMPANIES_LIST.get(position).colour)));
                         } else {
                              FilterManager.activeCompaniesColor.remove(Integer.valueOf(Color.parseColor(FragmentCompaniesList.COMPANIES_LIST.get(position).colour)));
                         }
                         cbChecked.startAnimation(AnimationManager.load(R.anim.bounce));
                    }
               });

               cbChecked.setOnClickListener(new OnClickListener() {

                    @Override public void onClick(View v) {
                         if ( cbChecked.isChecked() ) {
                              ADrawingCompanies.DRAW_VIEW.setCompanyColourFilter(3);
                              FilterManager.activeCompaniesColor.add(Integer.valueOf(Color.parseColor(FragmentCompaniesList.COMPANIES_LIST.get(position).colour)));
                         } else {
                              FilterManager.activeCompaniesColor.remove(Integer.valueOf(Color.parseColor(FragmentCompaniesList.COMPANIES_LIST.get(position).colour)));
                         }

                         cbChecked.startAnimation(AnimationManager.load(R.anim.bounce));
                    }
               });
               return rowView;
          }
     }

     public static Bitmap createRoundImage(Paint paint) {
          Bitmap circleBitmap = Bitmap.createBitmap(30, 30, Bitmap.Config.ARGB_8888);
          Canvas c = new Canvas(circleBitmap);
          c.drawCircle(circleBitmap.getWidth() - 15, circleBitmap.getHeight() - 15, 14, paint);
          return circleBitmap;
     }
}
