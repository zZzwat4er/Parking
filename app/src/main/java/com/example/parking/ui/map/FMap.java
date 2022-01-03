package com.example.parking.ui.map;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.parking.R;
import com.example.parking.ui.vehicle.VehicleAddVM;
import com.example.parking.utility.AccountHolder;
import com.example.parking.utility.MapTouchListener;

import java.util.Objects;

public class FMap extends Fragment{

    public View onCreateView(@NonNull LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        // TODO: zoom btns logic
        View root = inflater.inflate(R.layout.f_map, container, false);
        final ImageView img = root.findViewById(R.id.imageView);
        Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).hide();
        final MapTouchListener listener = new MapTouchListener();
        img.setOnTouchListener(listener);




        Button balance = root.findViewById(R.id.map_balance_btn);
        Button vehicleBtn = root.findViewById(R.id.map_vehicle_btn);
        Button addCarBtn = root.findViewById(R.id.map_add_car_btn);
        Button zoomIn = root.findViewById(R.id.zoom_in);
        Button zoomOut = root.findViewById(R.id.zoom_out);

        zoomIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onTouch(img, MotionEvent.obtain(0, 0, MotionEvent.ACTION_DOWN, 0, 0, 0));
                listener.zoomIn();
                listener.onTouch(img, MotionEvent.obtain(0, 0, MotionEvent.ACTION_UP, 0, 0, 0));
            }
        });
        zoomOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onTouch(img, MotionEvent.obtain(0, 0, MotionEvent.ACTION_DOWN, 0, 0, 0));
                listener.zoomOut();
                listener.onTouch(img, MotionEvent.obtain(0, 0, MotionEvent.ACTION_UP, 0, 0, 0));
            }
        });



        balance.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_nav_map_to_nav_top_up_an_account));
        balance.setText(String.format("%s: %d ₽", getString(R.string.balance), AccountHolder.account.mBalance));
        vehicleBtn.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_nav_map_to_nav_vehicle));
        addCarBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.action_nav_map_to_nav_vehicle_add);
                VehicleAddVM.isFromVehicleTab = false;
            }
        });

        return root;
    }

}
