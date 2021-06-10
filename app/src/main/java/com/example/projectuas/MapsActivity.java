package com.example.projectuas;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private String[] nama, alamat, jumlah;
    private Integer[] id;
    int jumdata;
    private Double[] latitude, longitude;
    LatLng latLng[];
    Boolean MarkerD[];


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
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
        tampilpeta();



        //Add a marker in Sydney and move the camera
        //LatLng sydney = new LatLng(-34, 151);
       //mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        //mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }
    private void tampilpeta() {
        String Url = "http://192.168.1.7:8080/vaksin/koneksi.php";
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, Url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                jumdata = response.length();
                Log.d("DEBUG", "Parse JSON");
                latLng = new LatLng[jumdata];
                MarkerD = new Boolean[jumdata];
                nama = new String[jumdata];
                alamat = new String[jumdata];
                jumlah = new String[jumdata];
                id = new Integer[jumdata];
                latitude = new Double[jumdata];
                longitude = new Double[jumdata];
                for (int i = 0; i < jumdata; i++) {
                    try {
                        JSONObject data = response.getJSONObject(i);
                        id[i] = data.getInt("id");
                        latLng[i] = new LatLng(data.getDouble("latitude"), data.getDouble("longitude"));
                        nama[i] = data.getString("nama");
                        alamat[i] = data.getString("alamat");
                        jumlah[i] = data.getString("jumlah");
                        latitude[i] = data.getDouble("latitude");
                        longitude[i] = data.getDouble("longitude");
                        MarkerD[i] = false;
                        mMap.addMarker(new MarkerOptions()
                                .position(latLng[i])
                                //.icon(BitmapDescriptorFactory.fromResource(R.drawable.icon))
                                .title(nama[i]));
                    } catch (JSONException e) {
                    }
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng[i], 15.5f));
                }
                mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                    @Override
                    public boolean onMarkerClick(@NonNull Marker marker) {
                        for (int i = 0; i<jumdata; i++){
                            if (marker.getTitle().equals(nama[i])){
                                if (MarkerD[i]){
                                    DetailActivity.nama= nama[i];
                                    DetailActivity.alamat= alamat[i];
                                    DetailActivity.jumlah= jumlah[i];
                                    Intent pindahdetail= new Intent(MapsActivity.this,DetailActivity.class);
                                    startActivity(pindahdetail);
                                    MarkerD[i]=false;

                                }else{
                                    MarkerD[i]=true;
                                    marker.showInfoWindow();
                                    Toast pesan= Toast.makeText(MapsActivity.this,"Silahkan Klik Untuk Detail",Toast.LENGTH_LONG);
                                    TextView tv=pesan.getView().findViewById(R.id.message);
                                    if(tv !=null)
                                        tv.setGravity(Gravity.CENTER);
                                    pesan.show();
                                }

                            }else{
                                MarkerD[i]=false;
                            }
                        }
                        return false;
                    }
                });
            }
        },new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(MapsActivity.this);
                        builder.setTitle("error");
                        builder.setMessage("failed");
                        builder.setPositiveButton("reload", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                tampilpeta();
                            }
                        });
                        AlertDialog alert = builder.create();
                        alert.show();
                    }
                });
                Volley.newRequestQueue(this).add(request);

                }
            }

