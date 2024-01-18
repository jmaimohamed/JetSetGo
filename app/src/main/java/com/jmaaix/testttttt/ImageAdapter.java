package com.jmaaix.testttttt;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import com.jmaaix.testttttt.entities.Facture;

import java.util.ArrayList;
import java.util.List;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ImageViewHolder> {

    private List<Facture> factureList;
    private TextView idTextView;

    public void setFactureList(List<Facture> factureList) {
        this.factureList = factureList;
        notifyDataSetChanged();
    }

    @Override
    public ImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_image, parent, false);
        return new ImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ImageViewHolder holder, int position) {
        Facture facture = factureList.get(position);
        holder.bind(facture);
    }

    @Override
    public int getItemCount() {
        return factureList != null ? factureList.size() : 0;
    }

    public class ImageViewHolder extends RecyclerView.ViewHolder {

        private ImageView imageView;

        public ImageViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageViewS);
            idTextView = itemView.findViewById(R.id.idfacturetext);
        }

        public void bind(Facture facture) {
            // Display the image in the ImageView
            byte[] imageData = facture.getImage();
            Bitmap imageBitmap = BitmapFactory.decodeByteArray(imageData, 0, imageData.length);
            imageView.setImageBitmap(imageBitmap);
            idTextView.setText("Reçu: "+String.valueOf(facture.getFactureId()));
        }
    }
}




