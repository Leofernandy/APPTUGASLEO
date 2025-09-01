package edu.uph.m23si2.pertamaapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

import edu.uph.m23si2.pertamaapp.model.Mahasiswa;
import io.realm.Realm;

public class DashboardActivity extends AppCompatActivity {
    LinearLayout llyProfil,llyTodolist,llyMahasiswa;
    TextView txvMahasiswa,txvNama,txvProdi;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ActionBarDrawerToggle toggle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_dashboard);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        txvMahasiswa = findViewById(R.id.txvMahasiswa);
        txvNama = findViewById(R.id.txvNama);
        txvProdi= findViewById(R.id.txvProdi);
        Realm realm = Realm.getDefaultInstance();
        Mahasiswa mhs = realm.where(Mahasiswa.class).findFirst();
        if (mhs != null) {
            txvNama.setText(mhs.getNama());
            txvProdi.setText(mhs.getEmail()+"\nProgram Studi "+mhs.getProdi());
            txvMahasiswa.setText(mhs.toString());
        }
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
//        Toolbar toolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);


//        toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,
//                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
//        drawerLayout.addDrawerListener(toggle);
//        toggle.syncState();


        llyProfil= findViewById(R.id.llyProfil);
        llyTodolist=findViewById(R.id.llyTodolist);
        llyMahasiswa=findViewById(R.id.llyMahasiswa);
        llyProfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toProfil();
            }
        });
        llyTodolist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toTodolist();
            }
        });
        llyMahasiswa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toListMahasiswa();
            }
        });

    }
    public void toListMahasiswa(){
        Intent intent = new Intent(this,ListMahasiswaActivity.class);
        startActivity(intent);
    }
    public void toProfil(){
        Intent intent = new Intent(this,ProfilActivity.class);
        intent.putExtra("mode","create");
        startActivity(intent);
    }

    public void toTodolist(){
        Intent intent = new Intent(this,TodoListActivity.class);
        startActivity(intent);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish(); // Menutup aktivitas saat tombol "Back" ditekan
    }

}