package id.co.ewalabs.nongki_nongki.home;

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

public class DaftarCafeAdapter extends RecyclerView.Adapter<DaftarCafeAdapter.MyViewHolder> {

    private Context context;
    private List<DaftarCafeModel> list;

    public DaftarCafeAdapter(Context c, List<DaftarCafeModel> p) {
        context = c;
        list = p;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.daftar_cafe_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Picasso.get().load(list.get(position).getThumbCafe()).into(holder.ivCafeThumb);
        holder.tvNamaCafe.setText(list.get(position).getNamaCafe());
        holder.tvJumlahLike.setText(String.valueOf(list.get(position).getJumLike())+" Suka");
        holder.tvJumlahKomentar.setText(String.valueOf(list.get(position).getJumKomentar())+" Komen");

//        holder.onClick(position);


    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvNamaCafe, tvJumlahLike, tvJumlahKomentar;
        ImageView ivCafeThumb;


        public MyViewHolder(View itemView) {
            super(itemView);

            tvNamaCafe = itemView.findViewById(R.id.tvNamaCafe);
            tvJumlahLike = itemView.findViewById(R.id.tvJumlahLike);
            tvJumlahKomentar = itemView.findViewById(R.id.tvJumlahKomentar);
            ivCafeThumb = itemView.findViewById(R.id.ivCafeThumb);

        }


    }
}
