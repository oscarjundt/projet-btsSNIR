<?php
//on includ le fichier pour la connection a la db(data base) 
include_once "connect_db.php";
include_once "fonction.php";
//si les get appelez username et password existe et sont remplis
if(isset($_GET['username']) && !empty($_GET['username']) && isset($_GET['password']) && !empty($_GET['password'])){
    $count = 0;
    $id = "";
    $type = "";
    $mail = "";
    //on prepare une requette sql
    $request = $pdo->prepare("select count(id) from admin where id='".strtolower($_GET['username'])."'");
    //on execute la requette sql
    $request->execute();
    //on recupere les donnee de la requette sql
    while($row=$request->fetch()){
        //on met les donnee de la requette dans une variable count
        $count=$row['count(id)'];
    }
    
    //si la variable count est different de 0
    if($count!=0){
        $request = $pdo->prepare("select id,type,mail from admin where id='".strtolower($_GET['username'])."'");
        $request->execute();
        while($row=$request->fetch()){
            $id=$row['id'];
            $type=$row['type'];
            $mail=$row['mail'];
        }
        //on prepare une requette sql
        $request = $pdo->prepare("select password from admin where id='".strtolower($_GET['username'])."'");
        //on execute une requette sql
        $request->execute();
        //on recupere les donnee de la requette sql
        while($row=$request->fetch()){
            //si le get appellez password et egale a la donnee(password) envoyer par la requette sql
            if($row['password']==$_GET['password']){
                //on creer un token
                $sess = "ok".session_create_id();
                //on met le token dans la base de donnee
                $requ = $pdo->prepare("update admin set id_session='$sess',ip='".getIp()."' where id='".$_GET['username']."'");
                $requ->execute();
                $requ = $pdo->prepare("insert into historique_connection (interface,utilisateur,type,action,ip,sysda) values ('api rest','".$id."','".$type."','success connection','".getIp()."',sysdate())");
                $requ->execute();
                //on affiche le token pour que l'aplication moblie la recupere
                echo $sess;
            }
            else{
                $requ = $pdo->prepare("insert into historique_connection (interface,utilisateur,type,action,ip,sysda) values ('api rest','".$id."','".$type."','erreur connection(password)','".getIp()."',sysdate())");
                $requ->execute();
                mail($mail,'alert',"quelqu'un a essayer de rentrer dans votre compte ip : ".getIp()." sur api rest","From:notif@CNR-BTS.com");
            }
        }
    }
    else{
        $requ = $pdo->prepare("insert into historique_connection (interface,utilisateur,type,action,ip,sysda) values ('api rest','".$id."','".$type."','erreur connection(username)','".getIp()."',sysdate())");
        $requ->execute();
        //mail($mail,'alert',"quelqu'un a essayer de rentrer dans votre compte ip : ".getIp()." sur api rest","From:notif@CNR-BTS.com");
    }
}

?>