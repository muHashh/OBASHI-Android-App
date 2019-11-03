<?php
include 'db/db_connect.php';
$response = array();
 
//Check for mandatory parameters
if(isset($_POST['name'])&&isset($_POST['description'])&&isset($_POST['X_coord'])&&isset($_POST['Y_coord'])&&isset($_POST['Z_coord'])){
	$name = $_POST['name'];
	$description = $_POST['description'];
	$X_coord = $_POST['X_coord'];
	$Y_coord = $_POST['Y_coord'];
	$Z_coord = $_POST['Z_coord'];
	
	//Query to insert a device
	$query = "INSERT INTO devices(name, description, X_coord, Y_coord, Z_coord) VALUES (?,?,?,?,?)";
	//Prepare the query
	if($stmt = $con->prepare($query)){
		//Bind parameters
		$stmt->bind_param("ssis",$name,$description,$X_coord,$Y_coord,$Z_coord);
		//Exceting MySQL statement
		$stmt->execute();
		//Check if data got inserted
		if($stmt->affected_rows == 1){
			$response["success"] = 1;			
			$response["message"] = "Device Successfully Added";			
			
		}else{
			//Some error while inserting
			$response["success"] = 0;
			$response["message"] = "Error while adding device";
		}					
	}else{
		//Some error while inserting
		$response["success"] = 0;
		$response["message"] = mysqli_error($con);
	}
 
}else{
	//Mandatory parameters are missing
	$response["success"] = 0;
	$response["message"] = "missing mandatory parameters";
}
//Displaying JSON response
echo json_encode($response);
?>