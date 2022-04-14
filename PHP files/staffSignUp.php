<?php
        $host="127.0.0.1";
        $username="s2275253";
        $password="s2275253";
        $database="d2275253";
	$con=mysqli_connect($host,$username,$password,$database);

        $STAFF_ID=$_REQUEST['STAFF_ID'];
        $passwrd=$_REQUEST['passwrd'];
        $F_NAME=$_REQUEST['F_NAME'];
	   $L_NAME=$_REQUEST['L_NAME'];
	   $RESTAURANT=$_REQUEST['RESTAURANT'];
	   $passwrd=md5($passwrd);

	$SQL="INSERT INTO STAFF VALUES('$STAFF_ID', '$F_NAME', '$L_NAME', '$RESTAURANT', '$passwrd',NULL)";

	if ($r=mysqli_query($con,$SQL))
	{
	    echo "Success";
	}
	else
	{
	    echo "Failed";
	}

?>
