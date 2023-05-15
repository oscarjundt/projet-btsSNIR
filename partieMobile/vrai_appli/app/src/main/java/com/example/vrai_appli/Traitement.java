package com.example.vrai_appli;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RectShape;
import android.os.AsyncTask;
import android.os.Build;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import org.json.JSONException;

/**
 * C'est une classe qui permet de faire des requêtes asynchrones à un serveur et de traiter les reponse en fonction du choix
 */
/**
 * The Traitement class is an AsyncTask in Java that performs various actions based on the value of the
 * "action" variable, such as calling methods for login, displaying data, and mapping.
 */
// The above code is declaring a class that extends the `AsyncTask` class in Java. This class is used
// for performing background operations and updating the UI thread with the results. The three generic
// types `<String, String, String>` represent the types of parameters passed to the task, the type of
// progress units published during the background computation, and the type of result returned by the
// task's `doInBackground` method.
public class Traitement extends AsyncTask<String,String,String>{
    //on declare les variable de la classe ne priver
    // Le code ci-dessus déclare une variable d'instance privée nommée `progressDialog` de type
    // `ProgressDialog`. Cette variable peut être utilisée pour afficher une boîte de dialogue de
    // progression dans une application Android pendant l'exécution d'une tâche de longue durée.
    private ProgressDialog progressDialog;
    // Le code ci-dessus déclare une variable privée nommée "page" de type Fragment en Java. Il
    // n'initialise ni n'attribue de valeur à la variable.
    private Fragment page;
    // Le code ci-dessus déclare une variable d'instance privée nommée "MapOuver" de type Map. Il
    // n'initialise pas la variable, il aura donc une valeur nulle jusqu'à ce qu'une valeur lui soit
    // affectée plus tard dans le code.
    private Map MapOuver;
    // Le code ci-dessus déclare une variable d'instance privée nommée "action" de type String dans une
    // classe Java.
    private String action;
    // Le code ci-dessus déclare une variable d'instance privée nommée "myUrl" de type "String" en
    // Java. Cette variable est accessible et modifiable uniquement au sein de la classe dans laquelle
    // elle est déclarée.
    private String myUrl;

    /**
     * Cette fonction définit la valeur d'une variable Map appelée "map_ouver".
     *
     * @param mapOuver map_ouver est un paramètre de type Map, qui est défini sur la variable
     * d'instance map_ouver à l'aide de la méthode setMap_ouver(). Le but de cette méthode est
     * d'attribuer une valeur à la variable map_ouver, qui peut ensuite être utilisée par d'autres
     * méthodes de la classe. Le
     */
    public void setMapOuver(Map mapOuver){
        this.MapOuver = mapOuver;
    }
    /**
     * Cette fonction définit la variable de page sur la variable page_log
     *
     * @param page_log Le fragment que vous souhaitez afficher.
     */
    public void setPage(Fragment page_log){
        this.page = page_log;
    }

    /**
     * Cette fonction définit la valeur de la variable myUrl sur la valeur de la variable url.
     *
     * @param url L'URL de la page que vous souhaitez récupérer.
     */
    public void setMyUrl(String url){
        this.myUrl = url;
    }

    // Cette fonction définit la valeur de la variable action sur la valeur de la variable action.
    public void setAction(String action) {
        this.action = action;
    }

    @Override
    public void onPreExecute() {
        super.onPreExecute();
        this.progressDialog = new ProgressDialog(this.page.getContext());
        this.progressDialog.setMessage("en progression");
        this.progressDialog.setCancelable(false);
        this.progressDialog.show();
    }

    /**
     * *|MARKER_CURSOR|*
     *
     * @return Le résultat de la demande.
     */
    @Override
    public String doInBackground(String... strings) {

        // Création d'une nouvelle instance de la classe `TraitementHttp`.
        TraitementHttp Http = new TraitementHttp(this.myUrl);
        // Renvoie le résultat de la requête.
        return Http.Traitement();
    }

