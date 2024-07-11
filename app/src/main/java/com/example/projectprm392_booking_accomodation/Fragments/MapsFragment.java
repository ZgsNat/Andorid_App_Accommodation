package com.example.projectprm392_booking_accomodation.Fragments;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import com.example.projectprm392_booking_accomodation.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.tasks.OnSuccessListener;

public class MapsFragment extends Fragment implements OnMapReadyCallback {

    private GoogleMap mMap;
    private double destinationLatitude;
    private double destinationLongitude;
    private double accommodationLatitude;
    private double accommodationLongitude;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Lấy tọa độ từ arguments
        if (getArguments() != null) {
            accommodationLatitude = getArguments().getDouble("ACCOMMODATION_LATITUDE", 0);
            accommodationLongitude = getArguments().getDouble("ACCOMMODATION_LONGITUDE", 0);
            destinationLatitude = getArguments().getDouble("DESTINATION_LATITUDE", 0);
            destinationLongitude = getArguments().getDouble("DESTINATION_LONGITUDE", 0);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_maps, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }
        view.setOnClickListener(v -> openGoogleMaps(destinationLatitude, destinationLongitude));
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;
        LatLng accommodationLocation = new LatLng(accommodationLatitude, accommodationLongitude);
        LatLng destination = new LatLng(destinationLatitude, destinationLongitude);

        mMap.addMarker(new MarkerOptions().position(accommodationLocation).title("Accommodation"));
        mMap.addMarker(new MarkerOptions().position(destination).title("Đại học FPT"));

        // Vẽ đường đi
        PolylineOptions polylineOptions = new PolylineOptions()
                .add(accommodationLocation)
                .add(destination)
                .width(5)
                .color(R.color.red);
        mMap.addPolyline(polylineOptions);

        // Zoom bản đồ để bao gồm cả hai điểm
        mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(
                new LatLngBounds.Builder()
                        .include(accommodationLocation)
                        .include(destination)
                        .build(), 100));

        showDistance(accommodationLocation, destination);
    }

    private void showDistance(LatLng accommodationLocation, LatLng destination) {
        float[] results = new float[1];
        Location.distanceBetween(accommodationLocation.latitude, accommodationLocation.longitude,
                destination.latitude, destination.longitude, results);
        float distance = results[0] / 1000; // Chuyển đổi từ mét sang km

        if (getView() != null) {
            TextView txtDistance = getView().findViewById(R.id.txtDistance);
            txtDistance.setText(String.format("Khoảng cách: %.2f km", distance));
        }
    }
    private void openGoogleMaps(double latitude, double longitude) {
        String uri = String.format("geo:%s,%s?q=%s,%s(Đại+học+FPT)", latitude, longitude, latitude, longitude);
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
        intent.setPackage("com.google.android.apps.maps");
        if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivity(intent);
        }
    }
}
