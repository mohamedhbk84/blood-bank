package com.shareme.bank.fragments.notify_fragment;

import android.os.Parcel;
import android.os.Parcelable;

public class NotifyModel implements Parcelable {

    String date , notifyText ;
    int img ;

    public NotifyModel(){}

    public NotifyModel(String date, String notifyText, int img) {
        this.date = date;
        this.notifyText = notifyText;
        this.img = img;
    }


    protected NotifyModel(Parcel in) {
        date = in.readString();
        notifyText = in.readString();
        img = in.readInt();
    }

    public static final Creator<NotifyModel> CREATOR = new Creator<NotifyModel>() {
        @Override
        public NotifyModel createFromParcel(Parcel in) {
            return new NotifyModel(in);
        }

        @Override
        public NotifyModel[] newArray(int size) {
            return new NotifyModel[size];
        }
    };

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getNotifyText() {
        return notifyText;
    }

    public void setNotifyText(String notifyText) {
        this.notifyText = notifyText;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(date);
        dest.writeString(notifyText);
        dest.writeInt(img);
    }
}
