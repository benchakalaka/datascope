package com.touchip.organizer.activities.custom.components.dialogs;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

import android.app.Dialog;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.squareup.timessquare.sample.R;
import com.touchip.organizer.activities.ADrawingCompanies;
import com.touchip.organizer.activities.SuperActivity;
import com.touchip.organizer.utils.Utils.AnimationManager;

@EViewGroup ( R.layout.dialog_type_of_resources ) public class CDialogTypeOfResources extends RelativeLayout {

     // ===================== views
     @ViewById ImageView         ivDeliveriesSelected , ivDeliveries , ivTradesSelected , ivTrades , ivAssetsSelected , ivAssets;
     // ====================== variable
     private final SuperActivity activity;
     private final Dialog        dialog;
     private final int           animationId = R.anim.grow_from_top;
     private int                 requestResourceType;

     public CDialogTypeOfResources ( SuperActivity act , Dialog hostDialog ) {
          super(act);
          this.activity = act;
          this.dialog = hostDialog;
     }

     @AfterViews void afterViews() {
          // selectViewByTypesOfShape();
     }

     @Click void ivDeliveries() {
          playAnimationOnView(ivDeliveries);
          ivDeliveriesSelected.setVisibility(View.VISIBLE);
          requestResourceType = ResourceType.DELIVERIES;
          ((ADrawingCompanies) activity).displayDeliveries();
          dialog.dismiss();
     }

     @Click void ivTrades() {
          playAnimationOnView(ivTrades);
          ivTradesSelected.setVisibility(View.VISIBLE);
          requestResourceType = ResourceType.TRADES;
          ((ADrawingCompanies) activity).displayTrades();
          dialog.dismiss();
     }

     @Click void ivAssets() {
          playAnimationOnView(ivAssets);
          ivAssetsSelected.setVisibility(View.VISIBLE);
          requestResourceType = ResourceType.ASSETS;
          ((ADrawingCompanies) activity).displayAssets();
          dialog.dismiss();
     }

     private void playAnimationOnView(View target) {
          target.startAnimation(AnimationManager.load(animationId));
          unselectAllTypesOfShapes();
     }

     private void unselectAllTypesOfShapes() {
          ivAssetsSelected.setVisibility(View.INVISIBLE);
          ivDeliveriesSelected.setVisibility(View.INVISIBLE);
          ivTradesSelected.setVisibility(View.INVISIBLE);
     }

     // resources types constants
     public static class ResourceType {
          public static final int TRADES     = -1;
          public static final int ASSETS     = 0;
          public static final int DELIVERIES = 1;

          private ResourceType () {
          }
     }
}