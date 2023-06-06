package com.example.vrai_appli;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.vrai_appli.databinding.ActivityMainBinding;

//import kotlinx.coroutines.scheduling.Task;

/**
 * Il s'agit de la classe d'activité principale d'une application Android qui gère la navigation, les
 * autorisations et les paramètres.
 */
public class MainActivity extends AppCompatActivity {
    private static final int REQUEST_PERMISSIONS_REQUEST_CODE = 1;
    private AppBarConfiguration appBarConfiguration;
    private String Token;
    //private String domaine = "http://172.17.50.132/ihm-web/appi";
    private String domaine = "https://projet-bts.go.yj.fr/appi";
    //private String domaine = "http://54.89.253.51/appi";
    //private String domaine = "http://192.168.1.14:8000/projet_carla/appi";
    private String[] header = {"ideolienne","Dvent","Vvent","dateP","timeP"};
    private String[] header_eolienne = {"ideolienne","name"};
    public String[] getHeader_eolienne() {
        return header_eolienne;
    }

    public String[] getHeader() {
        return header;
    }
    public String getDomaine() {
        return domaine;
    }

    @Override
    // Il s'agit de la méthode `onCreate()` de la classe `MainActivity` dans une application Android.
    // Il est responsable de l'initialisation de l'activité et de la configuration de l'interface
    // utilisateur.
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
        requestPermissionsIfNecessary(new String[]{
                Manifest.permission.ACCESS_COARSE_LOCATION,
                // if you need to show the current location, uncomment the line below
                Manifest.permission.ACCESS_FINE_LOCATION,
                // WRITE_EXTERNAL_STORAGE is required in order to show the map
                Manifest.permission.WRITE_EXTERNAL_STORAGE
        });
        //requestPermissionsIfNecessary(new String[]{Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.WRITE_EXTERNAL_STORAGE});
        /*
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel t = new NotificationChannel("my","hello", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager t2 = getSystemService(NotificationManager.class);
            t2.createNotificationChannel(t);
            NotificationCompat.Builder buol = new NotificationCompat.Builder(this,"my").setSmallIcon(android.R.drawable.stat_notify_sync).setContentTitle("hello").setContentText("hello");
            NotificationManagerCompat.from(this).notify(1,buol.build());
        }
        */
        com.example.vrai_appli.databinding.ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.toolbar);
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
        NavigationUI.setupActionBarWithNavController(   this, navController, appBarConfiguration);


    }
    /**
     * La fonction renvoie un jeton de chaîne.
     *
     * @return La méthode `getToken()` renvoie une valeur `String`, qui est la valeur de la variable
     * `Token`.
     */
    public String getToken() {
        return Token;
    }

    /**
     * Cette fonction définit la valeur d'une variable appelée "Token" sur le paramètre d'entrée
     * "token".
     *
     * @param token Le paramètre "token" est une variable de chaîne qui représente un jeton. La méthode
     * "setToken" fixe la valeur de la variable d'instance "Token" à la valeur du paramètre "token".
     */
    public void setToken(String token) {
        Token = token;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }
    /**
     * Cette fonction vérifie si une liste d'autorisations est accordée et les demande si nécessaire.
     *
     * @param permissions Un tableau de chaînes d'autorisation que l'application doit demander à
     * l'utilisateur.
     */
    private void requestPermissionsIfNecessary(String[] permissions) {
        int tr = 0;
        for (int i=0;i<permissions.length;i++) {
            String permission = permissions[i];
            if (ContextCompat.checkSelfPermission(this, permission)
                    != PackageManager.PERMISSION_GRANTED) {
                // Permission is not granted
                tr++;
            }
        }
        System.out.println(tr);
        if ((tr == permissions.length || tr<permissions.length) && tr!=0) {
            ActivityCompat.requestPermissions(
                    this,
                    permissions,
                    REQUEST_PERMISSIONS_REQUEST_CODE);
        }
    }
}
