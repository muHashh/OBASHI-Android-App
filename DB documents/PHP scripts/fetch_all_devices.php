<?php
include 'db/db_connect.php';
//Query to select device name and ID for all devices
$query = "SELECT DeviceID, Name FROM Devices";
$result = array();
$devicesArray = array();
$response = array();
//Prepare the query
if($stmt = $con->prepare($query)){
	$stmt->execute();
	//Bind the fetched data to $DeviceID and $Name
	$stmt->bind_result($DeviceID, $Name);
	//Fetch 1 row at a time					
	while($stmt->fetch()){
		//Populate the device array
		$devicesArray["DeviceID"] = $DeviceID;
		$devicesArray["Name"] = $Name;
		$result[]=$devicesArray;
		
	}
	$stmt->close();
	$response["success"] = 1;
	$response["data"] = $result;
	
 
}else{
	//Some error while fetching data
	$response["success"] = 0;
	$response["message"] = mysqli_error($con);
		
	
}
//Display JSON response
echo json_encode($response);
 
?>