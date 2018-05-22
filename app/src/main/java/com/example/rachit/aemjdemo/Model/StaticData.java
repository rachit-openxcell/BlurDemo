package com.example.rachit.aemjdemo.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class StaticData {

        @SerializedName("statusCode")
        @Expose
        private String statusCode;
        @SerializedName("message")
        @Expose
        private String message;
        @SerializedName("data")
        @Expose
        private StaticDatum data;

        /**
         * No args constructor for use in serialization
         *
         */
        public StaticData() {
        }

        /**
         *
         * @param message
         * @param statusCode
         * @param data
         */
        public StaticData(String statusCode, String message, StaticDatum data) {
            super();
            this.statusCode = statusCode;
            this.message = message;
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

        public StaticDatum getData() {
            return data;
        }

        public void setData(StaticDatum data) {
            this.data = data;
        }

    }
