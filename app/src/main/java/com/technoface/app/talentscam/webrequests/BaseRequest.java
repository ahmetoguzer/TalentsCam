package com.technoface.app.talentscam.webrequests;

import java.io.File;
import java.lang.reflect.Type;
import java.util.HashMap;

/**
 * Created by admin on 6/12/2015.
 */
public class BaseRequest
{
    private int requestType;
    private HashMap<String,String>  keyValue    =   new HashMap<String,String>();
    private HashMap<String,File>  keyValueFile    =   new HashMap<String,File>();
    private String  action;
    private int localKey;
    private boolean progressShow        = true;
    private String  url;
    private Type    type;
    private ServiceCallBack serviceCallBack;
    private int methodType;

    public int getMethodType() {
        return methodType;
    }

    public void setMethodType(int methodType) {
        this.methodType = methodType;
    }

    public String getUrl() {
        return url;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public HashMap<String, File> getKeyValueFile() {
        return keyValueFile;
    }

    public void setKeyValueFile(HashMap<String, File> keyValueFile) {
        this.keyValueFile = keyValueFile;
    }

    public void setUrl(String url) {
        this.url = url;
    }




    public ServiceCallBack getServiceCallBack() {
        return serviceCallBack;
    }

    public void setServiceCallBack(ServiceCallBack serviceCallBack) {
        this.serviceCallBack = serviceCallBack;
    }

    public int getRequestType() {
        return requestType;
    }

    public void setRequestType(int requestType) {
        this.requestType = requestType;
    }

    public HashMap<String, String> getKeyValue() {
        return keyValue;
    }

    public void setKeyValue(HashMap<String, String> keyValue) {
        this.keyValue = keyValue;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public int getLocalKey() {
        return localKey;
    }

    public void setLocalKey(int localKey) {
        this.localKey = localKey;
    }

    public boolean isProgressShow() {
        return progressShow;
    }

    public void setProgressShow(boolean progressShow) {
        this.progressShow = progressShow;
    }
    public  void addPair(String key,String value)
    {
        keyValue.put(key,value);
    }
    public  void addPairFile(String key,File value)
    {
        keyValueFile.put(key,value);
    }

}
