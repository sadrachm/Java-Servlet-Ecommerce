  
function getPhone (number)
{
  if (window.XMLHttpRequest)
  {  // IE7+, Firefox, Chrome, Opera, Safari
     var xhr = new XMLHttpRequest();
  }
  else
  {  // IE5, IE6
     var xhr = new ActiveXObject ("Microsoft.XMLHTTP");
  }

  // Register the embedded handler function
  // This function will be called when the server returns
  // (the "callback" function)
  xhr.onreadystatechange = function ()
  { // 4 means finished, and 200 means okay.
    if (xhr.readyState === 4 && xhr.status === 200)
    { // Data should look like "Fairfax, Virginia"
      var result = xhr.responseText;
      var place = result.split(",");
      //if (document.getElementById ("city").value == "")
        document.getElementById ("fname").value = place[0];
        document.getElementById ("lname").value = place[1];
        document.getElementById ("shipA").value = place[2];
        document.getElementById ("card").value = place[3];
        document.getElementById ("expD").value = place[4];
        document.getElementById ("secC").value = place[5];
        document.getElementById ("billA").value = place[6];
        document.getElementById ("zip").value = place[7];
    } 
  } 
  // Call the response software component
  //xhr.open ("GET", "getCityState?zip=" + zip, true);
  //xhr.send ();
  xhr.open ("POST", "getPhone", true);
  xhr.setRequestHeader("content-type", "application/x-www-form-urlencoded");
  xhr.send ("number="+number);  
}
