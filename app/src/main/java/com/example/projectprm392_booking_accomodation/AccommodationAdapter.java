package com.example.projectprm392_booking_accomodation;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class AccommodationAdapter extends RecyclerView.Adapter<AccommodationAdapter.AccommodationViewHolder> {
    private List<Accommodation> accommodationList;
    private Context context;

    public AccommodationAdapter(Context context, List<Accommodation> accommodationList) {
        this.context = context;
        this.accommodationList = accommodationList;
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
        holder.imgBackground.setImageResource(accommodation.getImage());
        if (accommodation.isFavorite()) {
            holder.imgLike.setImageResource(R.drawable.baseline_thumb_up); // like thump
        } else {
            holder.imgLike.setImageResource(R.drawable.baseline_thumb_up_off_alt); // Outline heart
        }
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
