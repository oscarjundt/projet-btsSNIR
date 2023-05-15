package com.example.vrai_appli;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;


import com.example.vrai_appli.databinding.PageMenuBinding;

/**
 * La classe est une sous-classe de Fragment. Il a une méthode appelée onCreateView qui renvoie une vue
 */
public class PageMenu extends Fragment {
    // Variable utilisée pour accéder à la mise en page du fragment.
    private PageMenuBinding binding;
    @Override
    // Gonflement de la mise en page et retour de la vue racine.
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = PageMenuBinding.inflate(inflater, container, false);
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
        MainActivity activity = (MainActivity) getActivity();
        System.out.println(activity.getToken());
        //on creer la fonction event onClick sur le bouton appeler history
        //cette fonction cerre a nous envoyer verre une nouvelle page appeler blankFragment
        binding.ButtonHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(PageMenu.this)
                        .navigate(R.id.PageHistorique);
            }
        });

        // nous envoye vers cette nouvelle page.
        binding.ButtonRecherche.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(PageMenu.this)
                        .navigate(R.id.PageRecherche);
            }
        });
        // Ce code définit un onClickListener sur le bouton "bouttonMap" dans la mise en page associée
        // au fragment "page_menu". Lorsque le bouton est cliqué, il navigue vers la destination
        // "page_map" en utilisant le NavController.
        binding.BouttonMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(PageMenu.this)
                        .navigate(R.id.PageMap);
            }
        });
        // Ce code définit un onClickListener sur le bouton avec l'ID "buttonEol" dans la mise en page
        // associée au fragment "page_menu". Lorsque le bouton est cliqué, il navigue vers la
        // destination "page_eolienne" à l'aide du NavController.
        binding.ButtonEolienne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavHostFragment.findNavController(PageMenu.this)
                        .navigate(R.id.PageEolienne);
            }
        });
    }

    @Override
    // Méthode appelée lorsque la vue est détruite. Il est utilisé pour libérer de la mémoire.
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}