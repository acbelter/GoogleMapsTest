package com.acbelter.maptest;

import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.redmadrobot.chronos.gui.activity.ChronosAppCompatActivity;

public class MainActivity extends ChronosAppCompatActivity {
    private EditText mStartAddress;
    private EditText mFinishAddress;
    private Button mFindRouteButton;
    private Button mChangeAddressesButton;
    private boolean mUiEnabled;

    private static TestData TEST_DATA = new TestData();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mStartAddress = (EditText) findViewById(R.id.start_address);
        mFinishAddress = (EditText) findViewById(R.id.finish_address);

        mFindRouteButton = (Button) findViewById(R.id.btn_find_route);
        mFindRouteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String startAddress = mStartAddress.getText().toString();
                String finishAddress = mFinishAddress.getText().toString();

                if (startAddress.isEmpty() || finishAddress.isEmpty()) {
                    Toast.makeText(getApplicationContext(), R.string.toast_empty_address,
                            Toast.LENGTH_SHORT).show();
                    return;
                }


                setUiEnabled(false);
                runOperation(new FindRouteOperation(startAddress, finishAddress,
                        getString(R.string.maps_api_key)));
            }
        });

        mChangeAddressesButton = (Button) findViewById(R.id.btn_change_addresses);
        mChangeAddressesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Pair<String, String> addresses = TEST_DATA.getNextAddresses();
                mStartAddress.setText(addresses.first);
                mFinishAddress.setText(addresses.second);
            }
        });

        mUiEnabled = true;
        if (savedInstanceState == null) {
            Pair<String, String> addresses = TEST_DATA.getNextAddresses();
            mStartAddress.setText(addresses.first);
            mFinishAddress.setText(addresses.second);
        } else {
            mUiEnabled = savedInstanceState.getBoolean("ui_enabled");
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean("ui_enabled", mUiEnabled);
    }

    private void setUiEnabled(boolean enabled) {
        mStartAddress.setEnabled(enabled);
        mFinishAddress.setEnabled(enabled);
        mFindRouteButton.setEnabled(enabled);
        mChangeAddressesButton.setEnabled(enabled);
        mUiEnabled = enabled;
    }

    public void onOperationFinished(final FindRouteOperation.Result result) {
        if (result.isSuccessful()) {
            RouteData routeData = result.getOutput();
            if (routeData == null) {
                setUiEnabled(true);
                Toast.makeText(getApplicationContext(), R.string.toast_route_not_found,
                        Toast.LENGTH_SHORT).show();
                return;
            }

            Intent intent = new Intent(MainActivity.this, MapActivity.class);
            intent.putExtra("route_data", routeData);
            startActivity(intent);
            setUiEnabled(true);
        } else {
            setUiEnabled(true);
            Toast.makeText(getApplicationContext(), R.string.toast_route_not_found,
                    Toast.LENGTH_SHORT).show();
        }
    }
}
