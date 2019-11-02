<?php
include 'db/db_connect.php';
$response = array();
 
//Check for mandatory parameters
if(isset($_POST['senderID'])&&isset($_POST['receiverID'])&&isset($_POST['dataID'])){
	$senderID = $_POST['senderID'];
	$receiverID = $_POST['receiverID'];
	$dataID = $_POST['dataID'];
	
	//Query to insert a send to relation
	$query = "INSERT INTO sendto(senderID, receiverID, dataID) VALUES (?,?,?)";
	//Prepare the query
	if($stmt = $con->prepare($query)){
		//Bind parameters
		$stmt->bind_param("ssis",$senderID,$receiverID,$dataID);
		//Exceting MySQL statement
		$stmt->execute();
		//Check if data got inserted
		if($stmt->affected_rows == 1){
			$response["success"] = 1;			
			$response["message"] = "Send to relation Successfully Added";			
			
		}else{
			//Some error while inserting
			$response["success"] = 0;
			$response["message"] = "Error while adding send to relation";
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