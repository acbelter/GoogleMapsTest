package com.acbelter.maptest;

import com.google.maps.android.PolyUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class RouteDataParser {
    public static RouteData parse(JSONObject json) throws JSONException {
        if (!json.optString("status").equals("OK")) {
            return null;
        }

        JSONArray routes = json.getJSONArray("routes");
        if (routes.length() == 0) {
            return null;
        }

        RouteData result = new RouteData();
        String points = routes.getJSONObject(0).getJSONObject("overview_polyline").getString("points");
        result.route.addAll(PolyUtil.decode(points));

        JSONArray legs = routes.getJSONObject(0).getJSONArray("legs");
        JSONObject leg;
        for (int i = 0; i < legs.length(); i++) {
            leg = legs.getJSONObject(i);
            if (leg.has("duration")) {
                JSONObject duration = leg.getJSONObject("duration");
                if (duration.has("value")) {
                    result.duration += duration.getInt("value");
                }
            }
            if (leg.has("duration_in_traffic")) {
                JSONObject durationInTraffic = leg.getJSONObject("duration_in_traffic");
                if (durationInTraffic.has("value")) {
                    result.durationInTraffic += durationInTraffic.getInt("value");
                }
            }
        }
        return result;
    }
}
