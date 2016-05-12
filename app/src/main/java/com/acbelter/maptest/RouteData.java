package com.acbelter.maptest;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;

public class RouteData implements Parcelable {
    public List<LatLng> route;
    public int duration;
    public int durationInTraffic;

    public RouteData() {
        route = new ArrayList<>();
    }

    protected RouteData(Parcel in) {
        route = in.createTypedArrayList(LatLng.CREATOR);
        duration = in.readInt();
        durationInTraffic = in.readInt();
    }

    public String getFormattedDuration() {
        return Utils.formatDuration(duration);
    }

    public String getFormattedDurationInTraffic() {
        return Utils.formatDuration(durationInTraffic);
    }

    public static final Creator<RouteData> CREATOR = new Creator<RouteData>() {
        @Override
        public RouteData createFromParcel(Parcel in) {
            return new RouteData(in);
        }

        @Override
        public RouteData[] newArray(int size) {
            return new RouteData[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeTypedList(route);
        out.writeInt(duration);
        out.writeInt(durationInTraffic);
    }

    @Override
    public String toString() {
        return "RouteData{" +
                "routeSize=" + route.size() +
                ", duration=" + getFormattedDuration() +
                ", durationInTraffic=" + getFormattedDurationInTraffic() +
                '}';
    }
}
