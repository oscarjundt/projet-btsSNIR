package com.example.vrai_appli;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * C'est une classe qui prend une URL comme paramètre, se connecte à l'URL et renvoie les données de
 * l'URL
 */
public class TraitementHttp {
    // Variable privée utilisée pour stocker l'URL transmise au constructeur.
    private String Myurl;
    // Un constructeur.
    public TraitementHttp(String url){
        this.Myurl = url;
    }
    /**
     * Il se connecte à un serveur et renvoie les données du serveur.
     *
     * @return Les données renvoyées par le serveur(la reponse).
     */
    public String Traitement(){
        // Il s'agit d'une méthode appelée `Traitement()` qui se connecte à un serveur à l'aide d'une
        // URL HTTP et renvoie les données reçues du serveur sous forme de chaîne.
        String result = "";
        //connection au serveur
        try {
            // Création d'un nouvel objet URL.
            URL url;
            // Créer un nouvel objet de la classe `HttpURLConnection` et l'affecter à la variable
            // `urlConnection`.
            HttpURLConnection urlConnection = null;
            try {

                // Création d'un nouvel objet URL.
                url = new URL(this.Myurl);

                // Ouverture d'une connexion à l'URL donner.
                urlConnection = (HttpURLConnection) url.openConnection();

                // Réglage du délai d'attente de la connexion à 3500 millisecondes.
                urlConnection.setConnectTimeout(3500);
                urlConnection.setReadTimeout(3500);
                try{
                    // Obtenir le flux d'entrée de la connexion.
                    InputStream in = urlConnection.getInputStream();
                    // Lecture des données du flux d'entrée.
                    InputStreamReader isw = new InputStreamReader(in);
                    // Lire les données du flux d'entrée et les stocker dans la variable `result`.
                    int data = isw.read();
                    while (data != -1) {
                        result += (char) data;
                        data = isw.read();
                    }

                    // on retourne les donnee a la fonction onPostExecute
                    return result;

                }
                catch (Exception SocketTimeoutException){
                    result = "timeout";
                }
            } catch (Exception e) {
                //si exception lever
                e.printStackTrace();
            } finally {
                //si une connection
                if (urlConnection != null) {
                    //deconnecter
                    urlConnection.disconnect();
                }
            }
        } catch (Exception e) {
            //si exception lever
            e.printStackTrace();
            return "Exception: " + e.getMessage();
        }
        //on retourne les donnee
        return result;
    }
}
