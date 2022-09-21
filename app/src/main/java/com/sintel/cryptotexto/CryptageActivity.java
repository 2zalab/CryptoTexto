package com.sintel.cryptotexto;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipboardManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CryptageActivity extends AppCompatActivity {

    Button btn_cryptage;
    EditText texte_clair, text_crypte, clé_cryptage;
    ImageButton btn_copy, btn_share, btn_exit;
    Spinner algo;
    String TexteCrypté, TexteClair;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.action_bar_layout_cryptage);
        setContentView(R.layout.activity_cryptage);


        btn_copy=findViewById(R.id.btn_copy);
        btn_cryptage=findViewById(R.id.btn_cryptage);
        btn_share=findViewById(R.id.btn_share);
        btn_exit=findViewById(R.id.btn_exit);
        texte_clair=findViewById(R.id.intput_cryptage);
        text_crypte=findViewById(R.id.output_cryptage);
        algo=findViewById(R.id.algo);
        clé_cryptage=findViewById(R.id.cle_cryptage);

        btn_copy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String texte_à_copier = text_crypte.getText().toString();
                if (texte_à_copier.isEmpty()){
                    Toast.makeText(CryptageActivity.this, "Le texte à copier est vide !", Toast.LENGTH_LONG).show();
                } else {
                    copier(texte_à_copier);
                }
            }
        });

        btn_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String texte_à_partager = text_crypte.getText().toString();
                if (texte_à_partager.isEmpty()){
                    Toast.makeText(CryptageActivity.this, "Le texte à partager est vide !", Toast.LENGTH_LONG).show();
                } else {
                    share(texte_à_partager);
                }
            }
        });

        btn_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                text_crypte.setText("");
                Toast.makeText(CryptageActivity.this, "Texte crypté effacé avec succès!", Toast.LENGTH_SHORT).show();
            }
        });

        btn_cryptage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String texte_à_crypter = texte_clair.getText().toString();
                String cle = clé_cryptage.getText().toString();
                if (texte_à_crypter.contains("O")||texte_à_crypter.contains("o")||texte_à_crypter.contains("Q")||texte_à_crypter.contains("q")||texte_à_crypter.contains("X")||texte_à_crypter.contains("x")){
                    Toast.makeText(CryptageActivity.this, "Votre texte à crypter n'est pas ecrit en langue Mofu. Il ne doit pas contenir les lettres 'O' et 'Q'.", Toast.LENGTH_LONG).show();
                } else if (cle.contains("O")||cle.contains("o")||cle.contains("Q")||cle.contains("q")||cle.contains("X")||cle.contains("x")){
                    Toast.makeText(CryptageActivity.this, "La Clé n'est pas ecrit en langue Mofu. Il ne doit pas contenir les lettres 'O' et 'Q'.", Toast.LENGTH_LONG).show();
                }
                else {
                    String chiffre = algo.getSelectedItem().toString();
                    // on remplace de caracteres contenant des accents.
                    texte_à_crypter = texte_à_crypter.replaceAll("[éèêë]", "e");
                    texte_à_crypter = texte_à_crypter.replaceAll("[àâ]", "a");
                    texte_à_crypter = texte_à_crypter.replaceAll("[ûù]", "u");
                    //texte_à_crypter=texte_à_crypter.replaceAll("[ô]","o");
                    texte_à_crypter = texte_à_crypter.replaceAll("[î]", "i");
                    texte_à_crypter = texte_à_crypter.toUpperCase();
                    cle = cle.replaceAll("[éèêë]", "e");
                    cle = cle.replaceAll("[àâ]", "a");
                    cle = cle.replaceAll("[ûù]", "u");
                    //cle=cle.replaceAll("[ô]","o");
                    cle = cle.replaceAll("[î]", "i");
                    boolean isnumeric = cle.matches("[+-]?\\d*(\\.\\d+)?");
                    if (texte_à_crypter.isEmpty() || cle.isEmpty()) {
                        Toast.makeText(CryptageActivity.this, "Le Message à crypter ou la clé est vide", Toast.LENGTH_SHORT).show();
                    } else {
                        if (chiffre.contains("Cesar")) {
                            if (isnumeric) {
                                Integer key = Integer.parseInt(cle);
                                TexteCrypté = Caesar.cryptage(texte_à_crypter, key).toUpperCase();
                                text_crypte.setText(TexteCrypté);
                            } else
                                Toast.makeText(CryptageActivity.this, "Saisir un nombre entier comme clé", Toast.LENGTH_SHORT).show();

                        } else if (chiffre.contains("Vigénère")) {
                            if (isnumeric) {
                                Toast.makeText(CryptageActivity.this, "La clé doit être une chaine des caractères !", Toast.LENGTH_SHORT).show();
                            } else {
                                TexteCrypté = VigenereCryptage(texte_à_crypter, cle);
                                text_crypte.setText(TexteCrypté);
                            }
                        } else {
                            Toast.makeText(CryptageActivity.this, "Le chiffrement avec l'algorithme de PlayFair n'est pas implementé pour le moment!", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        });
    }

     public static String VigenereCryptage(String texte_à_crypter, String cle){
         //Supression des caracteres non alphabetique dans le message et dans la clé
         texte_à_crypter=texte_à_crypter.replaceAll(" ","");
         texte_à_crypter=texte_à_crypter.replaceAll("[,;_«'\"».?!-:@{}()*/+&]","");
         cle=cle.replaceAll(" ","");
         cle=cle.replaceAll("[,;_.?!-:@{}()*/+&]","");
         cle=cle.toUpperCase();

         //Convertion du message et de la clé en tableau des caracteres
        char[] keyTab = cle.toCharArray();
        char[] message = texte_à_crypter.toCharArray();
        char [] key = new char[1000];

        //determination de la longueur de la clé et du message
         int longMessage= message.length;
         int longCle = keyTab.length;

        //repartition de la clé sur la longueur du message
        for(int i =0; i < longMessage; i++){
            // on verifie si chaque lettre du message à coder est une lettre de l'alphabet Mofu
            boolean trouve=false;
            for (int j = 0; j <Caesar.alpha.length; j++){
                if (Caesar.alpha[j]==message[i]) {
                    trouve = true;
                }
            }
            if (trouve) {
                key[i] = keyTab[i % longCle];
            }
        }
        //numerisation de la clé et stockage dans un tableau d'entier
         Integer [] NumericKey = new Integer[key.length];
        for (int k = 0 ; k < key.length; k++){
            char c= key[k];
            for (int i=0; i<Caesar.alpha.length;i++){
             if (c==Caesar.alpha[i]) {
                NumericKey[k] = i;
             }
            }
        }

        //Cryptage et stockage des resultats en utilisant Cesar
         StringBuilder s= new StringBuilder();
         for (int j=0;j<longMessage; j++){
             char c = message[j];
             s.append(Caesar.cryptage(String.valueOf(c), NumericKey[j]));
         }
        return s.toString();

    }

    private void share(String s) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT,s);
        startActivity(intent);
    }

    private void copier(String texte) {
        ClipboardManager clipboardManager = (ClipboardManager)getSystemService(CLIPBOARD_SERVICE);
        clipboardManager.setText(texte);
        Toast.makeText(CryptageActivity.this, "Texte copier dans presse papier", Toast.LENGTH_SHORT).show();
    }

}