package id.co.ewalabs.nongki_nongki.home.pencarian;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

import id.co.ewalabs.nongki_nongki.R;
import id.co.ewalabs.nongki_nongki.home.DaftarCafeModel;

public class PencarianCafeAdapter extends RecyclerView.Adapter<PencarianCafeAdapter.MyViewHolder> {

    private Context context;
    private List<DaftarCafeModel> list;

    public PencarianCafeAdapter(Context c, List<DaftarCafeModel> p) {
        context = c;
        list = p;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.hasil_pencarian_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Picasso.get().load(list.get(position).getThumbCafe()).placeholder(R.drawable.bg_loading).into(holder.ivCafeThumb);
        holder.tvNamaCafe.setText(list.get(position).getNamaCafe());


//        holder.onClick(position);


    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvNamaCafe;
        ImageView ivCafeThumb;


        public MyViewHolder(View itemView) {
            super(itemView);

            tvNamaCafe = itemView.findViewById(R.id.tvNamaCafeHasilPencarian);
            ivCafeThumb = itemView.findViewById(R.id.ivHasilPencarian);


        }


    }
}
