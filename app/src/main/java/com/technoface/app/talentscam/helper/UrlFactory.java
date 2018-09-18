package com.technoface.app.talentscam.helper;

/**
 * Created by Kamlesh Mourya on 7/10/2015.
 */
public class UrlFactory {
    public static final int GET_METHOD = 11111;
    public static final int POST_METHOD = 22222;

    public static String BASE_URL="http://technofaceapps.com/duellows/wsgateway.aspx";


    public static final int LOGIN_URL_TAG=1;
    public static String getLoginUrlAction(){
       return "act=profilkayit";
    }


}
