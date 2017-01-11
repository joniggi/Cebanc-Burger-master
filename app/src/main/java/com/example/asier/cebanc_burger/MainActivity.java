package com.example.asier.cebanc_burger;

import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback {

    Button entrar;
    GoogleMap mapa;
    private FirstMapFragment mFirstMapFragment;
    MediaPlayer mp3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        entrar = (Button) findViewById(R.id.entrar);
        mp3 = MediaPlayer.create(this,R.raw.moneda);

        entrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mp3.start();
                Intent i = new Intent(MainActivity.this,DatosCliente.class);
                startActivity(i);
                overridePendingTransition(R.anim.left_in, R.anim.left_out);
            }
        });

       /* mFirstMapFragment = FirstMapFragment.newInstance();
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.map, mFirstMapFragment)
                .commit();*/
    }





    @Override
    public void onMapReady(GoogleMap googleMap) {
        LatLng cebanckebab = new LatLng(43.30469411639206, -2.0168709754943848);

        mapa.addMarker(new MarkerOptions()
                .position(cebanckebab)
                .title("Cebanc-Kebab")
                .snippet("Berio Pasealekua, San Sebastian"))
                .showInfoWindow();
        CameraPosition cameraPosition = CameraPosition.builder()
                .target(cebanckebab)
                .zoom(13)
                .build();
        googleMap.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

    }

}
