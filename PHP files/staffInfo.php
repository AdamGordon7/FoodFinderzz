<?php
        $host="127.0.0.1";
        $username="s2275253";
        $password="s2275253";
        $database="d2275253";

        $con=mysqli_connect($host,$username,$password,$database);
        $output=array();

	$STAFF_ID=$_REQUEST['STAFF_ID'];
	$SQL="SELECT F_NAME,L_NAME FROM STAFF WHERE STAFF_ID='$STAFF_ID'";

	if($r=mysqli_query($con,$SQL))
	{
	    while($row=$r->fetch_assoc())
	    {
		$output[]=$row;
	    }
	    echo json_encode($output);
	}


?>