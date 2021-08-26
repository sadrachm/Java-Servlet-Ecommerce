  
function getTax (zip)
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
        document.getElementById ("tax").innerHTML = place[2] + "%";
        document.getElementById ("state").value = place[0];
        document.getElementById ("county").value = place[1].replace(/['"]+/g, '');
        var a = (place[2]/100)+1;
        var b = document.getElementById ("ogTotal").innerHTML;
        document.getElementById("total").innerHTML = Math.round((b*a) * 100) / 100;
    } 
  } 
  // Call the response software component
  //xhr.open ("GET", "getCityState?zip=" + zip, true);
  //xhr.send ();
  xhr.open ("POST", "getTax", true);
  xhr.setRequestHeader("content-type", "application/x-www-form-urlencoded");
  xhr.send ("zip="+zip);  
}
