package com.sintel.cryptotexto;

public class Caesar {


    public static final char[] alpha = {'A','B', 'Ɓ','C','D','Ɗ','E','Ə','F','G','H','I','J','K','L','M',
                    'N','Ŋ','P','R','S','T','U','V','W','Y','Z'};

    protected static char[] texte_code = new char[27];
    protected static char[] texte_clair = new char[27];

    public Caesar(Integer decalage) {
        for (int i=0; i < 27; i++) texte_code[i] = alpha[(i + decalage) % 27];
        for (int i=0; i < 27; i++) texte_clair[texte_code[i] - 'A'] = alpha[i];
    }

    public static String cryptage(String secret, Integer key) {
        char[] message = secret.toCharArray();
        for (int i=0; i < 27; i++) texte_code[i] = alpha[(i + key) % 27];
        for (int i=0; i < message.length; i++) {
            /*
            if (Character.isUpperCase(message[i]) || message[i]=='Ɓ'||
                    message[i]=='Ɗ'|| message[i]=='Ə'|| message[i]=='Ŋ')
            {
                message[i] = texte_code[message[i]-'A'];
            }
             */
            boolean trouve=false;
            int pos = 0;
            for (int j=0;j<alpha.length;j++){
                if (alpha[j]==message[i]) {
                    trouve = true;
                    pos=j;
                }
            }
            if (trouve) {
                message[i] = texte_code[pos];
            }
        }
        return new String(message);
    }

    public static String decryptage(String secret, Integer cle) {
        for (int i=0; i < 27; i++) texte_code[i] = alpha[(i + cle) % 27];
       // for (int i=0; i < 27; i++) texte_clair[texte_code[i] - 'A'] = alpha[i];
        char[] message = secret.toCharArray();
        for (int i=0; i < message.length; i++) {
            boolean trouve=false;
            int pos=0;
            for (int j=0;j< texte_code.length;j++){
                if (texte_code[j]==message[i]) {
                    trouve = true;
                    pos=j;
                }
            }
            if (trouve) {
                message[i] = alpha[pos];
            }
        }
        return new String(message);
    }
}
