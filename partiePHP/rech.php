<?php
//on includ le fichier de connection a la base de donnee
include_once "connect_db.php";
include_once "fonction.php";
$count_total=0;
$count = 0;
//si le token existe et il est remplis
if(isset($_GET['token']) && !empty($_GET['token']) && isset($_GET['ge']) && !empty($_GET['ge'])){
    $requ = $pdo->prepare("select * from admin where id_session='".$_GET['token']."'");
    //on execute la requette sql
    $requ->execute();
    //on recupere les donnee de la requette sql
    $user = $requ->fetchAll(PDO::FETCH_ASSOC);
    //on prepare une requette sql
    if(count($user)>0){
         $date = $_GET['ge'];
            //on prepare une requette sql pour afficher les donnee en fonction de la date donne par l'utilisateur
            $request = $pdo->prepare("select * from data_meteo where dateP like '".$date."%' order by dateP desc");
            //on execute la requette sql
            $request->execute();
            //on met les donne recupere dans une liste
            $lt = $request->fetchAll(PDO::FETCH_ASSOC);
            //on affiche la liste en json
            header('Content-Type: application/json');
            echo json_encode($lt, JSON_PRETTY_PRINT);
    }
    else if(count($user)==0){
        echo "erreur";
    }
}
else if(isset($_GET['ge']) && empty($_GET['ge'])){
    echo "[]";
}
//si le token n'est pas remplis
else if(isset($_GET['token']) && empty($_GET['token'])){
    //afficher erreur
    echo "erreur";
}
else if(!isset($_GET['token']) && empty($_GET['token']) || !isset($_GET) && empty($_GET)){
    echo "erreur";
}
?>