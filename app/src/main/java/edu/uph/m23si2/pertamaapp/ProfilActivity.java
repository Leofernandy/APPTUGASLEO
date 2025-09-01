package edu.uph.m23si2.pertamaapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import edu.uph.m23si2.pertamaapp.model.Mahasiswa;
import io.realm.Realm;

public class ProfilActivity extends AppCompatActivity {
    Button btnSubmit,btnBersihkan;
    EditText edtNama, edtEmail;
    TextView txvHasil;
    Spinner sprProdi;
    RadioButton rdbPria, rdbWanita;
    RadioGroup rdgJenisKelamin;
    CheckBox ckbMakan,ckbTidur, ckbBelajar;
    String mode;
    int studentID;
    Realm realm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_profil);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        init();
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                simpan();
            }
        });
        btnBersihkan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mode.equals("edit")){ deleteData();}
            }
        });
    }
    public  void deleteData(){
        realm.executeTransaction(r-> {
            Mahasiswa mhs = r.where(Mahasiswa.class)
                    .equalTo("studentID",studentID).findFirst();
            if(mhs!=null){
                mhs.deleteFromRealm();
            }
        });
        toListMahasiswa();
    }
    public void init(){
        btnSubmit = findViewById(R.id.btnSubmit);
        btnBersihkan = findViewById(R.id.btnBersihkan);
        edtNama = findViewById(R.id.edtNama);
        edtEmail = findViewById(R.id.edtEmail);
        txvHasil = findViewById(R.id.txvHasil);
        sprProdi = findViewById(R.id.sprProdi);
        rdgJenisKelamin = findViewById(R.id.rdgJenisKelamin);
        rdbPria = findViewById(R.id.rdbPria);
        rdbWanita = findViewById(R.id.rdbWanita);
        ckbTidur = findViewById(R.id.ckbTidur);
        ckbMakan = findViewById(R.id.ckbMakan);
        ckbBelajar = findViewById(R.id.ckbBelajar);

        // Create an ArrayAdapter using the string array and a default spinner layout.
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this,
                R.array.prodi_array,
                android.R.layout.simple_spinner_item
        );
        // Specify the layout to use when the list of choices appears.
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner.
        sprProdi.setAdapter(adapter);
        realm = Realm.getDefaultInstance();
        mode = getIntent().getStringExtra("mode");
        if(mode.equals("create")){
            btnSubmit.setText("Simpan Data");
            btnBersihkan.setText("Bersihkan Data");
        } else if (mode.equals("edit")) {
            btnSubmit.setText("Ubah Data");
            btnBersihkan.setText("Hapus Data");
            studentID = getIntent().getIntExtra("studentID",0);

            Mahasiswa mhs = realm.where(Mahasiswa.class)
                            .equalTo("studentID",studentID).findFirst();
            if (mhs != null) {
                edtNama.setText(mhs.getNama());
                edtEmail.setText(mhs.getEmail());
                if(mhs.getJenisKelamin().equals("Pria")) rdbPria.setChecked(true);
                else if(mhs.getJenisKelamin().equals("Wanita")) rdbWanita.setChecked(true);
                int posisi = adapter.getPosition(mhs.getProdi());
                sprProdi.setSelection(posisi);
                String[] hobies = mhs.getHobi().split(";");
                for (String hobi:hobies) {
                    if(hobi.equals("Belajar")) ckbBelajar.setChecked(true);
                    else if(hobi.equals("Makan")) ckbMakan.setChecked(true);
                    else if(hobi.equals("Tidur")) ckbTidur.setChecked(true);
                }
            }
        }

    }
    public boolean isValidated(){
        if(edtNama.getText().toString().equals("")){
            edtNama.setError("Nama wajib di isi");
            return false;
        }else if(edtEmail.getText().toString().equals("")) {
            edtEmail.setError("Email wajib di isi");
            return false;
        }
        return true;
    }
    public void simpan(){
        if(isValidated()){
            // Menampilkan hasil di txvHasil
            String nama = edtNama.getText().toString();
            String email = edtEmail.getText().toString();
            String prodi = sprProdi.getSelectedItem().toString();
            String jenisKelamin = "";
            String hobi="";
            if(rdbWanita.isChecked()) jenisKelamin= rdbWanita.getText().toString();
            else if(rdbPria.isChecked()) jenisKelamin= rdbPria.getText().toString();
            String jk=jenisKelamin;

            if(ckbBelajar.isChecked()) hobi += ckbBelajar.getText().toString()+"; ";
            if(ckbMakan.isChecked()) hobi += ckbMakan.getText().toString()+"; ";
            if(ckbTidur.isChecked()) hobi += ckbTidur.getText().toString()+"; ";
            String hobby = hobi;
            txvHasil.setText(nama + "("+jenisKelamin+")"+
                    "\nHobi "+hobi
                    + "\n"+ email + "\nProgram Studi " + prodi + "\n" +
                    getNamaFakultas(prodi));

            //simpan ke realm
            if(mode.equals("create")) {
                realm.executeTransaction(r -> {
                    Number maxId = r.where(Mahasiswa.class).max("studentID");
                    int nextId = (maxId == null) ? 1 : maxId.intValue() + 1;
                    Mahasiswa mhs = r.createObject(Mahasiswa.class, nextId);
                    mhs.setNama(nama);
                    mhs.setEmail(email);
                    mhs.setProdi(prodi);
                    mhs.setJenisKelamin(jk);
                    mhs.setHobi(hobby);
                });
                Toast.makeText(this, "Data tersimpan", Toast.LENGTH_SHORT).show();
            }
            else if(mode.equals("edit")){
                realm.executeTransaction(r-> {
                    Mahasiswa mhs = r.where(Mahasiswa.class)
                            .equalTo("studentID",studentID).findFirst();
                    mhs.setNama(nama);
                    mhs.setEmail(email);
                    mhs.setProdi(prodi);
                    mhs.setJenisKelamin(jk);
                    mhs.setHobi(hobby);
                });
                toListMahasiswa();
            }
        }
    }

    String getNamaFakultas(String prodi){
        String namaProdi = prodi.toLowerCase();
        if(namaProdi.equals("sistem informasi") || namaProdi.equals("informatika")){
            return "Fakultas Teknologi Informasi";
        }
        else if(namaProdi.equals("hukum") || namaProdi.equals("law")){
            return "Fakultas Hukum";
        }
        else if(namaProdi.equals("akuntansi") || namaProdi.equals("manajemen")||
                namaProdi.equals("perhotelan")){
            return "Fakultas Ekonomi dan Bisnis";
        }
        else
            return "Fakultas Tidak di Temukan";
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_profil, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection.
        if(item.getItemId()==R.id.mSimpan){
            simpan();
            return true;
        }
        else{
            return super.onOptionsItemSelected(item);
        }
    }
    public void toListMahasiswa(){
        Intent intent = new Intent(this,ListMahasiswaActivity.class);
        startActivity(intent);
    }
}