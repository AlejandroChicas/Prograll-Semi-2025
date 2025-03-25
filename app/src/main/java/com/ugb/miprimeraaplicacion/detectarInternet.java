package com.ugb.miprimeraaplicacion;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;

import java.net.NetworkInterface;

public class detectarInternet {
    public Context context;
    public detectarInternet(Context context) {
        this.context = context;
    }
    public boolean hayConexionInternet() {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager==null) return false;

        NetworkInfo[] info = connectivityManager.getAllNetworkInfo();
        if (info== null) {
            for (int i = 0; i < info.length; i++) {
                if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                    return true;
                }
            }
        }
        return false;
    }
}
