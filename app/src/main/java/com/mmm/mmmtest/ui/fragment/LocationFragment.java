package com.mmm.mmmtest.ui.fragment;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mmm.mmmtest.R;

public class LocationFragment extends Fragment implements LocationListener {

    private final long LOCATION_REFRESH_TIME = 5000;
    private final float LOCATION_REFRESH_DISTANCE = 5.0f;

    TextView latTextView;
    TextView lonTextView;

    LocationManager locationManager;

    ProgressDialog progressDialog;

    final int LOCATION_REQUEST_CODE = 4333;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        View fragmentLayout = inflater.inflate(R.layout.fragment_location, container, false);
        latTextView = (TextView) fragmentLayout.findViewById(R.id.tvLat);
        lonTextView = (TextView)fragmentLayout.findViewById(R.id.tvLon);

        locationManager = (LocationManager)getActivity().getSystemService(FragmentActivity.LOCATION_SERVICE);

        //REQUEST LOCATION UPDATES, IF NO PERMISSION IS FOUND THEN REQUEST IT
        if(!requestLocation())
            requestPermissions();

        return fragmentLayout;
    }

    @SuppressLint("DefaultLocale")
    @Override
    public void onLocationChanged(Location location) {
        if(progressDialog != null)
            progressDialog.dismiss();

        if(latTextView != null && lonTextView != null){
            latTextView.setText(String.format("Lat: %.3f", location.getLatitude()));
            lonTextView.setText(String.format("Lon: %.3f", location.getLongitude()));
        }
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
    }


    @Override
    public void onProviderEnabled(String provider) {
    }

    @Override
    public void onProviderDisabled(String provider) {
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == LOCATION_REQUEST_CODE) {
            /*if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, LOCATION_REFRESH_TIME, LOCATION_REFRESH_DISTANCE, this);*/
            /*if(grantResults[0] == PackageManager.PERMISSION_GRANTED)
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, LOCATION_REFRESH_TIME, LOCATION_REFRESH_DISTANCE, this);*/
            requestLocation();
        }
    }

    void requestPermissions(){
        /*if(ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){

            //ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_REQUEST_CODE);
            //REQUEST THE PERMISSIONS FROM THE FRAGMENT SO THAT ON REQUEST PERMISSIONS RESULT GETS CALLED INSIDE THE FRAGMENT
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_REQUEST_CODE);
        } else{
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, LOCATION_REFRESH_TIME, LOCATION_REFRESH_DISTANCE, this);
        }*/

        requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_REQUEST_CODE);
    }

    boolean requestLocation(){
        if(ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, LOCATION_REFRESH_TIME, LOCATION_REFRESH_DISTANCE, this);
            progressDialog = showProgressDialog();
            return true;
        }
        return false;
    }

    private ProgressDialog showProgressDialog(){
        ProgressDialog pd = new ProgressDialog(getContext());
        pd.setTitle("Locating");
        pd.setMessage("Gathering current location");
        pd.setCancelable(false);
        pd.show();
        return pd;
    }
}
