package com.mrmaster.hivaskeppel;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Environment;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;


public class Inditas extends Activity {

    Button btn;
    int buttonStatus = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inditas);
        btn = (Button) findViewById(R.id.button_inditas);

    }

    public void inditasClicked(View view) {

        File mappa = new File(Environment.getExternalStorageDirectory() + "/HivasKep");

        if (!mappa.exists()) {

            mappa.mkdirs();

        }

        if (mappa.exists() && mappa.isDirectory()) {

            File file = new File(Environment.getExternalStorageDirectory() + "/HivasKep/google.csv");

            if (!file.exists()) {

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Inditas.this);

                alertDialogBuilder.setTitle("Figyelem!");

                alertDialogBuilder
                        .setMessage("Másold be a google.csv fájlt a HivasKep mappába a telefonon!")
                        .setNegativeButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                                dialog.cancel();

                            }
                        });

                AlertDialog alertDialog = alertDialogBuilder.create();

                alertDialog.show();

            } else {

                Intent i = new Intent(Inditas.this, MyService.class);

                if (buttonStatus == 1) {

                    buttonStatus = 0;

                    startService(i);
                    Toast.makeText(this, "Elindítva!", Toast.LENGTH_SHORT).show();
                    btn.setText("Leállítás");

                } else {

                    buttonStatus = 1;

                    stopService(i);
                    Toast.makeText(this, "Leállítva!", Toast.LENGTH_SHORT).show();
                    btn.setText("Indítás!");

                }

            }

        }

    }

}