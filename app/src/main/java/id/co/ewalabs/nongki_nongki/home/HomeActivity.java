package id.co.ewalabs.nongki_nongki.home;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import id.co.ewalabs.nongki_nongki.R;
import id.co.ewalabs.nongki_nongki.util.CustomVolleyRequest;

import static id.co.ewalabs.nongki_nongki.IPAddress.ipAddress;

public class HomeActivity extends AppCompatActivity {

    private LinearLayout sliderDotspanel;
    private int dotscount;
    private ImageView[] dots;
    private ViewPager viewPager;
    private List<RecommendedModel> bannerList;
    private SliderAdapter sliderAdapter;
    private static int NUM_PAGES = 0;
    private static int currentPage = 0;
    private String RECOMMENDED_URL="http://"+ipAddress+"/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_main);

        viewPager = (ViewPager) findViewById(R.id.viewPager);
        sliderDotspanel = (LinearLayout) findViewById(R.id.sliderDots);


        loadRecommendedBanner();
    }


    private void loadRecommendedBanner(){

        bannerList = new ArrayList<>();

        JsonArrayRequest jsonArrayRequestBanners = new JsonArrayRequest(Request.Method.GET, RECOMMENDED_URL, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                for(int i = 0; i < response.length(); i++){

                    try {
                        JSONObject jsonObject = response.getJSONObject(i);
                        RecommendedModel recommendedModel = new RecommendedModel(
                                jsonObject.getString("id_makanan"),
                                jsonObject.getInt("nama_makanan")
                        );

                        bannerList.add(recommendedModel);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }

                sliderAdapter = new SliderAdapter(bannerList, HomeActivity.this);

                viewPager.setAdapter(sliderAdapter);

                dotscount = sliderAdapter.getCount();
                dots = new ImageView[dotscount];

                for(int i = 0; i < dotscount; i++){

                    dots[i] = new ImageView(HomeActivity.this);
                    dots[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.default_dot));

                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

                    params.setMargins(8, 0, 8, 0);

                    sliderDotspanel.addView(dots[i], params);

                }

                dots[0].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.selected_dot));
                NUM_PAGES =bannerList.size();

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

                for(int i = 0; i< dotscount; i++){
                    dots[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.default_dot));
                }

                dots[position].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.selected_dot));

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }
}
