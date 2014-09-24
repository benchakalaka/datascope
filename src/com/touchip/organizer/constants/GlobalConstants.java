package com.touchip.organizer.constants;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Environment;
import android.widget.ImageButton;

import com.squareup.timessquare.sample.R;
import com.touchip.organizer.communication.rest.model.AssetsList.POJORoboSingleAsset;
import com.touchip.organizer.communication.rest.model.CompaniesList.POJORoboCompany;
import com.touchip.organizer.communication.rest.model.HotspotsList.POJORoboHotspot;
import com.touchip.organizer.communication.rest.model.ModelFullSiteInfo;
import com.touchip.organizer.communication.rest.model.ModelUser;
import com.touchip.organizer.communication.rest.model.PathsCreationTimeList.POJORoboPathCreationTime;
import com.touchip.organizer.utils.Utils;

public class GlobalConstants {

     public static final String                                             COLOURS_BUTTON            = "colours" , SHAPES_BUTTON = "shapes" , BRUSH_SIZE_BUTTON = "size" , TEXT_SETTINGS_BUTTON = "text";
     public static POJORoboHotspot                                          LAST_CLICKED_HOTSPOT;
     public static String                                                   SITE_ID                   = "-1";
     public static POJORoboPathCreationTime                                 LAST_CLICKED_WHITE_BOARD;
     public static String                                                   SITE_PLAN_IMAGE_NAME;
     public static List <ImageButton>                                       HOTSPOTS_BUTTONS_ARRAY    = new ArrayList <ImageButton>();
     public static String                                                   APP_VERSION               = "0";

     @SuppressWarnings ( "deprecation" ) public static final BitmapDrawable PIN_PICTURE_IMAGE         = new BitmapDrawable(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(Utils.getResources(), R.drawable.pin_picture), 60, 60, false).copy(Bitmap.Config.ARGB_8888, true));
     @SuppressWarnings ( "deprecation" ) public static final BitmapDrawable SHOW_PICTURE_IMAGE        = new BitmapDrawable(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(Utils.getResources(), R.drawable.show_pictures), 60, 60, false).copy(Bitmap.Config.ARGB_8888, true));
     @SuppressWarnings ( "deprecation" ) public static final BitmapDrawable SHOW_HOTSPOT_DETAIL_IMAGE = new BitmapDrawable(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(Utils.getResources(), R.drawable.show_hotspot_detail), 60, 60, false)

                                                                                                      .copy(Bitmap.Config.ARGB_8888, true));

     public static ModelFullSiteInfo                                        SITE_PLAN_FULL_INFO;

     // contains full photo filename
     public static String                                                   capturedPhotoFilename;
     public static ModelUser                                                CURRENT_USER;

     /**
      * FragmentCompaniesList data
      */
     public static POJORoboCompany                                          LAST_CLICKED_COMPANY;

     public static POJORoboSingleAsset                                      LAST_CLICKED_ASSET;

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
     public static final String                                             DATE_FORMAT               = "yyyy-MM-dd";

     private GlobalConstants () {
     }

     public static final String[] DRAWING_PATH_TYPES = { "SPD", "GWD", "HWD" };

     public static final int      SPD                = 0;
     public static final int      GWD                = 1;
     public static final int      HWD                = 2;
}