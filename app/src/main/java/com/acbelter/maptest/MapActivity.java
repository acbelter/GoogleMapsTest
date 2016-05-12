package com.acbelter.maptest;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback {
    private RouteData mRouteData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        if (savedInstanceState == null) {
            mRouteData = getIntent().getExtras().getParcelable("route_data");
        } else {
            mRouteData = savedInstanceState.getParcelable("route_data");
        }

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        TextView timeWithoutJams = (TextView) findViewById(R.id.time_without_jams);
        TextView timeWithJams = (TextView) findViewById(R.id.time_with_jams);

        timeWithoutJams.setText(mRouteData.getFormattedDuration());
        timeWithJams.setText(mRouteData.getFormattedDurationInTraffic());
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable("route_data", mRouteData);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        googleMap.setPadding(50, 140, 50, 50);

        PolylineOptions line = new PolylineOptions();
        line.width(10.0f).color(ContextCompat.getColor(this, R.color.colorRoute));
        LatLngBounds.Builder boundsBuilder = new LatLngBounds.Builder();
        for (int i = 0; i < mRouteData.route.size(); i++) {
            if (i == 0) {
                MarkerOptions startMarkerOptions = new MarkerOptions()
                        .position(mRouteData.route.get(i))
                        .title(getString(R.string.start_marker_title))
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
                googleMap.addMarker(startMarkerOptions);
            } else if (i == mRouteData.route.size() - 1) {
                MarkerOptions finishMarkerOptions = new MarkerOptions()
                        .position(mRouteData.route.get(i))
                        .title(getString(R.string.finish_marker_title))
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
                googleMap.addMarker(finishMarkerOptions);
            }
            line.add(mRouteData.route.get(i));
            boundsBuilder.include(mRouteData.route.get(i));
        }
        googleMap.addPolyline(line);

        LatLngBounds bounds = boundsBuilder.build();
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngBounds(bounds, 0);
        googleMap.moveCamera(cameraUpdate);
    }
}
