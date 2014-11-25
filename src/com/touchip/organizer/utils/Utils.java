package com.touchip.organizer.utils;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.http.converter.ByteArrayHttpMessageConverter;

import quickutils.core.QUFactory.QCollection;
import quickutils.core.QUFactory.QLog;
import quickutils.core.QUFactory.QNotifications;
import quickutils.core.QUFactory.QPreconditions;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.hardware.Camera;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.text.format.DateFormat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.webkit.WebSettings.PluginState;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.roomorama.caldroid.CaldroidFragment;
import com.squareup.timessquare.sample.R;
import com.touchip.organizer.activities.AUserSettings;
import com.touchip.organizer.activities.SuperActivity;
import com.touchip.organizer.communication.rest.request.RestAddresses;
import com.touchip.organizer.communication.rest.request.SuperRequest;
import com.touchip.organizer.communication.rest.request.listener.ResponseUpdateApp;
import com.touchip.organizer.communication.rest.serializables.PathSerializable;
import com.touchip.organizer.utils.GlobalConstants.Hotspots;

public class Utils {

     public static final SimpleDateFormat DATE_FORMAT            = new SimpleDateFormat(GlobalConstants.DATE_FORMAT, java.util.Locale.getDefault());

     private static Context               CONTEXT;

     public static final String           UPDATE_APP_DIR         = Environment.getExternalStorageDirectory() + File.separator + "update" + File.separator;
     public static final String           DATE_FORMAT_YYYY_MM_DD = "yyyy-MM-dd";

     public static void updateApp(final AUserSettings aUserSettings) {

          AlertDialog.Builder ab = new AlertDialog.Builder(aUserSettings);
          ab.setMessage("Do you really want to update app?");
          ab.setPositiveButton("Yes", new DialogInterface.OnClickListener() {

               @Override public void onClick(DialogInterface arg0, int arg1) {
                    SuperRequest <byte[]> requestGetDatesToHighlight = new SuperRequest <byte[]>(aUserSettings, byte[].class, RestAddresses.UPDATE_APPLICATION, new ByteArrayHttpMessageConverter(), QCollection.newHashMap());
                    aUserSettings.execute(requestGetDatesToHighlight, new ResponseUpdateApp(aUserSettings));
                    arg0.dismiss();
               }
          });

          ab.setNegativeButton("No", new DialogInterface.OnClickListener() {

               @Override public void onClick(DialogInterface dialog, int which) {
               }
          });
          ab.show();
     }

     public static File writeFile(byte[] data, String fileName) throws IOException {
          File file = null , retFile = null;
          if ( !Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED) ) {
               // handle case of no SDCARD present
               QLog.debug("SDCARD not mounted");
          } else {

               file = new File(UPDATE_APP_DIR); // file name
               if ( !file.exists() ) {
                    file.mkdirs();
               }
               retFile = new File(UPDATE_APP_DIR + fileName);
               FileOutputStream out = new FileOutputStream(retFile);
               out.write(data);
               out.close();
          }

