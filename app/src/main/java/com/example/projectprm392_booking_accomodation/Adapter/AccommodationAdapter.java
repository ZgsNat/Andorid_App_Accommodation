package com.example.projectprm392_booking_accomodation.Adapter;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.projectprm392_booking_accomodation.DetailedAccommodation;
import com.example.projectprm392_booking_accomodation.Interface.OnFavoriteClickListener;
import com.example.projectprm392_booking_accomodation.Model.Accommodation;
import com.example.projectprm392_booking_accomodation.R;

import java.util.List;

public class AccommodationAdapter extends RecyclerView.Adapter<AccommodationAdapter.AccommodationViewHolder> {
    private List<Accommodation> accommodationList;
    private Context context;
    private OnFavoriteClickListener onFavoriteClickListener;

    public AccommodationAdapter(Context context, List<Accommodation> accommodationList) {
        this.context = context;
        this.accommodationList = accommodationList;
    }

    public AccommodationAdapter(Context context, List<Accommodation> accommodationList, OnFavoriteClickListener listener) {
        this.context = context;
        this.accommodationList = accommodationList;
        this.onFavoriteClickListener = listener;
    }

    @NonNull
    @Override
    public AccommodationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.accommodation_item, parent, false);
        return new AccommodationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AccommodationViewHolder holder, int position) {
        Accommodation accommodation = accommodationList.get(position);
        holder.txtName.setText(accommodation.getTitle());
        holder.txtAvgStar.setText(String.valueOf(accommodation.getAvgStar()));
        holder.txtAddress.setText(accommodation.getAddress());
        Glide.with(context)
                .load(accommodation.getImage())
                .placeholder(R.drawable.baseline_cloud_download) // Ảnh chờ trong khi tải
                .error(R.drawable.baseline_running_with_errors) // Ảnh lỗi nếu không tải được
                .into(holder.imgBackground);

        if (accommodation.isFavorite()) {
            holder.imgLike.setImageResource(R.drawable.baseline_thumb_up); // like thump
        } else {
            holder.imgLike.setImageResource(R.drawable.baseline_thumb_up_off_alt); // Outline heart
        }
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, DetailedAccommodation.class);
            intent.putExtra("AccommodationId", accommodation.getAccommodationId());
            context.startActivity(intent);
        });
        holder.imgLike.setOnClickListener(v -> {
            boolean isFavorite = !accommodation.isFavorite(); // Toggle favorite status
            onFavoriteClickListener.onFavoriteClick(accommodation, isFavorite);
        });
    }

    @Override
    public int getItemCount() {
        return accommodationList.size();
    }

    public static class AccommodationViewHolder extends RecyclerView.ViewHolder {
        TextView txtName, txtAvgStar, txtAddress;
        ImageView imgBackground, imageViewStar, imageViewLocation, imgLike;

        public AccommodationViewHolder(@NonNull View itemView) {
            super(itemView);
            txtName = itemView.findViewById(R.id.txtName);
            txtAvgStar = itemView.findViewById(R.id.txtAvgStar);
            txtAddress = itemView.findViewById(R.id.txtAddress);
            imgBackground = itemView.findViewById(R.id.imgBackground);
            imageViewStar = itemView.findViewById(R.id.imageView);
            imageViewLocation = itemView.findViewById(R.id.imageView2);
            imgLike = itemView.findViewById(R.id.imgLike);
        }
    }
}
