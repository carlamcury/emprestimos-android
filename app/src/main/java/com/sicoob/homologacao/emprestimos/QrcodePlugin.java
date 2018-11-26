package com.sicoob.homologacao.emprestimos;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import com.google.zxing.integration.android.IntentIntegrator;

/**
 * Created by leonardoSilveira on 24/08/18.
 */
public class QrcodePlugin {
    private static IntentIntegrator integrator;
    private static String qrcodeString ;

    static final Object  waits = new Object();


    public static void permission( Activity act ) {
        if (ContextCompat.checkSelfPermission(act, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(act, new String[]{Manifest.permission.CAMERA}, 0);
        }

    }

    public static void openActivity( Activity act ) {
        integrator = new IntentIntegrator(act);
//            integrator.setCaptureLayout(R.layout.custom_layout);
        integrator.addExtra("PROMPT_MESSAGE","Crimatex qrcode ");
        integrator.setOrientationLocked(false);
        integrator.setBeepEnabled(true);
        integrator.setCaptureActivity(AnyOrientationCaptureActivity.class);

        integrator.initiateScan(IntentIntegrator.QR_CODE_TYPES);
    }

    public static String getResult() {
        try {
            synchronized ( waits ) {
                waits.wait();
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return QrcodePlugin.qrcodeString;
    }

    public static void setResult ( String value ) {
        try {
            synchronized ( waits ){
                QrcodePlugin.qrcodeString = value;
                waits.notify();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
