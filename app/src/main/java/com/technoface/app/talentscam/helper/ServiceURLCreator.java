package com.technoface.app.talentscam.helper;

/**
 * Created by Ahmet on 5.9.2017.
 */
public class ServiceURLCreator {

    public static String LoginWithFacebook(String urlFacebookUserId,
                                           String name, String avatar,
                                           String UniqueId,String email,String age) {

        String url = ApiAddress.urlLoginUserWFacebook
                + ApiAddress.urlFacebookUserId + urlFacebookUserId
                + ApiAddress.urlName + name
                + ApiAddress.urlAvatar + avatar
                + ApiAddress.urlFacebookUniqueID + UniqueId
                + ApiAddress.urlFacebookEmail + email
                + ApiAddress.urlFacebookUserAge + age;
        return url;
    }


    public static String RegisterDevice(String devicetoken, String appname, String OsVersion,
                                           String UniqueId,String MachineName ,String lang,String country,String userid,
                                        String Latitude,String Longitude) {

        String url = ApiAddress.urlRegisterDevice
                + ApiAddress.urlDeviceToken + devicetoken
                + ApiAddress.urlAppName + appname
                + ApiAddress.urlOsVersion + OsVersion
                + ApiAddress.urlUniqueID + UniqueId
                + ApiAddress.urlMachineName + MachineName
                + ApiAddress.urlLang + lang
                +ApiAddress.urlCountry + country
                +ApiAddress.urlUserId + userid
                +ApiAddress.urlLatitude + Latitude
                +ApiAddress.urlLongitude + Longitude;
        return url;
    }

    public static String LoginWithPassword(String unique,
                                           String name,String password) {

        String url = ApiAddress.urlLoginUserWithUsernamePassword
                + ApiAddress.urlUniqueID + unique
                + ApiAddress.urlUserName+ name
                + ApiAddress.urlPassword + password;
        return url;
    }

    public static String LogContentWatching(String unique,
                                           String UserId,String IcerikId) {

        String url = ApiAddress.urlLogContentWatching
                + ApiAddress.urlUniqueID + unique
                + ApiAddress.urlUserId+ UserId
                + ApiAddress.urlIcerikId + IcerikId;
        return url;
    }

    public static String GetCountries(String unique) {

        String url = ApiAddress.urlGetCountries
                + ApiAddress.urlUniqueID + unique;
        return url;
    }

    public static String CreateNewUser(String unique, String useremail,String name,
                                       String username,String password,String age,String gender) {

        String url = ApiAddress.urlCreateNewUser
                + ApiAddress.urlUniqueID + unique
                + ApiAddress.urlUserEmail+ useremail
                + ApiAddress.urlName + name
                + ApiAddress.urlUserName + username
                + ApiAddress.urlPassword + password
                + ApiAddress.urlAge + age
                + ApiAddress.urlGender + gender;
        return url;
    }

    public static String GetCompetition (String userID) {
        String url = ApiAddress.urlGetOpponents
                + ApiAddress.urlUserId + userID;
        return url;
    }

    public static String GetContents (String userID) {
        String url = ApiAddress.urlGetContents
                + ApiAddress.urlUserId + userID;
        return url;
    }

    public static String GetOpponentsBest (String userID) {
        String url = ApiAddress.urlGetOpponentsBest
                + ApiAddress.urlUserId + userID;
        return url;
    }

    public static String GetUserDetails (String userID) {
        String url = ApiAddress.urlGetUserDetails
                + ApiAddress.urlUserId + userID;
        return url;
    }

    public static String GetUserHistory (String userID,String maxrec) {
        String url = ApiAddress.urlGetUserHistory
                + ApiAddress.urlUserId + userID
                + ApiAddress.urlMaxRec + maxrec;
        return url;
    }

    public static String GetHistory (String userID) {
        String url = ApiAddress.urlGetHistory
                + ApiAddress.urlUserId + userID;
        return url;
    }

    public static String ForgotMyPassword (String unique,String useremail) {
        String url = ApiAddress.urlForgotMyPassword
                + ApiAddress.urlUniqueID + unique
                + ApiAddress.urlUserEmail + useremail;
        return url;
    }

