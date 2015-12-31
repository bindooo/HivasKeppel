package com.mrmaster.hivaskeppel;

import android.content.Context;
import android.graphics.PixelFormat;
import android.os.Environment;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class hivasFogado extends PhoneStateListener {

    Context ctx;
    private LayoutInflater li;
    private View myview;
    private android.view.WindowManager.LayoutParams layoutParams;
    private WindowManager localWindowManager;


    private ImageView imageView_callimage;

    public hivasFogado(Context context) {

        ctx = context;
        li = (LayoutInflater) ctx.getSystemService(ctx.LAYOUT_INFLATER_SERVICE);

        layoutParams = new android.view.WindowManager.LayoutParams();
        layoutParams.flags = android.view.WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                | android.view.WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
                | android.view.WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN
                | android.view.WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL;
        layoutParams.format = PixelFormat.TRANSLUCENT;
        layoutParams.type = android.view.WindowManager.LayoutParams.TYPE_SYSTEM_OVERLAY;
        layoutParams.x = 0;
        layoutParams.y = 0;
        layoutParams.verticalWeight = 1.0F;
        layoutParams.horizontalWeight = 1.0F;
        layoutParams.verticalMargin = 0.0F;
        layoutParams.horizontalMargin = 0.0F;
        layoutParams.gravity = Gravity.CENTER | Gravity.TOP;

        myview = li.inflate(R.layout.csorog, null);

        imageView_callimage = (ImageView) myview.findViewById(R.id.callImageView);
        imageView_callimage.setImageResource(R.drawable.ok);

        localWindowManager = (WindowManager) ctx.getSystemService("window");
        localWindowManager.addView(myview, layoutParams);

    }

    @Override
    public void onCallStateChanged(int state, String incomingNumber) {
        super.onCallStateChanged(state, incomingNumber);

        switch (state) {
            case TelephonyManager.CALL_STATE_IDLE:

                imageView_callimage.setVisibility(View.GONE);

                break;
            case TelephonyManager.CALL_STATE_RINGING:

                String phoneNumber = incomingNumber;
                String result;
                if (phoneNumber != null && !phoneNumber.isEmpty())
                    phoneNumber = phoneNumber.substring(3);

                try {

                    File myFile = new File(Environment.getExternalStorageDirectory() + "/HivasKep/google.csv");
                    FileInputStream fIn = new FileInputStream(myFile);
                    BufferedReader br = new BufferedReader(
                            new InputStreamReader(fIn));

                    br.readLine();
                    String line;

                    while ((line = br.readLine()) != null) {

                        if (phoneNumber == null || phoneNumber.equals("-2") || phoneNumber.isEmpty()) {

                            imageView_callimage.setImageResource(R.drawable.dk);
                            imageView_callimage.setVisibility(View.VISIBLE);
                            break;

                        }

                        result = line.substring(line.lastIndexOf(",") + 1);
                        result = result.replace(" ", "");

                        if (result != null && !result.isEmpty()) {
                            if (result.substring(0, 1).equals("0")) result = result.substring(2);
                            if (result.substring(0, 1).equals("+")) result = result.substring(3);
                        }

                        if (phoneNumber.equals(result)) {

                            imageView_callimage.setImageResource(R.drawable.ok);
                            imageView_callimage.setVisibility(View.VISIBLE);
                            break;

                        } else {

                            imageView_callimage.setImageResource(R.drawable.nok);
                            imageView_callimage.setVisibility(View.VISIBLE);

                        }

                    }

                    br.close();
                    fIn.close();

                } catch (IOException e) {
                    e.printStackTrace();
                }

                break;

            case TelephonyManager.CALL_STATE_OFFHOOK:

                break;

        }
    }

}