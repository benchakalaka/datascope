package com.touchip.organizer.utils;

import org.androidannotations.annotations.sharedpreferences.DefaultString;
import org.androidannotations.annotations.sharedpreferences.SharedPref;

/**
 * Class represents
 * 
 * @author Karpachev Ihor
 */
@SharedPref ( value = SharedPref.Scope.UNIQUE ) public interface AppSharedPreferences {
     // production settings
     @DefaultString ( "www.datascopesystem.com:80/Quilt" ) String ip();

     @DefaultString ( "" ) String port();

     @DefaultString ( "1" ) String tvId();

     @DefaultString ( "1" ) String siteId();

     @DefaultString ( "" ) String tvUrl();
}
