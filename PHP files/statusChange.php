<?php
        $host="127.0.0.1";
        $username="s2275253";
        $password="s2275253";
        $database="d2275253";

        $con=mysqli_connect($host,$username,$password,$database);
        $ORDER_ID=$_REQUEST['ORDER_ID'];
        $STATUS=$_REQUEST['STATUS'];

        $SQL="UPDATE ORDERS SET ORDER_STATUS='$STATUS' WHERE ORDER_ID='$ORDER_ID'";

        if(mysqli_query($con,$SQL))
        {
            echo "Success";
        }
        else
        {
             echo "Fail";
        }
?>


