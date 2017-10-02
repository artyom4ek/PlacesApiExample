package vasylenko.placesapiexample.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import vasylenko.placesapiexample.R;

/**
 * Активити для работы с Google картой.
 * Created by Тёма on 02.10.2017.
 */
public class GoogleMapActivity extends AppCompatActivity implements OnMapReadyCallback {
    private String title, address;
    private double latitude,longitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_google_map);

        Intent intent = getIntent();
        latitude = intent.getDoubleExtra("latitude", 0d);
        longitude = intent.getDoubleExtra("longitute",0d);
        title = intent.getStringExtra("name");
        address = intent.getStringExtra("address");

        SupportMapFragment mapFragment = (SupportMapFragment)
                getSupportFragmentManager()
                        .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        LatLng latlng = new LatLng(latitude,longitude);

        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latlng, 13));

        googleMap.addMarker( new MarkerOptions()
                .title(title)
                .snippet(address)
                .position(latlng));
    }
}
