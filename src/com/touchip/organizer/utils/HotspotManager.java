package com.touchip.organizer.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.squareup.timessquare.sample.R;

public class HotspotManager {

     /**
      * Interface which provide hotspot types
      * 
      * @author Karpachev Ihor
      */
     public static interface Hotspots {

          public static Bitmap         CAMERA_HOTSPOT_IMAGE     = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(Utils.getResources(), R.drawable.camera), 50, 50, false).copy(Bitmap.Config.ARGB_8888, true);
          public static Bitmap         TRADE_HOTSPOT_IMAGE      = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(Utils.getResources(), R.drawable.trade), 60, 60, false).copy(Bitmap.Config.ARGB_8888, true);
          public static Bitmap         ASSET_HOTSPOT_IMAGE      = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(Utils.getResources(), R.drawable.asset), 60, 60, false).copy(Bitmap.Config.ARGB_8888, true);

          public static Bitmap         SAFETY_HOTSPOT_IMAGE     = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(Utils.getResources(), R.drawable.safety_hotspot), 60, 60, false).copy(Bitmap.Config.ARGB_8888, true);
          public static Bitmap         NOTE_HOTSPOT_IMAGE       = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(Utils.getResources(), R.drawable.note_hotspot), 60, 60, false).copy(Bitmap.Config.ARGB_8888, true);
          public static Bitmap         PERMITS_HOTSPOT_IMAGE    = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(Utils.getResources(), R.drawable.permission_hotspot), 60, 60, false).copy(Bitmap.Config.ARGB_8888, true);
          public static Bitmap         WASTE_HOTSPOT_IMAGE      = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(Utils.getResources(), R.drawable.waste), 60, 60, false).copy(Bitmap.Config.ARGB_8888, true);
          public static Bitmap         WHITEBOARD_HOTSPOT_IMAGE = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(Utils.getResources(), R.drawable.whiteboard), 60, 60, false).copy(Bitmap.Config.ARGB_8888, true);
          public static Bitmap         SHOW_ALL_HOTSPOT_IMAGE   = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(Utils.getResources(), R.drawable.show_all), 60, 60, false).copy(Bitmap.Config.ARGB_8888, true);
          public static Bitmap         HIDE_ALL_HOTSPOT_IMAGE   = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(Utils.getResources(), R.drawable.hide_hotspot), 60, 60, false).copy(Bitmap.Config.ARGB_8888, true);

          public static final String[] HOTSPOTS_NAMES           = { "Trade", "Permit Details hotspot", "Waste Issues hotspot", "Notes hotspot", "Safety Issues hotspot", "Whiteboard hotspot", "Asset", "CCTV Camera hotspot", "all", "hide_all" };

          public static final int      TRADE                    = 0;
          public static final int      PERMIT                   = 1;
          public static final int      WASTE                    = 2;
          public static final int      NOTE                     = 3;
          public static final int      SAFETY                   = 4;
          public static final int      WHITEBOARD               = 5;
          public static final int      ASSET                    = 6;
          public static final int      CAMERA                   = 7;
          public static final int      SHOW_ALL                 = 8;
          public static final int      HIDE_ALL                 = 9;

     }
}