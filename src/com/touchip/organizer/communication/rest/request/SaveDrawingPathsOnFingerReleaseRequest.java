package com.touchip.organizer.communication.rest.request;

import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.List;

import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import android.net.Uri;
import android.provider.MediaStore.Images.Media;

import com.octo.android.robospice.request.springandroid.SpringAndroidSpiceRequest;
import com.touchip.organizer.activities.DrawingCompaniesActivity;
import com.touchip.organizer.communication.rest.serializables.PathSerializable;
import com.touchip.organizer.utils.GlobalConstants;
import com.touchip.organizer.utils.HTTP_PARAMS;
import com.touchip.organizer.utils.Utils;

public class SaveDrawingPathsOnFingerReleaseRequest extends SpringAndroidSpiceRequest <String> {

     // Variable array for POST request
     private static MultiValueMap <String, Object> vars = new LinkedMultiValueMap <String, Object>();
     // private final FileSystemResource file;
     private final String                          currentFloor;

     // private final FileSystemResource snapshot;

     public SaveDrawingPathsOnFingerReleaseRequest ( FileSystemResource file , FileSystemResource snapshot , String currFloor ) {
          super(String.class);
          // this.file = file;
          // this.snapshot = snapshot;
          Utils.logw("SaveDrawingPathsOnFingerReleaseRequest Post....." + RestAddresses.SAVE_DRAWING_PATHES);
          this.currentFloor = currFloor;
     }

     /**
      * Network request
      */
     @Override public String loadDataFromNetwork() throws Exception {
          File file = null;
          ObjectOutputStream out = null;
          FileSystemResource snapshot = null;
          try {
               file = new File(DrawingCompaniesActivity.INSTANCE.getCacheDir(), System.currentTimeMillis() + ".paths");
               List <PathSerializable> listPathes = DrawingCompaniesActivity.DRAW_VIEW.getPaths();
               out = new ObjectOutputStream(new FileOutputStream(file));
               out.writeObject(listPathes);
               out.close();

               // attempt to save drawing
               String saveResponseFilePath = null;
               try {
                    saveResponseFilePath = Media.insertImage(DrawingCompaniesActivity.INSTANCE.getContentResolver(), DrawingCompaniesActivity.DRAW_VIEW.getDrawingCache(), Utils.getCurrentDate() + ".png", "drawing");
                    if ( saveResponseFilePath != null ) {
                         snapshot = new FileSystemResource(new File(Utils.getRealPathFromURI(DrawingCompaniesActivity.INSTANCE.getApplicationContext(), Uri.parse(saveResponseFilePath))));
                    }
               } catch (Exception ex) {
                    ex.printStackTrace();
               }

          } catch (Exception e) {
               Utils.logw(e.getMessage());
          }

          // if map exist and has elements clear it, instead of creating a new one
          if ( null != vars && !vars.isEmpty() ) {
               vars.clear();
          }

          vars.add(/* HTTP_PARAMS.SITE_ID */"markerId", GlobalConstants.LAST_CLICKED_MARKER_ID);
          vars.add(HTTP_PARAMS.TYPE, GlobalConstants.DrawingType.SITE_PLAN_DRAWING);
          vars.add(HTTP_PARAMS.DATE, GlobalConstants.SITE_PLAN_IMAGE_NAME);
          vars.add(HTTP_PARAMS.FLOOR, this.currentFloor);

          if ( null != file ) {
               vars.add(HTTP_PARAMS.DRAWING_DATA, new FileSystemResource(file));
          }

          if ( null != snapshot ) {
               vars.add(HTTP_PARAMS.SNAPSHOT, snapshot);
          }

          HttpHeaders headers = new HttpHeaders();
          headers.setContentType(MediaType.MULTIPART_FORM_DATA);
          getRestTemplate().getMessageConverters().add(new FormHttpMessageConverter());
          return getRestTemplate().exchange(RestAddresses.SAVE_DRAWING_PATHES, HttpMethod.POST, new HttpEntity <MultiValueMap <String, Object>>(vars, headers), String.class).getBody();
     }

     /**
      * This method generates a unique cache key for this request.
      * 
      * @return string representation of cache word
      */
     public String createCacheKey() {
          return getClass().getSimpleName() + ".cache";
     }
}
