package com.touchip.organizer.activities.custom.components;

import java.util.HashMap;

import org.androidannotations.annotations.EView;

import quickutils.core.QUFactory.QCollection;
import quickutils.core.QUFactory.QNotifications;
import quickutils.core.QUFactory.QSystem;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.text.TextUtils;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnDragListener;
import android.view.View.OnTouchListener;
import android.webkit.WebView;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;

import com.squareup.timessquare.sample.R;
import com.touchip.organizer.activities.AGeneralWhiteBoard;
import com.touchip.organizer.activities.AGeneralWhiteBoard_;
import com.touchip.organizer.activities.SuperActivity;
import com.touchip.organizer.activities.custom.components.dialogs.CDialogDisplayQuickNote;
import com.touchip.organizer.activities.custom.components.dialogs.CDialogDisplayQuickNote_;
import com.touchip.organizer.activities.fragments.FragmentCompaniesList;
import com.touchip.organizer.activities.fragments.FragmentHotspotsList;
import com.touchip.organizer.communication.rest.model.ModelHotspotsList.POJORoboHotspot;
import com.touchip.organizer.communication.rest.model.ModelSuitableOperativesList;
import com.touchip.organizer.communication.rest.request.RestAddresses;
import com.touchip.organizer.communication.rest.request.SuperRequest;
import com.touchip.organizer.communication.rest.request.listener.ResponseGetSuitableOperativesList;
import com.touchip.organizer.utils.GlobalConstants;
import com.touchip.organizer.utils.GlobalConstants.Hotspots;
import com.touchip.organizer.utils.HTTP_PARAMS;
import com.touchip.organizer.utils.Utils;
import com.touchip.organizer.utils.Utils.AnimationManager;

/**
 * Class represents hotspot button on canvas
 * 
 * @author Karpachev Ihor
 */
@EView public class HotspotButton extends ImageButton implements OnTouchListener , OnDragListener , android.view.View.OnClickListener {

     private final POJORoboHotspot hotspot;
     SuperActivity                 activity;
     String                        typeOfHotspotWhichUserIsDragging;
     CDialogDisplayQuickNote       view;

     static Paint                  paint;

     @SuppressLint ( "NewApi" ) public HotspotButton ( SuperActivity context , POJORoboHotspot pojoRoboHotspot ) {
          super(context);
          this.activity = context;
          this.hotspot = pojoRoboHotspot;
          if ( null == paint ) {
               paint = new Paint();
               paint.setAntiAlias(true);
          }

          int color = FragmentCompaniesList.getCompanyColorById(this.hotspot.companyId);
          paint.setColor(color);
          paint.setAlpha(150);

          // if ( this.hotspot.type.equals(GlobalConstants.Hotspots.ASSET_HOTSPOT) || this.hotspot.type.equals(GlobalConstants.Hotspots.TRADE_HOTSPOT) ) {
          setBackground(new BitmapDrawable(FragmentCompaniesList.createRoundImage(paint)));
          setImageDrawable(new BitmapDrawable(activity.getResources(), Utils.getBitmapByHotspotType(this.hotspot.type)));
          // } else {
          // setBackground(new BitmapDrawable(activity.getResources(), Utils.getBitmapByHotspotType(this.hotspot.type)));
          // }

          setOnTouchListener(this);
          setOnDragListener(this);
          setOnClickListener(this);
     }

     public static Bitmap createRoundImage(Paint paint) {
          Bitmap circleBitmap = Bitmap.createBitmap(30, 30, Bitmap.Config.ARGB_8888);
          Canvas c = new Canvas(circleBitmap);
          c.drawCircle(circleBitmap.getWidth() - 15, circleBitmap.getHeight() - 15, 14, paint);
          return circleBitmap;
     }

     public POJORoboHotspot getHotspot() {
          return hotspot;
     }

     public LayoutParams getPositionOnCanvas() {
          LayoutParams positionOnCanvas;
          if ( hotspot.type.equals(Hotspots.QUICK_NOTE_HOTSPOT) ) {
               positionOnCanvas = new RelativeLayout.LayoutParams(30, 30);
          } else {
               if ( hotspot.type.equals(Hotspots.HIGH_RISK) ) {
                    positionOnCanvas = new RelativeLayout.LayoutParams(35, 35);
               } else {
                    positionOnCanvas = new RelativeLayout.LayoutParams(45, 45);
               }
          }
          positionOnCanvas.leftMargin = (int) Math.round(Double.valueOf(hotspot.x) * CompaniesDrawingView.WIDTH);
          positionOnCanvas.topMargin = (int) Math.round(Double.valueOf(hotspot.y) * CompaniesDrawingView.HEIGHT);
          return positionOnCanvas;
     }

