package com.example.parking.ui.Map;

import android.graphics.ImageFormat;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.parking.R;
import com.example.parking.ui.Vehicle.VehicleAddViewModel;
import com.example.parking.utility.MapTouchListener;

public class MapFragment extends Fragment{

    private Button vehicleBtn;
    private Button addCarBtn;

    public View onCreateView(@NonNull LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_map, container, false);
        ImageView img = root.findViewById(R.id.imageView);
        ((AppCompatActivity)getActivity()).getSupportActionBar().hide();
        img.setOnTouchListener(new MapTouchListener());

        vehicleBtn = root.findViewById(R.id.map_vehicle_btn);
        addCarBtn = root.findViewById(R.id.map_add_car_btn);

        vehicleBtn.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_nav_map_to_nav_vehicle));
        addCarBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.action_nav_map_to_nav_vehicle_add);
                VehicleAddViewModel.isFromVehicleTab = false;
            }
        });

        return root;
    }

}
