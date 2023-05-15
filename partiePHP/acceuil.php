<?php
//on inclue le fichier de connection a la db
include_once "connect_db.php";
include_once "fonction.php";
//on declare et initialise des variable
$count_total = 0;
$count = 0;
//si le token et dans les valeur get
if(isset($_GET['token']) && !empty($_GET['token']) && isset($_GET['ge']) && !empty($_GET['ge'])){
    //on prepare une requette sql
    $requ = $pdo->prepare("select * from admin where id_session='".$_GET['token']."'");
    //on execute la requette sql
    $requ->execute();
    //on recupere les donnee de la requette sql
    $user = $requ->fetchAll(PDO::FETCH_ASSOC);
    //on prepare une requette sql
    if(count($user)>0){
         if($_GET['ge']=="meteo"){
                //on prepare une requette sql
                $requ=$pdo->prepare("select * from data_meteo");
                //on execute la requette sql
                $requ->execute();
                //on recupere les donnee dans une liste
                $lt = $requ->fetchAll(PDO::FETCH_ASSOC); 
                //on converti la liste lt en json
                header('Content-Type: application/json');
                echo json_encode($lt, JSON_PRETTY_PRINT);
            }
            else if($_GET['ge']=="eolienne"){
                //on prepare une requette sql
                $requ=$pdo->prepare("select ideolienne,name from eolienne");
                //on execute la requette sql
                $requ->execute();
                //on recupere les donnee dans une liste
                $lt = $requ->fetchAll(PDO::FETCH_ASSOC); 
                //on converti la liste lt en json
                header('Content-Type: application/json');
                echo json_encode($lt, JSON_PRETTY_PRINT);
            }
            else if($_GET['ge']=="meca"){
                $lt = array("id"=>"");
                //echo "mdr";
                header('Content-Type: application/json');
                echo json_encode($lt, JSON_PRETTY_PRINT);
            }
    }
    else if(count($user)==0){
        echo "erreur";
    }
    
}
//si le get appeler token existe mais n'est pas remplis
else if(isset($_GET['token']) && empty($_GET['token'])){
    //afficher erreur
    echo "erreur";
}
//si le get appeler token n'existe ou qu'il n'existe pas de get
else if(!isset($_GET['token']) && empty($_GET['token']) || !isset($_GET) && empty($_GET)){
    //on vas dans la page index.html
    echo "erreur";
}
?>