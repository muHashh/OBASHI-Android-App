<?php
include 'db/db_connect.php';
$devices = [['Lenovo ThinkPad P71', 'A laptop', '2', '1', '0'],
           ['Samsung Printer', 'A printer', '3', '6', '-1'],
           ['iPhone 8', 'A phone', '-2', '2', '2'],
           ['Surface Pro 8', 'A laptop', '4', '-3', '0'],
           ['Main server', 'The server', '0', '4', '1']];
$data = ['Request to mainframe', 'Response from mainframe',
        'Printing request', 'Phone backup'];
$connections = [['Lenovo ThinkPad P71', 'Main server', 'Request to mainframe'],
               ['Surface Pro 8', 'Main server', 'Request to mainframe'],
               ['Main server', 'Lenovo ThinkPad P71', 'Response from mainframe'],
               ['Main server', 'Surface Pro 8', 'Response from mainframe'],
               ['Lenovo ThinkPad P71', 'Samsung Printer', 'Printing request'],
               ['Surface Pro 8', 'Samsung Printer', 'Printing request'],
               ['Surface Pro 8', 'iPhone 8', 'Phone backup']];

// Add each device in $devices
foreach($devices as $device){
	$query = "SELECT * FROM Devices WHERE Name = ?";
	$stmt = $con->prepare($query);
	// Bind parameters
	$stmt->bind_param("s",$device[0]);
	// Executing MySQL statement
	$stmt->execute();
	// If no device found, insert device
	if(!$stmt->fetch()){
		// Query to insert a device
		$query = "INSERT INTO Devices(name, description, X_coord, Y_coord, Z_coord) VALUES (?,?,?,?,?)";
		$stmt = $con->prepare($query);
		// Bind parameters
		$stmt->bind_param("ssddd",$device[0],$device[1],$device[2],$device[3],$device[4]);
		// Executing MySQL statement
		$stmt->execute();
		if($stmt->affected_rows == 1){
			echo "Device with name ";
			echo $device[0];
			echo " successfully added\n";
		}
		else{
			echo "Failure to add device with name ";
			echo $device[0];
			echo "\n";
		}
	}
	else{
		echo "Device with name ";
		echo $device[0];
		echo " already on database\n";
	}
	// Reset $stmt. Don't know why, but it needs this to work properly
	$stmt = '';
}

// Add each data item in $data
foreach($data as $d){
	$query = "SELECT * FROM Data WHERE Name = ?";
	$stmt = $con->prepare($query);
	// Bind parameters
	$stmt->bind_param("s",$d);
	// Executing MySQL statement
	$stmt->execute();
	// If no data item found, insert data item
	if(!$stmt->fetch()){
		// Query to insert a data item
		$query = "INSERT INTO Data(name) VALUES (?)";
		$stmt = $con->prepare($query);
		// Bind parameters
		$stmt->bind_param("s",$d);
		// Executing MySQL statement
		$stmt->execute();
		if($stmt->affected_rows == 1){
			echo "Data with name ";
			echo $d;
			echo " successfully added\n";
		}
		else{
			echo "Failure to add data with name ";
			echo $d;
			echo "\n";
		}
	}
	else{
		echo "Data with name ";
		echo $d;
		echo " already on database\n";
	}
	// Reset $stmt. Don't know why, but it needs this to work properly
	$stmt = '';
}

// Add each connection in $connections
foreach($connections as $connection){
	$SenderID = -1;
	$ReceiverID = -1;
	$DataID_sendto = -1;
	
	// Get the DeviceID of the sender
	$query = "SELECT DeviceID FROM Devices WHERE Name = ?";
	$stmt = $con->prepare($query);
	// Bind parameters
	$stmt->bind_param("s",$connection[0]);
	// Executing MySQL statement
	$stmt->execute();
	// Bind the fetched data to all of the attributes
	$stmt->bind_result($DeviceID);
	if($stmt->fetch()){
		$SenderID = $DeviceID;
	}
	// Reset $stmt. Don't know why, but it needs this to work properly
	$stmt = '';
	
	// Get the DeviceID of the sender
	$query = "SELECT DeviceID FROM Devices WHERE Name = ?";
	$stmt = $con->prepare($query);
	// Bind parameters
	$stmt->bind_param("s",$connection[1]);
	// Executing MySQL statement
	$stmt->execute();
	// Bind the fetched data to all of the attributes
	$stmt->bind_result($DeviceID);
	if($stmt->fetch()){
		$ReceiverID = $DeviceID;
	}
	// Reset $stmt. Don't know why, but it needs this to work properly
	$stmt = '';
	
	// Get the DataID of the data item
	$query = "SELECT DataID FROM Data WHERE Name = ?";
	$stmt = $con->prepare($query);
	// Bind parameters
	$stmt->bind_param("s",$connection[2]);
	// Executing MySQL statement
	$stmt->execute();
	// Bind the fetched data to all of the attributes
	$stmt->bind_result($DataID);
	if($stmt->fetch()){
		$DataID_sendto = $DataID;
	}
	// Reset $stmt. Don't know why, but it needs this to work properly
	$stmt = '';
	
	$query = "SELECT * FROM SendTo WHERE SenderID = ? AND ReceiverID = ? AND DataID = ?";
	$stmt = $con->prepare($query);
	// Bind parameters
	$stmt->bind_param("iii",$SenderID, $ReceiverID, $DataID_sendto);
	// Executing MySQL statement
	$stmt->execute();
	// If no connection found, insert connection
	if(!$stmt->fetch()){
		// Query to insert a data item
		$query = "INSERT INTO SendTo(SenderID, ReceiverID, DataID) VALUES (?,?,?)";
		$stmt = $con->prepare($query);
		// Bind parameters
		$stmt->bind_param("iii",$SenderID, $ReceiverID, $DataID_sendto);
		// Executing MySQL statement
		$stmt->execute();
		if($stmt->affected_rows == 1){
			echo "Conection with sender ";
			echo $connection[0];
			echo ", receiver ";
			echo $connection[1];
			echo " and data ";
			echo $connection[2];
			echo " successfully added\n";
		}
		else{
			echo "Failure to add data with sender ";
			echo $connection[0];
			echo ", receiver ";
			echo $connection[1];
			echo " and data ";
			echo $connection[2];
			echo "\n";
		}
	}
	else{
		echo "Conection with sender ";
		echo $connection[0];
		echo ", receiver ";
		echo $connection[1];
		echo " and data ";
		echo $connection[2];
		echo " already on database\n";
	}
	// Reset $stmt. Don't know why, but it needs this to work properly
	$stmt = '';
}
?>