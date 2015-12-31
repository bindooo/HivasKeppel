package com.mrmaster.hivaskeppel;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;

public class MyService extends Service {

    hivasFogado phoneCallListener;
    TelephonyManager tManager;

    @Override
    public void onCreate() {

        phoneCallListener = new hivasFogado(this);
        tManager = (TelephonyManager) this.getSystemService(this.TELEPHONY_SERVICE);

    }

    @Override
    public IBinder onBind(Intent intent) {

        return null;

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        tManager.listen(phoneCallListener, PhoneStateListener.LISTEN_CALL_STATE);
        return START_STICKY;

    }

    @Override
    public void onDestroy() {

        tManager.listen(phoneCallListener, PhoneStateListener.LISTEN_NONE);
    }

}