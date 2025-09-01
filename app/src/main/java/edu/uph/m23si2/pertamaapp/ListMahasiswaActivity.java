package edu.uph.m23si2.pertamaapp;

import android.os.Bundle;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

import edu.uph.m23si2.pertamaapp.adapter.MahasiswaAdapter;
import edu.uph.m23si2.pertamaapp.model.Mahasiswa;
import io.realm.Realm;
import io.realm.RealmResults;

public class ListMahasiswaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_list_mahasiswa);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        Realm realm = Realm.getDefaultInstance();
        final ArrayList<Mahasiswa> arrayList = new ArrayList<>();

        RealmResults<Mahasiswa> results = realm.where(Mahasiswa.class).findAll();
        if (results != null) {
            arrayList.addAll(realm.copyFromRealm(results));
        }

        MahasiswaAdapter mhsAdapter = new MahasiswaAdapter(this, arrayList);
        ListView mhsListView = findViewById(R.id.lsvMahasiswa);
        mhsListView.setAdapter(mhsAdapter);

    }
}