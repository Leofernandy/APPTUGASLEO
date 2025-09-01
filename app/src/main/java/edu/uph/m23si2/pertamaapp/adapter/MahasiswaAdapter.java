package edu.uph.m23si2.pertamaapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import edu.uph.m23si2.pertamaapp.ProfilActivity;
import edu.uph.m23si2.pertamaapp.R;
import edu.uph.m23si2.pertamaapp.model.Mahasiswa;
import io.realm.Realm;

public class MahasiswaAdapter extends ArrayAdapter<Mahasiswa> {

    public MahasiswaAdapter(@NonNull Context context, int resource, @NonNull List<Mahasiswa> objects) {
        super(context, resource, objects);
    }
    public MahasiswaAdapter(@NonNull Context context, ArrayList<Mahasiswa> arrayList) {
        super(context, 0, arrayList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View currentItemView = convertView;
        if (currentItemView == null) {
            currentItemView = LayoutInflater.from(getContext()).inflate(R.layout.layout_mahasiswa, parent,
                    false);
        }
        Mahasiswa mhs = getItem(position);
        assert mhs != null;

        ImageView imvMahasiswa = currentItemView.findViewById(R.id.imvMahasiswa);
        if(mhs.getJenisKelamin().equals("Pria")){
            imvMahasiswa.setImageResource(R.drawable.user4);
        }else{
            imvMahasiswa.setImageResource(R.drawable.user1);
        }

        TextView txvNama = currentItemView.findViewById(R.id.txvNama);
        TextView txvProdi = currentItemView.findViewById(R.id.txvProdi);
        txvNama.setText(mhs.getNama());
        txvProdi.setText(mhs.getProdi());
        currentItemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toProfil(mhs.getStudentID());
            }
        });

        ImageView imvDelete =currentItemView.findViewById(R.id.imvDelete);
        imvDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Realm realm = Realm.getDefaultInstance();
                realm.executeTransaction(r-> {
                    Mahasiswa mhs = r.where(Mahasiswa.class)
                            .equalTo("studentID",
                                    getItem(position).getStudentID()).findFirst();
                    if(mhs!=null){
                        mhs.deleteFromRealm();
                    }
                });
            }
        });
        // then return the recyclable view
        return currentItemView;
    }
    public void toProfil(@Nullable int studentID){
        Intent intent = new Intent(getContext(), ProfilActivity.class);
        intent.putExtra("mode","edit");
        intent.putExtra("studentID",studentID);
        getContext().startActivity(intent);
    }
}
