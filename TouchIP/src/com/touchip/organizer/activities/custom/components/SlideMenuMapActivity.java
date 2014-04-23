package com.touchip.organizer.activities.custom.components;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;

import com.google.android.gms.maps.GoogleMap;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu.OnCloseListener;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu.OnOpenListener;
import com.squareup.timessquare.sample.R;
import com.touchip.organizer.activities.MapActivity;
import com.touchip.organizer.activities.UserSettingsActivity_;
import com.touchip.organizer.utils.Utils.AnimationManager;

@EViewGroup ( R.layout.menu_map_activity ) public class SlideMenuMapActivity extends ScrollView implements OnOpenListener , OnCloseListener {

     // Parent activity
     private final MapActivity activity;
     // Side slide menu
     private SlidingMenu       menu;
     // Control button for displaying/hiding menu
     private ImageButton       controlButton;

     // load views
     @ViewById LinearLayout    settings;
     @ViewById LinearLayout    changeMapType;
     @ViewById LinearLayout    exit;
     private Dialog            dialog;

     /**
      * Default constructor
      * 
      * @param context
      *             object of parent activity
      */
     public SlideMenuMapActivity ( Context context ) {
          super(context);
          this.activity = (MapActivity) context;
     }

     @AfterViews void afterViews() {
          dialog = new Dialog(activity);
          // configure the SlidingMenu
          Point size = new Point();
          activity.getWindowManager().getDefaultDisplay().getSize(size);
          menu = new SlidingMenu(activity);
          menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);
          menu.setShadowWidthRes(R.dimen.shadow_width);
          menu.setBehindOffset((int) (size.x * 0.80));
          menu.attachToActivity(activity, SlidingMenu.SLIDING_WINDOW);
          menu.setMenu(this);
          menu.setAlwaysDrawnWithCacheEnabled(true);
          menu.setMode(SlidingMenu.LEFT);

          menu.setOnOpenListener(this);
          menu.setOnCloseListener(this);
     }

     /**
      * Return menu object
      * 
      * @return SlidingMenu
      */
     public SlidingMenu getMenu() {
          return menu;
     }

     /**
      * Set control button for changing state, depends on menu state (open = show left arrow, closed = show right arrow)
      * 
      * @param control
      *             button to be set
      */
     public void setMenuControlButton(ImageButton control) {
          this.controlButton = control;
     }

     /**
      * Undo operation for drawing
      */
     @Click void settings() {
          settings.startAnimation(AnimationManager.load(android.R.anim.fade_out));
          activity.startActivity(new Intent(activity, UserSettingsActivity_.class));
     }

     @Click void changeMapType() {
          dialog = new Dialog(activity);
          AlertDialog.Builder builder = new AlertDialog.Builder(activity).setTitle("Select map type");

          ListView modeList = new ListView(activity);
          final String[] stringArray = new String[] { "NORMAL", "TERRAIN", "SATELLITE" };
          ArrayAdapter <String> modeAdapter = new ArrayAdapter <String>(activity, android.R.layout.simple_list_item_1, android.R.id.text1, stringArray);
          modeList.setAdapter(modeAdapter);
          builder.setView(modeList);
          dialog = builder.create();

          modeList.setOnItemClickListener(new OnItemClickListener() {

               @Override public void onItemClick(AdapterView <?> adapter, View view, int pos, long arg3) {
                    String mapType = adapter.getItemAtPosition(pos).toString();
                    if ( mapType.equals(stringArray[0]) ) {
                         activity.googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL); // NORMAL
                    }

                    if ( mapType.equals(stringArray[1]) ) {
                         activity.googleMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN); // TERRAIN
                    }

                    if ( mapType.equals(stringArray[2]) ) {
                         activity.googleMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE); // SATTELITE
                    }
                    dialog.dismiss();
               }
          });

          dialog.show();
     }

     /**
      * Redo operation for drawing
      */
     @Click void exit() {
          activity.moveTaskToBack(true);
     }

     /**
      * Callback which will be invoked when menu is start open
      */
     @Override public void onOpen() {
          controlButton.setImageResource(R.drawable.left_arrow);
     }

     /**
      * Callback which will be invoked when menu is start closing
      */
     @Override public void onClose() {
          controlButton.setImageResource(R.drawable.right_arrow);
     }
}
