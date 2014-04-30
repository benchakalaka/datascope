package com.touchip.organizer.activities.custom.components;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;

import com.touchip.organizer.activities.DrawingCompaniesActivity_;
import com.touchip.organizer.activities.fragments.FragmentHotspotsList;
import com.touchip.organizer.communication.rest.serializables.PaintSerializable;
import com.touchip.organizer.communication.rest.serializables.PathSerializable;
import com.touchip.organizer.utils.Utils;

public class CompaniesDrawingView extends View {
     // canvas bitmap
     public static Bitmap             canvasBitmap , startBitmap;
     // drawing path
     private PathSerializable         drawPath;
     // drawing and canvas paint
     private PaintSerializable        drawPaint , canvasPaint;
     // initial color
     private int                      paintColor         = Color.TRANSPARENT;
     // canvas
     private Canvas                   drawCanvas;
     // brush sizes
     private float                    brushSize;
     // step for drawing coordinates (100 pixels)
     private static final float       STEP               = 100;
     private float                    touchX;
     private float                    touchY;
     // default paint for grid drawing
     private final Paint              coordinatesPaint   = new Paint();
     // default paint for grid drawing
     private final TextPaint          labelsPaint        = new TextPaint();
     static float                     temporaryBrushSize;
     // companies and colours filter (0 means draw everything, 1 means hide all)
     private int                      companyColorFilter = 0;
     // for UNDO operation
     private List <PathSerializable>  paths              = new ArrayList <PathSerializable>();
     // for UNDO operation
     private List <PathSerializable>  redoPaths          = new ArrayList <PathSerializable>();
     //
     private static PaintSerializable staticPaint        = new PaintSerializable();

     private boolean                  drawCoordinates    = false;

     public static int                WIDTH;
     public static int                HEIGHT;

     public CompaniesDrawingView ( Context context , AttributeSet attrs ) {
          super(context, attrs);
          resetDrawingTools();
          setDrawingCacheEnabled(true);
          setDrawingCacheQuality(DRAWING_CACHE_QUALITY_HIGH);
          staticPaint.setAntiAlias(true);
          staticPaint.setStyle(Paint.Style.STROKE);
          staticPaint.setStrokeJoin(Paint.Join.ROUND);
          staticPaint.setStrokeCap(Paint.Cap.ROUND);
     }

     /**
      * Reset drawing brush to default state
      */
     private void resetDrawingTools() {
          drawPath = new PathSerializable();
          drawPaint = new PaintSerializable();
          drawPaint.setColor(Color.TRANSPARENT);
          drawPaint.setAntiAlias(true);
          drawPaint.setStrokeWidth(brushSize);
          drawPaint.setStyle(Paint.Style.STROKE);
          drawPaint.setStrokeJoin(Paint.Join.ROUND);
          drawPaint.setStrokeCap(Paint.Cap.ROUND);

          canvasPaint = new PaintSerializable();
          labelsPaint.setFakeBoldText(true);
          labelsPaint.setUnderlineText(true);
          labelsPaint.setTypeface(Typeface.SERIF);
     }

     @Override protected void onSizeChanged(int w, int h, int oldw, int oldh) {
          super.onSizeChanged(w, h, oldw, oldh);
          BitmapFactory.Options opt = new BitmapFactory.Options();
          opt.inMutable = true;
          WIDTH = w;
          HEIGHT = h;
          canvasBitmap = Bitmap.createScaledBitmap(canvasBitmap, w, h, false);
          startBitmap = Bitmap.createScaledBitmap(startBitmap, w, h, false);
          drawCanvas = new Canvas(canvasBitmap);

          FragmentHotspotsList.ADAPTER.updateHotspotsButtonsList("all");
          DrawingCompaniesActivity_.INSTANCE.loadPathes();
     }

     /**
      * Check paths array, if > 0 - need to be saved
      * 
      * @return
      */
     public boolean isNeedAutoSave() {
          return (null != getPaths()) && (!getPaths().isEmpty());
     }

