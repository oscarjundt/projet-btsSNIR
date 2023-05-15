package com.example.vrai_appli;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.vrai_appli.databinding.PageMapBinding;

import org.osmdroid.config.Configuration;


/**
 * La classe "PageMap" est un fragment Java qui affiche une carte et permet à l'utilisateur de
 * rechercher des emplacements et de zoomer sur leur emplacement actuel.
 */
public class PageMap extends Fragment{
    // `private FragmentPageMapBinding binding;` déclare une variable privée `binding` de type
    // `FragmentPageMapBinding`. Cette variable est utilisée pour accéder aux vues et aux éléments de
    // mise en page définis dans le fichier de mise en page XML associé au fragment `page_map`. Il est
    // initialisé dans la méthode `onCreateView` en gonflant la mise en page et en renvoyant la vue
    // racine. Il est également utilisé dans d'autres méthodes pour accéder et manipuler les vues et
    // les éléments de mise en page.
    private PageMapBinding binding;
    // Une variable qui contiendra l'adresse IP du serveur.
    private String ip;
    // Une variable qui contiendra l'URL à utiliser avec les paramètres GET.
    private String myUrl = "";
    // `carte privée map;` déclare une variable privée `map` de type `Map`. Cette variable est utilisée
    // pour créer une instance de la classe `Map`, qui est définie dans un autre fichier et contient
    // des méthodes pour gérer les tâches liées à la carte telles que le zoom, l'affichage des
    // marqueurs et l'obtention de l'emplacement de l'utilisateur. La variable `map` est initialisée
    // dans la méthode `onViewCreated` en appelant le constructeur `Map` et en transmettant l'instance
    // actuelle du fragment `page_map`. Il est ensuite utilisé tout au long de la classe pour accéder
    // et manipuler la carte.
    private Map map;


    @Override
    // Cette méthode est chargée de gonfler le fichier XML de mise en page associé au fragment
    // `page_map` et de renvoyer la vue racine de la mise en page gonflée. Il prend trois paramètres :
    // `inflater`, `container` et `savedInstanceState`.
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = PageMapBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    /**
     * Cette fonction configure la vue d'une page de carte, y compris la définition de la valeur de
     * l'agent utilisateur, l'obtention du jeton et du domaine de l'utilisateur, la vérification des
     * autorisations de localisation et la configuration des écouteurs de clic de bouton pour
     * rechercher et réinitialiser la carte.
     *
     * @param view La vue qui est créée ou gonflée par le fragment.
     * @param savedInstanceState saveInstanceState est un objet Bundle qui contient l'état précédent du
     * fragment, qui peut être utilisé pour restaurer l'état du fragment s'il a été précédemment
     * détruit et recréé par le système. Il peut être nul s'il n'y avait pas d'état précédent.
     */
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.editTextRechercheEoliennePosition.setText("");
        Configuration.getInstance().setUserAgentValue(BuildConfig.APPLICATION_ID);
        MainActivity activity = (MainActivity) getActivity();
        String token = activity.getToken();
        this.ip = activity.getDomaine();
        this.map = new Map(PageMap.this);
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                binding.buttonGene.setText("erreur: localisation non autorise");
                binding.mapview.setVisibility(View.INVISIBLE);
            }
        }
        else if(ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            if(binding.mapview.getVisibility()==View.INVISIBLE){
                binding.mapview.setVisibility(View.VISIBLE);
                binding.buttonGene.setText("");
            }
            this.map.LocationTraitement();
            //on recupere les donnee du MainActivity, le token(identifiant)
            myUrl = ip+"/map.php?ge=position&token="+token;

            // Ce code crée une nouvelle instance de la classe `Traitement`, définit ses propriétés
            // (`setPage`, `setMyUrl`, `setMapOuver` et `setAction`) avec les valeurs obtenues à partir
            // de l'instance `PageMap` actuelle, puis exécute ` mon objet en utilisant la méthode
            // `execute()`. La classe `Traitement` effectue une tâche en
            // arrière-plan, pour envoyer une requette http et recupere la reponse.
            Traitement my = new Traitement();
            my.setPage(PageMap.this);
            my.setMyUrl(myUrl);
            my.setMapOuver(this.map);
            my.setAction("map");
            my.execute();
            binding.buttonGene.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String valeurIdEolienne = (String) binding.editTextRechercheEoliennePosition.getText().toString();
                    if(!valeurIdEolienne.equals("")){
                        //on recupere les donnee du MainActivity, le token(identifiant)
                        myUrl = ip+"/map.php?ge=position&id="+valeurIdEolienne+"&token="+token;
                        //on lance la AsyncTasks
                        Traitement my = new Traitement();
                        my.setPage(PageMap.this);
                        my.setMyUrl(myUrl);
                        my.setAction("map_rech");
                        my.setMapOuver(map);
                        my.execute();
                    }

                }
            });
            binding.buttonVous.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    map.zoom(map.getLatitude(), map.getLongitude(),20.5);
                }
            });
            binding.buttonReset.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    binding.editTextAffichageCoordonneeEolienne.setText("");
                    binding.editTextRechercheEoliennePosition.setText("");
                }
            });
        }


    }
    /**
     * La fonction renvoie un objet de type FragmentPageMapBinding.
     *
     * @return La méthode renvoie un objet de type `FragmentPageMapBinding`.
     */
    public PageMapBinding getBinding() {
        return binding;
    }
    /**
     * La fonction onPause est appelée lorsque l'activité est en pause et qu'elle met en pause la vue
     * cartographique.
     */
    @Override
    public void onPause() {
        super.onPause();

        binding.mapview.onPause();
    }

    /**
     * La fonction onResume est appelée lorsque l'activité est reprise et elle reprend le MapView.
     */
    @Override
    public void onResume() {
        super.onResume();
        binding.mapview.onResume();
    }
    /**
     * La fonction "onDestroyView" définit l'objet de liaison sur null lorsque la vue est détruite.
     */
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}