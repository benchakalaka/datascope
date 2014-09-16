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
import com.touchip.organizer.communication.rest.model.HotspotsList.POJORoboHotspot;
import com.touchip.organizer.communication.rest.model.PathsCreationTimeList.POJORoboPathCreationTime;
import com.touchip.organizer.communication.rest.model.User;

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

     @SuppressWarnings ( "deprecation" ) public static final BitmapDrawable PIN_PICTURE_IMAGE         = new BitmapDrawable(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(Utils.getResources(), R.drawable.pin_picture), 60, 60, false).copy(Bitmap.Config.ARGB_8888, true));
     @SuppressWarnings ( "deprecation" ) public static final BitmapDrawable SHOW_PICTURE_IMAGE        = new BitmapDrawable(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(Utils.getResources(), R.drawable.show_pictures), 60, 60, false).copy(Bitmap.Config.ARGB_8888, true));
     @SuppressWarnings ( "deprecation" ) public static final BitmapDrawable SHOW_HOTSPOT_DETAIL_IMAGE = new BitmapDrawable(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(Utils.getResources(), R.drawable.show_hotspot_detail), 60, 60, false)
                                                                                                                .copy(Bitmap.Config.ARGB_8888, true));

     // contains full photo filename
     public static String                                                   capturedPhotoFilename;
     public static User                                                     CURRENT_USER;

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
