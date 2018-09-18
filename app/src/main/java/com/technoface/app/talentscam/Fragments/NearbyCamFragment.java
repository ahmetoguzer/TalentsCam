package com.technoface.app.talentscam.Fragments;

import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.technoface.app.talentscam.Activities.MainMenuActivity;
import com.technoface.app.talentscam.BuildConfig;
import com.technoface.app.talentscam.Controller.AppController;
import com.technoface.app.talentscam.R;
import com.technoface.app.talentscam.Utils.Common;
import com.technoface.app.talentscam.helper.AppLocationService;

/**
 * Created by Ahmet Oguzer on 26.10.2017.
 */

public class NearbyCamFragment extends BaseFragment implements OnMapReadyCallback {

    public static final String FRAGMENT_TAG =
            BuildConfig.APPLICATION_ID + ".DEBUG_EXAMPLE_TWO_FRAGMENT_TAG";

    GoogleMap mGoogleMap;
    MapView mMapView;
    private AppLocationService appLocationService;
    private double latitude;
    private double longitude;
    private Location location;
    private ImageButton btnBack;
    private String  state;

    public static NearbyCamFragment newInstance() {
        NearbyCamFragment fragment = new NearbyCamFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container, Bundle savedInstanceState) {
        View rootview= inflater.inflate(R.layout.fragment_nearby_cam, container, false);
        return rootview;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init(view);
    }


    @Override
    public void onActivityCreated( Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        MainMenuActivity.rv.setVisibility(View.GONE);

        appLocationService = new AppLocationService(getContext());
        location = appLocationService
                .getLocation(LocationManager.NETWORK_PROVIDER);

        if (location != null) {
            latitude = location.getLatitude();
            longitude = location.getLongitude();

        }else{
            Common.alert(getActivity(),"Uyarı",getResources().getString(R.string.location_error));
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        MapsInitializer.initialize(getContext());
        mGoogleMap=googleMap;
        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        googleMap.addMarker(new MarkerOptions().position(new LatLng(latitude,longitude)));
        googleMap.addMarker(new MarkerOptions().position(new LatLng(40.974466,29.077189)).icon(BitmapDescriptorFactory.fromResource(R.drawable.locationicon)));
        googleMap.addMarker(new MarkerOptions().position(new LatLng(40.984472,29.127460)).icon(BitmapDescriptorFactory.fromResource(R.drawable.locationicon)));

        CameraPosition Liberty = CameraPosition.builder().target(new LatLng(latitude,longitude)).zoom(12).bearing(0).tilt(45).build();
        googleMap.moveCamera(CameraUpdateFactory.newCameraPosition(Liberty));
    }

    private void init(View view){
        btnBack = (ImageButton) view.findViewById(R.id.btn_back);
        mMapView = (MapView)view.findViewById(R.id.map);

         state = getArguments().getString("state");

        if(mMapView!=null){
            mMapView.onCreate(null);
            mMapView.onResume();
            mMapView.getMapAsync(this);
        }
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            MainMenuActivity.screenBackCall();
            }
        });
    }

}
