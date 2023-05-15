package com.example.vrai_appli;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.osmdroid.api.IMapController;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;

import java.util.ArrayList;

/**
 * La classe Map est une classe Java qui gère l'affichage et la manipulation d'une vue cartographique,
 * y compris les marqueurs et le suivi de l'emplacement.
 */
public class Map{
    // Ce sont des variables d'instance de la classe `Map` en Java. Ils sont utilisés pour stocker et
    // accéder aux données et aux objets au sein de la classe.

    // `private Fragment page;` déclare une variable d'instance privée `page` de type `Fragment`. Cette
    // variable est utilisée pour stocker une référence au fragment actuel dans lequel la carte est
    // affichée. Il permet à la classe `Map` d'accéder au contexte et aux vues du fragment, nécessaires
    // à l'affichage et à la manipulation de la carte.
    private Fragment page;
    // `private Marker vous;` déclare une variable d'instance privée `vous` de type `Marker`. Cette
    // variable est utilisée pour stocker une référence à un objet marqueur qui représente
    // l'emplacement actuel de l'utilisateur sur la carte. Il est utilisé pour mettre à jour la
    // position du marqueur lorsque l'emplacement de l'utilisateur change et pour afficher le marqueur
    // sur la carte.
    private Marker vous;
    private IMapController ma;
    // `private ProgressDialog prog;` déclare une variable d'instance privée `prog` de type
    // `ProgressDialog`. Cette variable est utilisée pour stocker une référence à une boîte de dialogue
    // de progression qui s'affiche pendant que l'application recherche l'emplacement de l'utilisateur.
    // Il permet à l'utilisateur de savoir que l'application fonctionne et n'est pas figée. La variable
    // est déclarée comme privée pour s'assurer qu'elle n'est accessible qu'au sein de la classe `Map`.
    private ProgressDialog prog;
    // `private MapView map;` déclare une variable d'instance privée `map` de type `MapView`. Cette
    // variable est utilisée pour stocker une référence à l'objet de la vue cartographique qui
    // s'affiche à l'écran. Il permet à la classe "Map" d'accéder à la vue de la carte et de la
    // manipuler, par exemple en définissant le niveau de zoom et en y ajoutant des marqueurs.
    private MapView map;
    // `private LocationManager lm;` déclare une variable d'instance privée `lm` de type
    // `LocationManager`. Cette variable est utilisée pour stocker une référence au service système
    // "LocationManager", qui permet d'accéder aux services de localisation de l'appareil. Il est
    // utilisé dans la méthode `LocationTraitement()` pour demander des mises à jour de localisation et
    // récupérer la localisation actuelle de l'utilisateur.
    private LocationManager lm;
    // `private double latitude;` déclare une variable d'instance privée `latitude` de type `double`.
    // Cette variable est utilisée pour stocker la valeur de latitude de l'emplacement actuel de
    // l'utilisateur sur la carte. Il est accessible et modifiable dans la classe `Map`.
    private double latitude;
    // `private double longitude;` déclare une variable d'instance privée `longitude` de type `double`
    // dans la classe `Map`. Cette variable est utilisée pour stocker la valeur de longitude de
    // l'emplacement actuel de l'utilisateur sur la carte. Il est accessible et modifiable dans la
    // classe `Map`.
    private double longitude;

    /**
     * La fonction renvoie la valeur de latitude sous la forme d'un double.
     *
     * @return La méthode renvoie une valeur double qui représente la latitude.
     */
    public double getLatitude() {
        return latitude;
    }

    /**
     * La fonction renvoie la valeur de longitude sous la forme d'un double.
     *
     * @return La méthode renvoie une valeur double qui représente la longitude.
     */
    public double getLongitude() {
        return longitude;
    }

