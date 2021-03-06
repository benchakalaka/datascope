package com.touchip.organizer.utils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Environment;
import android.widget.ImageButton;

import com.squareup.timessquare.sample.R;
import com.touchip.organizer.communication.rest.model.ModelAssetsList.POJORoboSingleAsset;
import com.touchip.organizer.communication.rest.model.ModelCompaniesList.POJORoboCompany;
import com.touchip.organizer.communication.rest.model.ModelDatesToHighlightList;
import com.touchip.organizer.communication.rest.model.ModelHotspotsList;
import com.touchip.organizer.communication.rest.model.ModelHotspotsList.POJORoboHotspot;
import com.touchip.organizer.communication.rest.model.ModelPathsCreationTimeList.POJORoboPathCreationTime;
import com.touchip.organizer.communication.rest.model.ModelUserList.ModelUser;

public class GlobalConstants {

     public static final String                                             COLOURS_BUTTON            = "colours" , SHAPES_BUTTON = "shapes" , BRUSH_SIZE_BUTTON = "size" , TEXT_SETTINGS_BUTTON = "text";
     public static POJORoboHotspot                                          LAST_CLICKED_HOTSPOT;
     public static String                                                   LAST_CLICKED_MARKER_ID    = "-1";
     public static POJORoboPathCreationTime                                 LAST_CLICKED_WHITE_BOARD;
     public static String                                                   CURRENT_FLOOR             = "";
     public static String                                                   SITE_PLAN_IMAGE_NAME;
     public static List <ImageButton>                                       HOTSPOTS_BUTTONS_ARRAY    = new ArrayList <ImageButton>();
     public static String                                                   APP_VERSION               = "0";
     public static String                                                   TODAY_FROM_SERVER         = "";

     /**
      * FragmentCompaniesList data
      */
     public static POJORoboCompany                                          LAST_CLICKED_COMPANY;

     /**
      * MapActivity Data
      */
     public static ModelDatesToHighlightList                                datestoHighlight;

     /**
      * Hotspots Data
      */
     public static ModelHotspotsList                                        SIGNED_HOTSPOTS;
     public static ModelHotspotsList                                        UNASSIGNED_HOTSPOTS;
     public static ModelHotspotsList                                        SIGNED_HOTSPOTS_ALL;
     public static ModelHotspotsList                                        UNASSIGNED_HOTSPOTS_ALL;

     public static POJORoboSingleAsset                                      LAST_CLICKED_ASSET;

