package com.touchip.organizer.activities;

import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import quickutils.core.QUFactory.QCollection;
import quickutils.core.QUFactory.QLog;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.net.Uri;
import android.provider.MediaStore.Images.Media;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.octo.android.robospice.persistence.DurationInMillis;
import com.squareup.timessquare.sample.R;
import com.touchip.organizer.activities.custom.components.ActionBarGeneralWhiteboard;
import com.touchip.organizer.activities.custom.components.ActionBarGeneralWhiteboard_;
import com.touchip.organizer.activities.custom.components.WhiteBoardDrawingView;
import com.touchip.organizer.activities.custom.components.WhiteBoardDrawingView.ShapesType;
import com.touchip.organizer.activities.custom.components.dialogs.CDialogClearWhiteBoard_;
import com.touchip.organizer.activities.custom.components.dialogs.CDialogInputTextToDraw_;
import com.touchip.organizer.activities.custom.components.dialogs.CDialogSaveWhiteBoard_;
import com.touchip.organizer.communication.rest.model.ModelPathsCreationTimeList;
import com.touchip.organizer.communication.rest.request.RestAddresses;
import com.touchip.organizer.communication.rest.request.SaveHSWhiteboardRequest;
import com.touchip.organizer.communication.rest.request.SaveWhiteboardRequest;
import com.touchip.organizer.communication.rest.request.SuperRequest;
import com.touchip.organizer.communication.rest.request.listener.ResponseDownloadOrCreateNewWhiteboard;
import com.touchip.organizer.communication.rest.request.listener.ResponseGetPathsCreationTime;
import com.touchip.organizer.communication.rest.request.listener.SaveHSWhiteboardRequestListener;
import com.touchip.organizer.communication.rest.request.listener.SaveWhiteboardRequestListener;
import com.touchip.organizer.communication.rest.serializables.PathSerializable;
import com.touchip.organizer.utils.GlobalConstants;
import com.touchip.organizer.utils.GlobalConstants.DrawingType;
import com.touchip.organizer.utils.HTTP_PARAMS;
import com.touchip.organizer.utils.Utils;
import com.touchip.organizer.utils.Utils.AnimationManager;

