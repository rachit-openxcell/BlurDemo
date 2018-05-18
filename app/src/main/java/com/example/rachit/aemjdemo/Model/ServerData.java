
package com.example.rachit.aemjdemo.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ServerData implements Serializable{

    @SerializedName("statusCode")
    @Expose
    private String statusCode;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("totalRecord")
    @Expose
    private String totalRecord;
    @SerializedName("perPageRecord")
    @Expose
    private String perPageRecord;
    @SerializedName("data")
    @Expose
    private List<Datum> data = new ArrayList<>();

    /**
     * No args constructor for use in serialization
     * 
     */
    public ServerData() {
    }

    /**
     * 
     * @param message
     * @param statusCode
     * @param data
     * @param perPageRecord
     * @param totalRecord
     */
    public ServerData(String statusCode, String message, String totalRecord, String perPageRecord, List<Datum> data) {
        super();
        this.statusCode = statusCode;
        this.message = message;
        this.totalRecord = totalRecord;
        this.perPageRecord = perPageRecord;
        this.data = data;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTotalRecord() {
        return totalRecord;
    }

    public void setTotalRecord(String totalRecord) {
        this.totalRecord = totalRecord;
    }

    public String getPerPageRecord() {
        return perPageRecord;
    }

    public void setPerPageRecord(String perPageRecord) {
        this.perPageRecord = perPageRecord;
    }

    public List<Datum> getData() {
        return data;
    }

    public void setData(List<Datum> data) {
        this.data = data;
    }

}
