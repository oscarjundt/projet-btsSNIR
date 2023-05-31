package com.example.vrai_appli;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.vrai_appli.databinding.PageEolienneBinding;


/**
 * Il s'agit d'une classe Java pour un fragment qui gonfle une mise en page et envoie des données à un
 * serveur à l'aide d'AsyncTask.
 */
public class PageEolienne extends Fragment {
    // Variable utilisée pour accéder à la mise en page du fragment.
    private PageEolienneBinding binding;
    // Une variable qui contiendra l'adresse IP du serveur.
    private String ip;
    // Une variable qui contiendra l'URL à utiliser avec les paramètres GET.
    private String myUrl = "";
    @Override
    // Gonflement de la mise en page et retour de la vue racine.
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = PageEolienneBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }
    /**
     * Cette fonction est utilisée pour créer une nouvelle page et nous envoyer vers une nouvelle page.
     *
     * @param view La vue renvoyée par onCreateView(LayoutInflater, ViewGroup, Bundle).
     * @param savedInstanceState Si le fragment est recréé à partir d'un état enregistré précédent, il
     * s'agit de l'état.
     */
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //on recupere les donnee du MainActivity la on recupere le token(identifiant)
        binding.TextViewMessageInconnu.setVisibility(View.INVISIBLE);
        MainActivity activity = (MainActivity) getActivity();
        String token = activity.getToken();
        this.ip = activity.getDomaine();
        //on creer la fonction event onClick sur le bouton appeler history
        //cette fonction cerre a nous envoyer verre une nouvelle page appeler blankFragment
        myUrl = ip+"/acceuil.php?ge=eolienne&token="+token;
        //on lance la AsyncTasks
        Traitement myAsyncTasks = new Traitement();
        //on transmet les donnees à utiliser
        myAsyncTasks.setPage(PageEolienne.this);
        myAsyncTasks.setMyUrl(myUrl);
        myAsyncTasks.setAction("eolienne");
        //on execute
        myAsyncTasks.execute();

    }

    @Override
    // Méthode appelée lorsque la vue est détruite. Il est utilisé pour libérer de la mémoire.
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}