@EActivity ( R.layout.fragment_white_board ) public class AGeneralWhiteBoard extends SuperActivity {
     private AlertDialog.Builder                   builder;
     public static String                          WHITEBOARD_TYPE   = "";
     public static boolean                         IS_WHITEBOARD_NEW = false;
     public static boolean                         IS_NEED_TO_BACK   = false;

     public static AGeneralWhiteBoard              INSTANCE;

     // Load Views
     @ViewById public static WhiteBoardDrawingView WHITE_BOARD_DRAWING;

     @ViewById ImageButton                         ibRedo , ibUndo , ibThick , ibMedium , ibThin , ibEraser;
     @ViewById ImageButton                         ibShapesRectangle , ibShapesCircle , ibShapesLine , ibShapesTriangle , ibShapesFreeDrawing , ibDrawText;
     @ViewById ImageButton                         ibClearAll , ibLoadWhiteboard , ibSaveDrawing , ibCreateNewWhiteboard , ibRefreshWhiteboard;

     @ViewById ImageView                           ivWhiteboardSettings , ivShapes , ivWhiteboardSettingsSelected , ivShapesSelected;
     @ViewById public ImageView                    ivDrawingState;

     // Layouts which contains all control button (for hide/show feature)
     @ViewById LinearLayout                        llBrushSize , llChangeTextSize , llDrawShapes;
     public ActionBarGeneralWhiteboard             customActionBar;

     @AfterViews void afterViews() {
          INSTANCE = this;

          builder = new AlertDialog.Builder(this);
          customActionBar = ActionBarGeneralWhiteboard_.build(AGeneralWhiteBoard.this);
          Utils.configureCustomActionBar(getActionBar(), customActionBar);
          getActionBar().setIcon(R.drawable.drawings);

          ibShapesCircle.setTag(GlobalConstants.SHAPES_BUTTON);
          ibShapesRectangle.setTag(GlobalConstants.SHAPES_BUTTON);
          ibShapesLine.setTag(GlobalConstants.SHAPES_BUTTON);
          ibShapesTriangle.setTag(GlobalConstants.SHAPES_BUTTON);
          ibShapesFreeDrawing.setTag(GlobalConstants.SHAPES_BUTTON);

          ibThin.setTag(GlobalConstants.BRUSH_SIZE_BUTTON);
          ibMedium.setTag(GlobalConstants.BRUSH_SIZE_BUTTON);
          ibThick.setTag(GlobalConstants.BRUSH_SIZE_BUTTON);
          ibEraser.setTag(GlobalConstants.TEXT_SETTINGS_BUTTON);

          ibShapesFreeDrawing.setBackgroundResource(R.drawable.background_view_rounded_single);
          ibThick.setBackgroundResource(R.drawable.background_view_rounded_single);

          WHITE_BOARD_DRAWING.setColor(Color.BLACK);
          WHITE_BOARD_DRAWING.setBrushSize(5);
     }

     @Override protected void onResume() {
          super.onResume();
          WHITE_BOARD_DRAWING.setDrawingShape(ShapesType.STANDART_DRAWING);
          WHITE_BOARD_DRAWING.setColor(Color.BLACK);

          ivDrawingState.setBackgroundColor(Color.BLACK);
     }

     @Click void ivDrawingState() {
          ivDrawingState.startAnimation(AnimationManager.load(R.anim.shake));
          Utils.showCustomToast(INSTANCE, "Display current drawing state", R.drawable.color_picker);
     }

     private void unselectAllSettingsButton() {
          ivWhiteboardSettingsSelected.setVisibility(View.INVISIBLE);
          ivShapesSelected.setVisibility(View.INVISIBLE);
     }

     @Override public boolean onOptionsItemSelected(MenuItem item) {
          switch (item.getItemId()) {
               case android.R.id.home:
                    Dialog d = Utils.getConfiguredDialog(AGeneralWhiteBoard.this);
                    d.setContentView(CDialogSaveWhiteBoard_.build(AGeneralWhiteBoard.this, d));
                    d.show();
                    return true;
               default:
                    return super.onOptionsItemSelected(item);
          }
     }

     /**
      * Load paths array
      */
     public void loadPathes() {
          try {
               // Variable array for POST request
               Map <String, String> params = QCollection.newHashMap();
               if ( WHITEBOARD_TYPE.equals(GlobalConstants.DrawingType.HOTSPOT_WHITEBOARD_DRAWING) || IS_WHITEBOARD_NEW ) {
                    params.put(HTTP_PARAMS.MARKER_ID, GlobalConstants.LAST_CLICKED_MARKER_ID);
                    params.put(HTTP_PARAMS.DATE, GlobalConstants.SITE_PLAN_IMAGE_NAME);
                    params.put(HTTP_PARAMS.FLOOR, GlobalConstants.CURRENT_FLOOR);
                    params.put(HTTP_PARAMS.TYPE, WHITEBOARD_TYPE);
                    params.put(HTTP_PARAMS.HOTSPOT_ID, String.valueOf(GlobalConstants.LAST_CLICKED_HOTSPOT.id));
               } else {
                    params.put(HTTP_PARAMS.PATH_ID, String.valueOf(GlobalConstants.LAST_CLICKED_WHITE_BOARD.id));
               }
               String url = IS_WHITEBOARD_NEW ? RestAddresses.CREATE_NEW_WHITE_BOARD : RestAddresses.DOWNLOAD_DRAWING_PATHES;
               SuperRequest <byte[]> request = new SuperRequest <byte[]>(this, byte[].class, url, new ByteArrayHttpMessageConverter(), params);
               execute(request, new ResponseDownloadOrCreateNewWhiteboard(this));
          } catch (Exception e) {
               QLog.debug(e.getMessage());
               return;
          }
     }

     public void saveAndSendDrawing(String whiteBoardName) {
          progressDialog.show();
          try {
               // Variable array for POST request
               MultiValueMap <String, Object> params = new LinkedMultiValueMap <String, Object>();
               File file = new File(getApplicationContext().getCacheDir(), "binary" + System.currentTimeMillis() + ".binary");
               List <PathSerializable> listPathes = WHITE_BOARD_DRAWING.getPaths();
               if ( file.exists() ) {
                    file.delete();
               }
               file.createNewFile();
               ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file));
               out.writeObject(listPathes);
               out.close();

               FileSystemResource fileSystemResources = new FileSystemResource(file);

               // send WB name
               if ( null != whiteBoardName ) {
                    params.add(HTTP_PARAMS.NAME, whiteBoardName);
               }

               if ( !WHITE_BOARD_DRAWING.getPaths().isEmpty() ) {
                    params.add(HTTP_PARAMS.DRAWING_DATA, fileSystemResources);
               }

               if ( WHITEBOARD_TYPE.equals(GlobalConstants.DrawingType.HOTSPOT_WHITEBOARD_DRAWING) ) {
                    params.add(HTTP_PARAMS.TYPE, WHITEBOARD_TYPE);
                    params.add(HTTP_PARAMS.MARKER_ID, GlobalConstants.LAST_CLICKED_MARKER_ID);
                    params.add(HTTP_PARAMS.DATE, GlobalConstants.SITE_PLAN_IMAGE_NAME);
                    params.add(HTTP_PARAMS.FLOOR, GlobalConstants.CURRENT_FLOOR);
                    params.add(HTTP_PARAMS.HOTSPOT_ID, String.valueOf(GlobalConstants.LAST_CLICKED_HOTSPOT.id));
               } else {
                    if ( !IS_WHITEBOARD_NEW ) {
                         params.add(HTTP_PARAMS.PATH_ID, String.valueOf(GlobalConstants.LAST_CLICKED_WHITE_BOARD.id));
                    } else {
                         IS_WHITEBOARD_NEW = !IS_WHITEBOARD_NEW;
                         params.add(HTTP_PARAMS.TYPE, WHITEBOARD_TYPE);
                         params.add(HTTP_PARAMS.MARKER_ID, GlobalConstants.LAST_CLICKED_MARKER_ID);
                         params.add(HTTP_PARAMS.DATE, GlobalConstants.SITE_PLAN_IMAGE_NAME);
                         params.add(HTTP_PARAMS.FLOOR, GlobalConstants.CURRENT_FLOOR);
                    }
               }

               // attempt to save drawing
               try {
                    WHITE_BOARD_DRAWING.setDrawingCacheEnabled(true);
                    String saveResponseFilePath = Media.insertImage(getContentResolver(), WHITE_BOARD_DRAWING.getDrawingCache(), Utils.getCurrentDate() + ".png", "drawing");
                    // Preparing feedback for user
                    boolean isSuccessfullySaved = saveResponseFilePath != null;
                    String userFeedback = (isSuccessfullySaved) ? Utils.getResources(R.string.drawing_saved_to_gallery) : Utils.getResources(R.string.cannot_save_image);
                    if ( isSuccessfullySaved ) {
                         FileSystemResource snapshotSpringWrapper = new FileSystemResource(new File(Utils.getRealPathFromURI(getApplicationContext(), Uri.parse(saveResponseFilePath))));
                         params.add(HTTP_PARAMS.WHITEBOARD_SNAPSHOT, snapshotSpringWrapper);
                    }
                    WHITE_BOARD_DRAWING.destroyDrawingCache();
                    Utils.showCustomToast(AGeneralWhiteBoard.this, userFeedback, R.drawable.save);
               } catch (Exception ex) {
                    ex.printStackTrace();
               }

               if ( WHITEBOARD_TYPE.equals(GlobalConstants.DrawingType.HOTSPOT_WHITEBOARD_DRAWING) ) {
                    // hotstpo wb
                    SaveHSWhiteboardRequest request = new SaveHSWhiteboardRequest(params, RestAddresses.SAVE_HWD_PATHS);
                    getSpiceManager().execute(request, request.createCacheKey(), DurationInMillis.ALWAYS_EXPIRED, new SaveHSWhiteboardRequestListener(this));
               } else {
                    SaveWhiteboardRequest request = new SaveWhiteboardRequest(params, RestAddresses.SAVE_GWD_PATHS);
                    getSpiceManager().execute(request, request.createCacheKey(), DurationInMillis.ALWAYS_EXPIRED, new SaveWhiteboardRequestListener(this));
               }
               if ( IS_WHITEBOARD_NEW ) {
                    IS_WHITEBOARD_NEW = !IS_WHITEBOARD_NEW;
               }
          } catch (Exception e) {
               QLog.debug(e.getMessage());
          }
     }

     // //////////////////////////// CLICK PROCESSOR SECTION //////////////////////////////////////////////////////////////
     @UiThread @Click void ibDrawText() {
          ibDrawText.startAnimation(AnimationManager.load(R.anim.pump_bottom, 500));
          WHITE_BOARD_DRAWING.disableEraserMode();
          Dialog dialogTextToDraw = Utils.getConfiguredDialog(INSTANCE);
          dialogTextToDraw.setContentView(CDialogInputTextToDraw_.build(this, dialogTextToDraw));
          dialogTextToDraw.show();
     }

     @Click void ibClearAll() {

          Dialog d = Utils.getConfiguredDialog(INSTANCE);
          d.setContentView(CDialogClearWhiteBoard_.build(INSTANCE, d));
          d.show();

          /*
           * builder.setTitle(getResources().getString(R.string.clear_canvas));
           * builder.setMessage(getResources().getString(R.string.everything_will_be_clear));
           * builder.setPositiveButton(getResources().getString(R.string.yes), new DialogInterface.OnClickListener() {
           * @Override public void onClick(DialogInterface dialog, int which) {
           * WHITE_BOARD_DRAWING.disableEraserMode();
           * WHITE_BOARD_DRAWING.clearAll();
           * ibClearAll.startAnimation(AnimationManager.load(R.anim.pump_bottom, 500));
           * Utils.showCustomToast(AGeneralWhiteBoard.this, R.string.whiteboard_clear, R.drawable.failure);
           * dialog.dismiss();
           * }
           * });
           * builder.setNegativeButton(Utils.getResources(R.string.no), new DialogInterface.OnClickListener() {
           * @Override public void onClick(DialogInterface dialog, int which) {
           * dialog.dismiss();
           * }
           * });
           * builder.create().show();
           */
     }

     @Click void ibEraser() {
          ibEraser.startAnimation(AnimationManager.load(R.anim.pump_bottom, 500));
          WHITE_BOARD_DRAWING.setEraserMode();
          Utils.showCustomToast(AGeneralWhiteBoard.this, R.string.erase_mode_is_active, R.drawable.erase);
     }

     @Click void ibThin() {
          ibThin.startAnimation(AnimationManager.load(R.anim.pump_bottom, 500));
          WHITE_BOARD_DRAWING.setBrushSize(15);
          Utils.showCustomToast(AGeneralWhiteBoard.this, R.string.thick_brush, R.drawable.big);
     }

     @Click void ibMedium() {
          ibMedium.startAnimation(AnimationManager.load(R.anim.pump_bottom, 500));
          WHITE_BOARD_DRAWING.setBrushSize(10);
          Utils.showCustomToast(AGeneralWhiteBoard.this, R.string.medium_brush_size, R.drawable.big);
     }

     @Click void ibShapesRectangle() {
          WHITE_BOARD_DRAWING.disableEraserMode();
          ibShapesRectangle.startAnimation(AnimationManager.load(R.anim.pump_bottom, 500));
          WHITE_BOARD_DRAWING.setDrawingShape(WhiteBoardDrawingView.ShapesType.RECTANGLE);
          ivDrawingState.setImageResource(R.drawable.rectangle_1);
          Utils.showCustomToast(AGeneralWhiteBoard.this, R.string.draw_rectangle, R.drawable.rectangle);
     }

     @Click void ibThick() {
          ibThick.startAnimation(AnimationManager.load(R.anim.pump_bottom, 500));
          WHITE_BOARD_DRAWING.setBrushSize(5);
          Utils.showCustomToast(AGeneralWhiteBoard.this, R.string.thin_brush_size, R.drawable.big);
     }

     @Click void ibShapesCircle() {
          WHITE_BOARD_DRAWING.disableEraserMode();
          ibShapesCircle.startAnimation(AnimationManager.load(R.anim.pump_bottom, 500));
          WHITE_BOARD_DRAWING.setDrawingShape(WhiteBoardDrawingView.ShapesType.CIRCLE);
          ivDrawingState.setImageResource(R.drawable.circle);
          Utils.showCustomToast(AGeneralWhiteBoard.this, R.string.draw_circle, R.drawable.circle_1);
     }

     @Click void ibShapesLine() {
          WHITE_BOARD_DRAWING.disableEraserMode();
          ibShapesLine.startAnimation(AnimationManager.load(R.anim.pump_bottom, 500));
          WHITE_BOARD_DRAWING.setDrawingShape(WhiteBoardDrawingView.ShapesType.LINE);
          ivDrawingState.setImageResource(R.drawable.line_2);
          Utils.showCustomToast(AGeneralWhiteBoard.this, R.string.draw_line, R.drawable.line);
     }

     @Click void ibShapesTriangle() {
          WHITE_BOARD_DRAWING.disableEraserMode();
          ibShapesTriangle.startAnimation(AnimationManager.load(R.anim.pump_bottom, 500));
          WHITE_BOARD_DRAWING.setDrawingShape(WhiteBoardDrawingView.ShapesType.TRIANGLE);
          ivDrawingState.setImageResource(R.drawable.triangle_1);
          Utils.showCustomToast(AGeneralWhiteBoard.this, R.string.draw_triangle, R.drawable.triangle);
     }

     @Click void ibShapesFreeDrawing() {
          WHITE_BOARD_DRAWING.disableEraserMode();
          ibShapesFreeDrawing.startAnimation(AnimationManager.load(R.anim.pump_bottom, 500));
          WHITE_BOARD_DRAWING.setDrawingShape(WhiteBoardDrawingView.ShapesType.STANDART_DRAWING);
          ivDrawingState.setImageResource(R.drawable.free_drawing_2);
          Utils.showCustomToast(AGeneralWhiteBoard.this, R.string.free_drawing, R.drawable.free_drawing);
     }

     @Click void ibSaveDrawing() {
          ibSaveDrawing.startAnimation(AnimationManager.load(R.anim.pump_bottom, 500));
          IS_NEED_TO_BACK = false;
          saveAndSendDrawing(null);
     }

     @Click void ibUndo() {
          ibUndo.startAnimation(AnimationManager.load(R.anim.pump_bottom, 500));
          WHITE_BOARD_DRAWING.redo();

     }

     @Click void ibRedo() {
          ibRedo.startAnimation(AnimationManager.load(R.anim.pump_bottom, 500));
          WHITE_BOARD_DRAWING.undo();
     }

     @Click void ibLoadWhiteboard() {

          ibLoadWhiteboard.startAnimation(AnimationManager.load(R.anim.pump_bottom, 500));
          if ( WHITEBOARD_TYPE.equals(DrawingType.HOTSPOT_WHITEBOARD_DRAWING) ) {
               Utils.showCustomToast(AGeneralWhiteBoard.this, "Disabled (whiteboard hotspot)", R.drawable.whiteboard);
               return;
          }

          HashMap <String, String> params = QCollection.newHashMap();

          params.put(HTTP_PARAMS.FLOOR, GlobalConstants.CURRENT_FLOOR);
          params.put(HTTP_PARAMS.DATE, GlobalConstants.SITE_PLAN_IMAGE_NAME);
          params.put(HTTP_PARAMS.MARKER_ID, GlobalConstants.LAST_CLICKED_MARKER_ID);

          SuperRequest <ModelPathsCreationTimeList> request = new SuperRequest <ModelPathsCreationTimeList>(this, ModelPathsCreationTimeList.class, RestAddresses.GET_TIMES_FOR_PATHS, null, params);
          execute(request, new ResponseGetPathsCreationTime(this));

     }

     @Click void ibCreateNewWhiteboard() {

          if ( WHITEBOARD_TYPE.equals(DrawingType.HOTSPOT_WHITEBOARD_DRAWING) ) {
               Utils.showCustomToast(AGeneralWhiteBoard.this, "Disabled (whiteboard hotspot)", R.drawable.whiteboard);
               return;
          }

          builder.setTitle(R.string.create_whiteboard);
          builder.setMessage(R.string.everything_will_be_cleared);
          builder.setPositiveButton(Utils.getResources(R.string.yes), new DialogInterface.OnClickListener() {
               @Override public void onClick(DialogInterface dialog, int which) {
                    ibCreateNewWhiteboard.startAnimation(AnimationManager.load(R.anim.pump_bottom, 500));
                    IS_WHITEBOARD_NEW = true;
                    WHITE_BOARD_DRAWING.clearAllPaths();
                    Utils.showCustomToast(AGeneralWhiteBoard.this, R.string.new_whiteboard_created, R.drawable.add);

                    customActionBar.setTimeCreatedText(GlobalConstants.SITE_PLAN_IMAGE_NAME + " not saved");
                    dialog.dismiss();
               }
          });
          builder.setNegativeButton(Utils.getResources(R.string.no), new DialogInterface.OnClickListener() {
               @Override public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
               }
          });
          AlertDialog alert = builder.create();
          alert.show();
     }

     @Click void ibRefreshWhiteboard() {
          ibRefreshWhiteboard.startAnimation(AnimationManager.load(R.anim.pump_bottom, 500));
          loadPathes();
     }

     /**
      * Show or hide whiteboard panel
      */
     @Click void ivWhiteboardSettings() {
          llChangeTextSize.setVisibility((llChangeTextSize.getVisibility() == View.GONE) ? View.VISIBLE : View.GONE);

          llBrushSize.setVisibility(View.GONE);
          llDrawShapes.setVisibility(View.GONE);

          // set selected image visible
          unselectAllSettingsButton();
          ivWhiteboardSettingsSelected.setVisibility((llChangeTextSize.getVisibility() == View.GONE) ? View.INVISIBLE : View.VISIBLE);
          ivWhiteboardSettingsSelected.startAnimation(AnimationManager.load(ivWhiteboardSettingsSelected.getVisibility() == View.VISIBLE ? R.anim.grow_from_bottom : R.anim.fade_out));
     }

     /**
      * Show or hide shapes panel
      */
     @Click void ivShapes() {
          llDrawShapes.setVisibility((llDrawShapes.getVisibility() == View.GONE) ? View.VISIBLE : View.GONE);
          llChangeTextSize.setVisibility(View.GONE);
          llBrushSize.setVisibility(View.GONE);

          // set selected image visible
          unselectAllSettingsButton();
          ivShapesSelected.setVisibility((llDrawShapes.getVisibility() == View.GONE) ? View.INVISIBLE : View.VISIBLE);
          ivShapesSelected.startAnimation(AnimationManager.load(ivShapesSelected.getVisibility() == View.VISIBLE ? R.anim.grow_from_bottom : R.anim.fade_out));
     }
}