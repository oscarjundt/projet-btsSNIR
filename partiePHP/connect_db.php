<?php
$SQL_DSN   = 'mysql:host=localhost;dbname=ryhcmjpm_test';
//identifiant
$SQL_LOGIN = 'ryhcmjpm_pi';
//mot passe
$SQL_PASS  = '_7p5u57ct,u_8Bp:uP';
                                    
try {
    //creation d'une connection
    $pdo = new PDO($SQL_DSN, $SQL_LOGIN, $SQL_PASS);
}
catch( PDOException $e ) {
    echo 'Erreur : '.$e->getMessage();
    exit;
}
?>