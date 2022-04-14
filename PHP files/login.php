
<?php
    $host="127.0.0.1";
    $username="s2275253";
    $password="s2275253";
    $database="d2275253";

    $con=mysqli_connect($host,$username,$password,$database);
    $CUSTOMER_USERNAME=$_REQUEST["CUSTOMER_USERNAME"];
    $passwrd=$_REQUEST["passwrd"];
    $userType=$_REQUEST["userType"];
    $ouput=array();
    $passwrd=md5($passwrd);
    
    $SQL="SELECT * FROM $userType  WHERE CUSTOMER_USERNAME='$CUSTOMER_USERNAME' AND passwrd='$passwrd'";
    
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

