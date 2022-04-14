<?php

        $host="127.0.0.1"; 
        $username="s2275253";
        $password="s2275253";
        $database="d2275253";

        $output=array();

        $con=mysqli_connect($host,$username,$password,$database);
	    $userType=$_REQUEST['userType'];
        
        $SQL="SELECT STAFF_ID FROM $userType";
	    if($r=mysqli_query($con,$SQL))
        {
                while($row=$r->fetch_assoc())
                {
                    $output[]=$row;
                }
        }
        echo json_encode($output);
	
	
?>
