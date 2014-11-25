package com.touchip.organizer.communication.rest.request;

public final class RestAddresses {

     // Server address settings
     private static String       IP                                             = "194.28.136.6";                                                                 // default
     private static String       REST_PORT                                      = "8071";                                                                         // default

     // RestAddresses addresses

     public static String        CREATE_ON_THE_FLY_TASKS                        = "http://datascopesystems.com/HotSpot/CreateOnTheFlyHotspot/";
     public static String        GET_ON_THE_FLY_TASKS                           = "http://datascopesystems.com/HotSpot/GetTasksOntheFlyForCompany/";
     public static String        REGISTER_OPERATIVES                            = "http://datascopesystems.com/Login/RegisterOperativesList/";
     public static String        GET_LIST_OF_MEETINGS                           = "http://datascopesystems.com/Login/GetListOfMeetings/";
     public static String        COMPLETE_HOTSPOT                               = "http://datascopesystems.com/HotSpot/UpdateHotspotStatus/";
     public static String        GET_SIGNED_IN_USERS_LIST                       = "http://datascopesystems.com/Login/GetRegisteredOperatives/";
     public static String        SIGN_IN_USER                                   = "http://datascopesystems.com/Login/RegisterOperative/";
     public static String        GET_INDUCTION_FILE_LIST                        = "http://datascopesystems.com/SitePlanMeeting/GetInductionFilesList/";
     public static String        GET_INDUCTION_FILE_BY_NAME                     = "http://datascopesystems.com/SitePlanMeeting/GetInductionFile/";
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
     public static String        CREATE_MARKER_WITH_CURRENT_LOCATION            = "http://datascopesystems.com/SiteLocation/CreateSiteLocation/";
     public static String        CREATE_HOTSPOT                                 = "http://datascopesystems.com/HotSpot/CreateHotSpot/";
     public static String        DOWNLOAD_SITE_PLAN                             = "http://datascopesystems.com/SitePlanImages/DownloadSitePlan/";
     public static String        GET_LIST_OF_DATES_TO_HIGHLIGHT_IN_CALENDAR     = "http://datascopesystems.com/SitePlanImages/GetDatesToHighLight/";
     public static String        GET_MEETING_PLAN                               = "http://datascopesystems.com/SitePlanMeeting/GetSitePlanMeeting/";
     public static String        DOWNLOAD_DRAWING_PATHES                        = "http://datascopesystems.com/SitePlanImages/LoadDrawingPaths/";
     public static String        SAVE_DRAWING_PATHES                            = "http://datascopesystems.com/SitePlanImages/SaveSitePlanDrawingPaths/";
     public static String        GET_OPERATIVES_ON_SITE                         = "http://datascopesystems.com/Companies/GetOperativesByCompanyId/";
     public static String        GET_ACTIVITIES_AND_RISKS                       = "http://datascopesystems.com/HotSpot/GetRisksForHotspot/";
     public static String        GET_TASK_BRIEFING                              = "http://datascopesystems.com/HotSpot/GetTaskBriefingsForHotspot/";
     public static String        GET_MEETINGPLAN_NAMES                          = "http://datascopesystems.com/SitePlanMeeting/GetListOfPlans/";
     public static String        GET_ASSETS_LIST                                = "http://datascopesystems.com/HotSpot/GetAssetsBySiteAndCompany/";
     public static String        UPDATE_APPLICATION                             = "http://datascopesystems.com/UpdateClientApplication/UpdateAndroidApplication/";
     public static String        LOGIN                                          = "http://datascopesystems.com/Login/LoginUser/";                                 // LoginUser
     public static String        GET_DELIVERIES_LIST                            = "http://datascopesystems.com/Deliveries/GetDeliveriesGrid/";
     public static String        SAVE_GWD_PATHS                                 = "http://datascopesystems.com/SitePlanImages/SaveGeneralWhiteBoardDrawingPaths/";
     public static String        SAVE_HWD_PATHS                                 = "http://datascopesystems.com/SitePlanImages/SaveHotSpotDrawingPaths/";
     public static String        GET_SUITABLE_OPERATIVES                        = "http://datascopesystems.com/Operatives/GetOperativesByTradeAndAsset";

     public static String        CREATE_ASSET_HOTSPOT                           = "http://datascopesystems.com/HotSpot/CreateAssetHotSpot/";
     public static String        UPDATE_HOTSPOT_POSITION                        = "http://datascopesystems.com/HotSpot/UpdateHotspotPosition/";

