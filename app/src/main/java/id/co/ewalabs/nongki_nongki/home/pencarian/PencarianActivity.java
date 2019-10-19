package id.co.ewalabs.nongki_nongki.home.pencarian;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import id.co.ewalabs.nongki_nongki.R;
import id.co.ewalabs.nongki_nongki.home.DaftarCafeModel;

public class PencarianActivity extends AppCompatActivity {

    private ImageView ivButtonKembali;
    private RecyclerView recyclerView;
    private EditText etPencarian;
    private ArrayList<DaftarCafeModel> listDaftarCafe;
    private PencarianCafeAdapter pencarianCafeAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_pencarian);

        Intent intent=getIntent();
        listDaftarCafe = intent.getParcelableArrayListExtra("data_cafe");

        ivButtonKembali=findViewById(R.id.btnKembaliPencarian);
        etPencarian=findViewById(R.id.etPencarian);
        recyclerView=findViewById(R.id.rvHasilPencarian);
        ivButtonKembali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        cariCafe();

    }


    private void cariCafe(){

        etPencarian.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {}

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            public void onTextChanged(CharSequence query, int start, int before, int count) {

                query = query.toString().toLowerCase();

                final List<DaftarCafeModel> filteredList = new ArrayList<>();

                for (int i = 0; i < listDaftarCafe.size(); i++) {

                    final String text = listDaftarCafe.get(i).getNamaCafe().toLowerCase();
                    if (text.contains(query)) {

                        filteredList.add(new DaftarCafeModel(
                                listDaftarCafe.get(i).getIdCafe(),
                                listDaftarCafe.get(i).getThumbCafe(),
                                listDaftarCafe.get(i).getNamaCafe(),
                                listDaftarCafe.get(i).getJumKomentar(),
                                listDaftarCafe.get(i).getJumLike()
                        ));
                    }

                    if(etPencarian.getText().toString().equals("")){
                        filteredList.clear();
                    }
                }

                recyclerView.setLayoutManager(new LinearLayoutManager(PencarianActivity.this));
                pencarianCafeAdapter=new PencarianCafeAdapter(PencarianActivity.this,filteredList);
                recyclerView.setAdapter(pencarianCafeAdapter);
                pencarianCafeAdapter.notifyDataSetChanged();
            }
        });
    }
}
