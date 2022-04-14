<?php
        $host="127.0.0.1";
        $username="s2275253";
        $password="s2275253";
        $database="d2275253";

        $con=mysqli_connect($host,$username,$password,$database);
	
	$RATING=$_REQUEST['RATING'];
	$ORDER_ID=$_REQUEST['ORDER_ID'];
	if($RATING==1)
	{
	   $SQL="UPDATE ORDERS SET RATING=1 WHERE ORDER_ID='$ORDER_ID'";
	   if($r=mysqli_query($con,$SQL))
	   {
		echo "Sucess";
	   }
	   else
	   {
		echo "Fail";
	   }
	}
	else
	{
	    $SQL="UPDATE ORDERS SET RATING=0 WHERE ORDER_ID='$ORDER_ID'";
	    if($r=mysqli_query($con,$SQL))
            {
                echo "Sucess";
            }
            else
            {
                echo "Fail";
            }
	}


?>
