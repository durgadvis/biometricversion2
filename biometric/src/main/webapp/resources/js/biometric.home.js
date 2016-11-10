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




/*	$("#fingerscanner").click(function(e) {
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
    	});*/



    	var rowcount=0;
        	$(document).on('click',"input.add_card",function(e){
        			 rowcount = rowcount + 1;
                   	var tr    = $(this).closest('#rowOfCardDetails');
        			console.log(tr);
        			var clone_obj = tr.clone();
        			console.log(clone_obj);
        			clone_obj.find(':text').val('');
        			tr.after(clone_obj);
        			clone_obj.find("input").each(function(){

        				$(this).attr('name', $(this).attr('name').replace("listCardDetails[0]", "listCardDetails["+ rowcount +"]"));
        			});
              });
});