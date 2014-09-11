package com.touchip.organizer.utils;

import org.androidannotations.annotations.sharedpreferences.DefaultString;
import org.androidannotations.annotations.sharedpreferences.SharedPref;

@SharedPref ( value = SharedPref.Scope.UNIQUE ) public interface AppSharedPreferences {
     // production settings
     @DefaultString ( "www.datascopesystem.com:80/Quilt" ) String ip();

     @DefaultString ( "" ) String port();

     @DefaultString ( "1" ) String tvId();

     @DefaultString ( "1" ) String siteId();

     // test settings - uncomment for testing
     // @DefaultString ( "194.28.136.6" )/* @DefaultString ( "10.1.254.154" ) */String ip();

     // @DefaultString ( "8071" )/* @DefaultString ( "18681" ) */String port();

     // @DefaultString ( "1" ) String tvId();
}
