package com.example.parking.ui.vehicle.recycler_view;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.parking.R;
import com.example.parking.ui.vehicle.VehicleAddViewModel;
import com.example.parking.ui.vehicle.VehicleViewModel;
import com.example.parking.utility.Car;

public class Adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    // TODO: logic for new design
    private static Activity mCurrentActivity;
    private final Car[] mCars;

    public Adapter(Activity currentActivity, Car[] cars){
        mCurrentActivity = currentActivity;
        mCars = cars;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        public int id;
        public TextView carID;
        public TextView parkingPlace;

        public ViewHolder(@NonNull ConstraintLayout itemView) {
            super(itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(((RecyclerView)view.getParent()).isClickable()) {
                        VehicleViewModel.carID = id;
                        Navigation.findNavController(mCurrentActivity, R.id.nav_host_fragment).
                                navigate(R.id.action_nav_vehicle_to_nav_vehicle_data);
                    }
                }
            });
            carID = (TextView) itemView.getChildAt(0);
            parkingPlace = (TextView) itemView.getChildAt(1);
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
                        VehicleAddViewModel.isFromVehicleTab = true;
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
                    .inflate(R.layout.fragment_vehicle_add_button, parent, false));
        return new ViewHolder((ConstraintLayout) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_vehicle_row, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        switch (holder.getItemViewType()){
            case 0:
                ViewHolder carHolder = (ViewHolder)holder;
                Car cCar = mCars[position];
                carHolder.id = cCar.id;
                carHolder.carID.setText(cCar.plates);
                carHolder.parkingPlace.setText(cCar.parkingLotName != null? carHolder.parkingPlace.getText().toString() +
                        ": " + cCar.parkingLotName : "");
                break;
            case 1:
                ButtonViewHolder btnHolder = (ButtonViewHolder)holder;
                break;
        }
    }

    @Override
    public int getItemCount() {return mCars.length + 1;}


}
