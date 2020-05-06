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
	
	
	function addAadhar() {
		
		
  		var new_no = parseInt($('#aadhaar').val()) + 1;
  	    
  	    var aadhaarCol = "<input style='min-width: 135%' type='text' onkeypress='return onKey(event)' autocomplete='off'  class='form-control mb-2 mr-sm-2' id='aadhaar"+new_no+"' placeholder='Enter No' name='aadhaar' required>";
  	    
  	    var mapstatusCol = "<select class='form-control mb-2 mr-sm-2' id='mapstatus"+new_no+"' name='mapstatus'> <option>A</option> <option>I</option> </select>";
  	    
  	    var mdflagCol = "<select class='form-control mb-2 mr-sm-2' id='mdflag"+new_no+"' name='mdflag'> <option>Y</option> <option>N</option> </select>";
  	  
  		var mddateCol = "<input type='date' autocomplete='off' class='form-control mb-2 mr-sm-2' id='mddate"+new_no+"' placeholder='Select' name='mddate' required>";
  	
  		var odflagCol = "<select class='form-control mb-2 mr-sm-2' id='odflag"+new_no+"' name='odflag'> <option>Y</option> <option>N</option> </select>";
  	
  		var oddateCol = "<input type='date' autocomplete='off' class='form-control mb-2 mr-sm-2' id='oddate"+new_no+"' placeholder='Select' name='oddate' required>";
  		
  		var delRowValue = 'deleteRow($(\'\#deleterow'+new_no+'\').val())';
  		
  		var delRowCol = "<button type='button' class='btn btn-danger mb-2' id='deleterow"+new_no+"' value='"+new_no+"' onclick="+delRowValue+">Delete</button>";
  	
  	
  	
  	$('#aadhaarCol').append(aadhaarCol);
  	$('#mapstatusCol').append(mapstatusCol);
  	$('#mdflagCol').append(mdflagCol);
  	$('#mddateCol').append(mddateCol);
  	$('#odflagCol').append(odflagCol);
  	$('#oddateCol').append(oddateCol);
  	var previinid = "previin"+new_no;
  	//console.log(previinid);
  	$('#previinCol').append('<form:select required="on" style="min-width: 180%" class="form-control mb-2 mr-sm-2" id="previin" name="previin1" path = "previinlist"> <form:option value = "" label = "Select"/> <form:options items = "${previinlist}"/> </form:select>');
  	$('#previin').attr('id', ""+previinid+"");
  	$('#delLabel').attr('class', "visible");
	$('#deleteRow').append(delRowCol);

  	
  
 	 $('#aadhaar').val(new_no);
 	$('#mapstatus').val(new_no);
 	$('#mdflag').val(new_no);
 	$('#mddate').val(new_no);
 	$('#odflag').val(new_no);
 	$('#oddate').val(new_no);
 	$('#previin').val(new_no);
 	
 	
 	/* $(function() {
        $( "#mddate"+new_no+"" ).datepicker({ dateFormat: 'dd-mm-yy'});
        $( "#mddate"+new_no+"" ).datepicker("show");
     });
 	
 	$(function() {
        $( "#oddate"+new_no+"" ).datepicker({ dateFormat: 'dd-mm-yy' });
        $( "#oddate"+new_no+"" ).datepicker("show");
     }); */
}

	function removeAadhar() {
  		var last_no = $('#aadhaar').val();
  		
  		

  	if (last_no > 1) {
   		$('#aadhaar' + last_no).remove();
   		$('#mapstatus' + last_no).remove();
    	$('#mdflag' + last_no).remove();
  		$('#mddate' + last_no).remove();
   		$('#odflag' + last_no).remove();
		$('#oddate' + last_no).remove();
		$('#previin' + last_no).remove();
		$('#aadhaar').val(last_no - 1);
		
	
  }
}
	
	
	function deleteRow(rowid){
		
		$('#aadhaar' + rowid).remove();
   		$('#mapstatus' + rowid).remove();
    	$('#mdflag' + rowid).remove();
  		$('#mddate' + rowid).remove();
   		$('#odflag' + rowid).remove();
		$('#oddate' + rowid).remove();
		$('#previin' + rowid).remove();
		$('#deleterow' + rowid).remove();
		
		
	}
	
	
	/* $(function() {
        $( "#mddate1" ).datepicker({ dateFormat: 'dd-mm-yy' });
        $( "#mddate1" ).datepicker("show");
     });

	
	$(function() {
        $( "#oddate1" ).datepicker({ dateFormat: 'dd-mm-yy' });
        $( "#oddate1" ).datepicker("show");
     }); */
	
	function sendData(){
		
		
    	 console.log($('#previin1').val());
		//var sel = $('#previin'+1).val();
		
		//alert("select data " + sel);
		
		var data = [];
		var last = $('#aadhaar1').val();
		console.log("last "+last);
		
		
		//alert(last);
		if(last === undefined){
			//alert("Atleast one record should be present");
			$("#errmsg").html("Atleast one record should be present").show().fadeOut(2500);
			setTimeout(function() {
				  $(location).attr('href', 'http://localhost:8080/aadhaarService');
			  }, 2500);
		}
		else{ 
		var data3 = $("input[name=aadhaar]").val()+"~"+$("#mapstatus1").val()+"~"+$("#mdflag1").val()+"~"+$("input[name=mddate]").val()+"~"+$("#odflag1").val()+"~"+$("input[name=oddate]").val()+"~"+$("#previin1").val();
		data.push(data3);
		for(var i=2;i<= $('#aadhaar').val();i++){
			
			var data2 = $('#aadhaar'+i).val()+"~"+$('#mapstatus'+i).val()+"~"+$('#mdflag'+i).val()+"~"+$('#mddate'+i).val()+"~"+$('#odflag'+i).val()+"~"+$('#oddate'+i).val()+"~"+$('#previin'+i).val();
		
			data.push(data2);
		}
		
		
		var finaldata  = data.toString();
		
		//alert(finaldata);
		
		$.ajax({
	        type: "POST",
	        traditional: true,
	        url: "/aadhaarData",
	        data: finaldata,
	      });
		
		$('#form_id').trigger("reset");
		
		//alert("Data Sent Successfully");
		var success = "<h3 style='color:green'>Data Sent Successfully</h3>";
		$('#cd1').append(success);
		
		//setTimeout($(location).attr('href', 'http://localhost:8080/home'),5000);
		
		$(document).ready(function() {
			  setTimeout(function() {
				  $(location).attr('href', 'http://localhost:8080/aadhaarService');
			  }, 3000);
			}
		
		
		
		);
		}
	}
	
	

	$(document).ready(function(){
		  $('[data-toggle="tooltip"]').tooltip();
		  
		  $('form').attr('autocomplete','off');
		  
		});
	
	function validateAddress(){
		for(var i=1;i<= $('#aadhaar').val();i++){
	    var TCode = $('#aadhaar'+i).val();
	    
	    var previin =  $('#previin'+i).val();
	    
	    if(previin === ""){
	    	
	    	alert('select prev iin');
	    }

	    if( /[^a-zA-Z0-9\-\/]/.test( TCode ) ) {
	        alert('Special Characters are not allowed check row no '+i);
	        return false;
	    }
	    
	}
	    return true;     
	}
	
	function onKey(e){
		if (e.which != 8 && e.which != 0 && (e.which < 48 || e.which > 57)) {
	        //display error message
	        $("#errmsg").html("Only digits are allowed").show().fadeOut("slow");
	               return false;
	    }
	}
	
	
	
	
	//$("#id").attr("onclick","new_function_name()");
	
	
	
	
