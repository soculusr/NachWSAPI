<!DOCTYPE html> 

<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html lang="en">
<head>
<meta name="viewport" content="width=device-width, initial-scale=1">
<link href = "https://code.jquery.com/ui/1.12.1/themes/ui-lightness/jquery-ui.css"
         rel = "stylesheet">
<script src="static/js/jquery-3.5.0.min.js"></script>
<script src = "https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
 <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
<link href="static/css/bootstrap.min.css" rel ="stylesheet">
<script src="static/js/bootstrap.min.js"></script>

<script type="text/javascript">
	
	
	function sendData(){
		
		
    	 
		
		var data = [];
		var last = $('#accountno1').val();
		
		
		//alert(last);
		if(last === undefined){
			
			$("#errmsg").html("Atleast one record should be present").show().fadeOut(2500);
			setTimeout(function() {
				  $(location).attr('href', 'http://localhost:8080/panDtlsService');
			  }, 2500);
		}
		else{ 
		var data3 = $("input[name=accountno]").val();
		data.push(data3);
		for(var i=2;i<= $('#accountno').val();i++){
			
			var data2 = $('#accountno'+i).val();
		
			data.push(data2);
		}
		
		var data4 = $("#destbank1").val();
		data.push(data4);
		
		
		var finaldata  = data.toString();
		
		//alert(finaldata);
		
		$.ajax({
	        type: "POST",
	        traditional: true,
	        url: "/panDtls",
	        data: finaldata,
	      });
		
		$('#form_id').trigger("reset");
		
		//alert("Data Sent Successfully");
		var success = "<h3 style='color:green'>Data Sent Successfully</h3>";
		$('#cd1').append(success);
		
		//setTimeout($(location).attr('href', 'http://localhost:8080/home'),5000);
		
		$(document).ready(function() {
			  setTimeout(function() {
				  $(location).attr('href', 'http://localhost:8080/panDtlsService');
			  }, 3000);
			}
		
		
		
		);
		}
	}
	
	

	$(document).ready(function(){
		  $('[data-toggle="tooltip"]').tooltip();
		  
		  $('form').attr('autocomplete','off');
		  
		});
	
	
	function onKey(e){
		if (e.which != 8 && e.which != 0 && (e.which < 48 || e.which > 57)) {
	        //display error message
	        $("#errmsg").html("Only digits are allowed").show().fadeOut("slow");
	               return false;
	    }
	}
	
	
	
	
	
	
</script>

<style type="text/css">

/* #logo{

	margin-top:1%;
	width:150px;
	
} */

/* #aadhaarlogo{

	
	width:150px;
	
} */

#header{
	
	
	padding-top: 50px;
}

#ColNo{



}

 @media (min-width: 1200px) {
    .container{
        max-width: 500px;
    }
}  

 p {
    margin-top: 0;
    margin-bottom: 1rem;
    padding-top: 40px;
}
/*
label {
    display: inline-block;
    padding-left: 80px;
} */

</style>

	
</head>

<body>

	<div class="container" id="cd1">
	<!-- <img src="static/images/aadhaar-logo.png" alt="logo" id="aadhaarlogo"> -->
	<!-- <img src="static/images/logo.PNG" alt="logo" id="logo"> -->
	
  <h2 id="header">PAN Details Service</h2>
  <p>Please fill the required details below</p>
  <span id="errmsg" style="color:red"></span>
  <form action="javascript:sendData()" id="form_id">
  <div class="row">
      <div class="col" id="accountNoCol">
    <label for="accountno" class="mb-2 mr-sm-2">Account No:</label>
    <!-- <h1>minlength="12" maxlength="12" </h1> -->
    <input type="text" onkeypress="return onKey(event)" class="form-control mb-2 mr-sm-2" placeholder="Enter account no" name="accountno" id="accountno1" required>
    <input type="hidden" class="form-control mb-2 mr-sm-2" id="accountno" placeholder="Enter account no" name="accountno" value="1">
   </div>
   
    <div class="col" id="destBankCol"> 
    <label for="previin" class="mb-2 mr-sm-2">Destination:</label>
    <form:select required="on" style="min-width: 120%" class="form-control mb-2 mr-sm-2" id="destbank1" name="destbank1" path = "destBankList">
        <form:option value = "" label = "Select"/>
        <form:options items = "${destBankList}"/>
     </form:select>
    </div>
   
    
   </div> 
    <button type="submit" class="btn btn-success mb-2" id="send" onclick="">Send Data</button>
  </form>
  
</div>



</body>



</html>