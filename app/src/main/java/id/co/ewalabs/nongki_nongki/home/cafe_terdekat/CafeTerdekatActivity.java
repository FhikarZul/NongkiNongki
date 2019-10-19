package id.co.ewalabs.nongki_nongki.home.cafe_terdekat;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
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

import java.util.ArrayList;
import java.util.List;

import id.co.ewalabs.nongki_nongki.R;

import static id.co.ewalabs.nongki_nongki.IPAddress.ipAddress;

public class CafeTerdekatActivity extends FragmentActivity implements OnMapReadyCallback, LocationListener {

    private boolean cafeTerdekat=false;
    private GoogleMap mMap;
    private List<CafeTerdekatModel> listLokasiCafe;
    private LocationManager locationManager;
    private long MIN_DISTANCE_CHANGE_FOR_UPDATES = 10;
    private long MIN_TIME_BW_UPDATES = 1000 * 60 * 1;
    private ProgressDialog loading = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cafe_terdekat);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);


        ImageView btnKembali=findViewById(R.id.btnKembaliCafeTerdekat);
        btnKembali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        loading=new ProgressDialog(this);
        getDataCafeTerdekat();

    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        mMap.setMyLocationEnabled(true);
        mMap.getUiSettings().setCompassEnabled(true);
        mMap.getUiSettings().setZoomControlsEnabled(true);

        showCurrentLocation();
    }


    private void showCurrentLocation() {
        Criteria criteria = new Criteria();
        String bestProvider = locationManager.getBestProvider(criteria, true);
        if (ActivityCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        Location location = locationManager.getLastKnownLocation(bestProvider);

        if (location != null) {
            onLocationChanged(location);
            showLoading(true);
        }
        locationManager.requestLocationUpdates(bestProvider, MIN_TIME_BW_UPDATES,
                MIN_DISTANCE_CHANGE_FOR_UPDATES, (LocationListener) CafeTerdekatActivity.this);
    }


    private void getDataCafeTerdekat() {
        listLokasiCafe = new ArrayList<>();
        JsonArrayRequest jsonArrayRequestDaftarCafe = new JsonArrayRequest(Request.Method.GET, "http://" + ipAddress + "/NONGKI_SERVER/API/tampil_daftar_cafe.php", null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                try {

                    for (int a = 0; a < response.length(); a++) {
                        JSONObject jsonObj = response.getJSONObject(a);

                        listLokasiCafe.add(new CafeTerdekatModel(
                                jsonObj.getInt("id_cafe"),
                                jsonObj.getString("nama_cafe"),
                                jsonObj.getString("lokasi")
                        ));
                    }

                } catch (JSONException e) {
                    System.out.println(e);
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(CafeTerdekatActivity.this, "Error", Toast.LENGTH_SHORT).show();
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(CafeTerdekatActivity.this);
        requestQueue.add(jsonArrayRequestDaftarCafe);


    }


    private void loadCafeTerdekat(LatLng latLngUser) {

        showLoading(false);

        for (int i = 0; i < listLokasiCafe.size(); i++) {

            String lokasi[] = listLokasiCafe.get(i).getLokasi().split("_");

            LatLng latLngCafe = new LatLng(Double.parseDouble(lokasi[0]), Double.parseDouble(lokasi[1]));

            if (getDistanceMeters(latLngUser, latLngCafe) < 500) {

                final Marker marker = mMap.addMarker(new MarkerOptions()
                        .position(latLngCafe)
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_cafe))
                        .title(listLokasiCafe.get(i).getNamaCafe()));
                marker.showInfoWindow();
                marker.setTag(listLokasiCafe.get(i).getIdCafe());

                mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                    @Override
                    public boolean onMarkerClick(Marker marker) {
                        Toast.makeText(CafeTerdekatActivity.this, String.valueOf((Integer) marker.getTag()), Toast.LENGTH_SHORT).show();
                        return false;
                    }
                });

                cafeTerdekat=true;

            }

            if(!cafeTerdekat) Toast.makeText(this, "Cafe terdekat tidak tersedia!", Toast.LENGTH_SHORT).show();


        }


        locationManager.removeUpdates(this);
    }


    @Override
    public void onLocationChanged(Location location) {
        double latitude = location.getLatitude();
        double longitude = location.getLongitude();

        LatLng latLng = new LatLng(latitude, longitude);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        float zoomLevel = 15.0f;
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoomLevel));
        loadCafeTerdekat(latLng);
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {
    }

    @Override
    public void onProviderDisabled(String s) {
    }


    public static long getDistanceMeters(LatLng pt1, LatLng pt2) {
        double distance = 0d;
        try {
            double theta = pt1.longitude - pt2.longitude;
            double dist = Math.sin(Math.toRadians(pt1.latitude)) * Math.sin(Math.toRadians(pt2.latitude))
                    + Math.cos(Math.toRadians(pt1.latitude)) * Math.cos(Math.toRadians(pt2.latitude)) * Math.cos(Math.toRadians(theta));

            dist = Math.acos(dist);
            dist = Math.toDegrees(dist);
            distance = dist * 60 * 1853.1596;
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return (long) distance;
    }


    private void showLoading(boolean status){
        if(status){
            loading.setIndeterminate(false);
            loading.setCancelable(false);
            loading.setMessage("Mencari Cafe Terdekat");
            loading.show();
        }
        else loading.dismiss();
    }

}
