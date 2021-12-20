package com.example.parking.utility;

import androidx.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

public class ServerError {
    public @SerializedName("err_num") Integer code;
    public @SerializedName("err_message") String msg;
    public @SerializedName("err_sub_message") @Nullable String subMsg;

}
