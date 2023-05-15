<?php
$SQL_DSN   = 'mysql:host=localhost;dbname=<nom>';
//identifiant
$SQL_LOGIN = '<username>';
//mot passe
$SQL_PASS  = '<password>';
                                    
try {
    //creation d'une connection
    $pdo = new PDO($SQL_DSN, $SQL_LOGIN, $SQL_PASS);
}
catch( PDOException $e ) {
    echo 'Erreur : '.$e->getMessage();
    exit;
}
?>
