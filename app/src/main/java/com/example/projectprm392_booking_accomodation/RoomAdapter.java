package com.example.projectprm392_booking_accomodation;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RoomAdapter extends RecyclerView.Adapter<RoomAdapter.RoomViewHolder> {
    private List<Room> roomList;
    private Context context;

    public RoomAdapter(Context context, List<Room> roomList) {
        this.context = context;
        this.roomList = roomList;
    }

    @NonNull
    @Override
    public RoomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.detailed_room_item, parent, false);
        return new RoomViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull RoomViewHolder holder, int position) {
        Room room = roomList.get(position);
        holder.txtRoomName.setText("Tên phòng: " +room.getRoomName());
        holder.txtCapacity.setText("Sức chứa: " +  String.valueOf(room.getCapacity()));
        holder.txtPrice.setText(String.format("Giá phòng: %s VND", room.getPrice()));
        holder.txtDescription.setText("Mô tả: " + room.getDescription());

        // Load image (use Glide or other image loading library)
        // Example: Glide.with(context).load(room.getImageUrl()).into(holder.imageView);

        // Set room status color
        if (room.getStatus() == 1) {
            holder.txtStatus.setText("Còn phòng");
            holder.txtStatus.setTextColor(ContextCompat.getColor(context, android.R.color.holo_green_dark));
        } else {
            holder.txtStatus.setText("Hết phòng");
            holder.txtStatus.setTextColor(ContextCompat.getColor(context, android.R.color.holo_red_dark));
        }
    }
    @Override
    public int getItemCount() {
        return roomList.size();
    }
    public static class RoomViewHolder extends RecyclerView.ViewHolder {
        TextView txtRoomName, txtCapacity, txtPrice, txtDescription, txtStatus;

        public RoomViewHolder(@NonNull View itemView) {
            super(itemView);
            txtRoomName = itemView.findViewById(R.id.txtRoomName);
            txtCapacity = itemView.findViewById(R.id.txtCapacity);
            txtPrice = itemView.findViewById(R.id.txtPrice);
            txtDescription = itemView.findViewById(R.id.txtDescription);
            txtStatus = itemView.findViewById(R.id.txtStatus);
        }
    }
}
