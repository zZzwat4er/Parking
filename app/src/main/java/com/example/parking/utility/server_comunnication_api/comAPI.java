package com.example.parking.utility.server_comunnication_api;

import android.content.Context;
import android.content.pm.PackageInfo;

import androidx.annotation.Nullable;

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
        String getAvailableParkingLots = "http://31.210.210.172/Parking/getAvailableParkingLots.php";
        String updateAutoPay = "http://31.210.210.172/Parking/updateAutoPay.php";
        String updateParkingLot = "http://31.210.210.172/Parking/updateParkingLot.php";
        String updatePlates = "http://31.210.210.172/Parking/updatePlates.php";
        String updateTariff = "http://31.210.210.172/Parking/updateTariff.php";
    }

    private static PackageInfo pInfo;

    private static @Nullable OnThreadExit mCallBack;

    public static void setCallBack(@Nullable OnThreadExit callback){
        mCallBack = callback;
    }

    public interface OnThreadExit{
        void exit();
    }

    private static void sendReq(String murl, String params, HttpRequest.Listener listener){
        try{
            URL url = new URL(murl);
            HttpRequest req = new HttpRequest(url, params, listener);
            req.start();
            if(mCallBack != null){
                req.join();
                mCallBack.exit();
                mCallBack = null;
            }
        }catch (MalformedURLException | InterruptedException e){}

    }

    public static void login(String email, String passHash, Context context, HttpRequest.Listener listener){
        try {
            if(pInfo == null)
                pInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);

            String params = JSON_TITLES.app_ver + "=" + URLEncoder.encode(pInfo.versionName, "UTF-8") + "&" +
                    JSON_TITLES.email + "=" + URLEncoder.encode(email,"UTF-8") + "&" +
                    JSON_TITLES.pass_hash + "=" + URLEncoder.encode(passHash,"UTF-8");


            sendReq(urls.login, params, listener);
        }catch (Exception e){}
    }
    public static void register(String email, String passHash, String phone, String firstName,
                                String lastName, @Nullable String secondName, Context context,
                                HttpRequest.Listener listener){
        try {
            if(pInfo == null)
                pInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);

            String params = JSON_TITLES.app_ver + "=" + URLEncoder.encode(pInfo.versionName, "UTF-8") + "&" +
                    JSON_TITLES.email + "=" + URLEncoder.encode(email,"UTF-8") + "&" +
                    JSON_TITLES.pass_hash + "=" + URLEncoder.encode(passHash,"UTF-8") + "&" +
                    JSON_TITLES.phone + "=" + URLEncoder.encode(phone,"UTF-8") + "&" +
                    JSON_TITLES.first_name + "=" + URLEncoder.encode(firstName,"UTF-8") + "&" +
                    JSON_TITLES.last_name + "=" + URLEncoder.encode(lastName,"UTF-8") +
                    (secondName != null? "&" + JSON_TITLES.second_name + "=" + URLEncoder.encode(secondName,"UTF-8"):"");

            sendReq(urls.register, params, listener);

        }catch (Exception e){}
    }

    public static void updateFIO(String email, String passHash, String firstName, String lastName,
                                 @Nullable String secondName, Context context,
                                 HttpRequest.Listener listener){
        try {
            if (pInfo == null)
                pInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);

            String params = JSON_TITLES.app_ver + "=" + URLEncoder.encode(pInfo.versionName, "UTF-8") + "&" +
                    JSON_TITLES.email + "=" + URLEncoder.encode(email,"UTF-8") + "&" +
                    JSON_TITLES.pass_hash + "=" + URLEncoder.encode(passHash,"UTF-8") + "&" +
                    JSON_TITLES.first_name + "=" + URLEncoder.encode(firstName,"UTF-8") + "&" +
                    JSON_TITLES.last_name + "=" + URLEncoder.encode(lastName,"UTF-8") +
                    (secondName != null? "&" + JSON_TITLES.second_name + "=" + URLEncoder.encode(secondName,"UTF-8"):"");

            sendReq(urls.updateFIO, params, listener);
        }catch (Exception e){}
    }

    public static void addCar(String email, String passHash, String plates, Integer mainCard,
                              /*Integer additionalCard,*/ Context context,
                              HttpRequest.Listener listener){
        try {
            if (pInfo == null)
                pInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);

            String params = JSON_TITLES.app_ver + "=" + URLEncoder.encode(pInfo.versionName, "UTF-8") + "&" +
                    JSON_TITLES.email + "=" + URLEncoder.encode(email,"UTF-8") + "&" +
                    JSON_TITLES.pass_hash + "=" + URLEncoder.encode(passHash,"UTF-8") + "&" +
                    JSON_TITLES.plates + "=" + URLEncoder.encode(plates,"UTF-8") + "&" +
                    JSON_TITLES.main_card + "=" + URLEncoder.encode(mainCard.toString(),"UTF-8") /*+ "&" +
                    JSON_TITLES.additional_card + "=" + URLEncoder.encode(additionalCard.toString(),"UTF-8")*/;

            sendReq(urls.addCar, params, listener);
        }catch (Exception e){}
    }

    public static void deleteCar(String email, String passHash, Integer carID, Context context,
                                 HttpRequest.Listener listener){
        try {
            if (pInfo == null)
                pInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);

            String params = JSON_TITLES.app_ver + "=" + URLEncoder.encode(pInfo.versionName, "UTF-8") + "&" +
                    JSON_TITLES.email + "=" + URLEncoder.encode(email,"UTF-8") + "&" +
                    JSON_TITLES.pass_hash + "=" + URLEncoder.encode(passHash,"UTF-8") + "&" +
                    JSON_TITLES.carID + "=" + URLEncoder.encode(carID.toString(),"UTF-8");

            sendReq(urls.deleteCar, params, listener);
        }catch (Exception e){}
    }

    public static void updateCards(String email, String passHash, Integer carID, Integer mainCard1,
                                   @Nullable Integer mainCard2, Integer additionalCard1,
                                   @Nullable Integer additionalCard2, @Nullable Integer additionalCard3,
                                   @Nullable Integer additionalCard4, @Nullable Integer additionalCard5,
                                   Context context, HttpRequest.Listener listener){
        try {
            if (pInfo == null)
                pInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);

            String params = JSON_TITLES.app_ver + "=" + URLEncoder.encode(pInfo.versionName, "UTF-8") + "&" +
                    JSON_TITLES.email + "=" + URLEncoder.encode(email,"UTF-8") + "&" +
                    JSON_TITLES.pass_hash + "=" + URLEncoder.encode(passHash,"UTF-8") + "&" +
                    JSON_TITLES.carID + "=" + URLEncoder.encode(carID.toString(),"UTF-8") + "&" +
                    JSON_TITLES.main_card_1 + "=" + URLEncoder.encode(mainCard1.toString(),"UTF-8") + "&" +
                    JSON_TITLES.additional_card_1 + "=" + URLEncoder.encode(additionalCard1.toString(),"UTF-8") +
                    (mainCard2 != null? "&" + JSON_TITLES.main_card_2 + "=" + URLEncoder.encode(mainCard2.toString(),"UTF-8"):"") +
                    (additionalCard2 != null? "&" + JSON_TITLES.additional_card_2 + "=" + URLEncoder.encode(additionalCard2.toString(),"UTF-8"):"") +
                    (additionalCard3 != null? "&" + JSON_TITLES.additional_card_3 + "=" + URLEncoder.encode(additionalCard3.toString(),"UTF-8"):"") +
                    (additionalCard4 != null? "&" + JSON_TITLES.additional_card_4 + "=" + URLEncoder.encode(additionalCard4.toString(),"UTF-8"):"") +
                    (additionalCard5 != null? "&" + JSON_TITLES.additional_card_5 + "=" + URLEncoder.encode(additionalCard5.toString(),"UTF-8"):"");
            sendReq(urls.updateCards, params, listener);
        }catch (Exception e){}
    }

    public static void payment(String email, String passHash, Integer paymentAmount, Context context,
                                 HttpRequest.Listener listener){
        if(paymentAmount > 0)
        try {
            if (pInfo == null)
                pInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);

            String params = JSON_TITLES.app_ver + "=" + URLEncoder.encode(pInfo.versionName, "UTF-8") + "&" +
                    JSON_TITLES.email + "=" + URLEncoder.encode(email,"UTF-8") + "&" +
                    JSON_TITLES.pass_hash + "=" + URLEncoder.encode(passHash,"UTF-8") + "&" +
                    JSON_TITLES.payment_amount + "=" + URLEncoder.encode(paymentAmount.toString(),"UTF-8");

            sendReq(urls.payment, params, listener);
        }catch (Exception e){}
    }

    public static void getAvailableParkingLots(Context context, HttpRequest.Listener listener){
        try{
            if(pInfo == null)
                pInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            String params = JSON_TITLES.app_ver + "=" + URLEncoder.encode(pInfo.versionName, "UTF-8");
            sendReq(urls.getAvailableParkingLots, params, listener);
        }catch(Exception e){}
    }

    public static void updateAutoPay(String email, String passHash, Integer carID, Integer autopay,
                                     Context context, HttpRequest.Listener listener) {
        try {
            if (pInfo == null)
                pInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            String params = JSON_TITLES.app_ver + "=" + URLEncoder.encode(pInfo.versionName, "UTF-8") + "&" +
                    JSON_TITLES.email + "=" + URLEncoder.encode(email,"UTF-8") + "&" +
                    JSON_TITLES.pass_hash + "=" + URLEncoder.encode(passHash,"UTF-8") + "&" +
                    JSON_TITLES.carID + "=" + URLEncoder.encode(carID.toString(),"UTF-8") + "&" +
                    JSON_TITLES.autoPay + "=" + URLEncoder.encode(autopay.toString(),"UTF-8");
            sendReq(urls.updateAutoPay, params, listener);
        }catch(Exception e){}
    }

    public static void updateParkingLot(String email, String passHash, Integer carID, String newLot,
                                        Context context, HttpRequest.Listener listener){
        try {
            if (pInfo == null)
                pInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            String params = JSON_TITLES.app_ver + "=" + URLEncoder.encode(pInfo.versionName, "UTF-8") + "&" +
                    JSON_TITLES.email + "=" + URLEncoder.encode(email,"UTF-8") + "&" +
                    JSON_TITLES.pass_hash + "=" + URLEncoder.encode(passHash,"UTF-8") + "&" +
                    JSON_TITLES.carID + "=" + URLEncoder.encode(carID.toString(),"UTF-8") + "&" +
                    JSON_TITLES.newLot + "=" + URLEncoder.encode(newLot,"UTF-8");
            sendReq(urls.updateParkingLot, params, listener);
        }catch(Exception e){}
    }

    public static void updatePlates(String email, String passHash, Integer carID, String plates,
                                    Context context, HttpRequest.Listener listener){
        try {
            if (pInfo == null)
                pInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            String params = JSON_TITLES.app_ver + "=" + URLEncoder.encode(pInfo.versionName, "UTF-8") + "&" +
                    JSON_TITLES.email + "=" + URLEncoder.encode(email,"UTF-8") + "&" +
                    JSON_TITLES.pass_hash + "=" + URLEncoder.encode(passHash,"UTF-8") + "&" +
                    JSON_TITLES.carID + "=" + URLEncoder.encode(carID.toString(),"UTF-8") + "&" +
                    JSON_TITLES.newPlates + "=" + URLEncoder.encode(plates,"UTF-8");
            sendReq(urls.updatePlates, params, listener);
        }catch(Exception e){}
    }

    public static void updateTariff(String email, String passHash, Integer carID, String tariff,
                                    Context context, HttpRequest.Listener listener){
        try {
            if (pInfo == null)
                pInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            String params = JSON_TITLES.app_ver + "=" + URLEncoder.encode(pInfo.versionName, "UTF-8") + "&" +
                    JSON_TITLES.email + "=" + URLEncoder.encode(email,"UTF-8") + "&" +
                    JSON_TITLES.pass_hash + "=" + URLEncoder.encode(passHash,"UTF-8") + "&" +
                    JSON_TITLES.carID + "=" + URLEncoder.encode(carID.toString(),"UTF-8") + "&" +
                    JSON_TITLES.newTariff + "=" + URLEncoder.encode(tariff,"UTF-8");
            sendReq(urls.updateTariff, params, listener);
        }catch(Exception e){}
    }

}
