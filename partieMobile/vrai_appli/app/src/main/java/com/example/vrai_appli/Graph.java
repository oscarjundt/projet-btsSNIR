package com.example.vrai_appli;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RectShape;
import android.os.Build;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.google.android.material.slider.LabelFormatter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.time.Instant;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.Random;


/**
 * La classe Graph est une classe Java qui crée et affiche des graphiques à barres basés sur des
 * données fournies au format JSON.
 */
public class Graph{
    /**
     * variable de type Fragment appler page
     */
    private Fragment Page;
    /**
     * variable de type String appeler Data
     */
    private String Data;
    /**
     * variable de type ArrayListe<Integer> appeler Colt
     */
    private ArrayList<Integer> Colt;
    /**
     * variable de type LinearLayout appeler champ_Graph
     */
    private LinearLayout ChampGraph;

    /**
     *
     * @param page de type Fragment relier a la variable Page
     * @param data2 de type String relier a la variable Dara
     * @param colt de type ArrayList<Integer> colt
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    public Graph(Fragment page, String data2, ArrayList<Integer> colt) {
        this.Page = page;
        this.Data = data2;
        this.Colt = colt;
        this.ChampGraph = page.getActivity().findViewById(R.id.champ_Graph);
    }


    /**
     * La fonction affiche un graphique à barres avec les données d'un tableau JSON basé sur un type
     * spécifié.
     *
     * @param type Le paramètre "type" est une chaîne qui représente le type de données à afficher dans
     * le graphique. Il est utilisé pour extraire les données correspondantes du tableau JSON et les
     * afficher dans le graphique.
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void GraphAffiche(String type){
        // Ce code crée et affiche un graphique à barres basé sur les données fournies au format JSON.
        // Il crée d'abord une étiquette TextView pour afficher le type de graphique affiché, définit
        // ses propriétés et l'ajoute au LinearLayout ChampGraph. Ensuite, il crée un objet BarChart et
        // initialise un tableau2 JSONArray vide. Il remplit ensuite array2 avec les données JSON
        // fournies dans la variable Data. Il crée ensuite une ArrayList d'objets IBarDataSet et
        // parcourt les données JSON pour créer des objets BarEntry pour chaque point de données. Il
        // crée ensuite un objet BarDataSet pour chaque point de données, définit sa couleur et
        // l'ajoute à la ArrayList des objets IBarDataSet. Il crée ensuite un objet BarData à partir de
        // l'ArrayList des objets IBarDataSet et le définit comme données pour l'objet BarChart. Enfin,
        // il définit les paramètres de mise en page de l'objet BarChart et l'ajoute au LinearLayout
        // ChampGraph. S'il y a une erreur dans l'analyse des données JSON, il imprime la trace de la
        // pile.
        TextView label = new TextView(this.Page.getContext());
        label.setText(type);
        label.setTextColor(Color.RED);
        label.setTextSize(50);
        this.ChampGraph.addView(label);
        BarChart mChart = new BarChart(this.Page.getContext());

        JSONArray array2 = null;
        try {
            array2 = new JSONArray(this.Data);
            Random rand = new Random();
            ArrayList<IBarDataSet> sets = new ArrayList<>();
            for(int i = 0; i < array2.length(); i++) {
                JSONObject object = array2.getJSONObject(i);
                ArrayList<BarEntry> entries = new ArrayList<>();
                LocalTime star = LocalTime.parse(object.getString("timeP"));
                entries.add(new BarEntry(i, Float.parseFloat((object.getString(type)))));
                BarDataSet ds = new BarDataSet(entries, object.getString("dateP"));
                ds.setColors(Colt.get(i));
                sets.add(ds);
            }
            BarData d = new BarData(sets);
            mChart.setData(d);
            this.ChampGraph.addView(mChart);
            LinearLayout.LayoutParams prame = (LinearLayout.LayoutParams) mChart.getLayoutParams();
            prame.width = 600;
            prame.height = 500;
            mChart.setLayoutParams(prame);
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }


    /**
     * La fonction supprime toutes les vues d'un graphique en Java.
     */
    public void RemoveGraph(){
        this.ChampGraph.removeAllViews();
    }
}
