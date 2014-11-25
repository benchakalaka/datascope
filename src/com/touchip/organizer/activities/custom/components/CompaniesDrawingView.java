package com.touchip.organizer.activities.custom.components;

import java.util.ArrayList;
import java.util.List;

import quickutils.core.QUFactory.QLog;
import quickutils.core.QUFactory.QPreconditions;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path.Direction;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;

import com.touchip.organizer.activities.ADrawingCompanies;
import com.touchip.organizer.activities.custom.components.WhiteBoardDrawingView.ShapesType;
import com.touchip.organizer.communication.rest.serializables.PaintSerializable;
import com.touchip.organizer.communication.rest.serializables.PathSerializable;
import com.touchip.organizer.utils.FilterManager;
import com.touchip.organizer.utils.GlobalConstants;
import com.touchip.organizer.utils.Utils;

public class CompaniesDrawingView extends View {
     // canvas bitmap
     public static Bitmap             canvasBitmap , startBitmap;
     // drawing path
     private PathSerializable         drawPath;
     // drawing and canvas paint
     private PaintSerializable        drawPaint , canvasPaint;
     // initial color
     private int                      paintColor           = Color.TRANSPARENT;

     private static int               CURRENT_DRAWING_TYPE = ShapesType.STANDART_DRAWING;
     // canvas
     private Canvas                   drawCanvas;
     // brush sizes
     private float                    brushSize;
     // step for drawing coordinates (100 pixels)
     private static final float       STEP                 = 100;
     private float                    touchX;
     private float                    touchY;
     // default paint for grid drawing
     public final static Paint        coordinatesPaint     = new Paint();
     // default paint for grid drawing
     private final TextPaint          labelsPaint          = new TextPaint();
     static float                     temporaryBrushSize;
     // companies and colours filter (0 means draw everything, 1 means hide all)
     private int                      companyColorFilter   = 0;
     // for UNDO operation
     private List <PathSerializable>  paths                = new ArrayList <PathSerializable>();
     // for UNDO operation
     private List <PathSerializable>  redoPaths            = new ArrayList <PathSerializable>();
     //
     private static PaintSerializable staticPaint          = new PaintSerializable();

     // rectangle of drawing for different types of shapes
     private final RectF              rectangleOfDrawing   = new RectF();

     // path for each shape
     private static PathSerializable  shapePath            = new PathSerializable();

     private boolean                  drawCoordinates      = false;
     private float                    touchDownX;
     private float                    touchDownY;
     private boolean                  isNeedToStopOnDrawMethod;

     public static int                WIDTH;
     public static int                HEIGHT;
     public static byte[]             DRAWING_PATHS_BYTES;

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

     @Override protected void onAttachedToWindow() {
          super.onAttachedToWindow();
          ADrawingCompanies.loadPathes();
          if ( null != GlobalConstants.LAST_CLICKED_COMPANY ) {
               setColor(GlobalConstants.LAST_CLICKED_COMPANY.colour);
          }
     }

     /**
      * This method is using to return from onDraw method when we do not need to draw shapes
      * 
      * @param value
      */
     public void setIsNeedToStopOnDrawMethod(boolean value) {
          isNeedToStopOnDrawMethod = value;
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

          shapePath = new PathSerializable();
     }

     /**
      * Set drawing type: it could be any constant from static ShapeType interface
      * 
      * @param shapeType
      */
     public void setDrawingShape(int shapeType) {
          CURRENT_DRAWING_TYPE = shapeType;
          isNeedToStopOnDrawMethod = true;
     }

