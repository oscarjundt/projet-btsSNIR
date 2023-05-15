package com.example.vrai_appli;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.vrai_appli.databinding.PageLoginBinding;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Cette classe est la page de connexion de l'application.
 */
public class PageLogin extends Fragment {
    //on declare les variable
    // Création d'un objet de liaison pour le fichier de mise en page.
    private PageLoginBinding binding;
    // Déclarer une variable appelée ip et l'initialise avec une chaîne vide.
    private String ip = "";
    // Déclarer une variable appelée myUrl et lui affecter une chaîne vide.
    private String myUrl = "";
    // Une variable qui est utilisée pour stocker l'identifiant de l'utilisateur.
    private String id = "";

    /**
     * Cette fonction renvoie l'objet de liaison pour cette page.
     *
     * @return L'objet contraignant.
     */
    public PageLoginBinding getBinding() {
        return binding;
    }

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
     * @return La vue racine de l'objet de liaison.
     */
    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        binding = PageLoginBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }
    public StringBuilder EncryptPassword(String passwd){
        MessageDigest msg = null;
        try {
            //on choisie le type d'algo de hashage
            msg = MessageDigest.getInstance("SHA1");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        //on hash avec l'algo choisie
        byte[] hash = msg.digest(passwd.getBytes(StandardCharsets.UTF_8));
        // convertir bytes en hexadécimal
        StringBuilder s = new StringBuilder();
        for (byte b : hash) {
            s.append(Integer.toString((b & 0xff) + 0x100, 16).substring(1));
        }
        return s;
    }
    /**
     * La fonction appelée lorsque l'utilisateur arrive sur la page login
     *
     * @param view La vue renvoyée par onCreateView(LayoutInflater, ViewGroup, Bundle).
     * @param savedInstanceState Un objet Bundle contenant l'état précédemment enregistré de
     * l'activité.
     */
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // Conversion de l'activité en MainActivity.
        MainActivity t = (MainActivity) getActivity();
        // Obtenir le nom de domaine qui est dans la classe MainActivity.
        this.ip = t.getDomaine();
        // C'est le code qui est exécuté lorsque l'utilisateur clique sur le bouton de connexion.
        binding.buttonFirst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //on recupere l'username et le password ecrie par l'utilisateur dans des champ de texte
                String password = binding.editTextTextPassword.getText().toString();
                String username = binding.editTextUsername.getText().toString();
                //partie de hachage du password
                //on donne dans une variable l'url a utiliser
                myUrl = ip + "/login.php?username=" + username + "&password=" + EncryptPassword(password);
                //on lence la AsyncTasks
                Traitement myAsyncTasks = new Traitement();
                //on transmet les donnee a utiliser
                myAsyncTasks.setPage(PageLogin.this);
                myAsyncTasks.setMyUrl(myUrl);
                myAsyncTasks.setAction("login");
                //on execute
                myAsyncTasks.execute();
                //on affiche sur l'apli les donnee recuper

                //on execute
            }
        });
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