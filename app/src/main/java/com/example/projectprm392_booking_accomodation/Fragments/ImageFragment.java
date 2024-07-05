package com.example.projectprm392_booking_accomodation.Fragments;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.projectprm392_booking_accomodation.Adapter.ImageAdapter;
import com.example.projectprm392_booking_accomodation.ApiClient;
import com.example.projectprm392_booking_accomodation.Model.RoomImage;
import com.example.projectprm392_booking_accomodation.R;


import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ImageFragment extends Fragment {
    private static final String TAG = "ImageFragment";
    private static final String ARG_ROOM_ID = "roomId";

    private RecyclerView recyclerView;
    private ImageAdapter imageAdapter;
    private ImageButton btnClose, btnPrevious, btnNext;
    private LinearLayoutManager layoutManager;
    private List<RoomImage> roomImages;
    private int roomId;

    public static ImageFragment newInstance(int roomId) {
        ImageFragment fragment = new ImageFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_ROOM_ID, roomId);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_image, container, false);

        recyclerView = view.findViewById(R.id.recyclerViewImages);
        btnClose = view.findViewById(R.id.btnClose);
        btnPrevious = view.findViewById(R.id.btnPrevious);
        btnNext = view.findViewById(R.id.btnNext);

        layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);

        // Initialize empty list
        roomImages = new ArrayList<>();
        imageAdapter = new ImageAdapter(getContext(),roomImages);
        recyclerView.setAdapter(imageAdapter);

        // Get roomId from arguments
        if (getArguments() != null) {
            roomId = getArguments().getInt(ARG_ROOM_ID);
            fetchRoomImages(roomId);
        }

        // Đóng fragment
        btnClose.setOnClickListener(v -> getParentFragmentManager().beginTransaction().remove(ImageFragment.this).commit());

        // Chuyển đến ảnh trước
        btnPrevious.setOnClickListener(v -> {
            int currentPosition = layoutManager.findFirstVisibleItemPosition();
            if (currentPosition > 0) {
                recyclerView.smoothScrollToPosition(currentPosition - 1);
            }
        });

        // Chuyển đến ảnh tiếp theo
        btnNext.setOnClickListener(v -> {
            int currentPosition = layoutManager.findFirstVisibleItemPosition();
            if (currentPosition < roomImages.size() - 1) {
                recyclerView.smoothScrollToPosition(currentPosition + 1);
            }
        });

        return view;
    }

    private void fetchRoomImages(int roomId) {
        Call<List<RoomImage>> call = ApiClient.getRoomApiEnpoint().GetListRoomImage(roomId);
        call.enqueue(new Callback<List<RoomImage>>() {
            @Override
            public void onResponse(Call<List<RoomImage>> call, Response<List<RoomImage>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    roomImages.clear();
                    roomImages.addAll(response.body());
                    if (isAdded() && getView() != null) { // Ensure Fragment is attached and view is available
                        getView().post(() -> {
                            imageAdapter.notifyDataSetChanged();
                            Toast.makeText(getContext(), "SuccessImage", Toast.LENGTH_SHORT).show(); // Use getContext()
                        });
                    }
                } else {
                    Log.e(TAG, "Response failed: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<List<RoomImage>> call, Throwable t) {
                Log.e(TAG, "API call failed: ", t);
            }
        });
    }

}