    /**
     *
     *
     * @param data les données renvoyées par doInBackground
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override

    public void onPostExecute(String data) {
        //on suprimme le prosessuce
        progressDialog.dismiss();
        // Le code ci-dessus est un extrait de code Java qui vérifie la valeur de la variable "action"
        // et effectue différentes actions en fonction de sa valeur. Si la valeur de "action" est
        // "login", elle appelle la méthode "Login" avec le paramètre "data". Si la valeur de "action"
        // est "history", elle appelle la méthode "Affichage" avec les paramètres "data" et "graphi".
        // Si la valeur de "action" est "recherche", elle appelle la méthode "Affichage" avec les
        // paramètres "data" et une chaîne vide. Si la valeur de "action" est
        if(this.action=="login"){
            Login(data);
        }
        else if(this.action.equals("history")){
            Affichage(data,"graphi");
        }
        else if(this.action.equals("recherche")){
            Affichage(data,"");
        }
        else if(this.action.equals("map")){
            map(data);
        }
        else if(this.action.equals("map_rech")){
            MapRech(data);
        }
        else if(this.action.equals("eolienne")){
            EolienneAffiche(data);
        }
    }
    /**
     * La fonction EolienneAffiche affiche les données sous forme de tableau s'il ne s'agit pas d'une
     * erreur ou est vide, et affiche un message d'erreur ou revient à la page de connexion s'il y a
     * une erreur.
     *
     * @param data Le paramètre "data" est une variable de chaîne qui contient des données extraites
     * d'une source, telle qu'une base de données ou une API. La méthode « EolienneAffiche » utilise
     * ces données pour afficher des informations sous forme de tableau sur l'interface utilisateur. La
     * méthode vérifie si les données sont valides et non vides
     */
    private void EolienneAffiche(String data){
        // Le code ci-dessus est écrit en Java et vérifie la valeur d'une variable appelée "data". Si
        // la valeur de "data" n'est pas égale à "erreur" ni à "[]", alors il crée un tableau avec les
        // valeurs récupérées et y insère les données d'en-tête et de section. Si la valeur de "data"
        // est "[]", alors il affiche un message disant "aucune donnéee" (no data). Si la valeur de
        // "data" est "erreur", alors il envoie une notification et revient à la page de connexion.
        if(!data.equals("erreur") && !data.equals("[]")) {
            //this.mainacrt.getBinding().textView.setText(s.toString());
            //on creer un tableau avec les valeur recupere
            MainActivity main = (MainActivity) page.getActivity();
            Tableaux tableaux = null;
            try {
                tableaux = new Tableaux(main.getHeader_eolienne(),this.page,false);
                tableaux.RemoveContenuTableaux();
                //tableaux.initCouleurdate(data);
                tableaux.RempliCouleurTableaux(0);
                tableaux.InsertHeader();
                tableaux.InsertSection(data);
            } catch (JSONException e) {
                e.printStackTrace();
            }


        }
        else if(data.equals("[]")){
            TextView message_inconnu = this.page.getActivity().findViewById(R.id.TextViewMessageInconnu);
            message_inconnu.setText("aucune donnee");
            message_inconnu.setVisibility(View.VISIBLE);


        }
        //si il y'a une erreur
        else if(data.equals("erreur")){
            //on revient a la page d'acceuil
            SendNotif();
            NavHostFragment.findNavController(this.page)
                    .navigate(R.id.PageLogin);
        }
    }
    /**
     * La fonction affiche une carte avec des marqueurs basés sur les données reçues et navigue vers la
     * page de connexion en cas d'erreur.
     *
     * @param data Le paramètre de données est une chaîne qui contient des informations à utiliser dans
     * la méthode MapRech.
     */
    private void MapRech(String data){
        // Le code ci-dessus est écrit en Java et il vérifie si la variable "data" n'est pas égale à la
        // chaîne "erreur" et n'est pas égale à un tableau vide "[]". Si cette condition est vraie, il
        // appelle la méthode "MapAffiche" avec la variable "data" et la chaîne "rech" en paramètres.
        // Si la variable "data" est égale à la chaîne "erreur", elle appelle la méthode "SendNotif" et
        // navigue vers la page de connexion à l'aide du NavHostFragment. Il existe également une
        // section de code commentée qui semble configurer une vue de carte
        if(!data.equals("erreur") && !data.equals("[]")) {
            this.MapOuver.MapAffiche(data,"rech");
        }
        else if(data.equals("erreur")){
            SendNotif();
            NavHostFragment.findNavController(this.page)
                    .navigate(R.id.PageLogin);
        }
    }
    /**
     * La fonction mappe un ensemble de coordonnées sur une vue cartographique et les affiche sous
     * forme de marqueurs.
     *
     * @param data Le paramètre "data" est une variable chaîne qui contient des informations à traiter
     * par la méthode "map". La méthode vérifie si la chaîne n'est pas égale à "erreur" ou "[]", puis
     * procède à l'analyse des données JSON contenues dans la chaîne pour afficher les marqueurs sur
     * une carte. Si la chaîne
     */
    private void map(String data) {
        if(!data.equals("erreur") && !data.equals("[]")) {
            System.out.println(data);
            this.MapOuver.MapAffiche(data,"tout");
        }
        else if(data.equals("erreur")){
            SendNotif();
            NavHostFragment.findNavController(this.page)
                    .navigate(R.id.PageLogin);
        }

    }
        //Map geo = new Map(data,this.page);
    /**
     * Il vérifie si les données reçues du serveur contiennent la chaîne "ok", si c'est le cas, il
     * définit le jeton dans MainActivity et navigue vers la page de menu, sinon il affiche un message
     * d'erreur et met en surbrillance les champs nom d'utilisateur et mot de passe
     *
     * @param data les données renvoyées par le serveur
     */
    private void Login(String data){
        // Il vérifie si les données reçues du serveur contiennent la chaîne "ok", si c'est le cas, il
        // définit le jeton dans MainActivity et navigue vers la page de menu, sinon il affiche un
        // message d'erreur et met en surbrillance les champs nom d'utilisateur et mot de passe.
        if(data.contains("ok")) {
            MainActivity activity = (MainActivity) this.page.getActivity();
            activity.setToken(data);
            NavHostFragment.findNavController(this.page)
                    .navigate(R.id.PageMenu);
        }
        else if(data.equals("timeout")){
            LoginErreur("timeout, le serveur a mis trop de temps pour repondre");
        }
        else {
            LoginErreur("mot de passe ou login");
        }
    }
    private void LoginErreur(String argument){
        ShapeDrawable shape = new ShapeDrawable(new RectShape());
        shape.getPaint().setColor(Color.RED);
        shape.getPaint().setStyle(Paint.Style.STROKE);
        shape.getPaint().setStrokeWidth(3);
        TextView message_erreur = page.getActivity().findViewById(R.id.textView3);
        EditText champ_username = page.getActivity().findViewById(R.id.editTextUsername);
        EditText champ_password = page.getActivity().findViewById(R.id.editTextTextPassword);
        message_erreur.setText("erreur de "+argument);
        message_erreur.setVisibility(View.VISIBLE);
        champ_username.setBackground(shape);
        champ_password.setBackground(shape);
    }
    /**
     * Il prend une chaîne et une chaîne comme paramètres, et il affiche les données dans un tableau et
     * un graphique
     *
     * @param data les données renvoyées par le serveur
     * @param option le type de données à afficher
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void Affichage(String data,String option){
        // Le code ci-dessus vérifie si les données reçues ne sont pas égales à "erreur" et non égales
        // à "[]". Si la condition est vraie, il crée un tableau avec les valeurs reçues, le remplit de
        // couleurs, insère des en-têtes et des sections et affiche un graphique. Si la donnée est
        // égale à "[]", il affiche un message disant "aucune donnéee". Si les données sont égales à
        // "erreur", il envoie une notification et revient à la page de connexion. Le code est écrit en
        // Java.
        // Vérifier si la donnée n'est pas égale à "erreur" et non égale à "[]"
        if(!data.equals("erreur") && !data.equals("[]")) {
            //this.mainacrt.getBinding().textView.setText(s.toString());
            //on creer un tableau avec les valeur recupere
            MainActivity main = (MainActivity) page.getActivity();
            Tableaux tableaux = null;
            try {
                tableaux = new Tableaux(main.getHeader(),this.page,true);
                tableaux.RemoveContenuTableaux();
                tableaux.InitCouleurDate(data);
                tableaux.RempliCouleurTableaux(0);
                tableaux.InsertHeader();
                tableaux.InsertSection(data);
                Graph GeneGraph = new Graph(this.page, data, tableaux.getCol());
                GeneGraph.RemoveGraph();
                GeneGraph.GraphAffiche("Dvent");
                GeneGraph.GraphAffiche("Vvent");
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
        else if(data.equals("[]")){
            TextView message_inconnu = this.page.getActivity().findViewById(R.id.TextViewMessageInconnu);
            message_inconnu.setText("aucune donnee");
            message_inconnu.setVisibility(View.VISIBLE);


        }
        //si il y'a une erreur
        else if(data.equals("erreur")){
            //on revient a la page d'acceuil
            SendNotif();
            NavHostFragment.findNavController(this.page)
                    .navigate(R.id.PageLogin);
        }
    }
    /**
     * Cette fonction envoie une notification avec un message d'erreur si la version d'Android est
     * égale ou supérieure à Oreo.
     */
    public void SendNotif(){
        // Le code ci-dessus crée un canal de notification et envoie une notification avec le titre
        // "erreur" et le contenu "erreur de connexion" si la version du SDK de l'appareil est égale ou
        // supérieure à Android Oreo (API niveau 26). Le canal de notification est utilisé pour
        // regrouper les notifications et définir leur comportement. La notification est créée à l'aide
        // de la classe NotificationCompat.Builder et envoyée à l'aide de la classe
        // NotificationManagerCompat.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel t = new NotificationChannel("erreur", "erreur de connection", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager t2 = page.getActivity().getSystemService(NotificationManager.class);
            t2.createNotificationChannel(t);
            NotificationCompat.Builder buol = new NotificationCompat.Builder(page.getContext(), "erreur").setSmallIcon(android.R.drawable.stat_notify_sync).setContentTitle("erreur").setContentText("erreur de connection");
            NotificationManagerCompat.from(page.getContext()).notify(1, buol.build());
        }
    }
}
