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

public class DecryptageActivity extends AppCompatActivity {
    Button btn_decryptage;
    EditText texte_clair, text_decrypte, clé_decryptage;
    ImageButton btn_copy, btn_share, btn_exit;
    Spinner algo;
    String TextedeCrypté, TexteClair;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.action_bar_layout_decryptage);
        setContentView(R.layout.activity_decryptage);


        btn_copy=findViewById(R.id.btn_copy2);
        btn_decryptage=findViewById(R.id.btn_decryptage);
        btn_share=findViewById(R.id.btn_share2);
        btn_exit=findViewById(R.id.btn_exit2);
        text_decrypte=findViewById(R.id.intput_decryptage);
        texte_clair=findViewById(R.id.output_decryptage);
        algo=findViewById(R.id.algo2);
        clé_decryptage=findViewById(R.id.cle_decryptage);

        btn_copy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String texte_à_copier = texte_clair.getText().toString();
                if (texte_à_copier.isEmpty()){
                    Toast.makeText(DecryptageActivity.this, "Le texte à copier est vide !", Toast.LENGTH_LONG).show();
                } else {
                    copier(texte_à_copier);
                }
            }
        });

        btn_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String texte_à_partager = texte_clair.getText().toString();
                if (texte_à_partager.isEmpty()){
                    Toast.makeText(DecryptageActivity.this, "Le texte à partager est vide !", Toast.LENGTH_LONG).show();
                } else {
                    share(texte_à_partager);
                }
            }
        });

        btn_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                text_decrypte.setText("");
                Toast.makeText(DecryptageActivity.this, "Texte décrypté effacé avec succès!", Toast.LENGTH_SHORT).show();
            }
        });

        btn_decryptage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String texte_à_decrypter = text_decrypte.getText().toString().toUpperCase();
                String cle = clé_decryptage.getText().toString();
                if (texte_à_decrypter.contains("O")||texte_à_decrypter.contains("o")||texte_à_decrypter.contains("Q")||texte_à_decrypter.contains("q")||texte_à_decrypter.contains("X")||texte_à_decrypter.contains("x")){
                    Toast.makeText(DecryptageActivity.this, "Votre texte à décrypter n'est pas ecrit en langue Mofu. Il ne doit pas contenir les lettres 'O' et 'Q'.", Toast.LENGTH_LONG).show();
                } else if (cle.contains("O")||cle.contains("o")||cle.contains("Q")||cle.contains("q")||cle.contains("X")||cle.contains("x")){
                    Toast.makeText(DecryptageActivity.this, "La Clé n'est pas ecrit en langue Mofu. Il ne doit pas contenir les lettres 'O' et 'Q'.", Toast.LENGTH_LONG).show();
                }
                else
                {
                String chiffre= algo.getSelectedItem().toString();
                boolean isnumeric = cle.matches("[+-]?\\d*(\\.\\d+)?");
                if(texte_à_decrypter.isEmpty() || cle.isEmpty()){
                    Toast.makeText(DecryptageActivity.this, "Le Message à décrypter ou la clé est vide", Toast.LENGTH_SHORT).show();
                } else {
                    if (chiffre.contains("Cesar")) {
                        if (isnumeric) {
                            Integer key = Integer.parseInt(cle);
                            TextedeCrypté = Caesar.decryptage(texte_à_decrypter, key).toUpperCase();
                            texte_clair.setText(TextedeCrypté);
                        } else
                            Toast.makeText(DecryptageActivity.this, "Saisir un nombre entier comme clé", Toast.LENGTH_SHORT).show();

                    } else if (chiffre.contains("Vigénère")) {
                        if (isnumeric) {
                            Toast.makeText(DecryptageActivity.this, "La clé doit être une chaine des caractères !", Toast.LENGTH_SHORT).show();
                        } else {
                            TextedeCrypté = VigenereDeCryptage(texte_à_decrypter, cle);
                            texte_clair.setText(TextedeCrypté);
                        }
                    } else
                        {
                          Toast.makeText(DecryptageActivity.this, "Le chiffrement avec l'algorithme de PlayFair n'est pas implementé pour le moment!", Toast.LENGTH_SHORT).show();
                         }
                    }
                }
            }
        });
    }

    private String VigenereDeCryptage(String texte_à_decrypter, String cle) {

        //Supression des caracteres non alphabetique dans le message et dans la clé
        texte_à_decrypter=texte_à_decrypter.replaceAll(" ","");
        texte_à_decrypter=texte_à_decrypter.replaceAll("[,;_.?!-:@{}()*/+&]","");
        cle=cle.replaceAll(" ","");
        cle=cle.replaceAll("[,;_.?!-:@{}()*/+&]","");
        cle=cle.toUpperCase();

        //Convertion du message et de la clé en tableau des caracteres
        char[] keyTab = cle.toCharArray();
        char[] message = texte_à_decrypter.toCharArray();
        char [] key = new char[1000];

        //determination de la longueur de la clé et du message
        int longMessage= message.length;
        int longCle = keyTab.length;

        //repartition de la clé sur la longueur du message
        for(int i =0; i < longMessage; i++){
            // on verifie si chaque lettre du message à décrypter est une lettre de l'alphabet Mofu
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
            Character c= key[k];
            for (int i=0; i<Caesar.alpha.length;i++){
                if (c.equals(Caesar.alpha[i])) {
                    NumericKey[k] = i;
                }
            }
        }

        //Cryptage et stockage des resultats en utilisant Cesar
        StringBuilder s= new StringBuilder();
        for (int j=0;j<longMessage; j++){
            Character c = message[j];
            s.append(Caesar.decryptage(String.valueOf(c), NumericKey[j]));
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
        Toast.makeText(DecryptageActivity.this, "Texte copier dans presse papier", Toast.LENGTH_SHORT).show();
    }

}