package com.technoface.app.talentscam.webrequests;

/**
 * Created by admin on 6/12/2015.
 */
public interface ServiceCallBack
{
    public void onSuccess(BaseRequest baseRequest, BaseResponse baseResponse);
    public void onSuccess(String baseRequest, BaseResponse baseResponse);
    public void onFail(BaseRequest baseRequest, BaseResponse baseResponse);
    public void onNoNetwork(BaseRequest baseRequest, BaseResponse baseResponse);
}
