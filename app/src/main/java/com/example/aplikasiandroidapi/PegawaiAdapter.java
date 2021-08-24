package com.example.aplikasiandroidapi;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.aplikasiandroidapi.model.Category;
import com.example.aplikasiandroidapi.model.PegawaiModel;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PegawaiAdapter extends RecyclerView.Adapter<PegawaiAdapter.MyViewHolder> {
    private Context context;
//    private ArrayList<PegawaiModel> pegawai;
    private ArrayList<Category> category;
    private String url = "http://192.168.1.101/dts-ci-restserver-master/api/pegawai/";

    public PegawaiAdapter(Context context, ArrayList<Category> category) {
        this.context = context;
        this.category = category;
    }


    @NonNull
    @Override
    public PegawaiAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;

        LayoutInflater layoutInflater = LayoutInflater.from(context);
        view = layoutInflater.inflate(R.layout.category_list, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PegawaiAdapter.MyViewHolder holder, int position) {
        holder.nama.setText(category.get(position).getNama());
        holder.alamat.setText(category.get(position).getAlamat());
        holder.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id = category.get(position).getId();
                String nama = category.get(position).getNama();
                String alamat = category.get(position).getAlamat();
                editPegawai(id, nama, alamat);
            }
        });
        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id = category.get(position).getId();
                deletePegawai(id);
            }
        });
    }

    private void deletePegawai(final int id) {
        TextView close, judul;
        final EditText txtAddNama, txtAddAlamat;
        Button submit;
        final Dialog dialog;

        dialog = new Dialog(context);

        dialog.setContentView(R.layout.delete_cat);

        close = (TextView) dialog.findViewById(R.id.txtClose);
        judul = (TextView) dialog.findViewById(R.id.judul);
        judul.setText("Hapus Pegawai");
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        submit = (Button) dialog.findViewById(R.id.submit);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Submit("DELETE", "", "", dialog, id);
            }
        });
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
    }

    private void editPegawai(final int id, String nama, String alamat) {
        TextView close, judul;
        final EditText txtAddNama, txtAddAlamat;
        Button submit;
        final Dialog dialog;

        dialog = new Dialog(context);

        dialog.setContentView(R.layout.activity_modcat);

        close = (TextView) dialog.findViewById(R.id.txtClose);
        judul = (TextView) dialog.findViewById(R.id.judul);
        judul.setText("Edit Pegawai");
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        txtAddNama = (EditText) dialog.findViewById(R.id.txtAddNama);
        txtAddAlamat = (EditText) dialog.findViewById(R.id.txtAddAlamat);
        submit = (Button) dialog.findViewById(R.id.submit);

        txtAddNama.setText(nama);
        txtAddAlamat.setText(alamat);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String dataNama = txtAddNama.getText().toString();
                String dataAlamat = txtAddAlamat.getText().toString();
                Submit("PUT", dataNama, dataAlamat, dialog, id);
            }
        });
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
    }

    private void Submit(String method, final String dataNama, final String dataAlamat, final Dialog dialog, final int id) {
        if(method == "PUT") {
            StringRequest request = new StringRequest(Request.Method.PUT, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    dialog.dismiss();
                    Toast.makeText(context, "Data Berhasil diubah", Toast.LENGTH_LONG).show();
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(context, "Data Gagal diubah", Toast.LENGTH_LONG).show();
                }
            }) {
                @Nullable
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<>();
                    params.put("nama", dataNama);
                    params.put("alamat", dataAlamat);
                    params.put("id", String.valueOf(id));
                    return params;
                }
            };
            Volley.newRequestQueue(context).add(request);
        } else if (method == "DELETE") {
            Log.d("Id", url + id);

            StringRequest request = new StringRequest(Request.Method.DELETE, url + id, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    dialog.dismiss();
                    Toast.makeText(context, "Data Berhasil dihapus", Toast.LENGTH_LONG).show();
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(context, "Data Gagal dihapus", Toast.LENGTH_LONG).show();
                }
            });
            Volley.newRequestQueue(context).add(request);
        }
    }

    @Override
    public int getItemCount() {
        return category.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView nama, alamat;
        private ImageView btnEdit, btnDelete;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            nama = (TextView) itemView.findViewById(R.id.nama);
            alamat = (TextView) itemView.findViewById(R.id.alamat);
            btnEdit = (ImageView) itemView.findViewById(R.id.btnEdit);
            btnDelete = (ImageView) itemView.findViewById(R.id.btnDelete);
        }
    }
}
