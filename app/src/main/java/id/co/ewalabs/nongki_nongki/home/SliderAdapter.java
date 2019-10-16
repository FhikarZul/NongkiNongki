package id.co.ewalabs.nongki_nongki.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.android.volley.toolbox.ImageLoader;

import java.util.List;

import id.co.ewalabs.nongki_nongki.R;
import id.co.ewalabs.nongki_nongki.util.CustomVolleyRequest;

public class SliderAdapter extends PagerAdapter {
    private List<RecommendedModel> bannerList;
    private Context context;
    private LayoutInflater layoutInflater;
    private ImageLoader imageLoader;

    public SliderAdapter(List<RecommendedModel> bannerList, Context context) {
        this.bannerList = bannerList;
        this.context = context;
    }


    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view == o;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, final int position) {
        View view = LayoutInflater.from(container.getContext()).inflate(R.layout.recommended_item, container, false);
        ImageView banner = view.findViewById(R.id.bannerSlider);
        imageLoader = CustomVolleyRequest.getInstance(context).getImageLoader();
        imageLoader.get(bannerList.get(position).getTumbCafe(), ImageLoader.getImageListener(banner, R.mipmap.ic_launcher, android.R.drawable.ic_dialog_alert));
        ViewPager vp = (ViewPager) container;
        banner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent = new Intent(context, MakananDetailActivity.class);
//                Common.common_makanan = bannerList.get(position);
//                context.startActivity(intent);
            }
        });
        vp.addView(view, 0);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {

        ViewPager vp = (ViewPager) container;
        View view = (View) object;
        vp.removeView(view);
    }
}