          return retFile;
     }

     public static Bundle putSeriliazableToBundle(String key, Serializable value) {
          Bundle bundle = new Bundle();
          bundle.putSerializable(key, value);
          return bundle;
     }

     public static String getCurrentDate() {
          Calendar calendar = Calendar.getInstance(); // Get current date.
          SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault());
          return dateFormat.format(calendar.getTime());
     }

     public static String getCurrentDateDD_MM_YYYY() {
          Calendar calendar = Calendar.getInstance(); // Get current date.
          SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
          return dateFormat.format(calendar.getTime());
     }

     @SuppressWarnings ( "unchecked" ) public static List <PathSerializable> parseByteArryaToPathSerializable(byte[] fileData, float canvasWidth, float canvasHeight) {
          List <PathSerializable> listPathes = null;
          try {
               ObjectInputStream ois;
               ois = new ObjectInputStream(new ByteArrayInputStream(fileData));
               listPathes = (ArrayList <PathSerializable>) ois.readObject();
               Matrix translateMatrix = new Matrix();

               for ( int i = 0; i < listPathes.size(); i++ ) {
                    translateMatrix.setScale(canvasWidth / listPathes.get(i).getSavedCanvasX(), canvasHeight / listPathes.get(i).getSavedCanvasY());
                    listPathes.get(i).transform(translateMatrix);
               }
          } catch (Exception e) {
               QLog.debug(e.getMessage());
          }

          return listPathes;
     }

     public static void setScaleFactor(View clickedView, View... arg) {
          List <View> oneRowButtonsArray = new ArrayList <View>();
          for ( View element : arg ) {
               if ( element.getTag().toString().equals(clickedView.getTag().toString()) ) {
                    oneRowButtonsArray.add(element);
               }
          }

          for ( int j = 0; j < oneRowButtonsArray.size(); j++ ) {
               oneRowButtonsArray.get(j).setScaleX(1f);
               oneRowButtonsArray.get(j).setScaleY(1f);
               oneRowButtonsArray.get(j).setBackgroundResource(R.drawable.background_view_rounded_single_trades);
          }

          clickedView.setScaleX(0.9f);
          clickedView.setScaleY(0.9f);
          clickedView.setBackgroundResource(R.drawable.background_view_rounded_single);
     }

     /**
      * Configure and set custom action bar
      * 
      * @param actionBar
      * @param customView
      */
     public static void configureCustomActionBar(ActionBar actionBar, View customView) {
          actionBar.setDisplayShowCustomEnabled(true);
          actionBar.setDisplayHomeAsUpEnabled(true);
          actionBar.setDisplayShowTitleEnabled(false);
          if ( null != customView ) {
               actionBar.setCustomView(customView);
          }
     }

     /**
      * Configure Web View
      * 
      * @param web
      */
     @SuppressLint ( "SetJavaScriptEnabled" ) public static WebView configureWebView(WebView webView) {
          webView.getSettings().setLoadWithOverviewMode(true);
          webView.getSettings().setJavaScriptEnabled(true);
          webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
          webView.getSettings().setUseWideViewPort(true);
          webView.getSettings().setGeolocationEnabled(true);
          webView.getSettings().setAllowContentAccess(true);
          webView.getSettings().setBuiltInZoomControls(true);
          webView.getSettings().setPluginState(PluginState.ON);
          webView.setLayerType(View.LAYER_TYPE_HARDWARE, null);
          webView.getSettings().setSupportZoom(true);
          webView.setVerticalScrollBarEnabled(true);
          webView.setHorizontalScrollBarEnabled(true);
          webView.setWebViewClient(new WebViewClient());

          int currentapiVersion = android.os.Build.VERSION.SDK_INT;
          if ( currentapiVersion >= android.os.Build.VERSION_CODES.JELLY_BEAN ) {
               fixNewAndroid(webView);
          }

          return webView;
     }

     @TargetApi ( 16 ) public static void fixNewAndroid(WebView webView) {
          try {
               webView.getSettings().setAllowUniversalAccessFromFileURLs(true);
          } catch (NullPointerException e) {
          }
     }

     public static String formatDate(Date date) {
          return DateFormat.format(DATE_FORMAT_YYYY_MM_DD, date).toString();
     }

     public static String getManufacturer() {
          return Build.MANUFACTURER;
     }

     public static Object deserializeObject(byte[] fileData) {
          try {
               ByteArrayInputStream b = new ByteArrayInputStream(fileData);
               ObjectInputStream o = new ObjectInputStream(b);
               return o.readObject();
          } catch (Exception e) {
               QLog.debug(e.getMessage());
          }
          return null;
     }

     public static void setViewsVisibility(int visibility, View... arg) {
          for ( View element : arg ) {
               try {
                    element.setVisibility(visibility);
               } catch (Exception e) {
                    QLog.debug(e.getMessage());
               }
          }
     }

     public static boolean hasGoogleMaps(Context context) {
          boolean installed = false;
          String[] libraries = context.getPackageManager().getSystemSharedLibraryNames();
          if ( !QPreconditions.isNull(libraries) ) {
               for ( String library : libraries ) {
                    if ( "com.google.android.maps".equalsIgnoreCase(library) ) {
                         installed = true;
                         break;
                    }
               }
          }
          return installed;
     }

     public static String convertPositionToLetter(int position) {
          switch (position) {
               case 0:
                    return "A";
               case 1:
                    return "B";
               case 2:
                    return "C";
               case 3:
                    return "D";
               case 4:
                    return "E";
               case 5:
                    return "F";
               case 6:
                    return "G";
               case 7:
                    return "H";
               case 8:
                    return "I";
               case 9:
                    return "J";
               case 10:
                    return "K";
               case 11:
                    return "L";
               case 12:
                    return "M";
               case 13:
                    return "N";
               default:
                    return "Z";
          }
     }

     /**
      * Convert Uri to real path on device
      * 
      * @param context
      * @param contentUri
      * @return string representation of full parh
      * @author Ihor Karpachev
      *         17 Dec 2013
      */
     public static String getRealPathFromURI(Context context, Uri contentUri) {
          Cursor cursor = null;
          try {
               String[] proj = { MediaStore.Images.Media.DATA };
               cursor = context.getContentResolver().query(contentUri, proj, null, null, null);
               int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
               cursor.moveToFirst();
               return cursor.getString(columnIndex);
          } finally {
               if ( cursor != null ) {
                    cursor.close();
               }
          }
     }

     /**
      * Check if ip address valid
      * 
      * @param ip
      *             string representation of ip address
      * @return true if given ip address is valid, false otherwise
      */
     public static boolean isIpAddressValid(String ip) {
          // Compile IP pattern from regular expression in GlobalConstants class
          Pattern pattern = Pattern.compile(GlobalConstants.PATTERN);
          // Create matcher with given ip address
          Matcher matcher = pattern.matcher(ip);
          // true if ip is valid, false otherwise
          return matcher.matches();
     }

     /**
      * Remove already showed dialog to avoid ANR
      * 
      * @param time
      *             period of time after which dialog will be hidden
      * @param dialog
      */
     public static void timerDelayRemoveDialog(long time, final Dialog dialog) {
          // create handler for posting dissmis event in UI thread
          new Handler().postDelayed(new Runnable() {
               @Override public void run() {
                    dialog.dismiss();
               }
          }, time);
     }

     /**
      * Wrapper for standart resources class
      * 
      * @param id
      * @return
      */
     public static String getResources(int id) {
          if ( null != CONTEXT ) {
               return CONTEXT.getResources().getString(id);
          } else {
               return String.valueOf("");
          }
     }

     /**
      * Return applcation's resources
      * 
      * @return Resources application's resources
      * @author Ihor Karpachev
      *         19 Dec 2013
      */
     public static Resources getResources() {
          if ( null != CONTEXT ) {
               return CONTEXT.getResources();
          } else {
               return null;
          }
     }

     /**
      * Set application context for static access
      * 
      * @param context
      *             context to set
      * @author Ihor Karpachev
      *         14 Dec 2013
      */
     public static void setAppContext(Context context) {
          CONTEXT = context;
     }

     /**
      * Get static context
      * 
      * @return Context application context
      * @author Ihor Karpachev
      *         14 Dec 2013
      */
     public static Context getAppContext() {
          return CONTEXT;
     }

     /**
      * Show custom toast with coloured background instead of image
      * 
      * @param activity
      *             current activity
      * @param message
      *             message to show
      * @param background
      *             value which represents color
      */
     public static void showCustomToastWithBackgroundColour(final Activity activity, final String message, final int background) {
          activity.runOnUiThread(new Runnable() {
               @Override public void run() {
                    try {
                         View layout = activity.getLayoutInflater().inflate(R.layout.toast_image_and_text, (ViewGroup) activity.findViewById(R.id.toast_layout_root));

                         ImageView image = (ImageView) layout.findViewById(R.id.toast_image_view);
                         TextView text = (TextView) layout.findViewById(R.id.toast_text_view);

                         text.setText(message);
                         image.setBackgroundColor(background);

                         Toast toast = new Toast(activity.getApplicationContext());

                         toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
                         toast.setDuration(Toast.LENGTH_SHORT);
                         toast.setView(layout);

                         toast.show();
                         text.startAnimation(AnimationManager.load(android.R.anim.slide_in_left));
                    } catch (Exception e) {
                         QLog.debug(e.getMessage());
                         QNotifications.showShortToast(activity.getApplicationContext(), message);
                    }
               }
          });
     }

     public static void showCustomToast(final Activity activity, final String message, final BitmapDrawable background) {
          activity.runOnUiThread(new Runnable() {

               @SuppressWarnings ( "deprecation" ) @Override public void run() {
                    try {
                         View layout = activity.getLayoutInflater().inflate(R.layout.toast_image_and_text, (ViewGroup) activity.findViewById(R.id.toast_layout_root));

                         ImageView image = (ImageView) layout.findViewById(R.id.toast_image_view);
                         TextView text = (TextView) layout.findViewById(R.id.toast_text_view);

                         text.setText(message);
                         image.setBackgroundDrawable(background);

                         Toast toast = new Toast(activity.getApplicationContext());

                         toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
                         toast.setDuration(Toast.LENGTH_SHORT);
                         toast.setView(layout);

                         toast.show();
                         text.startAnimation(AnimationManager.load(android.R.anim.slide_in_left));
                    } catch (Exception e) {
                         QLog.debug(e.getMessage());
                         QNotifications.showShortToast(activity.getApplicationContext(), message);
                    }
               }
          });
     }

     public static void showCustomToast(final Activity activity, final String message, final int imageResourcesId) {
          activity.runOnUiThread(new Runnable() {

               @Override public void run() {
                    try {
                         View layout = activity.getLayoutInflater().inflate(R.layout.toast_image_and_text, (ViewGroup) activity.findViewById(R.id.toast_layout_root));

                         ImageView image = (ImageView) layout.findViewById(R.id.toast_image_view);
                         TextView text = (TextView) layout.findViewById(R.id.toast_text_view);

                         text.setText(message);
                         image.setImageResource(imageResourcesId);

                         Toast toast = new Toast(activity.getApplicationContext());

                         toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
                         toast.setDuration(Toast.LENGTH_SHORT);
                         toast.setView(layout);

                         toast.show();
                         text.startAnimation(AnimationManager.load(android.R.anim.slide_in_left));
                    } catch (Exception e) {
                         QLog.debug(e.getMessage());
                         QNotifications.showShortToast(activity.getApplicationContext(), message);
                    }
               }
          });
     }

     public static void showCustomToast(final Activity activity, final int messageResourcesId, final int imageResourcesId) {

          activity.runOnUiThread(new Runnable() {

               @Override public void run() {
                    try {
                         View layout = activity.getLayoutInflater().inflate(R.layout.toast_image_and_text, (ViewGroup) activity.findViewById(R.id.toast_layout_root));

                         ImageView image = (ImageView) layout.findViewById(R.id.toast_image_view);
                         TextView text = (TextView) layout.findViewById(R.id.toast_text_view);

                         text.setText(Utils.getResources(messageResourcesId));
                         image.setImageResource(imageResourcesId);

                         Toast toast = new Toast(activity.getApplicationContext());

                         toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
                         toast.setDuration(Toast.LENGTH_SHORT);
                         toast.setView(layout);

                         toast.show();
                         text.startAnimation(AnimationManager.load(android.R.anim.slide_in_left));
                    } catch (Exception e) {
                         QLog.debug(e.getMessage());
                    }
               }
          });

     }

     public static void captureCameraPhoto(Activity activity) {
          try {
               // Create date formatter for filename
               SimpleDateFormat dateFormat = new SimpleDateFormat("yyyymmddhhmmss", Locale.ENGLISH);
               // format current date to string format
               String date = dateFormat.format(new Date());
               // construct filename
               GlobalConstants.capturedPhotoFilename = GlobalConstants.APP_PHOTO_DIRECTORY + date + ".jpg";
               // craete file with constructed filename
               File photoFile = new File(GlobalConstants.capturedPhotoFilename);
               photoFile.mkdirs();
               photoFile.createNewFile();

               // Construct Intent and show camera
               Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
               cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photoFile));
               activity.startActivityForResult(cameraIntent, GlobalConstants.CAPTURE_CAMERA_PHOTO);
          } catch (Exception e) {
               QLog.debug(e.getMessage());
               QNotifications.showShortToast(activity.getApplicationContext(), R.string.cannot_capture_picture);
          }
     }

     public static Bitmap getBitmapByHotspotType(String type) {

          Bitmap returnBitmap = Hotspots.PERMITS_HOTSPOT_IMAGE;

          if ( type.contains(Hotspots.ON_THE_FLY) ) {
               returnBitmap = Hotspots.ON_THE_FLY_IMAGE;
          }

          if ( type.contains(Hotspots.QUICK_NOTE_HOTSPOT) ) {
               returnBitmap = Hotspots.QUICK_NOTE_HOTSPOT_IMAGE;
          }

          if ( type.contains(Hotspots.HIGH_RISK) ) {
               returnBitmap = Hotspots.HIGH_RISK_HOTSPOT_IMAGE;
          }

          if ( type.contains(Hotspots.ASSET_HOTSPOT) ) {
               returnBitmap = Hotspots.ASSET_HOTSPOT_IMAGE;
          }

          if ( type.contains(Hotspots.CAMERA_HOTSPOT) ) {
               returnBitmap = Hotspots.CAMERA_HOTSPOT_IMAGE;
          }

          if ( type.contains(Hotspots.TRADE_HOTSPOT) ) {
               returnBitmap = Hotspots.TRADE_HOTSPOT_IMAGE;
          }

          if ( type.contains(Hotspots.WHITEBOARD_HOTSPOT) ) {
               returnBitmap = Hotspots.WHITEBOARD_HOTSPOT_IMAGE;
          }

          if ( type.contains(Hotspots.WASTE_HOTSPOT) ) {
               returnBitmap = Hotspots.WASTE_HOTSPOT_IMAGE;
          }

          if ( type.contains(Hotspots.NOTE_HOTSPOT) ) {
               returnBitmap = Hotspots.NOTE_HOTSPOT_IMAGE;
          }

          if ( type.equalsIgnoreCase(Hotspots.SAFETY_HOTSPOT) ) {
               returnBitmap = Hotspots.SAFETY_HOTSPOT_IMAGE;
          }

          if ( type.contains(Hotspots.TRADE_HOTSPOT) ) {
               returnBitmap = Hotspots.TRADE_HOTSPOT_IMAGE;
          }

          return returnBitmap;
     }

     /**
      * Define has device active camera or not
      * 
      * @return true if device has camera, false otherwise
      * @author Ihor Karpachev
      *         14 Dec 2013
      */
     public static boolean hasDeviceCamera() {
          return Camera.getNumberOfCameras() > 0;
     }

     /**
      * Description: Addition helper class for QuickAction which represents popup window
      * 
      * @author Ihor Karpachev
      *         All content is activity property of Datascope Ltd.
      *         Date: 14 Dec 2013
      *         Time: 00:25:44
      */
     public static class PopupWindows {
          protected Context       mContext;
          protected PopupWindow   mWindow;
          protected View          mRootView;
          protected Drawable      mBackground = null;
          protected WindowManager mWindowManager;

          public PopupWindows ( Context context ) {
               mContext = context;
               mWindow = new PopupWindow(context);

               mWindow.setTouchInterceptor(new OnTouchListener() {
                    @Override public boolean onTouch(View v, MotionEvent event) {
                         if ( event.getAction() == MotionEvent.ACTION_OUTSIDE ) {
                              mWindow.dismiss();
                              return true;
                         }
                         return false;
                    }
               });
               mWindowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
          }

          @SuppressWarnings ( "deprecation" ) protected void preShow() {
               if ( mRootView == null ) { throw new IllegalStateException("setContentView was not called with activity view to display."); }
               if ( mBackground == null ) {
                    mWindow.setBackgroundDrawable(new BitmapDrawable());
               } else {
                    mWindow.setBackgroundDrawable(mBackground);
               }

               mWindow.setWidth(WindowManager.LayoutParams.WRAP_CONTENT);
               mWindow.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
               mWindow.setTouchable(true);
               mWindow.setFocusable(true);
               mWindow.setOutsideTouchable(true);

               mWindow.setContentView(mRootView);
          }

          public void setBackgroundDrawable(Drawable background) {
               mBackground = background;
          }

          public void setContentView(View root) {
               mRootView = root;

               mWindow.setContentView(root);
          }

          public void setContentView(int layoutResID) {
               LayoutInflater inflator = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

               setContentView(inflator.inflate(layoutResID, null));
          }

          public void setOnDismissListener(PopupWindow.OnDismissListener listener) {
               mWindow.setOnDismissListener(listener);
          }

          public void dismiss() {
               mWindow.dismiss();
          }
     }

     /**
      * Provide animation utils
      * 
      * @author Karpachev Ihor
      */
     public static class AnimationManager {

          private AnimationManager () {

          }

          /**
           * Load animation with default duration defined in xml file which describes animation
           * 
           * @param id
           * @return
           */
          public static Animation load(int id) {
               Animation animation = null;
               if ( null != CONTEXT ) {
                    animation = AnimationUtils.loadAnimation(CONTEXT, id);
               }
               return animation;
          }

          /**
           * Animate menu (appear/dissapear)
           * 
           * @param view
           * @param visibility
           * @param animationId
           * @param duration
           */
          public static void animateMenu(final View view, final int visibility, int animationId, int duration) {
               Animation animation = AnimationManager.load(animationId, duration);
               animation.setAnimationListener(new AnimationListener() {

                    @Override public void onAnimationEnd(Animation animation) {
                         view.setVisibility(visibility);
                    }

                    @Override public void onAnimationRepeat(Animation animation) {
                    }

                    @Override public void onAnimationStart(Animation animation) {
                    }
               });
               view.setVisibility(visibility);
               view.startAnimation(animation);
          }

          /**
           * Return animation with some duration, when duration equal 0, work same as AnimationManager.loadAnimation(id)
           * 
           * @param id
           * @param duration
           *             set animation duration or in case of 0 return default duration
           * @return animation to be played
           */
          public static Animation load(int id, int duration) {
               Animation animation = load(id);
               if ( null != animation ) {
                    if ( 0 == duration ) {
                         return animation;
                    } else {
                         animation.setDuration(duration);
                    }
               }
               return animation;
          }

          /**
           * Return animation with presetted duration
           * 
           * @param id
           * @param duration
           *             set animation duration or in case of 0 return default duration
           * @param startOffset
           *             set start offset animation in seconds
           * @return animation to be played
           */
          public static Animation load(int id, int duration, int startOffset) {
               Animation animation = null;
               if ( null != CONTEXT ) {
                    animation = AnimationUtils.loadAnimation(CONTEXT, id);
                    if ( 0 != duration ) {
                         animation.setDuration(duration);
                    }
                    animation.setStartOffset(startOffset);
               }
               return animation;
          }
     }

     public static int getImageIdByType(String type) {
          int resultImageId = R.drawable.hide_hotspot;

          if ( type.equals(Hotspots.ON_THE_FLY) ) {
               resultImageId = (R.drawable.red_hat);
          }

          if ( type.equals(Hotspots.HIGH_RISK) ) {
               resultImageId = (R.drawable.high_risk);
          }

          if ( type.equals(Hotspots.QUICK_NOTE_HOTSPOT) ) {
               resultImageId = (R.drawable.abc);
          }

          if ( type.equals(Hotspots.ASSET_HOTSPOT) ) {
               resultImageId = (R.drawable.asset);
          }

          if ( type.equals(Hotspots.CAMERA_HOTSPOT) ) {
               resultImageId = (R.drawable.camera);
          }

          if ( type.equals(Hotspots.TRADE_HOTSPOT) ) {
               resultImageId = (R.drawable.trade);
          }

          if ( type.equals(Hotspots.WHITEBOARD_HOTSPOT) ) {
               resultImageId = (R.drawable.whiteboard);
          }

          if ( type.equals(Hotspots.NOTE_HOTSPOT) ) {
               resultImageId = (R.drawable.note_hotspot);
          }

          if ( type.equals(Hotspots.WASTE_HOTSPOT) ) {
               resultImageId = (R.drawable.waste);
          }

          if ( type.equalsIgnoreCase(Hotspots.SAFETY_HOTSPOT) ) {
               resultImageId = (R.drawable.safety_hotspot);
          }

          if ( type.equals(Hotspots.PERMITS_HOTSPOT) ) {
               resultImageId = (R.drawable.permission_hotspot);
          }

          if ( type.equals(Hotspots.HIDE_ALL) ) {
               resultImageId = (R.drawable.hide_hotspot);
          }

          if ( type.equals(Hotspots.SHOW_ALL) ) {
               resultImageId = (R.drawable.show_all);
          }

          return resultImageId;
     }

     /**
      * If URL does not contain http:// concatenate it with given string
      * 
      * @param url
      *             to normalize
      * @return browseable url starts with http
      */
     public static String normalizeUrl(String url) {
          // Check if user inputed without http concatenate it with http://
          if ( !url.startsWith("http://") ) {
               url = "http://" + url;
          }
          return url;
     }

     /**
      * Configure dialog and return it
      * 
      * @param activity
      *             which trigger event
      * @return fully configured dialog
      */
     public static Dialog getConfiguredDialog(SuperActivity activity) {
          Dialog d = new Dialog(activity);
          d.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
          d.setCanceledOnTouchOutside(true);
          return d;
     }

     /**
      * Configure caldroid fragment before displaying
      * 
      * @param today
      * @param datesAndColour
      * @return
      */
     public static CaldroidFragment getConfiguredCaldroid(Date today, HashMap <Date, Integer> datesAndColour) {
          final CaldroidFragment calendar = new CaldroidFragment();
          // becouse implementation of calendar we have to call setBackgroundResourceForDates FIRST, and only after setBackgroundResourceForDate
          calendar.setBackgroundResourceForDates(datesAndColour);
          calendar.setBackgroundResourceForDate(R.drawable.background_green_round_view, today);
          calendar.setTextColorForDate(R.color.white, today);
          Bundle bundle = new Bundle();
          bundle.putBoolean(com.roomorama.caldroid.CaldroidFragment.ENABLE_SWIPE, true);
          calendar.setArguments(bundle);
          return calendar;
     }
}
