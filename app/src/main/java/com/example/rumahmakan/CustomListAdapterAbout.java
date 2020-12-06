package com.example.rumahmakan;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CustomListAdapterAbout extends RecyclerView.Adapter<CustomListAdapterAbout.AnggotaViewHolder> {
    private ArrayList<About> dataList;

    public CustomListAdapterAbout(ArrayList<About> dataList) {
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public CustomListAdapterAbout.AnggotaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_about, parent, false);
        return new AnggotaViewHolder(view);
    }

    public void onBindViewHolder(AnggotaViewHolder holder, int position) {
        holder.txtNama.setText(dataList.get(position).getNama());
        holder.txtNim.setText(dataList.get(position).getNim());
        holder.srcGambar.setImageDrawable(dataList.get(position).getGambar());
    }

    @Override
    public int getItemCount() {
        return (dataList != null) ? dataList.size() : 0;
    }

    public class AnggotaViewHolder extends RecyclerView.ViewHolder{
        private TextView txtNama, txtNim;
        private ImageView srcGambar;


        public AnggotaViewHolder(View itemView) {
            super(itemView);
            txtNama = (TextView) itemView.findViewById(R.id.tv_nama);
            txtNim = (TextView) itemView.findViewById(R.id. tv_nim);
            srcGambar = (ImageView) itemView.findViewById(R.id.foto);

        }
    }

}
