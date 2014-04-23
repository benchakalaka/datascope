package com.touchip.organizer.test.scenarious;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.shadows.ShadowActivity;

import android.app.Activity;
import android.content.Intent;
import android.widget.Button;

import com.squareup.timessquare.sample.R;
import com.touchip.organizer.activities.StartActivity;
import com.touchip.organizer.activities.StartActivity_;
import com.touchip.organizer.activities.UserSettingsActivity_;

@RunWith ( RobolectricTestRunner.class )
public class ScenariousTest {

	
	private StartActivity mainActivity;
	
	
	/*@Test public void startUserSettingsActivity() {
		//StartActivity_ activity = new StartActivity_();//Robolectric.buildActivity(StartActivity_.class).create().get();
		//activity.onCreate(null);
		//ShadowActivity a = Robolectric.shadowOf(activity);
		assertNotNull(mainActivity);

		/*
		 * Activity mActivity=Robolectric.buildActivity(Splashscreen.class).create().get();
		 * ShadowActivity splashActivity = Robolectric.shadowOf(mActivity);
		 * assertEquals(shadowIntent.getIntentClass(), SAPHome.class);
		 */

	//	Button button = (Button) activity.findViewById(R.id.buttonSettings);
		//button.performClick();
		/*
		 * Intent intent = Robolectric.shadowOf(activity).peekNextStartedActivity();
		 * assertEquals(UserSettingsActivity_.class.getCanonicalName(), intent.getComponent().getClassName());
		 
	}*/

	@Test public void resourcesTest() throws Exception {
		StartActivity_ activity = new StartActivity_();
		assertNotNull(activity);
		String hello = activity.getResources().getString(R.string.save);
		assertThat(hello, equalTo("Save"));
	}

}