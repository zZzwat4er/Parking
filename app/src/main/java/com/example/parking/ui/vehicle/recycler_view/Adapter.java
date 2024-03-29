package com.example.parking.ui.vehicle.recycler_view;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.parking.R;
import com.example.parking.ui.vehicle.VehicleAddVM;
import com.example.parking.ui.vehicle.VehicleVM;
import com.example.parking.utility.Car;

public class Adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    // TODO: logic for new design
    private static Activity mCurrentActivity;
    private final @Nullable Car[] mCars;

    public Adapter(Activity currentActivity, @Nullable Car[] cars){
        mCurrentActivity = currentActivity;
        mCars = cars;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        public int id;
        public TextView carID;
        public TextView parkingPlace;
        public TextView payed;

        public ViewHolder(@NonNull final ConstraintLayout itemView) {
            super(itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });
            carID = itemView.findViewById(R.id.car_id_text);
            parkingPlace = itemView.findViewById(R.id.parking_place);
            payed = itemView.findViewById(R.id.payed_text);
            carID.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(((RecyclerView)itemView.getParent()).isClickable()) {
                        VehicleVM.carID = id;
                        Navigation.findNavController(mCurrentActivity, R.id.nav_host_fragment).
                                navigate(R.id.action_nav_vehicle_to_nav_vehicle_data);
                    }
                }
            });
            parkingPlace.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(((RecyclerView)itemView.getParent()).isClickable()) {
                        VehicleVM.carID = id;
                        Navigation.findNavController(mCurrentActivity, R.id.nav_host_fragment).
                                navigate(R.id.action_nav_vehicle_to_nav_vehicle_data_tariff_place);
                    }
                }
            });
            payed.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(((RecyclerView)itemView.getParent()).isClickable()) {
                        VehicleVM.carID = id;
                        Navigation.findNavController(mCurrentActivity, R.id.nav_host_fragment).
                                navigate(R.id.action_nav_vehicle_to_nav_vehicle_data_tariff_payment);
                    }
                }
            });
        }

    }
    public static class ButtonViewHolder extends RecyclerView.ViewHolder{

        public ButtonViewHolder(@NonNull LinearLayout itemView){
            super(itemView);
            itemView.getChildAt(0).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(((RecyclerView)v.getParent().getParent()).isClickable()) {
                        Navigation.findNavController(mCurrentActivity, R.id.nav_host_fragment).navigate(
                                R.id.action_nav_vehicle_to_nav_vehicle_add);
                        VehicleAddVM.isFromVehicleTab = true;
                    }
                }
            });
        }

    }

    @Override
    public int getItemViewType(int position) {
        if(position == getItemCount() - 1) return 1;
        return 0;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == 1) return new ButtonViewHolder((LinearLayout) LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.f_vehicle_add_button, parent, false));
        return new ViewHolder((ConstraintLayout) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.f_vehicle_row, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        switch (holder.getItemViewType()){
            case 0:
                ViewHolder carHolder = (ViewHolder)holder;
                Car cCar = mCars[position];
                carHolder.id = cCar.id;
                carHolder.carID.setText(cCar.plates);
                carHolder.parkingPlace.setText(cCar.parkingLotName != null? mCurrentActivity.getResources().getString(R.string.parking_place) +
                        ": " + cCar.parkingLotName : mCurrentActivity.getResources().getString(R.string.non_place));
                break;
            case 1:
                ButtonViewHolder btnHolder = (ButtonViewHolder)holder;
                break;
        }
    }

    @Override
    public int getItemCount() {return (mCars != null)? mCars.length + 1 : 1;}


}
