$(document).ready(function () {
	$("#fingerscanner").click(function(e) {
		$.ajax({
            type : "GET",
            url : "/biometric-1/ajaxfingerprint",
            success : function(data) {
                $("#response").html(data);
            }
        }); 
	    return false;
	});
	
	
	$("#scannerandgetdetails").click(function(e) {
		$.ajax({
            type : "GET",
			contentType: "application/json",
            url : "/biometric-1/getuserdetails",
			dataType: "json",
            success : function(data) {
                $("#response").html(data.user_id);
            },
                error: function(XMLHttpRequest, textStatus, errorThrown) { 
                    alert("Status: " + textStatus); alert("Error: " + errorThrown); 
                }  
        }); 
	    return false;
	});
	
	
});