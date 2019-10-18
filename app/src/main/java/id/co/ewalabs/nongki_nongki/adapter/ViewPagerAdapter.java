package id.co.ewalabs.nongki_nongki.adapter;

import android.content.Context;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import id.co.ewalabs.nongki_nongki.view.fragment.DeskripsiFragment;
import id.co.ewalabs.nongki_nongki.view.fragment.GambarFragment;
import id.co.ewalabs.nongki_nongki.view.fragment.LokasiFragment;

public class ViewPagerAdapter extends FragmentPagerAdapter {

    private ArrayList<Fragment> fragments;
    private ArrayList<String> titles;
    Context context;

    public ViewPagerAdapter(@NonNull FragmentManager fm, Context context) {
        super(fm);

        this.context = context;

        fragments = new ArrayList<>();
        fragments.add(new GambarFragment());
        fragments.add(new DeskripsiFragment());
        fragments.add(new LokasiFragment());

        titles = new ArrayList<>();
        titles.add("Gambar");
        titles.add("Deskripsi");
        titles.add("Lokasi");
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return titles.get(position);
    }
}
