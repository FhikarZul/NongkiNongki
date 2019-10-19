package id.co.ewalabs.nongki_nongki.home;

import android.content.Intent;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.provider.Settings;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import id.co.ewalabs.nongki_nongki.R;
import id.co.ewalabs.nongki_nongki.home.cafe_terdekat.CafeTerdekatActivity;
import id.co.ewalabs.nongki_nongki.home.pencarian.PencarianActivity;
import id.co.ewalabs.nongki_nongki.util.CustomVolleyRequest;

import static id.co.ewalabs.nongki_nongki.IPAddress.ipAddress;

public class HomeActivity extends AppCompatActivity {

    private LinearLayout sliderDotspanel;
    private int dotscount;
    private ImageView[] dots;
    private ViewPager viewPager;
    private List<RecommendedModel> bannerList;
    private ArrayList<DaftarCafeModel> listDaftarCafe;
    private RecommendedAdapter recommendedAdapter;
    private static int NUM_PAGES = 0;
    private static int currentPage = 0;
    private RecyclerView recyclerView;
    private DaftarCafeAdapter daftarCafeAdapter;
    private String RECOMMENDED_URL = "http://" + ipAddress + "/NONGKI_SERVER/API/tampil_daftar_cafe_recommended.php";
    private String DAFTAR_CAFE_URL = "http://" + ipAddress + "/NONGKI_SERVER/API/tampil_daftar_cafe.php";
    private TextView tvButtonCafeTerdekat;
    private ImageView btnSearch;
    private LocationManager locationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        );
        setContentView(R.layout.home_main);

        viewPager = findViewById(R.id.viewPager);
        sliderDotspanel = findViewById(R.id.sliderDots);
        recyclerView = findViewById(R.id.rvSemuaCafe);
        LinearLayoutManager layoutManager = new LinearLayoutManager(HomeActivity.this);
        recyclerView.setLayoutManager(layoutManager);
        tvButtonCafeTerdekat=findViewById(R.id.tvButtonCafeTerdekat);
        btnSearch=findViewById(R.id.btnSearch);

        loadRecommendedBanner();
        loadDaftarCafe();
        setButtonCafeTerdekat();
        setButtonSearch();
        cekGPS();
    }

    private void cekGPS(){
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
        }
    }

    private void setButtonSearch(){
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, PencarianActivity.class);
                intent.putParcelableArrayListExtra("data_cafe", listDaftarCafe);
                startActivity(intent);
            }
        });
    }

    private void setButtonCafeTerdekat(){
        tvButtonCafeTerdekat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeActivity.this, CafeTerdekatActivity.class));
            }
        });
    }


    private void loadRecommendedBanner() {

        bannerList = new ArrayList<>();

        JsonArrayRequest jsonArrayRequestBanners = new JsonArrayRequest(Request.Method.GET, RECOMMENDED_URL, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                for (int i = 0; i < response.length(); i++) {

                    try {
                        JSONObject jsonObject = response.getJSONObject(i);
                        RecommendedModel recommendedModel = new RecommendedModel(
                                jsonObject.getInt("id_cafe"),
                                "http://" + ipAddress + "/" + jsonObject.getString("thumbnail")
                        );

                        bannerList.add(recommendedModel);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }

                recommendedAdapter = new RecommendedAdapter(bannerList, HomeActivity.this);

                viewPager.setAdapter(recommendedAdapter);

                dotscount = recommendedAdapter.getCount();
                dots = new ImageView[dotscount];

                for (int i = 0; i < dotscount; i++) {

                    dots[i] = new ImageView(HomeActivity.this);
                    dots[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.default_dot));

                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

                    params.setMargins(8, 0, 8, 0);

                    sliderDotspanel.addView(dots[i], params);

                }

                dots[0].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.selected_dot));
                NUM_PAGES = bannerList.size();

                // Auto start of viewpager
                final Handler handler = new Handler();
                final Runnable Update = new Runnable() {
                    public void run() {
                        if (currentPage == NUM_PAGES) {
                            currentPage = 0;
                        }
                        viewPager.setCurrentItem(currentPage++, true);
                    }
                };
                Timer swipeTimer = new Timer();
                swipeTimer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        handler.post(Update);
                    }
                }, 1000, 3000);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });


        CustomVolleyRequest.getInstance(this).addToRequestQueue(jsonArrayRequestBanners);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                for (int i = 0; i < dotscount; i++) {
                    dots[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.default_dot));
                }

                dots[position].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.selected_dot));

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    private void loadDaftarCafe() {
        listDaftarCafe=new ArrayList<>();
        JsonArrayRequest jsonArrayRequestDaftarCafe = new JsonArrayRequest(Request.Method.GET, DAFTAR_CAFE_URL, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                try {

                    for (int a = 0; a < response.length(); a++) {
                        JSONObject daftarCafe = response.getJSONObject(a);

                        listDaftarCafe.add(new DaftarCafeModel(
                                daftarCafe.getInt("id_cafe"),
                                "http://" + ipAddress + "/" + daftarCafe.getString("thumbnail"),
                                daftarCafe.getString("nama_cafe"),
                                daftarCafe.getInt("jumlah_komentar"),
                                daftarCafe.getInt("jumlah_like")

                        ));


                    }


                    daftarCafeAdapter = new DaftarCafeAdapter(HomeActivity.this, listDaftarCafe);
                    recyclerView.setAdapter(daftarCafeAdapter);


                } catch (JSONException e) {
                    System.out.println(e);
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(HomeActivity.this, "ERROR", Toast.LENGTH_SHORT).show();
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(HomeActivity.this);
        requestQueue.add(jsonArrayRequestDaftarCafe);

    }
}