</script>

<style type="text/css">

#idbilogo{

	margin-left:70%;
	margin-top:1%;
	width:150px;
	
}

#aadhaarlogo{

	
	width:150px;
	
}

#header{
	
	text-align:center;
}

#ColNo{



}

 @media (min-width: 1200px) {
    .container{
        max-width: 1350px;
    }
} 



</style>

	
</head>

<body>

	<div class="container" id="cd1">
	<img src="static/images/aadhaar-logo.png" alt="logo" id="aadhaarlogo">
	<img src="static/images/idbi-bank-logo.PNG" alt="logo" id="idbilogo">
  <h2 id="header">Aadhaar Seeding and De-seeding</h2>
  <p>Please fill the required details below</p>
  <span id="errmsg" style="color:red"></span>
  <form action="javascript:sendData()" id="form_id" onsubmit = "return validateAddress()">
  <div class="row">
      <div class="col" id="aadhaarCol">
    <label for="aadhaar" class="mb-2 mr-sm-2">Aadhaar No:</label>
    <!-- <h1>minlength="12" maxlength="12" </h1> -->
    <input type="text" style="min-width: 135%" onkeypress="return onKey(event)" class="form-control mb-2 mr-sm-2" placeholder="Enter no" name="aadhaar" id="aadhaar1" required>
    <input type="hidden" class="form-control mb-2 mr-sm-2" id="aadhaar" placeholder="Enter no" name="aadhaar" value="1">
   </div>
   &nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp
    <div class="col" id="mapstatusCol"> 
    <label for="mapstatus1" class="mb-2 mr-sm-2"><a href="#" data-toggle="tooltip" title="A - Activate / I - Inactivate">Map Status:</a></label>
    
 	<select class="form-control mb-2 mr-sm-2" id="mapstatus1" name="mapstatus1">
        <option>A</option>
        <option>I</option>
      </select>
    </div>
    
    <div class="col" id="mdflagCol" > 
    <label for="mdflag1" class="mb-2 mr-sm-2"><a href="#" data-toggle="tooltip" title="Y - Mandate is submitted &nbsp&nbsp&nbsp N - Mandates are not submitted">MD Flag:</a></label>
    <select class="form-control mb-2 mr-sm-2" id="mdflag1" name="mdflag1">
        <option>Y</option>
        <option>N</option>
      </select>
    </div>
    
    <div class="col" id="mddateCol"> 
    <label for="mddate1" class="mb-2 mr-sm-2">MD Date:</label>
    <input type="date" autocomplete="off"  class="form-control mb-2 mr-sm-2" placeholder="Select" name="mddate" id="mddate1" required>
 	<input type="hidden" class="form-control mb-2 mr-sm-2" id="mddate" placeholder="Select" name="mddate" value="1">
    </div>
    
    <div class="col" id="odflagCol"> 
    <label for="odflag" class="mb-2 mr-sm-2"><a href="#" data-toggle="tooltip" title="Y - Mandate is submitted &nbsp&nbsp&nbsp N - Mandates are not submitted">OD Flag:</a></label>
    <select class="form-control mb-2 mr-sm-2" id="odflag1" name="odflag1">
        <option>Y</option>
        <option>N</option>
      </select>
    </div>
    
    <div class="col" id="oddateCol"> 
    <label for="oddate1" class="mb-2 mr-sm-2">OD Date:</label>
    <input type="date" autocomplete="off" class="form-control mb-2 mr-sm-2" placeholder="Select" name="oddate" id="oddate1" required>
 	<input type="hidden" class="form-control mb-2 mr-sm-2" id="oddate" placeholder="Select" name="oddate" value="1">
    </div>
    
    <div class="col" id="previinCol"> 
    <label for="previin" class="mb-2 mr-sm-2">Previous IIN:</label>
    <form:select required="on" style="min-width: 180%" class="form-control mb-2 mr-sm-2" id="previin1" name="previin1" path = "previinlist">
        <form:option value = "" label = "Select"/>
        <form:options items = "${previinlist}"/>
     </form:select>
    </div>
    &nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp
    <div class="col" id="deleteRow"> 
    <label id="delLabel" for="deleterow1" class="mb-2 mr-sm-2">Delete Row:</label>
    <!-- <button type="button" class="btn btn-primary mb-2" id="add" onclick="addAadhar()">Add</button> -->
   <button type="button" class="btn btn-danger mb-2" id="deleterow1" value="1" onclick="deleteRow($(this).attr('value'))">Delete</button>
    
    </div>
    
   </div> 
    <button type="button" class="btn btn-primary mb-2" id="add" onclick="addAadhar()">Add</button>&nbsp
    <!-- <button type="button" class="btn btn-primary mb-2" id="remove" onclick="removeAadhar()">Remove</button>&nbsp -->
    <button type="submit" class="btn btn-success mb-2" id="send" onclick="">Send Data</button>
  </form>
  
</div>



</body>



</html>