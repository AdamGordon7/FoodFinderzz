<?php
        $host="127.0.0.1";
        $username="s2275253";
        $password="s2275253";
        $database="d2275253";

        $con=mysqli_connect($host,$username,$password,$database);
        $output=array();

	
	$ACTION=$_REQUEST['ACTION'];
	$CUSTOMER_USERNAME=$_REQUEST['CUSTOMER_USERNAME'];
	if ($ACTION=="placeOrder")
	{       

	    $ORDER_ID=$_REQUEST['ORDER_ID'];
            $STAFF_ID=$_REQUEST['STAFF_ID'];
            $RESTAURANT=$_REQUEST['RESTAURANT'];
            $ORDER_STATUS=$_REQUEST['ORDER_STATUS'];
	    $ORDER_TIME=$_REQUEST['ORDER_TIME'];
	    $SQL="INSERT INTO ORDERS VALUES('$STAFF_ID', '$CUSTOMER_USERNAME', '$ORDER_ID', '$RESTAURANT','$ORDER_STATUS',NULL,'$ORDER_TIME')";
	     if($r=mysqli_query($con,$SQL))
        {
			$output=array();
            echo json_encode($output);
        }
	     else
	    {
			$output=array();
            echo json_encode($output);
	    }

	}

	else if($ACTION == "getOrderInfo")
	{
	    $SQL="SELECT * FROM ORDERS WHERE CUSTOMER_USERNAME='$CUSTOMER_USERNAME'";
	    if($r=mysqli_query($con,$SQL))
            {
                while($row=$r->fetch_assoc())
                {
                    $output[]=$row;
                }
            }
	    echo json_encode($output);
	}

?>

