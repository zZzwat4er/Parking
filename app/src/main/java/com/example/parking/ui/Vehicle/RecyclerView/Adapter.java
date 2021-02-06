package com.example.parking.ui.Vehicle.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;

import com.example.parking.R;
import com.example.parking.utility.Car;

import java.util.ArrayList;
import java.util.List;

public class Adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private Context mCurrentActivity;
    private List<Car> cars;

    public Adapter(Context currentActivity, List<Car> cars){
        mCurrentActivity = currentActivity;
        this.cars = cars;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        public TextView carID;
        public TextView parkingPlace;

        public ViewHolder(@NonNull LinearLayout itemView) {
            super(itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                }
            });
            carID = (TextView) itemView.getChildAt(0);
            parkingPlace = (TextView) itemView.getChildAt(1);
        }

    }
    public static class ButtonViewHolder extends RecyclerView.ViewHolder{

        public ButtonViewHolder(@NonNull LinearLayout itemView){
            super(itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                }
            });
        }

    }

    @Override
    public int getItemViewType(int position) {
        if(position == getItemCount()) return 1;
        return 0;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == 1) return new ButtonViewHolder((LinearLayout) LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.fragment_vehicle_add_button, parent, false));
        return new ViewHolder((LinearLayout) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_vehicle_row, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        switch (holder.getItemViewType()){
            case 0:
                ViewHolder carHolder = (ViewHolder)holder;
                break;
            case 1:
                ButtonViewHolder btnHolder = (ButtonViewHolder)holder;
                break;
        }
    }

    @Override
    public int getItemCount() {return cars.size() + 1;}


}