     // common constant
     private static final String IP_PORT                                        = "[ip:port]";

     // RestAddresses addresses
     private static final String DEF_GET_ALL_COMPANIES                          = "http://[ip:port]/Companies/GetAllCompanies/";
     private static final String DEF_GET_GOOGLE_MAP_LOCATIONS                   = "http://[ip:port]/SiteLocation/GetGMapLocations/";
     private static final String DEF_GET_BRIEFING_DETAILS                       = "http://[ip:port]/EditableText/GetEditableText/";
     private static final String DEF_GET_IMAGES_LIST                            = "http://[ip:port]/SitePlanImages/DownloadImagesPaths/";
     private static final String DEF_TEST_CONNECTION                            = "http://[ip:port]/TestConnection/TestConnection/";
     private static final String DEF_UPLOAD_CAPTURED_PHOTO                      = "http://[ip:port]/SitePlanImages/UploadPhoto/";
     private static final String DEF_CREATE_MARKER_WITH_CURRENT_LOCATION        = "http://[ip:port]/SiteLocation/CreateSiteLocation/";
     private static final String DEF_DOWNLOAD_SITE_PLAN                         = "http://[ip:port]/SitePlanImages/DownloadSitePlan/";
     private static final String DEF_CREATE_HOTSPOT                             = "http://[ip:port]/HotSpot/CreateHotSpot/";
     private static final String DEF_GET_LIST_OF_DATES_TO_HIGHLIGHT_IN_CALENDAR = "http://[ip:port]/SitePlanImages/GetDatesToHighLight/";
     private static final String DEF_GET_MEETING_PLAN                           = "http://[ip:port]/SitePlanMeeting/GetSitePlanMeeting/";
     private static final String DEF_DOWNLOAD_DRAWING_PATHES                    = "http://[ip:port]/SitePlanImages/LoadDrawingPaths/";
     private static final String DEF_SAVE_DRAWING_PATHES                        = "http://[ip:port]/SitePlanImages/SaveSitePlanDrawingPaths/";
     private static final String DEF_GET_OPERATIVES_ON_SITE                     = "http://[ip:port]/Companies/GetOperativesByCompanyId/";
     private static final String DEF_GET_TRADES                                 = "http://[ip:port]/HotSpot/GetTrades/";
     private static final String DEF_CREATE_TRADE_HOTSPOT                       = "http://[ip:port]/HotSpot/CreateTradeHotSpot/";
     private static final String DEF_UPDATE_TRADE_HOTSPOT                       = "http://[ip:port]/HotSpot/UpdateTradeHotSpot/";
     private static final String DEF_CREATE_NEW_WHITE_BOARD                     = "http://[ip:port]/SitePlanImages/SaveGeneralWhiteBoardPath/";
     private static final String DEF_GET_TIMES_FOR_PATHS                        = "http://[ip:port]/SitePlanImages/GetTimesForPaths/";
     private static final String DEF_GET_ACTIVITIES_AND_RISKS                   = "http://[ip:port]/HotSpot/GetRisksForHotspot/";
     private static final String DEF_GET_TASK_BRIEFING                          = "http://[ip:port]/HotSpot/GetTaskBriefingsForHotspot/";
     private static final String DEF_GET_MEETINGPLAN_NAMES                      = "http://[ip:port]/SitePlanMeeting/GetListOfPlans/";
     private static final String DEF_GET_ASSETS_LIST                            = "http://[ip:port]/HotSpot/GetAssetsBySiteAndCompany/";
     private static final String DEF_UPDATE_APPLICATION                         = "http://[ip:port]/UpdateClientApplication/UpdateAndroidApplication/";
     private static final String DEF_LOGIN                                      = "http://[ip:port]/Login/LoginUser/";                                            // LoginUser
     private static final String DEF_GET_DELIVERIES_LIST                        = "http://[ip:port]/Deliveries/GetDeliveriesGrid/";
     private static final String DEF_SAVE_GWD_PATHS                             = "http://[ip:port]/SitePlanImages/SaveGeneralWhiteBoardDrawingPaths/";
     private static final String DEF_SAVE_HWD_PATHS                             = "http://[ip:port]/SitePlanImages/SaveHotSpotDrawingPaths/";
     private static final String DEF_GET_SUITABLE_OPERATIVES                    = "http://[ip:port]/Operatives/GetOperativesByTradeAndAsset";
     private final static String DEF_CREATE_ASSET_HOTSPOT                       = "http://[ip:port]/HotSpot/CreateAssetHotSpot/";
     private final static String DEF_UPDATE_HOTSPOT_POSITION                    = "http://[ip:port]/HotSpot/UpdateHotspotPosition/";
     private static final String DEF_GET_INDUCTION_FILE_LIST                    = "http://[ip:port]/SitePlanMeeting/GetInductionFilesList/";
     private static final String DEF_GET_INDUCTION_FILE_BY_NAME                 = "http://[ip:port]/SitePlanMeeting/GetInductionFile/";
     private static final String DEF_SIGN_IN_ON_METEENG                         = "http://[ip:port]/Login/GetRegisteredOperatives/";
     private static final String DEF_SIGN_IN_USER                               = "http://[ip:port]/Login/RegisterOperative/";
     private static final String DEF_COMPLETE_HOTSPOT                           = "http://[ip:port]/HotSpot/UpdateHotspotStatus/";
     private static final String DEF_GET_LIST_OF_MEETINGS                       = "http://[ip:port]/Login/GetListOfMeetings/";
     private static final String DEF_REGISTER_OPERATIVES                        = "http://[ip:port]/Login/RegisterOperativesList/";
     private static final String DEF_GET_ON_THE_FLY_TASKS                       = "http://[ip:port]/HotSpot/GetTasksOntheFlyForCompany/";
     private static final String DEF_CREATE_ON_THE_FLY_TASKS                    = "http://[ip:port]/HotSpot/CreateOnTheFlyHotspot/";

