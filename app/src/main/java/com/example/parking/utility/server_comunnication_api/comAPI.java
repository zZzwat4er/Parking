package com.example.parking.utility.server_comunnication_api;

import android.content.Context;
import android.content.pm.PackageInfo;

import androidx.annotation.Nullable;

import com.google.gson.Gson;

import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

public class comAPI {

    private interface urls{
        String login = "http://31.210.210.172/Parking/login.php";
        String register = "http://31.210.210.172/Parking/registration.php";
        String addCar = "http://31.210.210.172/Parking/addCar.php";
        String deleteCar = "http://31.210.210.172/Parking/deleteCar.php";
        String payment = "http://31.210.210.172/Parking/payment.php";
        String updateCards = "http://31.210.210.172/Parking/updateCards.php";
        String updateFIO = "http://31.210.210.172/Parking/updateFIO.php";
    }

    private static void sendReq(String murl, String params, HttpRequest.Listener listener){
        try{
            URL url = new URL(murl);
            HttpRequest req = new HttpRequest(url, params, listener);
            req.start();
        }catch (MalformedURLException e){}

    }

    public static void login(String email, String passHash, Context context, HttpRequest.Listener listener){
        try {
            PackageInfo pInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);

            String params = JSON_TITTLES.app_ver + "=" + URLEncoder.encode(pInfo.versionName, "UTF-8") + "&" +
                    JSON_TITTLES.email + "=" + URLEncoder.encode(email,"UTF-8") + "&" +
                    JSON_TITTLES.pass_hash + "=" + URLEncoder.encode(passHash,"UTF-8");


            sendReq(urls.login, params, listener);
        }catch (Exception e){}
    }
    public static void register(String email, String passHash, String phone, String firstName,
                                String lastName, @Nullable String secondName, Context context,
                                HttpRequest.Listener listener){
        try {
            PackageInfo pInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);

            String params = JSON_TITTLES.app_ver + "=" + URLEncoder.encode(pInfo.versionName, "UTF-8") + "&" +
                    JSON_TITTLES.email + "=" + URLEncoder.encode(email,"UTF-8") + "&" +
                    JSON_TITTLES.pass_hash + "=" + URLEncoder.encode(passHash,"UTF-8") + "&" +
                    JSON_TITTLES.phone + "=" + URLEncoder.encode(phone,"UTF-8") + "&" +
                    JSON_TITTLES.first_name + "=" + URLEncoder.encode(firstName,"UTF-8") + "&" +
                    JSON_TITTLES.last_name + "=" + URLEncoder.encode(lastName,"UTF-8") + "&" +
                    (secondName != null? JSON_TITTLES.second_name + "=" + URLEncoder.encode(secondName,"UTF-8"):"");
            sendReq(urls.register, params, listener);

        }catch (Exception e){}
    }

}
