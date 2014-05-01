package com.touchip.organizer.communication.rest.request;

public final class RestAddresses {

     // Server address settings
     private static String       IP                                             = "194.28.136.6";                                                         // default
                                                                                                                                                           // ip
     private static String       REST_PORT                                      = "80";                                                                   // default
                                                                                                                                                           // port

     // RestAddresses addresses
     public static String        GET_TIMES_FOR_PATHS                            = "http://datascopesystems.com/SitePlanImages/GetTimesForPaths/";
     public static String        CREATE_NEW_WHITE_BOARD                         = "http://datascopesystems.com/SitePlanImages/SaveGeneralWhiteBoardPath/";
     public static String        UPDATE_TRADE_HOTSPOT                           = "http://datascopesystems.com/HotSpot/UpdateTradeHotSpot/";
     public static String        CREATE_TRADE_HOTSPOT                           = "http://datascopesystems.com/HotSpot/CreateTradeHotSpot/";
     public static String        GET_ALL_COMPANIES                              = "http://datascopesystems.com/Companies/GetAllCompanies/";
     public static String        GET_TRADES                                     = "http://datascopesystems.com/HotSpot/GetTrades/";
     public static String        GET_GOOGLE_MAP_LOCATIONS                       = "http://datascopesystems.com/SiteLocation/GetGMapLocations/";
     public static String        GET_BRIEFING_DETAILS                           = "http://datascopesystems.com/EditableText/GetEditableText/";
     public static String        GET_CAPTURED_IMAGES_LIST                       = "http://datascopesystems.com/SiteLocation/DownloadImagesPaths/";
     public static String        TEST_CONNECTION                                = "http://datascopesystems.com/TestConnection/TestConnection/";
     public static String        UPLOAD_CAPTURED_PHOTO                          = "http://datascopesystems.com/SitePlanImages/UploadPhoto/";
     public static String        UPLOAD_SITE_PLAN                               = "http://datascopesystems.com/SitePlanImages/UploadSitePlanImage/";
     public static String        CREATE_MARKER_WITH_CURRENT_LOCATION            = "http://datascopesystems.com/SiteLocation/CreateSiteLocation/";
     public static String        CREATE_HOTSPOT                                 = "http://datascopesystems.com/HotSpot/CreateHotSpot/";
     public static String        DOWNLOAD_SITE_PLAN                             = "http://datascopesystems.com/SitePlanImages/DownloadSitePlan/";
     public static String        GET_LIST_OF_DATES_TO_HIGHLIGHT_IN_CALENDAR     = "http://datascopesystems.com/SitePlanImages/GetDatesToHighLight/";
     public static String        GET_MEETING_PLAN                               = "http://datascopesystems.com/SitePlanMeeting/GetSitePlanMeeting/";
     public static String        DOWNLOAD_DRAWING_PATHES                        = "http://datascopesystems.com/SitePlanImages/LoadDrawingPaths/";
     public static String        SAVE_DRAWING_PATHES                            = "http://datascopesystems.com/SitePlanImages/SaveDrawingPaths/";
     public static String        GET_OPERATIVES_ON_SITE                         = "http://datascopesystems.com/Companies/GetOperativesByCompanyId/";
     public static String        GET_ACTIVITIES_AND_RISKS                       = "http://datascopesystems.com/HotSpot/GetRisksForHotspot/";

     // common cpnstant
     private static final String IP_PORT                                        = "[ip:port]";

     // RestAddresses addresses
     private static final String DEF_GET_ALL_COMPANIES                          = "http://[ip:port]/Companies/GetAllCompanies/";
     private static final String DEF_GET_GOOGLE_MAP_LOCATIONS                   = "http://[ip:port]/SiteLocation/GetGMapLocations/";
     private static final String DEF_GET_BRIEFING_DETAILS                       = "http://[ip:port]/EditableText/GetEditableText/";
     private static final String DEF_GET_IMAGES_LIST                            = "http://[ip:port]/SitePlanImages/DownloadImagesPaths/";
     private static final String DEF_TEST_CONNECTION                            = "http://[ip:port]/TestConnection/TestConnection/";
     private static final String DEF_UPLOAD_CAPTURED_PHOTO                      = "http://[ip:port]/SitePlanImages/UploadPhoto/";
     private static final String DEF_CREATE_MARKER_WITH_CURRENT_LOCATION        = "http://[ip:port]/SiteLocation/CreateSiteLocation/";
     private static final String DEF_UPLOAD_SITE_PLAN                           = "http://[ip:port]/SitePlanImages/UploadSitePlanImage/";
     private static final String DEF_DOWNLOAD_SITE_PLAN                         = "http://[ip:port]/SitePlanImages/DownloadSitePlan/";
     private static final String DEF_CREATE_HOTSPOT                             = "http://[ip:port]/HotSpot/CreateHotSpot/";
     private static final String DEF_GET_LIST_OF_DATES_TO_HIGHLIGHT_IN_CALENDAR = "http://[ip:port]/SitePlanImages/GetDatesToHighLight/";
     private static final String DEF_GET_MEETING_PLAN                           = "http://[ip:port]/SitePlanMeeting/GetSitePlanMeeting/";
     private static final String DEF_DOWNLOAD_DRAWING_PATHES                    = "http://[ip:port]/SitePlanImages/LoadDrawingPaths/";
     private static final String DEF_SAVE_DRAWING_PATHES                        = "http://[ip:port]/SitePlanImages/SaveDrawingPaths/";
     private static final String DEF_GET_OPERATIVES_ON_SITE                     = "http://[ip:port]/Companies/GetOperativesByCompanyId/";
     private static final String DEF_GET_TRADES                                 = "http://[ip:port]/HotSpot/GetTrades/";
     private static final String DEF_CREATE_TRADE_HOTSPOT                       = "http://[ip:port]/HotSpot/CreateTradeHotSpot/";
     private static final String DEF_UPDATE_TRADE_HOTSPOT                       = "http://[ip:port]/HotSpot/UpdateTradeHotSpot/";
     private static final String DEF_CREATE_NEW_WHITE_BOARD                     = "http://[ip:port]/SitePlanImages/SaveGeneralWhiteBoardPath/";
     private static final String DEF_GET_TIMES_FOR_PATHS                        = "http://[ip:port]/SitePlanImages/GetTimesForPaths/";
     private static final String DEF_GET_ACTIVITIES_AND_RISKS                   = "http://[ip:port]/HotSpot/GetRisksForHotspot/";

