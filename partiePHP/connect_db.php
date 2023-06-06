<?php
$SQL_DSN   = 'mysql:host=localhost;dbname=donnee';
//identifiant
$SQL_LOGIN = 'root';
//mot passe
$SQL_PASS  = '';
                                    
try {
    //creation d'une connection
    $pdo = new PDO($SQL_DSN, $SQL_LOGIN, $SQL_PASS);
}
catch( PDOException $e ) {
    echo 'Erreur : '.$e->getMessage();
    exit;
}
?>