     @SuppressWarnings ( "deprecation" ) public static final BitmapDrawable PIN_PICTURE_IMAGE         = new BitmapDrawable(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(Utils.getResources(), R.drawable.pin_picture), 60, 60, false).copy(Bitmap.Config.ARGB_8888, true));
     @SuppressWarnings ( "deprecation" ) public static final BitmapDrawable SHOW_PICTURE_IMAGE        = new BitmapDrawable(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(Utils.getResources(), R.drawable.show_pictures), 60, 60, false).copy(Bitmap.Config.ARGB_8888, true));
     @SuppressWarnings ( "deprecation" ) public static final BitmapDrawable SHOW_HOTSPOT_DETAIL_IMAGE = new BitmapDrawable(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(Utils.getResources(), R.drawable.show_hotspot_detail), 60, 60, false)
                                                                                                                .copy(Bitmap.Config.ARGB_8888, true));

     // contains full photo filename
     public static String                                                   capturedPhotoFilename;
     public static ModelUser                                                CURRENT_USER;

     /**
      * Deubug string which displaying in logcat
      */
     public static final String                                             LOG_TAG                   = "Datascope Ltd.";

     /**
      * GlobalConstants for image types PNG, JPEG
      */
     public static final String[]                                           IMAGE_TYPES_PNG_JPEG      = new String[] { "image/png", "image/jpeg" };

     /**
      * Application's directory for storing photo, images etc.
      */
     public static final String                                             APP_PHOTO_DIRECTORY       = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + File.separator + "touchIP" + File.separator;
     public static final int                                                CAPTURE_CAMERA_PHOTO      = 100;

     /**
      * Pattern to define correct IP address
      */
     public static final String                                             PATTERN                   = "^([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.";
     public static final String                                             DATE_FORMAT               = "yyyy-MM-dd";

     private GlobalConstants () {

     }

     /**
      * Interface which provide hotspot types
      * 
      * @author Karpachev Ihor
      */
     public static class Hotspots {

          public static Bitmap       CAMERA_HOTSPOT_IMAGE     = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(Utils.getResources(), R.drawable.camera), 50, 50, false).copy(Bitmap.Config.ARGB_8888, true);
          public static Bitmap       TRADE_HOTSPOT_IMAGE      = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(Utils.getResources(), R.drawable.trade), 30, 30, false).copy(Bitmap.Config.ARGB_8888, true);
          public static Bitmap       ASSET_HOTSPOT_IMAGE      = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(Utils.getResources(), R.drawable.asset), 30, 30, false).copy(Bitmap.Config.ARGB_8888, true);
          public static Bitmap       SAFETY_HOTSPOT_IMAGE     = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(Utils.getResources(), R.drawable.safety_hotspot), 30, 30, false).copy(Bitmap.Config.ARGB_8888, true);
          public static Bitmap       NOTE_HOTSPOT_IMAGE       = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(Utils.getResources(), R.drawable.note_hotspot), 30, 30, false).copy(Bitmap.Config.ARGB_8888, true);
          public static Bitmap       PERMITS_HOTSPOT_IMAGE    = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(Utils.getResources(), R.drawable.permission_hotspot), 30, 30, false).copy(Bitmap.Config.ARGB_8888, true);
          public static Bitmap       WASTE_HOTSPOT_IMAGE      = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(Utils.getResources(), R.drawable.waste), 30, 30, false).copy(Bitmap.Config.ARGB_8888, true);
          public static Bitmap       WHITEBOARD_HOTSPOT_IMAGE = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(Utils.getResources(), R.drawable.whiteboard), 30, 30, false).copy(Bitmap.Config.ARGB_8888, true);
          public static Bitmap       SHOW_ALL_HOTSPOT_IMAGE   = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(Utils.getResources(), R.drawable.show_all), 30, 30, false).copy(Bitmap.Config.ARGB_8888, true);
          public static Bitmap       HIDE_ALL_HOTSPOT_IMAGE   = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(Utils.getResources(), R.drawable.hide_hotspot), 30, 30, false).copy(Bitmap.Config.ARGB_8888, true);
          public static Bitmap       QUICK_NOTE_HOTSPOT_IMAGE = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(Utils.getResources(), R.drawable.abc), 30, 30, false).copy(Bitmap.Config.ARGB_8888, true);
          public static Bitmap       HIGH_RISK_HOTSPOT_IMAGE  = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(Utils.getResources(), R.drawable.high_risk), 30, 30, false).copy(Bitmap.Config.ARGB_8888, true);
          public static Bitmap       ON_THE_FLY_IMAGE         = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(Utils.getResources(), R.drawable.red_hat), 30, 30, false).copy(Bitmap.Config.ARGB_8888, true);

          public static final String CAMERA_HOTSPOT           = "CCTV Camera hotspot";
          public static final String TRADE_HOTSPOT            = "Trade";
          public static final String ASSET_HOTSPOT            = "Asset";
          public static final String PERMITS_HOTSPOT          = "Permit Details hotspot";
          public static final String WASTE_HOTSPOT            = "Waste Issues hotspot";
          public static final String NOTE_HOTSPOT             = "Notes hotspot";
          public static final String SAFETY_HOTSPOT           = "Work Hotspot";
          public static final String WHITEBOARD_HOTSPOT       = "Whiteboard hotspot";
          public static final String SHOW_ALL                 = "all";
          public static final String HIDE_ALL                 = "hide_all";
          public static final String QUICK_NOTE_HOTSPOT       = "Quick note";
          public static final String HIGH_RISK                = "High Risk";
          public static final String ON_THE_FLY               = "On the fly";

          private Hotspots () {
          }
     }

     /**
      * Interface which provide WHITE BOARDS TYPES
      * 
      * @author Karpachev Ihor
      */
     public static class DrawingType {
          /**
           * SPD - Site Plan Drawing
           */
          public static final String SITE_PLAN_DRAWING          = "SPD";
          /**
           * General Whiteboard Drawing
           */
          public static final String GENERAL_WHITEBOARD_DRAWING = "GWD";
          /**
           * Hotspot whiteboard drawing
           */
          public static final String HOTSPOT_WHITEBOARD_DRAWING = "HWD";

          private DrawingType () {

          }
     }
}
