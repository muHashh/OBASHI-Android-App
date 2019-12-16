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

//Add each device in $devices
foreach($devices as $device){
	$query = "SELECT * FROM Devices Where Name = ?";
	$stmt = $con->prepare($query);
	//Bind parameters
	$stmt->bind_param("s",$device[0]);
	//Executing MySQL statement
	$stmt->execute();
	//If no device found, insert device
	if(!$stmt->fetch()){
		//Query to insert a device
		$query = "INSERT INTO Devices(name, description, X_coord, Y_coord, Z_coord) VALUES (?,?,?,?,?)";
		$stmt = $con->prepare($query);
		//Bind parameters
		$stmt->bind_param("ssddd",$device[0],$device[1],$device[2],$device[3],$device[4]);
		//Executing MySQL statement
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
	//Reset $stmt. Don't know why, but it needs this to work properly
	$stmt = '';
}
?>