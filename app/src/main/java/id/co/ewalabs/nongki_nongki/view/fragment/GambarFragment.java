package id.co.ewalabs.nongki_nongki.view.fragment;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import id.co.ewalabs.nongki_nongki.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class GambarFragment extends Fragment {

    RecyclerView rvImageRoom;
    StaggeredGridLayoutManager staggeredGridLayoutManager;

    public GambarFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_gambar, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rvImageRoom = view.findViewById(R.id.rv_image_room);
        staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        rvImageRoom.setLayoutManager(staggeredGridLayoutManager);

//        
    }
}
