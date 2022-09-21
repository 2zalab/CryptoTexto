package com.sintel.cryptotexto;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.action_bar_layout);
        setContentView(R.layout.activity_main);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater= getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public void CrypterActivity(View view) {
        Intent intent = new Intent(MainActivity.this, CryptageActivity.class);
        startActivity(intent);
    }

    public void DecrypterActivity(View view) {
        Intent intent = new Intent(MainActivity.this, DecryptageActivity.class);
        startActivity(intent);
    }

    public void Exit(MenuItem item) {
        AlertDialog.Builder alertDialogBuilder= new AlertDialog.Builder(MainActivity.this);
        alertDialogBuilder.setTitle("Confirmer");
        alertDialogBuilder.setIcon(R.drawable.ic_baseline_warning_24);
        alertDialogBuilder.setMessage("Voulez-vous vraiment quitter?");
        alertDialogBuilder.setCancelable(false);
        alertDialogBuilder.setNegativeButton("Non", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });

        alertDialogBuilder.setPositiveButton("Oui", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });

        AlertDialog alertDialog= alertDialogBuilder.create();
        alertDialog.show();
    }

    public void Partager(MenuItem item) {
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_TEXT, "");
        String app_url = "Crypto Texto est une application qui me permet de chiffrer mes messages.Téléchargez aussi cette application  en cliquant sur le lien : https://play.google.com/store/apps/details?id=com.sintel.cryptotexto";
        shareIntent.putExtra(Intent.EXTRA_TEXT, app_url);
        startActivity(Intent.createChooser(shareIntent, "Partager via"));
    }

    public void Noter(MenuItem item) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("https://play.google.com/store/apps/details?id=com.sintel.cryptotexto"));
        startActivity(intent);
    }

    public void VoirApropos(MenuItem item) {
        Intent intent = new Intent(this, AproposActivity.class);
        startActivity(intent);
    }

    public void VoirGuide(MenuItem item) {
        Intent intent = new Intent(this, HelpActivity.class);
        startActivity(intent);
    }
}