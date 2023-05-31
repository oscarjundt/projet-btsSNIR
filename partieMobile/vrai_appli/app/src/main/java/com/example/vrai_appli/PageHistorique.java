/**
 * C'est un fragment qui affiche un bouton. Lorsque le bouton est cliqué, il appelle une fonction qui
 * fait une requête au serveur
 */
/**
 * C'est un fragment qui affiche un bouton. Lorsque le bouton est cliqué, il appelle une fonction qui
 * fait une requête au serveur
 */
package com.example.vrai_appli;

// Importation des bibliothèques nécessaires au fonctionnement du code.
// Importation de la classe Bundle à partir du package android.os.
import android.os.Bundle;

// Importation de l'annotation `NonNull` à partir du package `androidx.annotation`.
import androidx.annotation.NonNull;
// Il importe la classe Fragment du package androidx.fragment.app.
import androidx.fragment.app.Fragment;

// Il importe la classe LayoutInflater du package android.view.
// Il importe la classe View du package android.view.
import android.view.LayoutInflater;
import android.view.View;
// Il importe la classe ViewGroup du package android.view.
import android.view.ViewGroup;

import com.example.vrai_appli.databinding.PageHistoriqueBinding;


/**
 * C'est un fragment qui affiche un bouton qui, lorsqu'il est cliqué, appelle un script PHP sur le
 * serveur pour récupérer les données de la base de données
 */
public class PageHistorique extends Fragment {
    // Une variable qui contiendra l'objet de liaison.
    private PageHistoriqueBinding binding;
    // Une variable qui contiendra l'adresse IP du serveur.
    private String ip;
    // Une variable qui contiendra l'URL à utiliser avec les paramètres GET.
    private String myUrl = "";
    /**
     * > Cette fonction est appelée lors de la création du fragment. Il gonfle la mise en page et
     * renvoie la vue racine
     *
     * @param inflater L'objet LayoutInflater qui peut être utilisé pour gonfler toutes les vues du
     * fragment,
     * @param container La vue parent à laquelle l'interface utilisateur du fragment doit être
     * attachée.
     * @param savedInstanceState S'il n'est pas nul, ce fragment est reconstruit à partir d'un état
     * enregistré précédent, comme indiqué ici.
     * @return La racine de l'objet de liaison.
     */
    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = PageHistoriqueBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    /**
     * Fonction appelée lors de la création de la vue.
     *
     * @param view La vue renvoyée par onCreateView(LayoutInflater, ViewGroup, Bundle).
     * @param savedInstanceState Un objet Bundle contenant l'état précédemment enregistré de
     * l'activité.
     */
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.TextViewMessageInconnu.setVisibility(View.INVISIBLE);
        //on recupere les donnee du MainActivity, le token(identifiant)
        MainActivity activity = (MainActivity) getActivity();
        String token = activity.getToken();
        this.ip = activity.getDomaine();
        // Une fonction qui est appelée lorsque le bouton est cliqué. Il crée une nouvelle AsyncTask et
        //         l'exécute.
        binding.ButtonMeteo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //on donne dans une variable l'url à utiliser avec des parametres de type get
                //les parametres sont
                //les token = identifiant
                //le type de donnees meteo pour les donnees meteo
                myUrl = ip+"/acceuil.php?ge=meteo&token="+token;
                //on lance la AsyncTasks
                Traitement myAsyncTasks = new Traitement();
                //on transmet les donnees à utiliser
                myAsyncTasks.setPage(PageHistorique.this);
                myAsyncTasks.setMyUrl(myUrl);
                myAsyncTasks.setAction("history");
                //on execute
                myAsyncTasks.execute();
            }
        });
    }
    /**
     * Il renvoie l'objet de liaison
     *
     * @return L'objet contraignant.
     */
    public PageHistoriqueBinding getBinding() {
        return binding;
    }

    /**
     * > La fonction onDestroyView() est appelée lorsque le fragment n'est plus attaché à l'activité
     */
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}