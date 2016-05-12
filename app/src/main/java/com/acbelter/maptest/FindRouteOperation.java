package com.acbelter.maptest;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.redmadrobot.chronos.ChronosOperation;
import com.redmadrobot.chronos.ChronosOperationResult;

import org.json.JSONObject;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class FindRouteOperation extends ChronosOperation<RouteData> {
    private String mStartAddress;
    private String mFinishAddress;
    private String mApiKey;

    public FindRouteOperation(String startAddress, String finishAddress, String apiKey) {
        mStartAddress = startAddress;
        mFinishAddress = finishAddress;
        mApiKey = apiKey;
    }

    @Nullable
    @Override
    public RouteData run() {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(Api.buildFindRouteUrl(mStartAddress, mFinishAddress, mApiKey))
                .build();

        Response response = null;
        try {
            response = client.newCall(request).execute();
            if (!response.isSuccessful()) {
                throw new IOException("Unexpected response code " + response);
            }

            String data = response.body().string();
            return RouteDataParser.parse(new JSONObject(data));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (response != null) {
                response.body().close();
            }
        }
        return null;
    }

    @NonNull
    @Override
    public Class<? extends ChronosOperationResult<RouteData>> getResultClass() {
        return Result.class;
    }

    public final static class Result extends ChronosOperationResult<RouteData> {

    }
}
