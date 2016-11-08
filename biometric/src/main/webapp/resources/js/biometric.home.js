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




	$("#fingerscanner").click(function(e) {
    		$.ajax({
                type : "GET",
    			contentType: "application/json",
                url : "/biometric-1/user/fingerPrint",
    			dataType: "json",
                success : function(data) {
					alert("ada" + window.atob(data.fgIso));
                    $("#fgIsoId").val(window.atob(data.fgIso));
                    $("#fgBmpId").val(window.atob(data.fgBmp));
					$('#imgFgBmp').attr('src',"data:image/jpeg;base64,"+window.atob(data.fgBmp));
                },
                    error: function(XMLHttpRequest, textStatus, errorThrown) {
                        alert("Status: " + textStatus); alert("Error: " + errorThrown);
                    }
            });
    	    return false;
    	});
});