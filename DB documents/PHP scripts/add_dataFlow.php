<?php
include 'db/db_connect.php';
$response = array();
 
// Check for mandatory parameters
if(isset($_POST['name'])){
	$name = $_POST['name'];
	
	// Query to insert a data flow
	$query = "INSERT INTO data(name) VALUES (?)";
	// Prepare the query
	if($stmt = $con->prepare($query)){
		// Bind parameters
		$stmt->bind_param("s",$name);
		// Executing MySQL statement
		$stmt->execute();
		// Check if data got inserted
		if($stmt->affected_rows == 1){
			$response["success"] = 1;			
			$response["message"] = "Data flow successfully added";			
			
		}else{
			// Some error while inserting
			$response["success"] = 0;
			$response["message"] = "Error while adding data flow";
		}					
	}else{
		// Some error while inserting
		$response["success"] = 0;
		$response["message"] = mysqli_error($con);
	}
 
}else{
	// Mandatory parameters are missing
	$response["success"] = 0;
	$response["message"] = "Missing mandatory parameters";
}
// Displaying JSON response
echo json_encode($response);
?>