<?php
define('DB_USER', "myUser"); //  db user
define('DB_PASSWORD', ""); //  db password (mention your db password here)
define('DB_DATABASE', "ObashiDB"); //  database name
define('DB_SERVER', "localhost:3306"); //  db server
 
$con = mysqli_connect(DB_SERVER,DB_USER,DB_PASSWORD,DB_DATABASE);
 
// Check connection
if(mysqli_connect_errno()){
	echo "Failed to connect to MySQL: " . mysqli_connect_error();
}
?>