package com.example.projectprm392_booking_accomodation.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.projectprm392_booking_accomodation.Model.RoomImage;
import com.example.projectprm392_booking_accomodation.R;

import java.util.List;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ImageViewHolder> {
    private Context context;
    private List<RoomImage> roomImageList;

    public ImageAdapter(Context context,List<RoomImage> roomImageList) {
        this.context = context;
        this.roomImageList = roomImageList;
    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.image_item, parent, false);
        return new ImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {
        RoomImage roomImage = roomImageList.get(position);
        Glide.with(context)
                .load(roomImage.getLink())
                .placeholder(R.drawable.baseline_cloud_download) // Ảnh chờ trong khi tải
                .error(R.drawable.baseline_running_with_errors) // Ảnh lỗi nếu không tải được
                .into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return roomImageList.size();
    }

    public static class ImageViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;

        public ImageViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
        }
    }
}
