package com.example.food.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ProductData {


        @SerializedName("status")
        @Expose
        private Integer status;
        @SerializedName("responseArray")
        @Expose
        List<ResponseArray> responseArray = null;

        public Integer getStatus() {
            return status;
        }

        public void setStatus(Integer status) {
            this.status = status;
        }

        public List<ResponseArray> getResponseArray() {
            return responseArray;
        }

        public void setResponseArray(List<ResponseArray> responseArray) {
            this.responseArray = responseArray;
        }

}

