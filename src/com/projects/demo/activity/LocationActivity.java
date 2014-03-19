package com.projects.demo.activity;

import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.projects.demo.R;
import com.projects.demo.provider.gps.GPSTracker;

public class LocationActivity extends Activity {

	private Button btnShowLocation;
	private TextView locationTextView;
	private TextView locationWebTextView;

	private double latitude = 0.0;
	private double longitude = 0.0;
	private LocationManager locationManager;
	private LocationListener locationListener;
	
	private Location location;
	private boolean isGPSEnabled = false;
	private boolean isNetworkEnabled = false;
	private boolean canGetLocation = false;
	private final static long MIN_TIME_BW_UPDATES = 1000;
	private final static float MIN_DISTANCE_CHANGE_FOR_UPDATES = 1000;

	// GPSTracker class
    GPSTracker gps;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.location_layout_main);
		locationTextView = (TextView) findViewById(R.id.locationTextView);
		locationWebTextView = (TextView) findViewById(R.id.locationWebTextView);
		
		btnShowLocation = (Button) findViewById(R.id.gpsButton);

		locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

		
		// show location button click event
        btnShowLocation.setOnClickListener(new View.OnClickListener() {
             
            @Override
            public void onClick(View arg0) {        
                // create class object
                gps = new GPSTracker(LocationActivity.this);
 
                // check if GPS enabled     
                if (gps.canGetLocation()) {
                     
                    double latitude = gps.getLatitude();
                    double longitude = gps.getLongitude();
                     
                    // \n is for new line
                    Toast.makeText(getApplicationContext(), "Your Location is - \nLat: " + latitude + "\nLong: " + longitude, Toast.LENGTH_LONG).show();    
                } else {
                    // can't get location
                    // GPS or Network is not enabled
                    // Ask user to enable GPS/network in settings
					gps.showSettingsAlert();
                }
                
            }
        });
//		gpsOpen();
//		initLocation();
	}

	/*private void initLocation() {
		locationListener = new LocationListener() {

			// Provider��״̬�ڿ��á���ʱ�����ú��޷�������״ֱ̬���л�ʱ�����˺���
			@Override
			public void onStatusChanged(String provider, int status,
					Bundle extras) {

			}

			// Provider��enableʱ�����˺���������GPS����
			@Override
			public void onProviderEnabled(String provider) {

			}

			// Provider��disableʱ�����˺���������GPS���ر�
			@Override
			public void onProviderDisabled(String provider) {

			}

			// ������ı�ʱ�����˺��������Provider������ͬ�����꣬���Ͳ��ᱻ����
			@Override
			public void onLocationChanged(Location location) {
				if (location != null) {
					Log.e("Map",
							"Location changed : Lat: " + location.getLatitude()
									+ " Lng: " + location.getLongitude());
					locationTextView.setText("gps:" + "latitude:"
							+ location.getLatitude() + "==>longitude:"
							+ location.getLongitude());
				}
			}
		};

		if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
			Location location = locationManager
					.getLastKnownLocation(LocationManager.GPS_PROVIDER);
			if (location != null) {
				latitude = location.getLatitude();
				longitude = location.getLongitude();
				locationTextView.setText("gps:" + "latitude:" + latitude
						+ "==>longitude:" + longitude);
			} else {
				getLocationByNet();
			}
		}
		
		Location loc = getLocation();
		if (loc != null) {
			latitude = location.getLatitude();
			longitude = location.getLongitude();
			locationTextView.setText("gps:" + "latitude:" + latitude
					+ "==>longitude:" + longitude);
		}
	}*/

	/*private void gpsOpen() {
		// ����ǿ�ƿ���
		if (!isGPSENable()) {
			toggleGPS();
		}
	}*/

	/*private boolean isGPSENable() {
		String str = Settings.Secure.getString(getContentResolver(),
				Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
		if (str != null) {
			Log.i("TAG", "Settings");
			return str.contains("gps");
		} else {
			return false;
		}

		// TODO Auto-generated method stub

	}*/

	/*private void toggleGPS() {
		Log.v("TAG", "��");
		Intent gpsIntent = new Intent();
		gpsIntent.setClassName("com.android.settings",
				"com.android.settings.widget.SettingsAppWidgetProvider");
		gpsIntent.addCategory("android.intent.category.ALTERNATIVE");
		gpsIntent.setData(Uri.parse("custom:3"));
		startActivity(gpsIntent);
	}*/

	/*private void getLocationByNet() {
		locationManager.requestLocationUpdates(
				LocationManager.NETWORK_PROVIDER, 1000, 0, locationListener);
		Location location = locationManager
				.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
		if (location != null) {
			latitude = location.getLatitude(); // ����
			longitude = location.getLongitude(); // γ��
			locationWebTextView.setText("from network:" + "latitude:"
					+ latitude + "==>longitude:" + longitude);
		}
	}*/

}
