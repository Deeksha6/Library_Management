<%@page import="Models.SearchFields"%>
<%@page import="Models.BookCheckIn"%>
<%@page import="Models.Fines"%>
<%@page import="java.util.ArrayList"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Library Management System</title>
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<script type="text/javascript" src="mylibrary.js"></script>
</head>

<body>
<div id="main">
	<div class="jumbotron text-center">
		<h1>Library Management System</h1>
	</div>

	<div>
		<div class="tabcontent">
			<h3>Search a Book</h3>
			<button type="button" class="btn btn-primary"
				onclick="openTab(event, 'SearchBook')">Search</button>
		</div>
		<div class="tabcontent">
			<h3>Check In A Book</h3>
			<button type="button" class="btn btn-primary"
				onclick="openTab(event, 'CheckInBook')">Check In</button>
		</div>
		<div class="tabcontent">
			<h3>Add New Borrower</h3>
			<button type="button" class="btn btn-primary"
				onclick="openTab(event, 'AddBorrower')">Add Borrower</button>
		</div>
		<div class="tabcontent">
			<h3>Refresh Fine</h3>
			<button type="button" class="btn btn-primary"
				onclick="openTab(event, 'Fine')">Refresh Fine</button>
		</div>
	</div>
</div>
<br />
	<div id="SearchBook">
		<br />
		<form method="GET"
			action="http://localhost:8080/MyLibrary/SearchForBook"
			onsubmit="return validationsearch();">
			<p>
				<input type="text" id="search_book" name="search_book" />
			</p>
			<p>
				<button type="submit" class="btn btn-primary" >Search</button>
			</p>

		</form>
		<br />
	</div>

	<div id="searchdata">
		<br />
		<% if((String)session.getAttribute("searchval") =="hasvalue") {
  
  		session.removeAttribute("searchval");
  %>
		<p>Search Results</p>
		<table>
			<tr>
				<th>Book ISBN</th>
				<th>Book Title</th>
				<th>Book Author</th>
				<th>Book Availability</th>
			</tr>
			<tbody>

				<%
    SearchFields searchOb = (SearchFields)session.getAttribute("searchvalues");
    ArrayList<String> isbn = new ArrayList<String>();
	ArrayList<String> name = new ArrayList<String>();

	ArrayList<String> title = new ArrayList<String>();
	ArrayList<String> available = new ArrayList<String>();
	
	isbn=searchOb.getIsbn();
	name=searchOb.getName();
	title=searchOb.getTitle();
	available=searchOb.getAvailable();
	
	for(int i=0;i<isbn.size();i++){
    %>
				<tr>
					<td><%= isbn.get(i)%></td>
					<td><%= title.get(i)%></td>
					<td><%= name.get(i)%></td>
					<td><%= available.get(i)%></td>
					<% if(available.get(i).equals("YES")){%>						
					<td> <input type="button" name="checkout" id="<%= isbn.get(i)%>"  
					onclick="checkoutbook(this.id);" value="Check out"></td>
					<%}else if(available.get(i).equals("NO")) {%>
					<td></td>
					<%}%>
				</tr>
				<%} %>
			</tbody>
		</table>
		<% }
   else if((String)session.getAttribute("searchval") =="novalue"){
 		session.removeAttribute("searchval");
   %>
		No Results found
		<%} %>
	</div>
	


<div id="CheckInBook">
<br/><br/>
<form method="GET" action="http://localhost:8080/MyLibrary/CheckInBook" onsubmit="return validationcheckin();">
  <input type="text"  id="searchcheckin" name="searchcheckin"/>
  <button type="submit" class="btn btn-primary" >Search Book Loans</button>
  
 </form>
  <br/><br/>
  </div>

 <div id="searchlistcheckin">
  <br/><br/>
   <% if((String)session.getAttribute("searchvalcheckin") =="hasvalue") {
  
  		session.removeAttribute("searchvalcheckin");
  %>
  <p>Search Results</p>            
  <table >
    <thead>
      <tr>
        <th>Book ISBN</th>
        <th>Borrower Name</th>
        <th>Card ID</th>
      </tr>
    </thead>
    <tbody>
    
    <%
	BookCheckIn bookcheckin = (BookCheckIn)session.getAttribute("searchcheckinvalues");
    ArrayList<String> isbn = new ArrayList<String>();
	ArrayList<String> first_name = new ArrayList<String>();
	ArrayList<String> last_name = new ArrayList<String>();
	ArrayList<String> card_num = new ArrayList<String>();

	
	isbn=bookcheckin.getIsbn();
	first_name=bookcheckin.getFirstName();
	last_name=bookcheckin.getLastName();
	card_num=bookcheckin.getCardNum();
	
	for(int i=0;i<isbn.size();i++){
    %>
      <tr>
       <td><%= isbn.get(i)%></td>
       <td><%= card_num.get(i)%></td>
       <td><%= first_name.get(i)%></td>
       <td><%= last_name.get(i)%></td>
     
       <td>  <button onclick="checkinbook(this.id)" id="<%=isbn.get(i)%>+<%=card_num.get(i)%>">Check In</button>  </td>
      </tr>
    <%} %>  
    </tbody>
  </table>
  <% }
   
   else if((String)session.getAttribute("searchvalcheckin") =="novalue"){
 		session.removeAttribute("searchvalcheckin");

   %>
   
   No Results found for this criteria!
   
   <%} %>
