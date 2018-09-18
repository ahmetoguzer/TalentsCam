package com.technoface.app.talentscam.helper;


public class ApiAddress {



	public static String hostDuello = "http://technofaceapps.com/duellows/wsgateway.aspx?";


	public static String urlLoginUserWFacebook = hostDuello + "act=LoginUserWFacebook";
	public static String urlFacebookUserId = "&FacebookUserId=";

	public static String urlName = "&Name=";
	public static String urlAvatar = "&AvatarUrl=";
	public static String urlFacebookUniqueID = "&UniqueID=";
	public static String urlFacebookEmail = "&Email=";
	public static String urlFacebookUserAge = "&UserAge=";


	public static String urlLoginUserWithUsernamePassword =hostDuello+"act=LoginUserWithUsernamePassword";
	public static String urlUserName = "&UserName=";
	public static String urlPassword = "&Password=";
	public static String urlUniqueID = "&UniqueId=";

	public static String urlGetRanking = hostDuello + "act=GetRanking";
	public static String urlGetCompletedCompetitions = hostDuello + "act=GetCompletedCompetitions";

	public static String urlGetOpponents = hostDuello + "act=GetOpponents";
	public static String urlReserveSmartCam = hostDuello + "act=ReserveSmartCam";
	public static String urlGetSSIDByQrCode = hostDuello + "act=GetSSIDByQrCode";
	public static String urlUserId = "&UserId=";
	public static String urlQrCode  = "&QrCode=";

	public static String urlGetGroups = hostDuello + "act=GetGroups";
	public static String urlGetGroupMembers = hostDuello + "act=GetGroupMembers";
	public static String urlGroupId = "&GroupId=";


	public static String urlAddShooting = hostDuello + "act=AddShooting";
	public static String urlAddShootingBySmartCam = hostDuello + "act=AddShootingBySmartCam";

	public static String urlOpponentId = "&OpponentId=";
	public static String urlOpponentScore = "&OpponentScore=";
	public static String urlVideoUrl = "&VideoUrl=";
	public static String urlSmartCamId = "&SmartCamId=";
	public static String urlSmartCamRecordingId = "&SmartCamRecordingId=";


	public static String urlDuelloProgress = "&DuelloProgress=";
	public static String urlDuelloScore = "&DuelloScore=";


	public static String urlGenerateQrCode = hostDuello + "act=GenerateQrCode";
	public static String urlCheckQrcodeStatus = hostDuello + "act=CheckQrcodeStatus";


	// Register Device
	public static String urlRegisterDevice = hostDuello + "act=RegisterDevice";
	public static String urlDeviceToken = "&DeviceToken=";
	public static String urlAppName = "&AppName=";
	public static String urlOsVersion = "&OsVersion=";
	public static String urlMachineName = "&MachineName=";
	public static String urlLang = "&Lang=";
	public static String urlCountry = "&Country=";


	//Register
	public static String urlCreateNewUser = hostDuello + "act=CreateNewUser";
	public static String urlUserEmail = "&UserEmail=";
	public static String urlAge = "&Age=";
	public static String urlGender = "&Gender=";

	//forgot Password
	public static String urlForgotMyPassword = hostDuello + "act=ForgotMyPassword";

	//update profile
	public static String urlUpdateMyProfile = hostDuello + "act=UpdateMyProfile";

	//Change  password
	public static String urlChangeMyPassword = hostDuello + "act=ChangeMyPassword";
	public static String urlOldPassword = "&OldPassword=";
	public static String urlNewPassword = "&NewPassword=";

	//Get Opponents By Facebook
	public static String urlGetOpponentsByFacebook = hostDuello + "act=GetOpponentsByFacebook";
	public static String urlFacebookFriends = "&FacebookFriends=";

	// Get Opponents By Location
	public static String urlGetOpponentsByLocation = hostDuello + "act=GetOpponentsByLocation";

	public static String urlLatitude = "&Latitude=";
	public static String urlLongitude = "&Longitude=";


	// Get Opponents Best
	public static String urlGetOpponentsBest = hostDuello + "act=GetOpponentsBest";


	// Get User  History

	public static String urlGetUserHistory = hostDuello + "act=GetUserHistory";
	public static String urlMaxRec  = "&MaxRec=";

	public static String urlGetHistory = hostDuello + "act=GetHistory";

	public static String urlGetUserDetails = hostDuello + "act=GetUserDetails";

	public static String urlGetContents = hostDuello + "act=GetContents";

	public static String urlLogContentWatching = hostDuello + "act=LogContentWatching";

	public static String urlIcerikId = "&IcerikId=";

	public static String urlGetGuessQuestions = hostDuello + "act=GetGuessQuestions";
	public static String urlAnswerGuessQuestion = hostDuello + "act=AnswerGuessQuestion";
	public static String urlQuestionId = "&QuestionId=";
	public static String urlAnswerIsTrue= "&AnswerIsTrue=";

	public static String urlGetPrizes = hostDuello + "act=GetPrizes";
	public static String urlUsePrize = hostDuello + "act=UsePrize";
	public static String urlPrizeId= "&PrizeId=";
	public static String urlExtraData= "&ExtraData=";


	public static String urlGetUserPoints = hostDuello + "act=GetUserPoints";
	public static String urlGetWatches = hostDuello + "act=GetWatches";
	public static String urlAddWatch = hostDuello + "act=AddWatch";
	public static String urlShootingId= "&ShootingId=";


	public static String urlUpdateUserLocale = hostDuello + "act=UpdateUserLocale";
	public static String urlLanguage= "&Language=";

	public static String urlGetCountries = hostDuello + "act=GetCountries";

	public static String urlGetUserLocale = hostDuello + "act=GetUserLocale";
	public static String urlAddQuizAnswer = hostDuello + "act=AddQuizAnswer";
	public static String urlGetQuizQuestions = hostDuello + "act=GetQuizQuestions";
	public static String urlGetBasketCoinHistory = hostDuello + "act=GetBasketCoinHistory";

	public static String urlgift= "&gift=";

}