     private RestAddresses () {
     }

     /**
      * Method build proper address for rest services
      */
     private static void initAdresses() {
          if ( !IP.contains("datascope") ) {
               CREATE_ON_THE_FLY_TASKS = DEF_CREATE_ON_THE_FLY_TASKS.replace(IP_PORT, String.valueOf(IP + ":" + REST_PORT));
               GET_ON_THE_FLY_TASKS = DEF_GET_ON_THE_FLY_TASKS.replace(IP_PORT, String.valueOf(IP + ":" + REST_PORT));
               REGISTER_OPERATIVES = DEF_REGISTER_OPERATIVES.replace(IP_PORT, String.valueOf(IP + ":" + REST_PORT));
               GET_LIST_OF_MEETINGS = DEF_GET_LIST_OF_MEETINGS.replace(IP_PORT, String.valueOf(IP + ":" + REST_PORT));
               COMPLETE_HOTSPOT = DEF_COMPLETE_HOTSPOT.replace(IP_PORT, String.valueOf(IP + ":" + REST_PORT));
               SIGN_IN_USER = DEF_SIGN_IN_USER.replace(IP_PORT, String.valueOf(IP + ":" + REST_PORT));
               GET_INDUCTION_FILE_BY_NAME = DEF_GET_INDUCTION_FILE_BY_NAME.replace(IP_PORT, String.valueOf(IP + ":" + REST_PORT));
               GET_INDUCTION_FILE_LIST = DEF_GET_INDUCTION_FILE_LIST.replace(IP_PORT, String.valueOf(IP + ":" + REST_PORT));
               GET_ALL_COMPANIES = DEF_GET_ALL_COMPANIES.replace(IP_PORT, String.valueOf(IP + ":" + REST_PORT));
               GET_GOOGLE_MAP_LOCATIONS = DEF_GET_GOOGLE_MAP_LOCATIONS.replace(IP_PORT, String.valueOf(IP + ":" + REST_PORT));
               GET_BRIEFING_DETAILS = DEF_GET_BRIEFING_DETAILS.replace(IP_PORT, String.valueOf(IP + ":" + REST_PORT));
               TEST_CONNECTION = DEF_TEST_CONNECTION.replace(IP_PORT, String.valueOf(IP + ":" + REST_PORT));
               UPLOAD_CAPTURED_PHOTO = DEF_UPLOAD_CAPTURED_PHOTO.replace(IP_PORT, String.valueOf(IP + ":" + REST_PORT));
               GET_CAPTURED_IMAGES_LIST = DEF_GET_IMAGES_LIST.replace(IP_PORT, String.valueOf(IP + ":" + REST_PORT));
               CREATE_MARKER_WITH_CURRENT_LOCATION = DEF_CREATE_MARKER_WITH_CURRENT_LOCATION.replace(IP_PORT, String.valueOf(IP + ":" + REST_PORT));
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
               GET_TASK_BRIEFING = DEF_GET_TASK_BRIEFING.replace(IP_PORT, String.valueOf(IP + ":" + REST_PORT));
               GET_MEETINGPLAN_NAMES = DEF_GET_MEETINGPLAN_NAMES.replace(IP_PORT, String.valueOf(IP + ":" + REST_PORT));
               GET_ASSETS_LIST = DEF_GET_ASSETS_LIST.replace(IP_PORT, String.valueOf(IP + ":" + REST_PORT));
               UPDATE_APPLICATION = DEF_UPDATE_APPLICATION.replace(IP_PORT, String.valueOf(IP + ":" + REST_PORT));
               LOGIN = DEF_LOGIN.replace(IP_PORT, String.valueOf(IP + ":" + REST_PORT));
               GET_DELIVERIES_LIST = DEF_GET_DELIVERIES_LIST.replace(IP_PORT, String.valueOf(IP + ":" + REST_PORT));
               SAVE_GWD_PATHS = DEF_SAVE_GWD_PATHS.replace(IP_PORT, String.valueOf(IP + ":" + REST_PORT));
               SAVE_HWD_PATHS = DEF_SAVE_HWD_PATHS.replace(IP_PORT, String.valueOf(IP + ":" + REST_PORT));
               GET_SUITABLE_OPERATIVES = DEF_GET_SUITABLE_OPERATIVES.replace(IP_PORT, String.valueOf(IP + ":" + REST_PORT));
               CREATE_ASSET_HOTSPOT = DEF_CREATE_ASSET_HOTSPOT.replace(IP_PORT, String.valueOf(IP + ":" + REST_PORT));
               UPDATE_HOTSPOT_POSITION = DEF_UPDATE_HOTSPOT_POSITION.replace(IP_PORT, String.valueOf(IP + ":" + REST_PORT));
               GET_SIGNED_IN_USERS_LIST = DEF_SIGN_IN_ON_METEENG.replace(IP_PORT, String.valueOf(IP + ":" + REST_PORT));
          } else {
               GET_ALL_COMPANIES = DEF_GET_ALL_COMPANIES.replace(IP_PORT, String.valueOf(IP + REST_PORT));
               GET_GOOGLE_MAP_LOCATIONS = DEF_GET_GOOGLE_MAP_LOCATIONS.replace(IP_PORT, String.valueOf(IP + REST_PORT));
               GET_BRIEFING_DETAILS = DEF_GET_BRIEFING_DETAILS.replace(IP_PORT, String.valueOf(IP + REST_PORT));
               TEST_CONNECTION = DEF_TEST_CONNECTION.replace(IP_PORT, String.valueOf(IP + REST_PORT));
               UPLOAD_CAPTURED_PHOTO = DEF_UPLOAD_CAPTURED_PHOTO.replace(IP_PORT, String.valueOf(IP + REST_PORT));
               CREATE_MARKER_WITH_CURRENT_LOCATION = DEF_CREATE_MARKER_WITH_CURRENT_LOCATION.replace(IP_PORT, String.valueOf(IP + REST_PORT));
               DOWNLOAD_SITE_PLAN = DEF_DOWNLOAD_SITE_PLAN.replace(IP_PORT, String.valueOf(IP + REST_PORT));
               CREATE_HOTSPOT = DEF_CREATE_HOTSPOT.replace(IP_PORT, String.valueOf(IP + REST_PORT));
               GET_LIST_OF_DATES_TO_HIGHLIGHT_IN_CALENDAR = DEF_GET_LIST_OF_DATES_TO_HIGHLIGHT_IN_CALENDAR.replace(IP_PORT, String.valueOf(IP + REST_PORT));
               GET_MEETING_PLAN = DEF_GET_MEETING_PLAN.replace(IP_PORT, String.valueOf(IP + REST_PORT));
               DOWNLOAD_DRAWING_PATHES = DEF_DOWNLOAD_DRAWING_PATHES.replace(IP_PORT, String.valueOf(IP + REST_PORT));
               SAVE_DRAWING_PATHES = DEF_SAVE_DRAWING_PATHES.replace(IP_PORT, String.valueOf(IP + REST_PORT));
               GET_OPERATIVES_ON_SITE = DEF_GET_OPERATIVES_ON_SITE.replace(IP_PORT, String.valueOf(IP + REST_PORT));
               GET_TRADES = DEF_GET_TRADES.replace(IP_PORT, String.valueOf(IP + REST_PORT));
               CREATE_TRADE_HOTSPOT = DEF_CREATE_TRADE_HOTSPOT.replace(IP_PORT, String.valueOf(IP + REST_PORT));
               UPDATE_TRADE_HOTSPOT = DEF_UPDATE_TRADE_HOTSPOT.replace(IP_PORT, String.valueOf(IP + REST_PORT));
               CREATE_NEW_WHITE_BOARD = DEF_CREATE_NEW_WHITE_BOARD.replace(IP_PORT, String.valueOf(IP + REST_PORT));
               GET_TIMES_FOR_PATHS = DEF_GET_TIMES_FOR_PATHS.replace(IP_PORT, String.valueOf(IP + REST_PORT));
               GET_ACTIVITIES_AND_RISKS = DEF_GET_ACTIVITIES_AND_RISKS.replace(IP_PORT, String.valueOf(IP + REST_PORT));
               GET_TASK_BRIEFING = DEF_GET_TASK_BRIEFING.replace(IP_PORT, String.valueOf(IP + REST_PORT));
               GET_MEETINGPLAN_NAMES = DEF_GET_MEETINGPLAN_NAMES.replace(IP_PORT, String.valueOf(IP + REST_PORT));
               GET_CAPTURED_IMAGES_LIST = DEF_GET_IMAGES_LIST.replace(IP_PORT, String.valueOf(IP + REST_PORT));
               GET_ASSETS_LIST = DEF_GET_ASSETS_LIST.replace(IP_PORT, String.valueOf(IP + REST_PORT));
               UPDATE_APPLICATION = DEF_UPDATE_APPLICATION.replace(IP_PORT, String.valueOf(IP + REST_PORT));
               LOGIN = DEF_LOGIN.replace(IP_PORT, String.valueOf(IP + REST_PORT));
               GET_DELIVERIES_LIST = DEF_GET_DELIVERIES_LIST.replace(IP_PORT, String.valueOf(IP + REST_PORT));
               SAVE_GWD_PATHS = DEF_SAVE_GWD_PATHS.replace(IP_PORT, String.valueOf(IP + REST_PORT));
               SAVE_HWD_PATHS = DEF_SAVE_HWD_PATHS.replace(IP_PORT, String.valueOf(IP + REST_PORT));
               GET_SUITABLE_OPERATIVES = DEF_GET_SUITABLE_OPERATIVES.replace(IP_PORT, String.valueOf(IP + REST_PORT));
               CREATE_ASSET_HOTSPOT = DEF_CREATE_ASSET_HOTSPOT.replace(IP_PORT, String.valueOf(IP + REST_PORT));
               UPDATE_HOTSPOT_POSITION = DEF_UPDATE_HOTSPOT_POSITION.replace(IP_PORT, String.valueOf(IP + REST_PORT));
               GET_INDUCTION_FILE_LIST = DEF_GET_INDUCTION_FILE_LIST.replace(IP_PORT, String.valueOf(IP + REST_PORT));
               GET_INDUCTION_FILE_BY_NAME = DEF_GET_INDUCTION_FILE_BY_NAME.replace(IP_PORT, String.valueOf(IP + REST_PORT));
               GET_SIGNED_IN_USERS_LIST = DEF_SIGN_IN_ON_METEENG.replace(IP_PORT, String.valueOf(IP + REST_PORT));
               SIGN_IN_USER = DEF_SIGN_IN_USER.replace(IP_PORT, String.valueOf(IP + REST_PORT));
               COMPLETE_HOTSPOT = DEF_COMPLETE_HOTSPOT.replace(IP_PORT, String.valueOf(IP + REST_PORT));
               GET_LIST_OF_MEETINGS = DEF_GET_LIST_OF_MEETINGS.replace(IP_PORT, String.valueOf(IP + REST_PORT));
               REGISTER_OPERATIVES = DEF_REGISTER_OPERATIVES.replace(IP_PORT, String.valueOf(IP + REST_PORT));
               GET_ON_THE_FLY_TASKS = DEF_GET_ON_THE_FLY_TASKS.replace(IP_PORT, String.valueOf(IP + REST_PORT));
               CREATE_ON_THE_FLY_TASKS = DEF_CREATE_ON_THE_FLY_TASKS.replace(IP_PORT, String.valueOf(IP + REST_PORT));
          }
     }

     public static void setServerAddress(String ip, String port) {
          IP = ip;
          REST_PORT = port;
          initAdresses();
     }
}
