package com.example.vrai_appli;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.vrai_appli.databinding.PageRechercheBinding;

/**
 * Cette classe est la classe du fichier page_recherche.xml.
 */
public class PageRecherche extends Fragment {
    // Variable utilisée pour accéder à la mise en page du fragment.
    private PageRechercheBinding binding;
    // Une variable qui sera utilisée pour stocker l'adresse IP du serveur.
    private String ip;
    // Une variable qui sera utilisée pour stocker l'url du serveur.
    private String myUrl = "";
    /**
     * > Cette fonction est appelée lors de la création du fragment. Il gonfle la mise en page et
     * renvoie la vue racine
     *
     * @param inflater L'objet LayoutInflater qui peut être utilisé pour gonfler toutes les vues du
     * fragment,
     * @param container La vue parent à laquelle l'interface utilisateur du fragment doit être
     * attachée.
     * @param savedInstanceState Si le fragment est recréé à partir d'un état enregistré précédent, il
     * s'agit de l'état.
     * @return La racine de l'objet de liaison.
     */
    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = PageRechercheBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    /**
     * Cette fonction est appelée lors de la création de la vue. Il récupère le jeton et l'adresse du
     * serveur à partir de MainActivity. Il crée également l'événement onClick pour le bouton de
     * recherche. Lorsque le bouton est cliqué, la date saisie par l'utilisateur est récupérée et l'URL
     * de l'API est créée. La classe AsynTasks est appelée et l'URL de l'API et la classe
     * BlankFragment2 sont insérées. Les AsynTasks sont ensuite exécutées.
     *
     * @param view La vue renvoyée par onCreateView(LayoutInflater, ViewGroup, Bundle).
     * @param savedInstanceState Si le fragment est recréé à partir d'un état enregistré précédent, il
     * s'agit de l'état.
     */
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //on recupere les donnee qui sont dans le MainActivity le token et l'adresse du serveur
        MainActivity activity = (MainActivity) getActivity();
        String token = activity.getToken();
        this.ip = activity.getDomaine();
        //on creer la fonction event onCLick du bouton buttonDeRecherche
        binding.ButtonDeRecherche.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //on recupere la date qui a etait inserer par l'utilisateur
                String valeurDate = binding.ChampDate.getText().toString();
                //on recupere dans une variable l'url de l'api rest avec notament des parametre get
                //les paramete sont le token et la date donner par l'user
                myUrl = ip+"/rech.php?ge="+valeurDate+"&token="+token;
                //on convoque la classe pour l'AsynTasks
                Traitement ASyn = new Traitement();
                //on insert des valeur comme l'url de l'api et la classe BlanKFragment2
                ASyn.setMyUrl(myUrl);
                ASyn.setPage(PageRecherche.this);
                ASyn.setAction("recherche");
                //on execute l'AsynTasks
                ASyn.execute();
            }
        });
    }
    /**
     * Il renvoie l'objet de liaison
     *
     * @return L'objet contraignant.
     */
    public PageRechercheBinding getBinding() {
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