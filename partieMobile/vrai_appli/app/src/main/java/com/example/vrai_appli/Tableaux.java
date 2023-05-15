package com.example.vrai_appli;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RectShape;
import android.os.Build;
import android.util.ArrayMap;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

/**
 * C'est une classe qui permet de créer un tableau dans un fragment
 */
public class Tableaux{
    // Variable utilisée pour stocker le fragment passé dans le constructeur.
    private Fragment Frag;
    // Variable utilisée pour stocker le fragment passé dans le constructeur.
    // Une variable qui est utilisée pour stocker la disposition de table passée dans le constructeur.
    private TableLayout Tableaux;
    // Déclaration et initialisation d'un ArrayList d'objets TextView nommé "header". Le ArrayList sera
    // utilisé pour stocker des objets TextView qui seront utilisés comme en-têtes dans une table.
    private ArrayList<TextView> header = new ArrayList<TextView>();
    // Déclarer et initialiser une ArrayList d'objets TextView nommés "contenu". Cette ArrayList sera
    // utilisée pour stocker des objets TextView qui seront utilisés comme contenu dans une table.
    private ArrayList<TextView> contenu = new ArrayList<TextView>();
    // Déclarer et initialiser une ArrayList d'entiers nommée "col". Cette ArrayList sera utilisée pour
    // stocker des couleurs générées aléatoirement qui seront utilisées pour remplir l'arrière-plan
    // d'une cellule spécifique dans un tableau.
    private ArrayList<Integer> col = new ArrayList<Integer>();
    // `private String[] name_colonne;` déclare une variable d'instance privée `name_colonne` de type
    // `String` array. Il est utilisé pour stocker les noms des colonnes qui seront affichées dans le
    // tableau. Le tableau sera initialisé avec les noms de colonnes passés dans le constructeur de la
    // classe `Tableaux`.
    private String[] NameColonne;
    // `private boolean headerbg;` déclare une variable d'instance privée `headerbg` de type booléen.
    // Il est utilisé pour stocker une valeur booléenne qui détermine si l'en-tête du tableau doit
    // avoir une couleur de fond ou non. Cette variable est initialisée dans le constructeur de la
    // classe `Tableaux`.
    private boolean HeaderBg;

    // Il s'agit d'un constructeur pour la classe `Tableaux`. Il prend trois paramètres : un tableau de
    // noms de colonnes (`name_colonne_Ouput`), un objet `Fragment` (`fenetre`) et une valeur booléenne
    // (`headerbg`).
    public Tableaux(String[] nameColonneOuput, Fragment fenetre,boolean headerBg){
        this.HeaderBg = headerBg;
        this.Tableaux =  fenetre.getActivity().findViewById(R.id.tableaux);
        this.Frag = fenetre;
        this.NameColonne = nameColonneOuput;
        for(int i=0;i<this.NameColonne.length;i++){
            this.header.add(new TextView(this.Frag.getContext()));
        }
        for(int i=0;i<this.NameColonne.length;i++){
            this.contenu.add(new TextView(this.Frag.getContext()));
        }
    }

    /**
     * ça change la couleur du tableau
     *
     * @param i2 l'index de la couleur dans la liste des couleurs
     */
    public void RempliCouleurTableaux(int i2){
        ShapeDrawable shape = new ShapeDrawable(new RectShape());
        //on met la couleur du tableau a rouge
        shape.getPaint().setColor(Color.RED);
        shape.getPaint().setStyle(Paint.Style.STROKE);
        shape.getPaint().setStrokeWidth(3);
        for(int i=0;i<this.NameColonne.length;i++){

            this.header.get(i).setBackground(shape);
        }
        for(int i=0;i<this.NameColonne.length;i++){
            this.contenu.get(i).setBackground(shape);
        }
        if(this.HeaderBg){
            this.contenu.get(3).setBackgroundColor(this.col.get(i2));
        }
    }
    /**
     * Il crée une nouvelle ligne, puis y ajoute l'en-tête, et enfin ajoute la ligne à la table
     */
    public void InsertHeader(){
        TableRow ligne = new TableRow(this.Frag.getContext());
        for(int i=0;i<this.NameColonne.length;i++){
            this.header.get(i).setText("  "+this.NameColonne[i]+"  ");
            ligne.addView(this.header.get(i));

        }
        //TableLayout t = this.frag.getActivity().findViewById(R.id.tableaux);
        this.Tableaux.addView(ligne);
    }
    /**
     * Il prend un JSONArray et crée une table avec
     *
     * @param data les données à insérer dans le tableau
     */
    public void InsertSection(String data) throws JSONException {
        JSONArray array = null;
        try {
            array = new JSONArray(data);
            TableRow ligne;
            for (int i = 0; i < array.length(); i++) {

                ligne = new TableRow(this.Frag.getContext());
                JSONObject object = array.getJSONObject(i);
                for (int j = 0; j < this.NameColonne.length; j++) {
                    this.contenu.get(j).setText("  " + object.getString(this.NameColonne[j]) + "  ");
                }
                //ligne_Vvent.addView(cellule_Vvent);
                for (int j = 0; j < NameColonne.length; j++) {
                    ligne.addView(contenu.get(j));
                }
                //TableLayout tabl = this.frag.getActivity().findViewById(R.id.tableaux);
                this.Tableaux.addView(ligne);
                this.contenu.clear();
                for (int y = 0; y < this.NameColonne.length; y++) {
                    this.contenu.add(new TextView(this.Frag.getContext()));
                }
                RempliCouleurTableaux(i + 1);
            }



        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    /**
     * Il prend une chaîne et renvoie une liste de couleurs aléatoires
     *
     * @param data les données à afficher
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void InitCouleurDate(String data){
        Random rand = new Random();
        for(int i = 0; i<data.length(); i++){
            float r = rand.nextFloat();
            float g = rand.nextFloat();
            float b = rand.nextFloat();
            this.col.add(Color.argb(2,r,g,b));
        }
    }
    /**
     * Cette fonction retourne la couleur des date
     *
     * @return La méthode renvoie la colonne ArrayList.
     */
    public ArrayList<Integer> getCol() {
        return col;
    }
    /**
     * Il supprime toutes les vues du TableLayout
     */
    public void RemoveContenuTableaux(){
        this.Tableaux.removeAllViews();
    }
}
