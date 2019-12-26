<?php
include 'db/db_connect.php';
$result = array();
$response = array();
 
//Check for mandatory parameter DeviceID
if(isset($_GET['DeviceID'])){
	$DeviceID = $_GET['DeviceID'];
	//Query to fetch all connections to and from a device, including the name and ID of the data,
	//and the name and ID of the sender and receiver (one of the two is the device with ID $DeviceID)
	$query = "SELECT Data.DataID, Data.Name, Dev1.DeviceID, Dev1.Name, Dev2.DeviceID, Dev2.Name  FROM Data, SendTo, Devices AS Dev1, Devices AS Dev2
	WHERE (SenderID=? OR ReceiverID=?) AND SendTo.DataID = Data.DataID AND SendTo.SenderID = Dev1.DeviceID AND SendTo.ReceiverID = Dev2.DeviceID";
	//Prepare the query
	if($stmt = $con->prepare($query)){
		//Bind parameters
		$stmt->bind_param("ii",$DeviceID, $DeviceID);
		//Executing MySQL statement
		$stmt->execute();
		//Bind the fetched data to all of the attributes
		$stmt->bind_result($DataID, $DataName, $SenderID, $SenderName, $ReceiverID, $ReceiverName);
		//Fetch 1 row at a time
		while($stmt->fetch()){
			//Array that has all connection data
			$connectionArray = array();
			//Populate the array
			$connectionArray["DataID"] = $DataID;
			$connectionArray["Name"] = $DataName;
			//If the device with ID $DeviceID is the sender, add the data from the receiver,
			//otherwise add the data from the sender
			if($DeviceID == $SenderID){
				$connectionArray["ReceiverID"] = $ReceiverID;
				$connectionArray["ReceiverName"] = $ReceiverName;
			}
			else{
				$connectionArray["SenderID"] = $SenderID;
				$connectionArray["SenderName"] = $SenderName;
			}
			$result[]=$connectionArray;
			//Destroy the array
			unset($connectionArray);
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