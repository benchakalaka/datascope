package com.touchip.organizer.communication.rest.serializables;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import android.graphics.Path;
import android.graphics.RectF;

import com.touchip.organizer.communication.rest.serializables.PathSerializable.PathAction.PathActionType;

public class PathSerializable extends Path implements Serializable {

     private static final long       serialVersionUID = -5974912367682897467L;

     private final List <PathAction> actions          = new ArrayList <PathSerializable.PathAction>();

     private float                   textX , textY;

     private String                  text             = "";

     private PaintSerializable       paint            = new PaintSerializable();
     private float                   savedCanvasX , savedCanvasY;

     public void setDrawText(float x, float y, String textToDraw) {
          this.textX = x;
          this.textY = y;
          this.text = textToDraw;
     }

     public boolean hasTextToDraw() {
          return !"".equals(this.text);
     }

     public String getTextToDraw() {
          return text;
     }

     public float getTextX() {
          return textX;
     }

     public float getTextY() {
          return textY;
     }

     public float getSavedCanvasY() {
          return savedCanvasY;
     }

     public void setSavedCanvasY(float savedCanvasY) {
          this.savedCanvasY = savedCanvasY;
     }

     public float getSavedCanvasX() {
          return savedCanvasX;
     }

     public void setSavedCanvasX(float savedCanvasX) {
          this.savedCanvasX = savedCanvasX;
     }

     private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
          in.defaultReadObject();
          drawThisPath();
     }

     /**
      * @param paint
      *             the paint to set
      */
     public void setPaint(PaintSerializable paint) {
          this.paint = paint;
     }

     /**
      * @return the paint
      */
     public PaintSerializable getPaint() {
          return paint;
     }

     @Override public void moveTo(float x, float y) {
          actions.add(new ActionMove(x, y));
          super.moveTo(x, y);
     }

     @Override public void lineTo(float x, float y) {
          actions.add(new ActionLine(x, y));
          super.lineTo(x, y);
     }

     @Override public void addOval(RectF oval, Direction dir) {
          actions.add(new ActionOval(oval));
          super.addOval(oval, dir);
     }

     @Override public void addRoundRect(RectF rect, float rx, float ry, Direction dir) {
          actions.add(new ActionRoundrectangle(rect));
          super.addRoundRect(rect, rx, ry, dir);
     }

     private void drawThisPath() {
          for ( PathAction p : actions ) {
               if ( p.getType().equals(PathActionType.MOVE_TO) ) {
                    super.moveTo(p.getX(), p.getY());
               } else if ( p.getType().equals(PathActionType.LINE_TO) ) {
                    super.lineTo(p.getX(), p.getY());
               } else if ( p.getType().equals(PathActionType.ADD_OVAL) ) {
                    ActionOval oval = (ActionOval) p;
                    RectF r = new RectF(oval.getLeft(), oval.getTop(), oval.getRight(), oval.getBottom());
                    super.addOval(r, Direction.CW);
               } else if ( p.getType().equals(PathActionType.ADD_ROUND_RECT) ) {
                    ActionRoundrectangle oval = (ActionRoundrectangle) p;
                    RectF r = new RectF(oval.getLeft(), oval.getTop(), oval.getRight(), oval.getBottom());
                    super.addRoundRect(r, 15, 15, Direction.CW);
               }
          }
     }

     public interface PathAction {
          public enum PathActionType {
               LINE_TO , MOVE_TO , ADD_OVAL , ADD_ROUND_RECT
          };

          public PathActionType getType();

          public float getX();

          public float getY();
     }

     public class ActionMove implements PathAction , Serializable {
          private static final long serialVersionUID = -7198142191254133295L;

          private final float       x , y;

          public ActionMove ( float x , float y ) {
               this.x = x;
               this.y = y;
          }

          @Override public PathActionType getType() {
               return PathActionType.MOVE_TO;
          }

          @Override public float getX() {
               return x;
          }

          @Override public float getY() {
               return y;
          }
     }

     public class ActionRoundrectangle implements PathAction , Serializable {
          private static final long serialVersionUID = 2436181604041442662L;
          private final float       left , top , right , bottom;

          public ActionRoundrectangle ( RectF rect ) {
               this.left = rect.left;
               this.top = rect.top;
               this.right = rect.right;
               this.bottom = rect.bottom;
          }

          public float getBottom() {
               return bottom;
          }

          public float getRight() {
               return right;
          }

          public float getTop() {
               return top;
          }

          public float getLeft() {
               return left;
          }

          @Override public PathActionType getType() {
               return PathActionType.ADD_ROUND_RECT;
          }

          @Override public float getX() {
               return 0;
          }

          @Override public float getY() {
               return 0;
          }
     }

     public class ActionOval implements PathAction , Serializable {
          private static final long serialVersionUID = 2436181604041442662L;
          private final float       left , top , right , bottom;

          public ActionOval ( RectF oval ) {
               this.left = oval.left;
               this.top = oval.top;
               this.right = oval.right;
               this.bottom = oval.bottom;
          }

          public float getBottom() {
               return bottom;
          }

          public float getRight() {
               return right;
          }

          public float getTop() {
               return top;
          }

          public float getLeft() {
               return left;
          }

          @Override public PathActionType getType() {
               return PathActionType.ADD_OVAL;
          }

          @Override public float getX() {
               return 0;
          }

          @Override public float getY() {
               return 0;
          }
     }

     public class ActionLine implements PathAction , Serializable {
          private static final long serialVersionUID = 8307137961494172589L;

          private final float       x , y;

          public ActionLine ( float x , float y ) {
               this.x = x;
               this.y = y;
          }

          @Override public PathActionType getType() {
               return PathActionType.LINE_TO;
          }

          @Override public float getX() {
               return x;
          }

          @Override public float getY() {
               return y;
          }

     }
}