     public LayoutParams getPositionOnCanvas(int width, int height, int topMargin, int leftMargin) {
          LayoutParams positionOnCanvas = new RelativeLayout.LayoutParams(width, height);
          positionOnCanvas.leftMargin = (int) Math.round(Double.valueOf(hotspot.x) * CompaniesDrawingView.WIDTH) + leftMargin;
          positionOnCanvas.topMargin = (int) Math.round(Double.valueOf(hotspot.y) * CompaniesDrawingView.HEIGHT) + topMargin;
          return positionOnCanvas;
     }

     @Override public boolean onTouch(View v, MotionEvent event) {

          if ( event.getAction() == MotionEvent.ACTION_DOWN ) {
               GlobalConstants.LAST_CLICKED_HOTSPOT = hotspot;
               startAnimation(AnimationManager.load(R.anim.growview, 400));
          }
          return false;
     }

     /**
      * Set specific scale factor for view and bg res id
      * 
      * @param targetView
      *             target view
      * @param scaleX
      *             scale factor by x axis
      * @param scaleY
      *             scale factor by y axis
      * @param imageId
      *             image id form file R
      */
     private void setViewScaleAndImage(View targetView, float scaleX, float scaleY, int imageId) {
          targetView.setBackgroundResource(imageId);
          targetView.setScaleX(scaleX);
          targetView.setScaleY(scaleY);
     };

     @Override public boolean onDrag(View v, DragEvent event) {

          switch (event.getAction()) {

               case DragEvent.ACTION_DRAG_ENTERED:
                    typeOfHotspotWhichUserIsDragging = event.getClipDescription().getLabel().toString();
                    // Trade hs was dragged ONTO asset hotspot
                    if ( typeOfHotspotWhichUserIsDragging.contains("trade") && hotspot.type.equals(Hotspots.ASSET_HOTSPOT) ) {
                         setViewScaleAndImage(v, 1.5f, 1.5f, R.drawable.target);
                    }
                    // Asset hs has been dragged ONTO trade hotspot
                    if ( typeOfHotspotWhichUserIsDragging.contains("asset") && hotspot.type.equals(Hotspots.TRADE_HOTSPOT) ) {
                         setViewScaleAndImage(v, 1.5f, 1.5f, R.drawable.target);
                    }
                    break;

               case DragEvent.ACTION_DRAG_EXITED:
                    typeOfHotspotWhichUserIsDragging = event.getClipDescription().getLabel().toString();
                    if ( typeOfHotspotWhichUserIsDragging.contains("trade") && hotspot.type.equals(Hotspots.ASSET_HOTSPOT) ) {
                         setViewScaleAndImage(v, 1f, 1f, R.drawable.asset);
                    }

                    if ( typeOfHotspotWhichUserIsDragging.contains("asset") && hotspot.type.equals(Hotspots.TRADE_HOTSPOT) ) {
                         setViewScaleAndImage(v, 1f, 1f, R.drawable.trade);
                    }
                    break;

               case DragEvent.ACTION_DROP:
                    typeOfHotspotWhichUserIsDragging = event.getClipDescription().getLabel().toString();

                    // prepare request
                    HashMap <String, String> vars = QCollection.newHashMap();
                    if ( null == GlobalConstants.LAST_CLICKED_COMPANY ) { return true; }
                    vars.put(HTTP_PARAMS.COMPANY_ID, String.valueOf(GlobalConstants.LAST_CLICKED_COMPANY.companyId));
                    vars.put(HTTP_PARAMS.SITE_ID, GlobalConstants.LAST_CLICKED_MARKER_ID);
                    vars.put(HTTP_PARAMS.DATE, GlobalConstants.SITE_PLAN_IMAGE_NAME);

                    SuperRequest <ModelSuitableOperativesList> request = new SuperRequest <ModelSuitableOperativesList>(activity, ModelSuitableOperativesList.class, RestAddresses.GET_SUITABLE_OPERATIVES, vars);

                    // dragging Trade hotspot over Asset on canvas
                    if ( typeOfHotspotWhichUserIsDragging.contains("trade") && hotspot.type.equals(Hotspots.ASSET_HOTSPOT) ) {
                         QNotifications.showShortToast(activity, "Obtaining suitable operatives...");
                         setViewScaleAndImage(v, 1f, 1f, R.drawable.asset);
                         String tradeDescr = typeOfHotspotWhichUserIsDragging.substring(0, typeOfHotspotWhichUserIsDragging.indexOf("_"));
                         vars.put(HTTP_PARAMS.TRADE_DESCRIPTION, String.valueOf(tradeDescr));
                         vars.put(HTTP_PARAMS.ASSET_ID, String.valueOf(hotspot.assetId));
                         activity.execute(request, new ResponseGetSuitableOperativesList(activity));
                    }

                    // dragging Asset hotspot over Trade on canvas
                    if ( typeOfHotspotWhichUserIsDragging.contains("asset") && hotspot.type.equals(Hotspots.TRADE_HOTSPOT) ) {
                         QNotifications.showShortToast(activity, "Obtaining suitable operatives...");
                         setViewScaleAndImage(v, 1f, 1f, R.drawable.trade);
                         vars.put(HTTP_PARAMS.TRADE_DESCRIPTION, String.valueOf(hotspot.description));
                         vars.put(HTTP_PARAMS.ASSET_ID, String.valueOf(GlobalConstants.LAST_CLICKED_ASSET.id));
                         activity.execute(request, new ResponseGetSuitableOperativesList(activity));
                    }

                    startAnimation(AnimationManager.load(R.anim.shake, 300));
                    break;

          }
          return true;
     }

