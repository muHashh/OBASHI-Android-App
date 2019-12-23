<?php
include 'db/db_connect.php';
$dataArray = array();
$result = array();
$response = array();
 
//Check for mandatory parameter DeviceID
if(isset($_GET['DeviceID'])){
	$DeviceID = $_GET['DeviceID'];
	//Query to fetch all data connections that flow through a device
	$query = "SELECT DISTINCT Data.DataID, Name FROM Data, SendTo WHERE (SenderID=? OR ReceiverID=?) AND SendTo.DataID = Data.DataID";
	//Prepare the query
	if($stmt = $con->prepare($query)){
		//Bind parameters
		$stmt->bind_param("ii",$DeviceID, $DeviceID);
		//Executing MySQL statement
		$stmt->execute();
		//Bind the fetched data to all of the attributes
		$stmt->bind_result($DataID, $Name);
		//Fetch 1 row at a time
		while($stmt->fetch()){
			//Populate the data array
			$dataArray["DataID"] = $DataID;
			$dataArray["Name"] = $Name;
			$result[]=$dataArray;
		}
		$response["success"] = 1;
		$response["data"]=$result;
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