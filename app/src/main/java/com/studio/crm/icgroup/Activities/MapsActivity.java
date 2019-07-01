package com.studio.crm.icgroup.Activities;

import android.graphics.Typeface;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.studio.crm.icgroup.R;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {
    TextView nameTextView, rateTextView , rateLabelTextView, attLabelTextView, attDateTextView;
    ImageView avatar;

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        createViews();
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(43.236731, 76.874772);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Это то место"));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(sydney,12.0f));

       // mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }
    public void onClick(View view){

    }



    private void createViews(){
        nameTextView=(TextView)findViewById(R.id.nameTextView);
        rateTextView=((TextView)findViewById(R.id.rateTextView));
        rateLabelTextView=((TextView)findViewById(R.id.rateLabelTextView));
        attDateTextView=((TextView)findViewById(R.id.attDateTextView));
        attLabelTextView=((TextView)findViewById(R.id.attLabelTextView));
        avatar=(ImageView) findViewById(R.id.avatar);

        setType("demibold", nameTextView, rateTextView, attDateTextView);
        setType("light", rateLabelTextView, attLabelTextView);
    }
    public void setType(String type, TextView... a){
        for(int i=0;i<a.length;i++){
            a[i].setTypeface(getTypeFace(type));
        }

    }
    public Typeface getTypeFace(String s){
        switch (s){
            case "demibold":
                return (Typeface.createFromAsset(getAssets(),"fonts/AVENIRNEXT-DEMIBOLD.ttf"));
            case "medium":
                return (Typeface.createFromAsset(getAssets(),"fonts/AvenirNextLTPro-Medium.ttf"));
            case "light":
                return (Typeface.createFromAsset(getAssets(),"fonts/avenir-light.ttf"));
            case "black":
                return (Typeface.createFromAsset(getAssets(),"fonts/Avenir-Black.ttf"));
            case "bold":
                return (Typeface.createFromAsset(getAssets(),"fonts/AvenirNextLTPro-Bold.ttf"));
            case "regular":
                return Typeface.createFromAsset(getAssets(),"fonts/AvenirNextLTPro-Regular.ttf");
            default:
                return (Typeface.createFromAsset(getAssets(),"fonts/AvenirNextLTPro-It.ttf"));
        }
    }

    public void onBackPressed(View view){
        onBackPressed();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
