<?php
include 'db/db_connect.php';
$deviceArray = array();
$response = array();
 
//Check for mandatory parameter DeviceID
if(isset($_GET['DeviceID'])){
	$DeviceID = $GET['DeviceID'];
	//Query to fetch the details of a device
	$query = "SELECT * FROM Devices WHERE DeviceID=?";
	//Prepare the query
	if($stmt = $con->prepare($query)){
		//Bind parameters
		$stmt->bind_param("i",$DeviceID);
		//Exceting MySQL statement
		$stmt->execute();
		//Bind the fetched data to all of the attributes
		$stmt->bind_result($DeviceID, $Name, $Description, $X_coord, $Y_coord, $Z_coord);
		//Check if data got updated
		if($stmt->fetch()){
			//Populate the device array
			$deviceArray["DeviceID"] = $DeviceID;
			$deviceArray["Name"] = $Name;
			$deviceArray["Description"] = $Description;
			$deviceArray["X_coord"] = $X_coord;
			$deviceArray["Y_coord"] = $Y_coord;
			$deviceArray["Z_coord"] = $Z_coord;
			$response["success"] = 1;
			$response["data"]=$deviceArray;
			
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
	$response["message"] = "Missing parameter DeviceID";
}
//Displaying JSON response
echo json_encode($response);
?>