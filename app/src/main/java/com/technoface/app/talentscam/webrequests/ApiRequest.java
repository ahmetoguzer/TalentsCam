package com.technoface.app.talentscam.webrequests;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.technoface.app.talentscam.Utils.Common;
import com.technoface.app.talentscam.helper.UrlFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.util.Iterator;
import java.util.Map;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.entity.StringEntity;
import cz.msebera.android.httpclient.message.BasicHeader;
import cz.msebera.android.httpclient.protocol.HTTP;

/**
 * Created by admin on 6/12/2015.
 */
public class ApiRequest extends AsyncHttpResponseHandler {
    private BaseResponse baseResponse;
    private BaseRequest baseRequest;
    private AsyncHttpClient     asyncHttpClient;
    private Context context;
    private ProgressDialog      progressDialog;
   private StringEntity stringEntity=null;

    public  void executeGet(BaseRequest baseRequest,Context context)
    {
       this.baseRequest    =   baseRequest;
        this.context        =   context;
        asyncHttpClient     =   new AsyncHttpClient();
        asyncHttpClient.setTimeout(50000);
        asyncHttpClient.setConnectTimeout(50000);
        asyncHttpClient.setResponseTimeout(50000);
        asyncHttpClient.setUserAgent("Migros/1.2 CFNetwork/672.1.13 Darwin/14.0.0");
        CryptoManager cryptoManager = new CryptoManager();

        if(Common.isNetworkAvailable(context)) {
            RequestParams requestParams   =  new RequestParams();
            //requestParams.
            Iterator it = baseRequest.getKeyValue().entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry pair = (Map.Entry)it.next();
                if(pair.getValue()!=null) {
                    requestParams.put(pair.getKey().toString(), pair.getValue().toString());
                }
                it.remove();
            }
            it = baseRequest.getKeyValueFile().entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry pair = (Map.Entry)it.next();
                try {
                    if(pair.getValue()!=null) {
                        requestParams.put(pair.getKey().toString(), (File) pair.getValue());
                    }
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                it.remove();
            }
            Log.w("data", "data  "+requestParams);
            try {
                stringEntity    =   new StringEntity(cryptoManager.doCipher(requestParams.toString(), context));
                stringEntity.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

            if (baseRequest.getMethodType() == UrlFactory.POST_METHOD) {

                asyncHttpClient.post(context,baseRequest.getUrl() + baseRequest.getAction(), stringEntity, String.valueOf(new BasicHeader(HTTP.CONTENT_TYPE, "application/json")),this);
            }else{
                asyncHttpClient.get(baseRequest.getUrl() + baseRequest.getAction(), requestParams, this);
            }
        }
        else {
            if(baseRequest.getServiceCallBack()!=null)
                baseRequest.getServiceCallBack().onNoNetwork(baseRequest,null);
            Common.showToast(context, "Network connection not available.");
        }
    }
    @Override
    public void onStart() {
        super.onStart();
        if (baseRequest.isProgressShow())
            showProgress();
    }

    @Override
    public void onSuccess(int i, Header[] headers, byte[] bytes)
    {
      if(bytes==null)
            return;
        String responce         =    new String(bytes);
        Log.w("data","data  "+responce);
          Gson gson            =   new Gson();
        if(baseRequest.getType()==null)
        {
            if( baseRequest.getServiceCallBack()!=null)
            {
                baseRequest.getServiceCallBack().onSuccess(responce,baseResponse);
                return;
            }
        }

        baseResponse = gson.fromJson(responce,baseRequest.getType());
        if(baseResponse.isStatusApp())
        {
           if( baseRequest.getServiceCallBack()!=null)
           {
               baseRequest.getServiceCallBack().onSuccess(baseRequest,baseResponse);
           }

        }
        else
        {

            Common.showErrorDialog(context, baseResponse.getMessage());
            if( baseRequest.getServiceCallBack()!=null)
            {
                baseRequest.getServiceCallBack().onFail(baseRequest,baseResponse);
            }
        }
    }

    @Override
    public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable)
    {
        if(bytes==null)
            return;
            String responce = new String(bytes);
            if (baseRequest.getServiceCallBack() != null) {
                baseRequest.getServiceCallBack().onFail(baseRequest, baseResponse);
            }

            Log.v("data", "data " + responce);
    }

    @Override
    public void onFinish()
    {
        super.onFinish();
        if (baseRequest.isProgressShow())
            dismissDialog();
    }

    @Override
    public void onRetry(int retryNo) {
        super.onRetry(retryNo);
    }
    public void showProgress()
    {
        progressDialog      = new ProgressDialog(context);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();
    }
    public void dismissDialog()
    {
        if(progressDialog!=null&&progressDialog.isShowing())
        {
            progressDialog.dismiss();
        }
    }
}