     /**
      * onDraw will be called after any touch event or invalidating drawing surface
      */
     @Override protected void onDraw(Canvas canvas) {

          // draw simple bitmap
          canvas.drawBitmap(startBitmap, 0, 0, canvasPaint);

          // draw all pathes which in arraylst
          for ( int i = 0; i < paths.size(); i++ ) {
               switch (getCompanyColourFilter()) {
               // 0 draw lines for all companies
                    case 0:

                         staticPaint.setStrokeWidth(getPaths().get(i).getPaint().brushStrokeWith);
                         staticPaint.setColor(getPaths().get(i).getPaint().colour);
                         canvas.drawPath(getPaths().get(i), staticPaint);
                         break;

                    // 1 - hide all drawings
                    case 1:
                         break;

                    // apply filter, draw only lines with color equal companyColorFilter
                    default:
                         try {
                              if ( getPaths().get(i).getPaint().colour == getCompanyColourFilter() ) {
                                   staticPaint.setStrokeWidth(getPaths().get(i).getPaint().brushStrokeWith);
                                   staticPaint.setColor(getPaths().get(i).getPaint().colour);
                                   canvas.drawPath(getPaths().get(i), staticPaint);
                              }
                         } catch (Exception e) {
                              Utils.logw(e.getMessage());
                         }
                         break;
               }
          }
          // draw last path with last selected color
          drawPaint.setColor(paintColor);
          canvas.drawPath(drawPath, drawPaint);

          // draw Y axis - LETTERS
          for ( int i = 0; i < (startBitmap.getHeight() / 100) + 1; i++ ) {
               canvas.drawLine(0, STEP * i, 20, STEP * i, coordinatesPaint);
               canvas.drawText(Utils.convertPositionToLetter(i), 17, STEP * i + 15, coordinatesPaint);
          }

          // draw X axis - NUMBERS
          for ( int i = 0; i < (startBitmap.getWidth() / 100) + 1; i++ ) {
               canvas.drawLine(STEP * i, 0, STEP * i, 20, coordinatesPaint);
               canvas.drawText(String.valueOf(i), STEP * i + 5, 20, coordinatesPaint);
          }

          // if user draw something, draw coordinates of moving
          if ( drawCoordinates && paintColor != Color.TRANSPARENT ) {
               temporaryBrushSize = coordinatesPaint.getTextSize();
               coordinatesPaint.setTextSize(20);
               // draw two crossing horizontal and vertical lines and circle on finger touch position
               canvas.drawText(((int) touchX / 100) + ":" + Utils.convertPositionToLetter((int) touchY / 100), touchX - 40, touchY - 40, coordinatesPaint);

               coordinatesPaint.setTextSize(temporaryBrushSize);
               temporaryBrushSize = coordinatesPaint.getStrokeWidth();
               coordinatesPaint.setStrokeWidth(1);
               canvas.drawLine(0, touchY, WIDTH, touchY, coordinatesPaint);
               canvas.drawLine(touchX, 0, touchX, HEIGHT, coordinatesPaint);
               // canvas.drawCircle(touchX, touchY, 20, coordinatesPaint);
               coordinatesPaint.setStrokeWidth(temporaryBrushSize);
          }
     }

     @Override public boolean onTouchEvent(final MotionEvent event) {
          touchX = event.getX();
          touchY = event.getY();

          // respond to down, move and up events
          switch (event.getAction()) {
               case MotionEvent.ACTION_DOWN:
                    drawCoordinates = true;
                    drawPath.moveTo(touchX, touchY);
                    break;
               case MotionEvent.ACTION_MOVE:
                    drawPath.lineTo(touchX, touchY);
                    break;

               case MotionEvent.ACTION_UP:
                    drawCoordinates = false;
                    drawPath.lineTo(touchX, touchY);
                    drawCanvas.drawPath(drawPath, drawPaint);
                    if ( drawPaint.getColor() != Color.TRANSPARENT ) {

                         PaintSerializable ps = new PaintSerializable();
                         ps.brushStrokeWith = drawPaint.getStrokeWidth();
                         ps.colour = drawPaint.getColor();
                         // apply WIDTH and HEIGHT scale factor to path for different screen support
                         drawPath.setSavedCanvasX(CompaniesDrawingView.WIDTH);
                         drawPath.setSavedCanvasY(CompaniesDrawingView.HEIGHT);
                         // set brush to path
                         drawPath.setPaint(ps);
                         // add path
                         getPaths().add(drawPath);
                    }
                    resetDrawingTools();
                    break;
               default:
                    return false;
          }
          // redraw
          invalidate();
          return true;
     }

     /**
      * Undo function
      */
     public void undo() {
          if ( !getPaths().isEmpty() ) {
               redoPaths.add(getPaths().remove(getPaths().size() - 1));
               invalidate();
          }
     }

     /**
      * Redo function
      */
     public void redo() {
          if ( !redoPaths.isEmpty() ) {
               getPaths().add(redoPaths.remove(redoPaths.size() - 1));
               invalidate();
          }
     }

     // ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
     // GETTERS, SETTERS SECTION //
     // ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
     /**
      * Change brush colour
      * 
      * @param newColor
      */
     public void setColor(String newColor) {
          invalidate();
          paintColor = Color.parseColor(newColor);
          drawPaint.setColor(paintColor);
     }

     /**
      * Change brush colour
      * 
      * @param newColor
      */
     public void setColor(int newColor) {
          invalidate();
          paintColor = newColor;
          drawPaint.setColor(paintColor);
     }

     /**
      * Set colour filter
      */
     public void setCompanyColourFilter(int companyFilterColor) {
          companyColorFilter = companyFilterColor;
          invalidate();
     }

     /**
      * Company filter represents like integer value (0 - draw everything, 1 hide everuthing)
      * 
      * @return
      */
     public int getCompanyColourFilter() {
          return companyColorFilter;
     }

     /**
      * Return canvas height
      * 
      * @return
      */
     public static int getCanvasHeight() {
          if ( !Utils.isNull(canvasBitmap) ) { return startBitmap.getHeight(); }
          return 0;
     }

     /**
      * Return cavas width
      * 
      * @return
      */
     public static int getCanvasWidth() {
          if ( !Utils.isNull(canvasBitmap) ) { return startBitmap.getWidth(); }
          return 0;
     }

     /**
      * Set brush size
      * 
      * @param newSize
      *             size to set
      */
     public void setBrushSize(float newSize) {
          brushSize = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, newSize, getResources().getDisplayMetrics());
          drawPaint.setStrokeWidth(brushSize);
     }

     /**
      * @param paths
      *             the paths to set
      */
     public void setPaths(List <PathSerializable> paths) {
          this.paths = paths;
          redoPaths = new ArrayList <PathSerializable>();
          invalidate();
     }

     /**
      * @return the paths
      */
     public List <PathSerializable> getPaths() {
          return this.paths;
     }
}
