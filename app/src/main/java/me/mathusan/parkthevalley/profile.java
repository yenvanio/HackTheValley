package me.mathusan.parkthevalley;

/**
 * Created by Mathu on 2017-01-07.
 */


import android.os.Parcel;
import android.os.Parcelable;

public class profile implements Parcelable {
    protected profile(Parcel in){

    }

    public static final Creator<profile> CREATOR = new Creator<profile>() {
        @Override
        public profile createFromParcel(Parcel in) {
            return new profile(in);
        }

        @Override
        public profile[] newArray(int size) {
            return new profile[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {

    }
}
