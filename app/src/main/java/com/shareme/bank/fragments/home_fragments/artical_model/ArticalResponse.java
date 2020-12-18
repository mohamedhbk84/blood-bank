
package com.shareme.bank.fragments.home_fragments.artical_model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ArticalResponse {

    @SerializedName("status")
    @Expose
    private int status;
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("data")
    @Expose
    private ArticalData articalData;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public ArticalData getArticalData() {
        return articalData;
    }

    public void setArticalData(ArticalData articalData) {
        this.articalData = articalData;
    }

}