</div>

<!-- Check in div ends -->

<!-- Add Borrower -->
<div id="AddBorrower">
 <br/><br/> <h4>Add a Borrower</h4>
  <br/>
  <form method="GET" action="http://localhost:8080/MyLibrary/AddBorrower" 
  onsubmit="return validateadd();">
  <table>
  <tr>
  
<td>    <label for="fname">First Name:</label></td>
<td>
    <input type="text" id="fname" name="fname">
  </td>
  </tr>
  <tr>
<td>    <label for="lname">Last Name:</label></td>
 <td>   <input type="text" id="lname" name="lname">
  </td>
  </tr>
    <tr>
<td>    <label for="address">Address:</label></td>
 <td>   <input type="text" id="ad" name="ad">
  </td>
  </tr>
  <tr>
<td>    <label for="ssn">SSN:</label></td>
  <td>  <input type="text"  id="ssn" name="ssn">
  </td>
  </tr>
  <tr>
<td>    <label for="phone">Phone Number:</label></td>
   <td> <input type="text"  id="phone" name="phone">
  </td>
  </tr>
  <tr>
<td>    <label for="city">City:</label></td>
   <td> <input type="text"  id="city" name="city">
  </td>
  </tr>
  <tr>
<td>    <label for="state">State</label></td>
   <td> <input type="text"  id="state" name="state">
  </td>
  </tr>
  <tr>
 <td> <button type="submit" class="btn btn-default" style="align: center">Submit</button></td>
  </tr>
  </table>
</form>
</div>
<div id="successful">
 <%
System.out.println("value: "+ (String)session.getAttribute("addval"));
if((String)session.getAttribute("addval") =="hasvalue") {
%>
<br/><br/>
Borrower added successfully! <br/>
Card ID is : <%=(String)session.getAttribute("card_id")%>
<%
session.removeAttribute("addval");
}else if((String)session.getAttribute("addval") =="novalue"){ %>
Borrower was not added! Please check if you are already registered!!!
<%
session.removeAttribute("addval");
} %> 
</div>

<!--fine-->
<div id="Fine" class="tabcontent">
<br />
 <form method="GET" action="http://localhost:8080/MyLibrary/RefreshFines" >
  <p><button type="submit" class="btn btn-primary">Display Fines</button>  </p>
  
 </form> 
</div>

<div id="Result">
<%if(((String)session.getAttribute("fineresult")) == "hasvalue"){
	  session.removeAttribute("fineresult"); %>
  <p>Search Results</p>            
  <table class="table table-striped">
    <thead>
      <tr>
        <th>Card ID</th>
         <th>First Name</th>
          <th>Last Name</th>
        <th>Fine Amount</th>
      </tr>
    </thead>
    <tbody>
    
     <%
     	Fines fine = (Fines)session.getAttribute("fineresultval");
		ArrayList<Float> amt = new ArrayList<Float>();
		ArrayList<String> first_name = new ArrayList<String>();
		ArrayList<String> last_name = new ArrayList<String>();
		ArrayList<String> card_id = new ArrayList<String>();


    amt=fine.getFine_amt();
    card_id=fine.getCard_id();
    first_name=fine.getFirst_name();
    last_name=fine.getLast_name();
    
     for(int i=0;i<card_id.size();i++){
    %>
    <tr>
    <td><%=card_id.get(i) %></td>
    <td><%=first_name.get(i) %></td>
    <td><%=last_name.get(i)%></td>
    <td>$<%=amt.get(i) %></td>
	<td> <input type="button" name="pay" id="<%= card_id.get(i)%>"  
					onclick="payamount(this.id);" value="Pay Fine"></td>
    </tr>
        <%} %>
    </tbody>
    </table>
  <%}else if(((String)session.getAttribute("fineresult")) == "novalue"){ 
  session.removeAttribute("fineresult");%>
  
  No Data to Display!
  
  <%} %>
    </div>