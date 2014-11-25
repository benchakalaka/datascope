package com.touchip.organizer.activities;

import java.util.HashMap;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.OptionsItem;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.sharedpreferences.Pref;

import quickutils.core.QUFactory.QCollection;
import quickutils.core.QUFactory.QPreconditions;
import android.graphics.Bitmap;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageLoadingListener;
import com.squareup.timessquare.sample.R;
import com.touchip.organizer.communication.rest.model.ModelCapturedImagesUrlList;
import com.touchip.organizer.communication.rest.request.RestAddresses;
import com.touchip.organizer.communication.rest.request.SuperRequest;
import com.touchip.organizer.communication.rest.request.listener.ResponseGetCapturedImageNames;
import com.touchip.organizer.utils.AppSharedPreferences_;
import com.touchip.organizer.utils.GlobalConstants;
import com.touchip.organizer.utils.HTTP_PARAMS;
import com.touchip.organizer.utils.Utils;

/**
 * Description: represents view pager for pictures which has been captured
 * 
 * @author Ihor Karpachev
 *         Copyright (c) 2013-2014 Datascope Systems Ltd. All Rights Reserved.
 *         Date: 10 Dec 2013
 *         Time: 10:37:40
 */
@EActivity ( R.layout.activity_photos_imagepager ) public class AImagePager extends SuperActivity {

     @ViewById public ViewPager         viewPager;
     @Pref public AppSharedPreferences_ appPreff;

     @AfterViews void afterViews() {
          // Configure action bar
          Utils.configureCustomActionBar(getActionBar(), null);
          getActionBar().setIcon(R.drawable.drawings);
     }

     @Override protected void onResume() {
          super.onResume();
          // Load image names
          loadImageName();
     }

     @OptionsItem void homeSelected() {
          onBackPressed();
     }

     public void loadImageName() {
          HashMap <String, String> params = QCollection.newHashMap();

          params.put(HTTP_PARAMS.ID, String.valueOf(GlobalConstants.LAST_CLICKED_HOTSPOT.id));

          SuperRequest <ModelCapturedImagesUrlList> requestGetDatesToHighlight = new SuperRequest <ModelCapturedImagesUrlList>(this, ModelCapturedImagesUrlList.class, RestAddresses.GET_CAPTURED_IMAGES_LIST, null, params);
          execute(requestGetDatesToHighlight, new ResponseGetCapturedImageNames(this));
     }

     /**
      * Create new adapter for image pager and set data
      * 
      * @param array
      */
     public void setAdapterArray(ModelCapturedImagesUrlList array) {
          viewPager.setAdapter(new ImagePagerAdapter(array));
     }

     /**
      * Description: adapter for pager view (displaying downloaded images)
      * 
      * @author Ihor Karpachev
      *         Copyright (c) 2013-2014 Datascope Systems Ltd. All Rights Reserved.
      *         Date: 25 Dec 2013
      *         Time: 22:30:57
      */
     public class ImagePagerAdapter extends PagerAdapter {

          private final String[] imagesUrls;

          ImagePagerAdapter ( ModelCapturedImagesUrlList array ) {
               this.imagesUrls = new String[array.size()];
               // build url which can be used by ImageLoader library for displaying in ImagfeView loaded picture
               for ( int i = 0; i < array.size(); i++ ) {
                    if ( appPreff.ip().get().contains("datascope") ) {
                         this.imagesUrls[i] = "http://" + appPreff.ip().get() + array.get(i).imageName;
                    } else {
                         this.imagesUrls[i] = "http://" + appPreff.ip().get() + ":" + appPreff.port().get() + array.get(i).imageName;
                    }
               }
          }

          @Override public void destroyItem(ViewGroup container, int position, Object object) {
               ((ViewPager) container).removeView((View) object);
          }

          @Override public int getCount() {
               if ( !QPreconditions.isNull(imagesUrls) ) {
                    return imagesUrls.length;
               } else {
                    return 0;
               }
          }

          @Override public Object instantiateItem(final ViewGroup view, int position) {
               final View imageLayout = getLayoutInflater().inflate(R.layout.item_pager_image, view, false);
               ImageView imageView = (ImageView) imageLayout.findViewById(R.id.image_item);
               final TextView tvImageName = (TextView) imageLayout.findViewById(R.id.pager_textview_image_name);
               final String photoUrl = imagesUrls[position];
               final String filename = photoUrl.substring(photoUrl.lastIndexOf("/") + 1, photoUrl.length());

               // Then later, when you want to display image
               ImageLoader.getInstance().displayImage(photoUrl, imageView, new ImageLoadingListener() {
                    @Override public void onLoadingStarted(String imageUri, View view) {
                         tvImageName.setText(R.string.loading);
                    }

                    @Override public void onLoadingCancelled(String imageUri, View view) {
                    }

                    @Override public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
                         tvImageName.setText(String.format("Cannot downlaod %s, due to %s", filename, failReason.toString()));
                    }

                    @Override public void onLoadingComplete(String imageUri, View v, Bitmap loadedImage) {
                         tvImageName.setText(Utils.getResources(R.string.filename) + filename);
                         ((ViewPager) view).addView(imageLayout, 0);
                    }
               });
               return imageLayout;
          }

          @Override public boolean isViewFromObject(View view, Object object) {
               return view.equals(object);
          }
     }
}
