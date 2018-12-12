package com.avardonigltd.mobilemedicalaid.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Packages {
    @Expose @SerializedName("status") boolean status;
    @Expose @SerializedName("message") String message;
    @Expose @SerializedName("data") List<DataOfPackage> data;

    public Packages(boolean status, String message, List<DataOfPackage> data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<DataOfPackage> getData() {
        return data;
    }

    public void setData(List<DataOfPackage> data) {
        this.data = data;
    }

    public static class DataOfPackage {
        @Expose @SerializedName("Title") String title;
        @Expose @SerializedName("Price") String price;
        int img;
        public DataOfPackage(String title, String price) {
            this.title = title;
            this.price = price;
        }
        public DataOfPackage(String title, String price,int img) {
            this.title = title;
            this.price = price;
            this.img = img;
        }

        public int getImg() {
            return img;
        }

        public void setImg(int img) {
            this.img = img;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }
    }
}
