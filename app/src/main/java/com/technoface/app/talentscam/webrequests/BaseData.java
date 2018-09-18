package com.technoface.app.talentscam.webrequests;

/**
 * Created by admin on 6/16/2015.
 */
public class BaseData<T>  extends BaseResponse
{
    private StatusData status;

    private T             data;
    public T getData()
    {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
    public StatusData getStatus() {
        return status;
    }

    public void setStatus(StatusData status) {
        this.status = status;
    }
    @Override
 public boolean isStatusApp() {
    return status.isStatus();
}
    @Override
    public String getMessage()
    {
        return status.getMessage();

    }
}
