<?php
    $host="127.0.0.1";
	$username="s2275253";
	$password="s2275253";
	$database="d2275253";

	$con=mysqli_connect($host,$username,$password,$database);
	$STAFF_ID=$_REQUEST["STAFF_ID"];
        $passwrd=$_REQUEST["passwrd"];
	$userType=$_REQUEST["userType"];
	$passwrd=md5($passwrd);
	$ouput=array();

	$SQL="SELECT * FROM $userType  WHERE STAFF_ID='$STAFF_ID' AND passwrd='$passwrd'";
	
	if($r=mysqli_query($con,$SQL))
	{
	    while($row=$r->fetch_assoc())
	    {
		$ouput[]=$row;
		
	    }
	}
	
	if(count($ouput)==1)
	{
	    echo json_encode($ouput);
	}
	else
	{
	    echo "Fail";
	}

?>
