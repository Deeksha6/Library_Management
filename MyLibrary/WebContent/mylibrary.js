

function validationsearch(){
	if(document.getElementById('search_book').value == "" || document.getElementById('search_book').value == null){
		alert("Please enter a search criteria!");
		return false;
	}
	return true;
}

function validationcheckin(){
	if(document.getElementById('searchcheckin').value == "" || document.getElementById('searchcheckin').value == null){
		alert("Please enter a search criteria!");
		return false;
	}
	return true;
}

function openTab(evt, action) {
	hideElements();

	document.getElementById(action).style.display = "block";

}

function hideElements(){
	 if( document.getElementById("searchdata")){
		    document.getElementById("searchdata").style.display = "none";

	}
	 if( document.getElementById("searchlistcheckin")){

		    document.getElementById("searchlistcheckin").style.display = "none";

	}if( document.getElementById("AddBorrower")){

	    document.getElementById("AddBorrower").style.display = "none";

	} if( document.getElementById("successful")){
	
	    document.getElementById("successful").style.display = "none";
	
	}
	if( document.getElementById("Fine")){
		
	    document.getElementById("Fine").style.display = "none";
	
	}
	if( document.getElementById("CheckInBook")){
		
	    document.getElementById("CheckInBook").style.display = "none";
	
	}if( document.getElementById("SearchBook")){
		
	    document.getElementById("SearchBook").style.display = "none";
	
	}
	if( document.getElementById("Result")){
		
	    document.getElementById("Result").style.display = "none";
	
	}
}

function checkoutbook(isbn){
	var cardid = prompt("Please enter a valid card number", "");
	if(cardid){
	var xmlhttp;
    if (window.XMLHttpRequest) {
        xmlhttp = new XMLHttpRequest();
    } else {
        xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
    }
    xmlhttp.onreadystatechange = function() {
    	if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
    		alert(xmlhttp.responseText);
    		location.reload();
        }
    }
    xmlhttp.open('GET', 'http://localhost:8080/MyLibrary/CheckOutBook?isbn='+isbn+'&cardid='+cardid, true);
    xmlhttp.send(null);

}
}

function checkinbook(book_ref){
	var xmlhttp;
	var param=book_ref.split('+');
    if (window.XMLHttpRequest) {// code for IE7+, Firefox, Chrome, Opera, Safari
        xmlhttp = new XMLHttpRequest();
    } else {// code for IE6, IE5
        xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
    }
    xmlhttp.onreadystatechange = function() {
    	if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
            //document.getElementById("myDiv").innerHTML=xmlhttp.responseText;
    		alert(xmlhttp.responseText);
    		location.reload();

        }
    }
    xmlhttp.open('GET', 'http://localhost:8080/MyLibrary/CheckInUpdate?isbn='+param[0]+'&card_id='+param[1], true);
    xmlhttp.send(null);

}


function validateadd(){
	if(document.getElementById('fname').value == "" || document.getElementById('fname').value == null){
		alert("Please enter First Name");
		return false;
	}
	if(document.getElementById('lname').value == "" || document.getElementById('lname').value == null){
		alert("Please enter Last Name");
		return false;
	}
	if(document.getElementById('ad').value == "" || document.getElementById('ad').value == null){
		alert("Please enter Address");
		return false;
	}
	if(document.getElementById('city').value == "" || document.getElementById('city').value == null){
		alert("Please enter City");
		return false;
	}
	if(document.getElementById('state').value == "" || document.getElementById('state').value == null){
		alert("Please enter State");
		return false;
	}
	
	 var regex = /[0-9]|\./;
	if( document.getElementById('ssn').value.length!=9 || document.getElementById('ssn').value == "" || document.getElementById('ssn').value == null || !regex.test(document.getElementById('ssn').value)){
		alert("Please enter valid SSN!");
		return false;
	}
	if(document.getElementById('ssn').value.length!=10 || document.getElementById('phone').value != "" &&  !regex.test(document.getElementById('phone').value)){
		alert("Please enter valid Phone number!");
		return false;
	}
	return true;
}

function payamount(cardid){
	var xmlhttp;
	//print("hi");
    if (window.XMLHttpRequest) {// code for IE7+, Firefox, Chrome, Opera, Safari
        xmlhttp = new XMLHttpRequest();
    } else {// code for IE6, IE5
        xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
    }
    xmlhttp.onreadystatechange = function() {
    	//alert(xmlhttp.status);
    	if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
    		alert("Payment Successfull!");
    		location.reload();


        }
    }
    xmlhttp.open('GET', 'http://localhost:8080/MyLibrary/PayFine?cardid='+cardid, true);
    xmlhttp.send(null);
	
	
}