    // Il s'agit d'une méthode constructeur pour la classe `Map` en Java. Il prend un objet `Fragment`
    // `page` comme paramètre et initialise les variables d'instance de la classe `Map`.
    public Map(Fragment page) {
        // Le code ci-dessus configure une vue cartographique dans une application Android à l'aide de
        // la bibliothèque OpenStreetMap. Il définit le niveau de zoom et le point central de la carte
        // et initialise un LocationManager pour gérer les mises à jour de localisation.
        this.page = page;
        this.map = this.page.getActivity().findViewById(R.id.mapview);
        IMapController mapController = this.map.getController();
        mapController.setZoom(11.5);
        GeoPoint startPoint = new GeoPoint(45.7578137, 4.8320114);
        mapController.setCenter(startPoint);
        this.lm = (LocationManager) page.getActivity().getSystemService(Context.LOCATION_SERVICE);

    }
    /**
     * Cette fonction définit le niveau de zoom et le centre d'une carte en utilisant la latitude, la
     * longitude et la taille du zoom.
     *
     * @param Lat Latitude du lieu sur lequel zoomer.
     * @param Long Le paramètre "Long" dans ce code fait référence à la coordonnée de longitude d'un
     * emplacement sur une carte. La longitude est une coordonnée géographique qui spécifie la position
     * est-ouest d'un point sur la surface de la Terre, mesurée en degrés à partir du premier méridien
     * (qui est une ligne de longitude qui passe par le Royal
     * @param zoom_taille Niveau de zoom à définir pour la carte. C'est une valeur double.
     */
    public void zoom(double Lat,double Long,double zoom_taille){
        // Le code ci-dessus définit le niveau de zoom et le point central d'une carte dans une
        // application Java.
        IMapController mapController = this.map.getController();
        mapController.setZoom(zoom_taille);
        GeoPoint startPoint = new GeoPoint(Lat, Long);
        mapController.setCenter(startPoint);
    }
    /**
     * La fonction met à jour l'emplacement de l'utilisateur sur une carte à l'aide du GPS et affiche
     * un marqueur à l'emplacement mis à jour.
     */
    @SuppressLint("MissingPermission")
    public void LocationTraitement() {
        // Le code ci-dessus crée un marqueur sur une carte et définit ses propriétés telles que la
        // couleur d'arrière-plan de l'étiquette de texte, la couleur de premier plan, la taille de la
        // police, l'icône de texte et la visibilité. Il essaie ensuite d'obtenir l'emplacement actuel
        // de l'utilisateur à l'aide du fournisseur GPS et définit la position du marqueur sur
        // l'emplacement de l'utilisateur. Si l'emplacement ne peut pas être obtenu, une boîte de
        // dialogue de progression s'affiche. Le code configure également un écouteur d'emplacement
        // pour mettre à jour la position du marqueur lorsque l'emplacement de l'utilisateur change.
        this.vous = new Marker(this.map);
        vous.setTextLabelBackgroundColor(
                Color.TRANSPARENT
        );
        vous.setTextLabelForegroundColor(
                Color.BLUE
        );
        vous.setTextLabelFontSize(30);
        vous.setTextIcon("vous");
        vous.setVisible(false);
        vous.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_TOP);
        this.map.getOverlays().add(this.vous);
        try {
            Location tr = this.lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            latitude = (double) (tr.getLatitude());
            System.out.println("la2:" + latitude);
            longitude = (double) (tr.getLongitude());
            System.out.println("lon2:" + longitude);
            vous.setPosition(new GeoPoint(latitude, longitude));
            vous.setTitle(Double.toString(latitude) + ":" + Double.toString(longitude));
            vous.setVisible(true);
        } catch (Exception e) {
            this.prog = new ProgressDialog(page.getContext());
            this.prog.setMessage("recherche position");
            this.prog.setCancelable(true);
            this.prog.show();
        }
        // Le code ci-dessus demande des mises à jour de localisation au fournisseur GPS à l'aide de la
        // classe LocationManager en Java. Il définit le temps et la distance minimum entre les mises à
        // jour de localisation sur 0 et crée un nouveau LocationListener pour gérer les mises à jour.
        // Lorsqu'un nouvel emplacement est reçu, la méthode onLocationChanged est appelée, ce qui met
        // à jour les variables de latitude et de longitude, définit la position et le titre d'un objet
        // GeoPoint et le rend visible. Les autres méthodes de l'interface LocationListener sont vides
        // et n'exécutent aucune action.
        this.lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, new LocationListener() {
            @Override
            public void onLocationChanged(@NonNull Location location) {
                // Le code ci-dessus est écrit en Java et effectue les tâches suivantes :
                if (prog != null) {
                    prog.dismiss();
                }
                // Le code ci-dessus définit les valeurs de latitude et de longitude d'un objet de
                // localisation, crée un nouvel objet GeoPoint avec ces valeurs, définit le titre d'un
                // marqueur avec les valeurs de latitude et de longitude et rend le marqueur visible
                // sur une carte.
                latitude = (double) (location.getLatitude());
                System.out.println("la2:" + latitude);
                longitude = (double) (location.getLongitude());
                System.out.println("lon2:" + longitude);
                vous.setPosition(new GeoPoint(latitude, longitude));
                vous.setTitle(Double.toString(latitude) + ":" + Double.toString(longitude));
                vous.setVisible(true);
            }

            @Override
            public void onProviderEnabled(@NonNull String provider) {

            }

            @Override
            public void onProviderDisabled(@NonNull String provider) {

            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }
        });
    }
    /**
     * Il s'agit d'une fonction Java qui affiche une carte et des marqueurs basés sur les données
     * d'entrée et l'action.
     *
     * @param data Il s'agit d'un paramètre de chaîne qui contient des données au format JSON. Le
     * contenu des données dépend du paramètre d'action.
     * @param action Le paramètre "action" est une variable String qui permet de déterminer le type
     * d'action à effectuer dans la méthode MapAffiche. Il peut avoir deux valeurs possibles : "rech"
     * ou "tout".
     */
    public void MapAffiche(String data,String action){
        // Le code ci-dessus est écrit en Java et vérifie la valeur d'une variable nommée "action". Si
        // la valeur de "action" est "rech", il récupère un objet MapView et définit son niveau de zoom
        // et son point central en fonction des valeurs de latitude et de longitude obtenues à partir
        // d'un objet JSONArray. Si la valeur de "action" est "tout", il récupère un objet MapView et
        // crée des marqueurs sur la carte en fonction des valeurs de latitude et de longitude obtenues
        // à partir d'un JSONObject. Il définit également les écouteurs de clic sur les marqueurs pour
        // afficher leurs coordonnées dans les champs EditText.
        if(action.equals("rech")){
            MapView map = page.getActivity().findViewById(R.id.mapview);
            JSONArray array;
            try {
                array = new JSONArray(data);
                for (int i = 0; i < array.length(); i++) {
                    JSONObject val = array.getJSONObject(i);
                    IMapController mapController = map.getController();
                    mapController.setZoom(15.5);
                    GeoPoint startPoint = new GeoPoint(Double.parseDouble(val.getString("Latitude")), Double.parseDouble(val.getString("Longitude")));
                    mapController.setCenter(startPoint);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        else if(action.equals("tout")){
            MapView map = page.getActivity().findViewById(R.id.mapview);

            JSONObject liste = null;
            JSONObject valeur = null;
            ArrayList<double[]> te = new ArrayList<>();
            try {
                String chaine_coo = "";
                liste = new JSONObject(data);
                for (int i = 1; i <= liste.length(); i++) {
                    valeur = new JSONObject(liste.getString(Integer.toString(i)));
                    te.add(new double[]{Double.parseDouble(valeur.getString("Latitude")), Double.parseDouble(valeur.getString("Longitude"))});
                }
                ArrayList<Marker> m = new ArrayList<>();
                for (int i = 0; i < te.size(); i++) {
                    m.add(new Marker(map));
                    int title = i+1;
                    m.get(i).setOnMarkerClickListener(new Marker.OnMarkerClickListener() {
                        @Override
                        public boolean onMarkerClick(Marker marker, MapView mapView) {
                            EditText valeur_id = page.getActivity().findViewById(R.id.editTextRechercheEoliennePosition);
                            valeur_id.setText(Integer.toString(title));
                            EditText valeur_coord = page.getActivity().findViewById(R.id.editTextAffichageCoordonneeEolienne);
                            valeur_coord.setText(marker.getTitle());
                            return false;
                        }
                    });
                    m.get(i).setPosition(new GeoPoint(te.get(i)[0], te.get(i)[1]));
                    m.get(i).setTextLabelBackgroundColor(
                            Color.TRANSPARENT
                    );
                    m.get(i).setTextLabelForegroundColor(
                            Color.RED
                    );
                    m.get(i).setTextLabelFontSize(40);
                    m.get(i).setTextIcon(Integer.toString(title));
                    m.get(i).setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_TOP);
                    m.get(i).setTitle(Double.toString(te.get(i)[0]) + "::" + Double.toString(te.get(i)[1]));
                    map.getOverlays().add(m.get(i));
                }
            /*
            for(int i=0;i<coordonne.size();i++){

            }
            /*
            for (int i = 1; i <= tr.length(); i++) {
                JSONObject object = array2.getJSONObject(i);
                 tr.add(object.getString(Integer.toString(i)));
            }

             */
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
