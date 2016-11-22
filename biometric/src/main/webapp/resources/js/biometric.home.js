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
	
	
	$("#aadhaarRegistrationForm").formValidation({
		framework: 'bootstrap',
		icon: {
			valid: 'glyphicon glyphicon-ok',
			invalid: 'glyphicon glyphicon-remove',
			validating: 'glyphicon glyphicon-refresh'
		},
		err: {
			// You can set it to popover
			// The message then will be shown in Bootstrap popover
			container: 'tooltip'
		},
		fields: {
			name: {
				validators: {
					notEmpty: {
						message: 'Name is required'
					}, stringLength: {
						max: 44,
						message: 'The full name must be less than 45 characters'
					}
				}
			},
			phonenumber: {
				validators: {
					notEmpty: {
						message: 'Phone is required'
					},phone: {
						country: "IN",
						message: 'The value is not valid %s phone number'
					}
				}
			},
			emailId:{
				validators: {
					notEmpty: {
						message: 'Email Id is required'
					},
					emailAddress: {
						message: 'The input is not a valid email address'
					}
				}
			},
			address:{
				validators: {
					notEmpty: {
						message: 'Address is required'
					}, stringLength: {
						max: 44,
						message: 'Address must be less than 45 characters'
					}
				}
			},
			age:{
				validators: {
					notEmpty: {
						message: 'Age is required'
					},between: {
						min: 2,
						max: 100,
						message: 'Not valid age'
					}
				}
			}
		}
	});
	
	$("#bankRegistrationForm").formValidation({
		framework: 'bootstrap',
		icon: {
			valid: 'glyphicon glyphicon-ok',
			invalid: 'glyphicon glyphicon-remove',
			validating: 'glyphicon glyphicon-refresh'
		},
		err: {
			// You can set it to popover
			// The message then will be shown in Bootstrap popover
			container: 'tooltip'
		},
		fields: {
			pk: {
				validators: {
					notEmpty: {
						message: 'Aadhar Number is required'
					}
				}
			},
			'listCardDetails[0].cardNumber': {
				validators: {
					notEmpty: {
						message: 'Card Number is required'
					}
				}
			},
			'listCardDetails[0].nameOnCard':{
				validators: {
					notEmpty: {
						message: 'Name on Card is required'
					}
				}
			},
			'listCardDetails[0].expiryDate':{
				validators: {
					notEmpty: {
						message: 'Expiry Date is required'
					}
				}
			},'listCardDetails[1].cardNumber': {
				validators: {
					notEmpty: {
						message: 'Card Number is required'
					}
				}
			},
			'listCardDetails[1].nameOnCard':{
				validators: {
					notEmpty: {
						message: 'Name on Card is required'
					}
				}
			},
			'listCardDetails[1].expiryDate':{
				validators: {
					notEmpty: {
						message: 'Expiry Date is required'
					}
				}
			},
			'listCardDetails[2].cardNumber': {
				validators: {
					notEmpty: {
						message: 'Card Number is required'
					}
				}
			},
			'listCardDetails[2].nameOnCard':{
				validators: {
					notEmpty: {
						message: 'Name on Card is required'
					}
				}
			},
			'listCardDetails[2].expiryDate':{
				validators: {
					notEmpty: {
						message: 'Expiry Date is required'
					}
				}
			}
		}
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
	
	
	 $('#aadhaarRegistrationForm').submit(function() {
		 	$('#gif').css('visibility', 'visible'); 
	        return true;
	    });
	
	
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