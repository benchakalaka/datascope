package com.touchip.organizer.utils;

import org.androidannotations.annotations.sharedpreferences.DefaultString;
import org.androidannotations.annotations.sharedpreferences.SharedPref;

@SharedPref (
               value = SharedPref.Scope.UNIQUE ) public interface AppSharedPreferences {
     @DefaultString ( "194.28.136.6" ) String ip();

     @DefaultString ( "80" ) String port();
}
