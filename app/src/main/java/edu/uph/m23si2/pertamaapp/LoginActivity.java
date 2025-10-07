package edu.uph.m23si2.pertamaapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.List;

import edu.uph.m23si2.pertamaapp.api.ApiResponse;
import edu.uph.m23si2.pertamaapp.api.ApiResponseKabupaten;
import edu.uph.m23si2.pertamaapp.api.ApiService;
import edu.uph.m23si2.pertamaapp.model.KRS;
import edu.uph.m23si2.pertamaapp.model.KRS_detail;
import edu.uph.m23si2.pertamaapp.model.Kabupaten;
import edu.uph.m23si2.pertamaapp.model.Kelas_Matakuliah;
import edu.uph.m23si2.pertamaapp.model.Mahasiswa;
import edu.uph.m23si2.pertamaapp.model.Matakuliah;
import edu.uph.m23si2.pertamaapp.model.Prodi;
import edu.uph.m23si2.pertamaapp.model.Provinsi;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity {
    Button btnLogin;
    EditText edtNama, edtPassword;

    Spinner sprProvinsi;
    List<Provinsi> provinsiList = new ArrayList<>();
    List<String> namaProvinsi = new ArrayList<>();
    ArrayAdapter<String> provinsiAdapter;

    Spinner sprKabupaten;
    List<Kabupaten> kabupatenList = new ArrayList<>();
    List<String> namaKabupaten = new ArrayList<>();
    ArrayAdapter<String> kabupatenAdapter;

    ListView listKabupaten;
    ArrayAdapter<String> listAdapter;


    ApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Realm init
        Realm.init(this);
        RealmConfiguration config = new RealmConfiguration.Builder()
                .name("default.realm")
                .schemaVersion(1)
                .allowWritesOnUiThread(true) // sementara aktifkan untuk demo
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.setDefaultConfiguration(config);
        initData();

        // View binding
        btnLogin = findViewById(R.id.btnLogin);
        edtNama = findViewById(R.id.edtNama);
        edtPassword = findViewById(R.id.edtPassword);

        btnLogin.setOnClickListener(v -> toDashboard());

        // Spinner Provinsi
        sprProvinsi = findViewById(R.id.sprProvinsi);
        provinsiAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, namaProvinsi);
        provinsiAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sprProvinsi.setAdapter(provinsiAdapter);

        // Spinner Kabupaten
        sprKabupaten = findViewById(R.id.sprKabupaten);
        kabupatenAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, namaKabupaten);
        kabupatenAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sprKabupaten.setAdapter(kabupatenAdapter);

        // ListView untuk menampilkan 10 kabupaten pertama
        listKabupaten = findViewById(R.id.listKabupaten);
        listAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, new ArrayList<>());
        listKabupaten.setAdapter(listAdapter);


        // Retrofit init
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://wilayah.id/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        apiService = retrofit.create(ApiService.class);

        // Load Provinsi
        loadProvinsi();
    }

    private void loadProvinsi() {
        apiService.getProvinsi().enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    provinsiList = response.body().getData();
                    namaProvinsi.clear();
                    for (Provinsi p : provinsiList) {
                        if (p.getName() != null) {
                            namaProvinsi.add(p.getName());
                        }
                    }
                    provinsiAdapter.notifyDataSetChanged();

                    sprProvinsi.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            Provinsi selected = provinsiList.get(position);
                            Log.d("Provinsi", selected.getCode() + " - " + selected.getName());
                            loadKabupaten(selected.getCode());
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {}
                    });
                }
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                Toast.makeText(LoginActivity.this, "Gagal ambil provinsi: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void loadKabupaten(String provinsiCode) {
        apiService.getKabupaten(provinsiCode).enqueue(new Callback<ApiResponseKabupaten>() {
            @Override
            public void onResponse(Call<ApiResponseKabupaten> call, Response<ApiResponseKabupaten> response) {
                if (response.isSuccessful() && response.body() != null) {
                    kabupatenList = response.body().getData();
                    namaKabupaten.clear();

                    for (Kabupaten k : kabupatenList) {
                        if (k.getName() != null) {
                            namaKabupaten.add(k.getName());
                        }
                    }
                    kabupatenAdapter.notifyDataSetChanged();

                    // --- tampilkan 10 kabupaten pertama di ListView ---
                    List<String> firstTen = new ArrayList<>();
                    for (int i = 0; i < Math.min(10, namaKabupaten.size()); i++) {
                        firstTen.add(namaKabupaten.get(i));
                    }
                    listAdapter.clear();
                    listAdapter.addAll(firstTen);
                    listAdapter.notifyDataSetChanged();
                }
            }


            @Override
            public void onFailure(Call<ApiResponseKabupaten> call, Throwable t) {
                Toast.makeText(LoginActivity.this, "Gagal ambil kabupaten: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    public void initData() {
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(r -> {
            r.deleteAll(); // hapus semua isi database dulu

            // --- Prodi ---
            Prodi prodiSI = r.createObject(Prodi.class, 0);
            prodiSI.setFakultas("Fakultas Teknologi Informasi");
            prodiSI.setNama("Sistem Informasi");

            // --- Mata Kuliah ---
            Matakuliah matMobile = r.createObject(Matakuliah.class, 1);
            matMobile.setNama("Pemrograman Mobile Lanjut");
            matMobile.setSks(3);
            matMobile.setProdi(prodiSI);

            Matakuliah matPBO = r.createObject(Matakuliah.class, 2);
            matPBO.setNama("Pemrograman Berorientasi Objek");
            matPBO.setSks(3);
            matPBO.setProdi(prodiSI);

            // --- Mahasiswa ---
            Mahasiswa mhs1 = r.createObject(Mahasiswa.class, 0);
            mhs1.setNama("Budi");
            mhs1.setEmail("budi@uph.edu");
            mhs1.setJenisKelamin("Laki-laki");
            mhs1.setHobi("Tidur");
            mhs1.setProdi("Sistem Informasi");

            // --- Kelas Matakuliah ---
            Kelas_Matakuliah klsMobile = r.createObject(Kelas_Matakuliah.class, 0);
            klsMobile.setNamaKelas("23SI2");
            klsMobile.setMatakuliah(matMobile);

            Kelas_Matakuliah klsPBO = r.createObject(Kelas_Matakuliah.class, 1);
            klsPBO.setNamaKelas("23SI3");
            klsPBO.setMatakuliah(matPBO);

            // --- KRS ---
            KRS krs1 = r.createObject(KRS.class, 0);
            krs1.setMahasiswa(mhs1);
            krs1.setSemester(5);

            // --- Detail KRS ---
            KRS_detail d1 = r.createObject(KRS_detail.class, 0);
            d1.setKrs(krs1);
            d1.setKelasMatkul(klsMobile);

            KRS_detail d2 = r.createObject(KRS_detail.class, 1);
            d2.setKrs(krs1);
            d2.setKelasMatkul(klsPBO);
        });

        Toast.makeText(this, "Data awal berhasil diisi", Toast.LENGTH_SHORT).show();
    }

    public void toProfil() {
        Intent intent = new Intent(this, ProfilActivity.class);
        intent.putExtra("nama", edtNama.getText().toString());
        startActivity(intent);
    }

    public void toDashboard() {
        Intent intent = new Intent(this, AddPasienActivity.class);
        startActivity(intent);
    }
}
