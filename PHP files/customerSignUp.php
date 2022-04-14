<?php
        $host="127.0.0.1";
        $username="s2275253";
        $password="s2275253";
        $database="d2275253";
	$con=mysqli_connect($host,$username,$password,$database);

        $CUSTOMER_USERNAME=$_REQUEST['CUSTOMER_USERNAME'];
        $passwrd=$_REQUEST['passwrd'];
        $F_NAME=$_REQUEST['F_NAME'];
	$L_NAME=$_REQUEST['L_NAME'];
	$passwrd=md5($passwrd);

	$SQL="INSERT INTO CUSTOMER VALUES('$CUSTOMER_USERNAME', '$passwrd', '$F_NAME', '$L_NAME')";

	if ($r=mysqli_query($con,$SQL))
	{
	    echo "Success";
	}
	else
	{
	    echo "Failed";
	}

?>