     @Override public void onClick(View v) {

          if ( hotspot.type.equals(GlobalConstants.Hotspots.TRADE_HOTSPOT) ) {
               NumPad npa = new NumPad();
               npa.show(activity, "Trade hotspot details", NumPad.NOFLAGS, new NumPad.NumbPadInterface() {
                    @Override public String numPadInputValue(String value) {
                         return null;
                    }

                    @Override public String numPadCanceled() {
                         return null;
                    }
               });
          } else if ( GlobalConstants.LAST_CLICKED_HOTSPOT.type.equalsIgnoreCase(Hotspots.SAFETY_HOTSPOT) || GlobalConstants.LAST_CLICKED_HOTSPOT.type.equals(Hotspots.WASTE_HOTSPOT) || GlobalConstants.LAST_CLICKED_HOTSPOT.type.equals(Hotspots.ASSET_HOTSPOT) || GlobalConstants.LAST_CLICKED_HOTSPOT.type
                    .equals(Hotspots.NOTE_HOTSPOT) ) {

               final int[] location = new int[2];
               location[0] = getPositionOnCanvas().leftMargin;
               location[1] = getPositionOnCanvas().topMargin;

               FragmentHotspotsList.showPopUpWindow(location, this);

          } else {
               if ( GlobalConstants.LAST_CLICKED_HOTSPOT.type.equals(Hotspots.WHITEBOARD_HOTSPOT) ) {
                    AGeneralWhiteBoard.WHITEBOARD_TYPE = GlobalConstants.DrawingType.HOTSPOT_WHITEBOARD_DRAWING;
                    AGeneralWhiteBoard_.WHITEBOARD_TYPE = GlobalConstants.DrawingType.HOTSPOT_WHITEBOARD_DRAWING;
                    QSystem.navigateToActivity(activity, AGeneralWhiteBoard_.class);
               } else {
                    if ( hotspot.type.equals(GlobalConstants.Hotspots.QUICK_NOTE_HOTSPOT) ) {

                         if ( !TextUtils.isEmpty(hotspot.description) ) {
                              view = CDialogDisplayQuickNote_.build(activity);
                              view.tvText.setText(hotspot.description);
                              LayoutParams param = getPositionOnCanvas();
                              param.leftMargin = param.leftMargin + 30;
                              param.topMargin = param.topMargin + 30;
                              param.width = LayoutParams.WRAP_CONTENT;
                              param.height = LayoutParams.WRAP_CONTENT;
                              FragmentHotspotsList.hotspotsButtonLayout.addView(view, param);
                              view.startAnimation(Utils.AnimationManager.load(R.anim.slide_in_from_top));
                         } else {
                              QNotifications.showShortToast(activity, "There is no description found!");
                         }
                    } else {
                         if ( hotspot.type.equals(GlobalConstants.Hotspots.CAMERA_HOTSPOT) ) {

                              final Dialog d = new Dialog(activity);
                              d.setContentView(R.layout.dialog_cctv_camera);
                              d.setCanceledOnTouchOutside(true);
                              final WebView webViewTV = (WebView) d.findViewById(R.id.webView1);
                              Utils.configureWebView(webViewTV).loadUrl("http://194.28.136.8:8001/record/current.jpg");
                              d.show();

                              updateWebView(webViewTV, d);
                         } else {

                              FragmentHotspotsList.showDialog(GlobalConstants.LAST_CLICKED_HOTSPOT, activity);
                         }
                    }
               }
          }

     }

     private void updateWebView(WebView webViewTV, Dialog d) {
          while ( d.isShowing() ) {
               QSystem.sleep(400);
               webViewTV.reload();
          }

     }
}