     private RestAddresses () {
     }

     private static void initAdresses() {
          GET_ALL_COMPANIES = DEF_GET_ALL_COMPANIES.replace(IP_PORT, String.valueOf(IP + ":" + REST_PORT));
          GET_GOOGLE_MAP_LOCATIONS = DEF_GET_GOOGLE_MAP_LOCATIONS.replace(IP_PORT, String.valueOf(IP + ":" + REST_PORT));
          GET_BRIEFING_DETAILS = DEF_GET_BRIEFING_DETAILS.replace(IP_PORT, String.valueOf(IP + ":" + REST_PORT));
          TEST_CONNECTION = DEF_TEST_CONNECTION.replace(IP_PORT, String.valueOf(IP + ":" + REST_PORT));
          UPLOAD_CAPTURED_PHOTO = DEF_UPLOAD_CAPTURED_PHOTO.replace(IP_PORT, String.valueOf(IP + ":" + REST_PORT));
          GET_CAPTURED_IMAGES_LIST = DEF_GET_IMAGES_LIST.replace(IP_PORT, String.valueOf(IP + ":" + REST_PORT));
          CREATE_MARKER_WITH_CURRENT_LOCATION = DEF_CREATE_MARKER_WITH_CURRENT_LOCATION.replace(IP_PORT, String.valueOf(IP + ":" + REST_PORT));
          UPLOAD_SITE_PLAN = DEF_UPLOAD_SITE_PLAN.replace(IP_PORT, String.valueOf(IP + ":" + REST_PORT));
          DOWNLOAD_SITE_PLAN = DEF_DOWNLOAD_SITE_PLAN.replace(IP_PORT, String.valueOf(IP + ":" + REST_PORT));
          CREATE_HOTSPOT = DEF_CREATE_HOTSPOT.replace(IP_PORT, String.valueOf(IP + ":" + REST_PORT));
          GET_LIST_OF_DATES_TO_HIGHLIGHT_IN_CALENDAR = DEF_GET_LIST_OF_DATES_TO_HIGHLIGHT_IN_CALENDAR.replace(IP_PORT, String.valueOf(IP + ":" + REST_PORT));
          GET_MEETING_PLAN = DEF_GET_MEETING_PLAN.replace(IP_PORT, String.valueOf(IP + ":" + REST_PORT));
          DOWNLOAD_DRAWING_PATHES = DEF_DOWNLOAD_DRAWING_PATHES.replace(IP_PORT, String.valueOf(IP + ":" + REST_PORT));
          SAVE_DRAWING_PATHES = DEF_SAVE_DRAWING_PATHES.replace(IP_PORT, String.valueOf(IP + ":" + REST_PORT));
          GET_OPERATIVES_ON_SITE = DEF_GET_OPERATIVES_ON_SITE.replace(IP_PORT, String.valueOf(IP + ":" + REST_PORT));
          GET_TRADES = DEF_GET_TRADES.replace(IP_PORT, String.valueOf(IP + ":" + REST_PORT));
          CREATE_TRADE_HOTSPOT = DEF_CREATE_TRADE_HOTSPOT.replace(IP_PORT, String.valueOf(IP + ":" + REST_PORT));
          UPDATE_TRADE_HOTSPOT = DEF_UPDATE_TRADE_HOTSPOT.replace(IP_PORT, String.valueOf(IP + ":" + REST_PORT));
          CREATE_NEW_WHITE_BOARD = DEF_CREATE_NEW_WHITE_BOARD.replace(IP_PORT, String.valueOf(IP + ":" + REST_PORT));
          GET_TIMES_FOR_PATHS = DEF_GET_TIMES_FOR_PATHS.replace(IP_PORT, String.valueOf(IP + ":" + REST_PORT));
          GET_ACTIVITIES_AND_RISKS = DEF_GET_ACTIVITIES_AND_RISKS.replace(IP_PORT, String.valueOf(IP + ":" + REST_PORT));
     }

     public static void setServerAddress(String ip, String port) {
          IP = ip;
          REST_PORT = port;
          initAdresses();
     }
}