    public static String UpdateMyProfile (String unique,String userid,String useremail,
                                          String username,String age,String gender) {
        String url = ApiAddress.urlUpdateMyProfile
                + ApiAddress.urlUniqueID + unique
                + ApiAddress.urlUserId + userid
                + ApiAddress.urlUserEmail + useremail
                + ApiAddress.urlUserName + username
                + ApiAddress.urlAge + age
                + ApiAddress.urlGender + gender;
        return url;
    }

    public static String UpdateUserLocale(String UniqueId,String userid,String Language,String Country) {
        String url = ApiAddress.urlUpdateUserLocale
                + ApiAddress.urlUniqueID + UniqueId
                +ApiAddress.urlUserId + userid
                +ApiAddress.urlLanguage + Language
                +ApiAddress.urlCountry + Country;
        return url;
    }

    public static String ChangeMyPassword(String UniqueId,String userid,String oldpassword,String newpassword) {
        String url = ApiAddress.urlChangeMyPassword
                + ApiAddress.urlUniqueID + UniqueId
                +ApiAddress.urlUserId + userid
                +ApiAddress.urlOldPassword + oldpassword
                +ApiAddress.urlNewPassword + newpassword;
        return url;
    }

    public static String GetCompletedCompetition (String unique,String userID) {
        String url = ApiAddress.urlGetCompletedCompetitions
                + ApiAddress.urlUniqueID + unique
                + ApiAddress.urlUserId + userID;
        return url;
    }


    public static String GetGuessQuestions (String unique,String userID) {
        String url = ApiAddress.urlGetGuessQuestions
                + ApiAddress.urlUniqueID + unique
                + ApiAddress.urlUserId + userID;
        return url;
    }

    public static String GetPrizes(String unique,String userID) {
        String url = ApiAddress.urlGetPrizes
                + ApiAddress.urlUniqueID + unique
                + ApiAddress.urlUserId + userID;
        return url;
    }

    public static String GetWatches(String unique,String userID) {
        String url = ApiAddress.urlGetWatches
                + ApiAddress.urlUniqueID + unique
                + ApiAddress.urlUserId + userID;
        return url;
    }

    public static String GetUserPoints(String unique,String userID) {
        String url = ApiAddress.urlGetUserPoints
                + ApiAddress.urlUniqueID + unique
                + ApiAddress.urlUserId + userID;
        return url;
    }

    public static String GetBasketCoinHistory(String unique,String userID) {
        String url = ApiAddress.urlGetBasketCoinHistory
                + ApiAddress.urlUniqueID + unique
                + ApiAddress.urlUserId + userID;
        return url;
    }

    public static String GetQuizQuestions(String unique,String userID) {
        String url = ApiAddress.urlGetQuizQuestions
                + ApiAddress.urlUniqueID + unique
                + ApiAddress.urlUserId + userID;
        return url;
    }

    public static String GetUserLocale(String unique,String userID) {
        String url = ApiAddress.urlGetUserLocale
                + ApiAddress.urlUniqueID + unique
                + ApiAddress.urlUserId + userID;
        return url;
    }

    public static String UsePrize(String unique,String userID,String PrizeId,String ExtraData) {
        String url = ApiAddress.urlUsePrize
                + ApiAddress.urlUniqueID + unique
                + ApiAddress.urlUserId + userID
                + ApiAddress.urlPrizeId + PrizeId
                + ApiAddress.urlExtraData + ExtraData;
        return url;
    }

    public static String AddWatch(String unique,String userID,String ShootingId,String gift) {
        String url = ApiAddress.urlAddWatch
                + ApiAddress.urlUniqueID + unique
                + ApiAddress.urlUserId + userID
                + ApiAddress.urlShootingId + ShootingId
                + ApiAddress.urlgift + gift;
        return url;
    }

