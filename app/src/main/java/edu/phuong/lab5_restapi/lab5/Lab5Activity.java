package edu.phuong.lab5_restapi.lab5;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import edu.phuong.lab5_restapi.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Lab5Activity extends AppCompatActivity {
    EditText txt1, txt2, txt3;
    TextView tvKQ;
    Button btn1;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_lab5);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        txt1=findViewById(R.id.txt1);
        txt2=findViewById(R.id.txt2);
        txt3=findViewById(R.id.txt3);
        tvKQ=findViewById(R.id.tvKq);
        btn1=findViewById(R.id.btn);
        btn1.setOnClickListener(v->{
            insertData(txt1,txt2,txt3,tvKQ);
        });
    }

    private void insertData(EditText txt1, EditText txt2, EditText txt3, TextView tvKQ){
        //B1. tao doi tuong chua du lieu
        SanPham s=new SanPham(txt1.getText().toString(),
                txt2.getText().toString(),txt3.getText().toString());
        //b2. tao doi tuong retrofit
        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl("http://10.24.4.244/000/2024071/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        //b3. goi ham insert
        InterfaceInsertSanPham insertSanPham
                =retrofit.create(InterfaceInsertSanPham.class);
        //chuan bi ham
        Call<SvrResponseSanPham> call
                =insertSanPham.insertSanPham(s.getMaSP(),s.getTenSP(),s.getMoTa());
        //thuc thi ham
        call.enqueue(new Callback<SvrResponseSanPham>() {
            //thanh cong
            @Override
            public void onResponse(Call<SvrResponseSanPham> call, Response<SvrResponseSanPham> response) {
                SvrResponseSanPham res=response.body();
                tvKQ.setText(res.getMessage());
            }
            //that bai
            @Override
            public void onFailure(Call<SvrResponseSanPham> call, Throwable t) {
                tvKQ.setText(t.getMessage());
            }
        });

    }
}