     /**
      * Set drawing type: it could be any constant from static ShapeType interface
      * 
      * @param shapeType
      */
     public int getDrawingShape() {
          return CURRENT_DRAWING_TYPE;
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
          try {
               if ( null != DRAWING_PATHS_BYTES ) {
                    setPaths(Utils.parseByteArryaToPathSerializable(DRAWING_PATHS_BYTES, w, h));
               }
          } catch (Exception ex) {
               ex.printStackTrace();
          }
          // canvasBitmap = Bitmap.createBitmap(w, h, Config.ARGB_8888);
          // startBitmap = Bitmap.createBitmap(w, h, Config.ARGB_8888);
          // drawCanvas = new Canvas();

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
               canvas.drawText(((int) touchX / 100) + ":" + Utils.convertPositionToLetter((int) touchY / 100), touchX - 40, touchY - 20, coordinatesPaint);

               coordinatesPaint.setTextSize(temporaryBrushSize);
               temporaryBrushSize = coordinatesPaint.getStrokeWidth();
               coordinatesPaint.setStrokeWidth(2);
               canvas.drawLine(0, touchY, WIDTH, touchY, coordinatesPaint);
               canvas.drawLine(touchX, 0, touchX, HEIGHT, coordinatesPaint);
               // canvas.drawCircle(touchX, touchY, 20, coordinatesPaint);
               coordinatesPaint.setStrokeWidth(temporaryBrushSize);
               try {
                    temporaryBrushSize = coordinatesPaint.getColor();
                    coordinatesPaint.setColor(Color.parseColor(GlobalConstants.LAST_CLICKED_COMPANY.colour));
                    coordinatesPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD_ITALIC));
                    canvas.drawText(GlobalConstants.LAST_CLICKED_COMPANY.companyName, touchX + 20, touchY - 20, coordinatesPaint);
                    coordinatesPaint.setColor((int) temporaryBrushSize);
               } catch (Exception ex) {
                    ex.printStackTrace();
               }
          }

          // draw all pathes which in arraylst
          for ( int i = 0; i < paths.size(); i++ ) {
               switch (getCompanyColourFilter()) {
               // 0 draw lines for all companies
                    case 0:
                         PathSerializable pathToDraw = getPaths().get(i);
                         PaintSerializable paint = pathToDraw.getPaint();
                         staticPaint.setStrokeWidth(paint.brushStrokeWith);
                         staticPaint.setColor(paint.colour);
                         canvas.drawPath(pathToDraw, staticPaint);
                         break;

                    // 1 - hide all drawings
                    case 1:
                         break;

                    // apply filter, draw only lines with color equal companyColorFilter
                    default:
                         try {
                              if ( FilterManager.activeCompaniesColor.contains(Integer.valueOf(getPaths().get(i).getPaint().colour)) ) {
                                   staticPaint.setStrokeWidth(getPaths().get(i).getPaint().brushStrokeWith);
                                   staticPaint.setColor(getPaths().get(i).getPaint().colour);
                                   canvas.drawPath(getPaths().get(i), staticPaint);
                              }
                         } catch (Exception e) {
                              QLog.debug(e.getMessage());
                         }
                         break;
               }
          }

          if ( isNeedToStopOnDrawMethod ) {
               isNeedToStopOnDrawMethod = false;
               return;
          }

          // draw last path with last selected color
          drawPaint.setColor(paintColor);
          canvas.drawPath(drawPath, drawPaint);

          // ////////////////////////
          if ( CURRENT_DRAWING_TYPE != ShapesType.STANDART_DRAWING ) {
               shapePath.reset();
               staticPaint.setStrokeWidth(brushSize);
               staticPaint.setColor(paintColor);

               switch (CURRENT_DRAWING_TYPE) {
                    case ShapesType.CIRCLE:
                         rectangleOfDrawing.set(touchDownX, touchDownY, touchX, touchY);
                         shapePath.addOval(rectangleOfDrawing, Direction.CW);
                         canvas.drawPath(shapePath, staticPaint);
                         break;

                    case ShapesType.RECTANGLE:
                         if ( touchX < touchDownX ) {
                              if ( touchY < touchDownY ) {
                                   rectangleOfDrawing.set(touchX, touchY, touchDownX, touchDownY);
                                   shapePath.addRoundRect(rectangleOfDrawing, 5, 5, Direction.CW);
                              } else {
                                   rectangleOfDrawing.set(touchX, touchDownY, touchDownX, touchY);
                                   shapePath.addRoundRect(rectangleOfDrawing, 5, 5, Direction.CW);
                              }
                         } else {
                              if ( touchY > touchDownY ) {
                                   rectangleOfDrawing.set(touchDownX, touchDownY, touchX, touchY);
                                   shapePath.addRoundRect(rectangleOfDrawing, 5, 5, Direction.CW);
                              } else {
                                   rectangleOfDrawing.set(touchDownX, touchY, touchX, touchDownY);
                                   shapePath.addRoundRect(rectangleOfDrawing, 5, 5, Direction.CW);
                              }
                         }
                         canvas.drawPath(shapePath, staticPaint);
                         break;

                    case ShapesType.TRIANGLE:
                         shapePath.moveTo(touchDownX, touchDownY);
                         // line to finger touch
                         shapePath.lineTo(touchX, touchY);
                         shapePath.lineTo(touchDownX + (touchDownX - touchX), touchY);
                         shapePath.lineTo(touchDownX, touchDownY);
                         canvas.drawPath(shapePath, staticPaint);
                         break;

                    case ShapesType.ARROW:
                         shapePath.moveTo(touchDownX, touchDownY);
                         shapePath.lineTo(touchX, touchY);
                         canvas.drawPath(shapePath, staticPaint);

                         // DRAW ARROW
                         double phi = Math.toRadians(140);
                         int barb = 20;
                         float dy = touchDownY - touchY;
                         float dx = touchDownX - touchX;
                         double theta = Math.atan2(dy, dx);
                         double x ,
                         y ,
                         rho = theta + phi;
                         for ( int j = 0; j < 2; j++ ) {
                              x = touchX - barb * Math.cos(rho);
                              y = touchY - barb * Math.sin(rho);
                              canvas.drawLine(touchX, touchY, (float) x, (float) y, staticPaint);
                              rho = theta - phi;
                         }

                         break;

                    case ShapesType.LINE:
                         shapePath.moveTo(touchDownX, touchDownY);
                         shapePath.lineTo(touchX, touchY);
                         canvas.drawPath(shapePath, staticPaint);

                         // DRAW ARROW
                         /*
                          * double phi = Math.toRadians(140);
                          * int barb = 20;
                          * float dy = touchDownY - touchY;
                          * float dx = touchDownX - touchX;
                          * double theta = Math.atan2(dy, dx);
                          * double x ,
                          * y ,
                          * rho = theta + phi;
                          * for ( int j = 0; j < 2; j++ ) {
                          * x = touchX - barb * Math.cos(rho);
                          * y = touchY - barb * Math.sin(rho);
                          * canvas.drawLine(touchX, touchY, (float) x, (float) y, staticPaint);
                          * /*
                          * PathSerializable arrow = new PathSerializable();
                          * arrow.moveTo(touchX, touchY);
                          * arrow.lineTo((float) x, (float) y);
                          * canvas.drawPath(arrow, staticPaint);
                          * rho = theta - phi;
                          * }
                          */
                         break;
                    default:
                         QLog.debug("Unknown shape type");
               }
          } else {
               canvas.drawPath(drawPath, drawPaint);
          }
          // ////////////////////////
     }

     @Override public boolean onTouchEvent(final MotionEvent event) {
          touchX = event.getX();
          touchY = event.getY();

          // respond to down, move and up events
          switch (event.getAction()) {
               case MotionEvent.ACTION_DOWN:
                    touchDownX = touchX;
                    touchDownY = touchY;

                    if ( CURRENT_DRAWING_TYPE == ShapesType.STANDART_DRAWING ) {
                         drawCoordinates = true;
                         drawPath.moveTo(touchX, touchY);
                    }

                    break;
               case MotionEvent.ACTION_MOVE:
                    if ( CURRENT_DRAWING_TYPE == ShapesType.STANDART_DRAWING ) {
                         drawPath.lineTo(touchX, touchY);
                    }
                    break;

               case MotionEvent.ACTION_UP:
                    isNeedToStopOnDrawMethod = true;
                    PaintSerializable ps = new PaintSerializable();
                    ps.brushStrokeWith = drawPaint.getStrokeWidth();
                    ps.colour = drawPaint.getColor();

                    drawCoordinates = false;

                    if ( CURRENT_DRAWING_TYPE != ShapesType.STANDART_DRAWING ) {

                         PathSerializable newPath = new PathSerializable();
                         newPath.setPaint(ps);
                         newPath.setSavedCanvasX(CompaniesDrawingView.WIDTH);
                         newPath.setSavedCanvasY(CompaniesDrawingView.HEIGHT);

                         switch (CURRENT_DRAWING_TYPE) {

                              case ShapesType.LINE:
                                   newPath.moveTo(touchDownX, touchDownY);
                                   newPath.lineTo(event.getX(), event.getY());
                                   getPaths().add(newPath);
                                   break;

                              case ShapesType.ARROW:
                                   newPath.moveTo(touchDownX, touchDownY);
                                   newPath.lineTo(event.getX(), event.getY());

                                   // DRAW ARROW
                                   double phi = Math.toRadians(140);
                                   int barb = 20;
                                   float dy = touchDownY - touchY;
                                   float dx = touchDownX - touchX;
                                   double theta = Math.atan2(dy, dx);
                                   double x ,
                                   y ,
                                   rho = theta + phi;
                                   for ( int j = 0; j < 2; j++ ) {
                                        x = touchX - barb * Math.cos(rho);
                                        y = touchY - barb * Math.sin(rho);
                                        newPath.moveTo(touchX, touchY);
                                        newPath.lineTo((float) x, (float) y);
                                        rho = theta - phi;
                                   }
                                   getPaths().add(newPath);

                                   break;

                              case ShapesType.TRIANGLE:
                                   newPath.moveTo(touchDownX, touchDownY);
                                   // line to finger touch
                                   newPath.lineTo(touchX, touchY);
                                   newPath.lineTo(touchDownX + (touchDownX - touchX), touchY);
                                   newPath.lineTo(touchDownX, touchDownY);
                                   getPaths().add(newPath);
                                   break;

                              case ShapesType.CIRCLE:
                                   rectangleOfDrawing.set(touchDownX, touchDownY, touchX, touchY);
                                   newPath.addOval(rectangleOfDrawing, Direction.CW);
                                   getPaths().add(newPath);
                                   break;

                              case ShapesType.RECTANGLE:
                                   if ( touchX < touchDownX ) {
                                        if ( touchY < touchDownY ) {
                                             rectangleOfDrawing.set(touchX, touchY, touchDownX, touchDownY);
                                             newPath.addRoundRect(rectangleOfDrawing, 5, 5, Direction.CW);
                                        } else {
                                             rectangleOfDrawing.set(touchX, touchDownY, touchDownX, touchY);
                                             newPath.addRoundRect(rectangleOfDrawing, 5, 5, Direction.CW);
                                        }
                                   } else {
                                        if ( touchY > touchDownY ) {
                                             rectangleOfDrawing.set(touchDownX, touchDownY, touchX, touchY);
                                             newPath.addRoundRect(rectangleOfDrawing, 5, 5, Direction.CW);
                                        } else {
                                             rectangleOfDrawing.set(touchDownX, touchY, touchX, touchDownY);
                                             newPath.addRoundRect(rectangleOfDrawing, 5, 5, Direction.CW);
                                        }
                                   }
                                   getPaths().add(newPath);
                                   break;

                              default:
                                   shapePath.setPaint(ps);
                                   // add path
                                   getPaths().add(shapePath);
                                   break;
                         }
                    } else {
                         drawPath.lineTo(touchX, touchY);
                         drawCanvas.drawPath(drawPath, drawPaint);
                         if ( drawPaint.getColor() != Color.TRANSPARENT ) {
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
                    }

                    invalidate();

                    resetDrawingTools();
                    // //////////////////////////////////////////////////////////////////////////////////////////////
                    ADrawingCompanies.saveAndSendDrawingOnBackgroundThread(GlobalConstants.CURRENT_FLOOR);
                    // ////////////////////////////////////////////////////////////////////////////////////////////////
                    break;
               default:
                    return false;
          }
          // redraw
          invalidate();
          return true;
     }

     // get last path
     public void undo() {
          isNeedToStopOnDrawMethod = true;
          if ( !getPaths().isEmpty() ) {
               redoPaths.add(getPaths().remove(getPaths().size() - 1));
               invalidate();
          }
     }

     public void redo() {
          isNeedToStopOnDrawMethod = true;
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
      * 
      * @param companyFilterColor
      *             0 means show all, 1 hide all, or company color in integer format
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
          if ( !QPreconditions.isNull(canvasBitmap) ) { return startBitmap.getHeight(); }
          return 0;
     }

     /**
      * Return cavas width
      * 
      * @return
      */
     public static int getCanvasWidth() {
          if ( !QPreconditions.isNull(canvasBitmap) ) { return startBitmap.getWidth(); }
          return 0;
     }

     /**
      * Set brush size
      * 
      * @param newSize
      *             size to set
      */
     public int getBrushSize() {
          return (int) brushSize;
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
          if ( null == paths ) {
               this.paths = new ArrayList <PathSerializable>();
          } else {
               this.paths = paths;
          }
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
