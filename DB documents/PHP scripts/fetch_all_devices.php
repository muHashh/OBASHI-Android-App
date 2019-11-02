<?php
include 'db/db_connect.php';
//Query to select device name
$query = "SELECT name FROM devices";
$result = array();
$devicesArray = array();
$response = array();
//Prepare the query
if($stmt = $con->prepare($query)){
	$stmt->execute();
	//Bind the fetched data to $name
	$stmt->bind_result($name);
	//Fetch 1 row at a time					
	while($stmt->fetch()){
		//Populate the device array
		$devicesArray["name"] = $name;
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