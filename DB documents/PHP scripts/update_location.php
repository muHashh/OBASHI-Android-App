<?php
include 'db/db_connect.php';
$response = array();
 
//Check for mandatory parameters
if(isset($_POST['DeviceID'])&&isset($_POST['X_coord'])&&isset($_POST['Y_coord'])&&isset($_POST['Z_coord'])){
	$DeviceID = $_POST['DeviceID'];
	$X_coord = $_POST['X_coord'];
	$Y_coord = $_POST['Y_coord'];
	$Z_coord = $_POST['Z_coord'];
	
	//Query to update the location of a device
	$query = "UPDATE Devices SET X_coord=?,Y_coord=?,Z_coord=? WHERE DeviceID=?";
	//Prepare the query
	if($stmt = $con->prepare($query)){
		//Bind parameters
		$stmt->bind_param("ssisi",$X_coord,$Y_coord,$Z_coord,$DeviceID);
		//Exceting MySQL statement
		$stmt->execute();
		//Check if data got updated
		if($stmt->affected_rows == 1){
			$response["success"] = 1;			
			$response["message"] = "Device location successfully updated";
			
		}else{
			//When the device is not found
			$response["success"] = 0;
			$response["message"] = "Device not found";
		}					
	}else{
		//Some error while updating
		$response["success"] = 0;
		$response["message"] = mysqli_error($con);
	}
 
}else{
	//Mandatory parameters are missing
	$response["success"] = 0;
	$response["message"] = "Missing mandatory parameters";
}
//Displaying JSON response
echo json_encode($response);
?>