    public static String AnswerGuessQuestion (String unique,String userID,String QuestionId,String AnswerIsTrue) {
        String url = ApiAddress.urlAnswerGuessQuestion
                + ApiAddress.urlUniqueID + unique
                + ApiAddress.urlUserId + userID
                + ApiAddress.urlQuestionId + QuestionId
                + ApiAddress.urlAnswerIsTrue + AnswerIsTrue;
        return url;
    }

    public static String GetOpponentsByFacebook (String unique,String FacebookFriends) {
        String url = ApiAddress.urlGetOpponentsByFacebook
                + ApiAddress.urlUniqueID + unique
                + ApiAddress.urlFacebookFriends + FacebookFriends;
        return url;
    }

    public static String GetOpponentsByLocation (String unique,String userID,String lat,String lon) {
        String url = ApiAddress.urlGetOpponentsByLocation
                + ApiAddress.urlUniqueID + unique
                + ApiAddress.urlUserId + userID
                +ApiAddress.urlLatitude + lat
                +ApiAddress.urlLongitude + lon;
        return url;
    }

    public static String ReverseSmartCam (String unique,String userID,String smartCamid) {
        String url = ApiAddress.urlReserveSmartCam
                + ApiAddress.urlUniqueID + unique
                + ApiAddress.urlUserId + userID
                + ApiAddress.urlSmartCamId + smartCamid;
        return url;
    }

    public static String AddShooting(String unique,String userId,
                                   String OpponentId ,String OpponentScore,String VideoUrl,String SmartCamId ){
        String url = ApiAddress.urlAddShooting
                +ApiAddress.urlUniqueID + unique
                +ApiAddress.urlUserId + userId
                +ApiAddress.urlOpponentId + OpponentId
                +ApiAddress.urlOpponentScore + OpponentScore
                +ApiAddress.urlVideoUrl + VideoUrl
                +ApiAddress.urlSmartCamId + SmartCamId;

        return url;
    }

    public static String AddShootingBySmartCam(String unique,String userId,
                                     String OpponentId ,String OpponentScore,String VideoUrl,String SmartCamId,String SmartCamrecordId ){
        String url = ApiAddress.urlAddShootingBySmartCam
                +ApiAddress.urlUniqueID + unique
                +ApiAddress.urlUserId + userId
                +ApiAddress.urlOpponentId + OpponentId
                +ApiAddress.urlOpponentScore + OpponentScore
                +ApiAddress.urlVideoUrl + VideoUrl
                +ApiAddress.urlSmartCamId + SmartCamId
                +ApiAddress.urlSmartCamRecordingId + SmartCamrecordId;

        return url;
    }

    public static String GetRaking (String userID,String groupid) {
        String url = ApiAddress.urlGetRanking
                + ApiAddress.urlUserId + userID
                + ApiAddress.urlGroupId+ groupid;
        return url;
    }

    public static String GetGroups(String unique,String Userid){
        String url = ApiAddress.urlGetGroups
                + ApiAddress.urlUniqueID + unique
                + ApiAddress.urlUserId+ Userid;

        return url;
    }

    public static String GetSSIDByQrCode(String unique,String QrCode ){
        String url = ApiAddress.urlGetSSIDByQrCode
                + ApiAddress.urlUniqueID + unique
                + ApiAddress.urlQrCode + QrCode ;

        return url;
    }

    public static String GetGroupMembers(String unique,String Userid,String groupid){
        String url = ApiAddress.urlGetGroupMembers
                + ApiAddress.urlUniqueID + unique
                + ApiAddress.urlUserId+ Userid
                + ApiAddress.urlGroupId+ groupid;
        return url;
    }

    public static String GenerateQrCode(String UserId,String UniqueId ){
        String url = ApiAddress.urlGenerateQrCode
                + ApiAddress.urlUserId + UserId
                + ApiAddress.urlUniqueID+ UniqueId ;

        return url;
    }


    public static String GetCheckQrcodeStatus(String UserId,String QrCode ){
        String url = ApiAddress.urlCheckQrcodeStatus
                + ApiAddress.urlQrCode + QrCode
                + ApiAddress.urlUserId+ UserId;

        return url;
    }


}
