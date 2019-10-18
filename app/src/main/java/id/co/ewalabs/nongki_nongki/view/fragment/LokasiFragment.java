package id.co.ewalabs.nongki_nongki.view.fragment;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import id.co.ewalabs.nongki_nongki.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class LokasiFragment extends Fragment {


    public LokasiFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_lokasi, container, false);
    }

}
