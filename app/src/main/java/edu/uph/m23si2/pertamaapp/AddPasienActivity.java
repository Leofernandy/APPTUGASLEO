package edu.uph.m23si2.pertamaapp;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import edu.uph.m23si2.pertamaapp.api.ApiClient;
import edu.uph.m23si2.pertamaapp.api.ApiResponsePasien;
import edu.uph.m23si2.pertamaapp.api.ApiService;
import edu.uph.m23si2.pertamaapp.model.Pasien;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddPasienActivity extends AppCompatActivity {
    EditText etNama, etNik, etAlamat, etNoHp;
    Button btnSimpan;
    ApiService apiService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_pasien);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        etNama = findViewById(R.id.etNama);
        etNik = findViewById(R.id.etNik);
        etAlamat = findViewById(R.id.etAlamat);
        etNoHp = findViewById(R.id.etNoHp);
        btnSimpan = findViewById(R.id.btnSimpan);
        apiService = ApiClient.getClient().create(ApiService.class);
        btnSimpan.setOnClickListener(v -> {
            String nama = etNama.getText().toString();
            String nik = etNik.getText().toString();
            String alamat = etAlamat.getText().toString();
            String noHp = etNoHp.getText().toString();

            Pasien pasien = new Pasien(nama, nik, alamat, noHp);
            apiService.createPasien(pasien).enqueue(new Callback<ApiResponsePasien>() {
                @Override
                public void onResponse(Call<ApiResponsePasien> call, Response<ApiResponsePasien> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        Toast.makeText(AddPasienActivity.this, response.body().toString(), Toast.LENGTH_SHORT).show();
                        Log.d("API_RESPONSE", response.toString());
                    } else {
                        Toast.makeText(AddPasienActivity.this, "Gagal simpan pasien", Toast.LENGTH_SHORT).show();
                    }
                }
                @Override
                public void onFailure(Call<ApiResponsePasien> call, Throwable t) {
                    Toast.makeText(AddPasienActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                    Log.d("API_RESPONSE", t.toString());
                }

            });

        });
    }
}