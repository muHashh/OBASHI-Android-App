<?php
include 'db/db_connect.php';
// Query to select device name and ID for all devices
$query = "SELECT DeviceID, Name FROM Devices";
$devices = array(); // Array where each element is a JSON map containing the DeviceID and name of a device
$response = array();
// Prepare the query
if($stmt = $con->prepare($query)){
	$stmt->execute();
	// Bind the fetched data to $DeviceID and $Name
	$stmt->bind_result($DeviceID, $Name);
	$deviceArray = array();
	// Fetch 1 row at a time					
	while($stmt->fetch()){
		// Populate the device array
		$deviceArray["DeviceID"] = $DeviceID;
		$deviceArray["Name"] = $Name;
		// Add the device array to devices
		$devices[]=$deviceArray;
		
	}
	$stmt->close();
	$response["success"] = 1;
	$response["data"] = $devices;
	
 
}else{
	// Some error while fetching data
	$response["success"] = 0;
	$response["message"] = mysqli_error($con);
		
	
}
// Display JSON response
echo json_encode($response);
?>