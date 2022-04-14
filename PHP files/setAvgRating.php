<?php
        $host="127.0.0.1";
        $username="s2275253";
        $password="s2275253";
        $database="d2275253";
	    $con=mysqli_connect($host,$username,$password,$database);

        $output=array();
        $STAFF_ID=$_REQUEST['STAFF_ID'];
        $AVG_RATING=$_REQUEST['AVG_RATING'];
        $SQL="UPDATE STAFF SET AVG_RATING='$AVG_RATING' where STAFF_ID='$STAFF_ID'";
        if($r=mysqli_query($con,$SQL))
            {
                echo "Success";
            }
        else
        {
            echo "fail";
        }
?>
