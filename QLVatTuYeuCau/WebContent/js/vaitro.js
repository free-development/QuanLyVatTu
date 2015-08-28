function showForm(formId, check){
		if (check)
			document.getElementById(formId).style.display="block";
		else document.getElementById(formId).style.display="none";
		var f = document.getElementById('main-form'), s, opacity;
		s = f.style;
		opacity = check? '10' : '100';
		s.opacity = s.MozOpacity = s.KhtmlOpacity = opacity/100;
		s.filter = 'alpha(opacity='+opacity+')';
		for(var i=0; i<f.length; i++) f[i].disabled = check;
	}

		function preUpdateVt(formId, check){
			vtId = $('input:checkbox[name=vtId]:checked').val();
			var vtMaList = [];
			$.each($("input[name='vtId']:checked"), function(){            
				vtMaList.push($(this).val());
		    });
			if (vtMaList.length == 0)
				alert('Bạn phải chọn 1 vai trò để thay đổi!!');
			else if (vtMaList.length > 1)
				alert('Bạn chỉ được chọn 1 vai trò để thay đổi!!');
			else {
				$.ajax({
					url: "/QLVatTuYeuCau/preEditVt.html",
					type: "GET",
					dataType: "JSON",
					data: {"vtId": vtId},
					contentType: "application/json",
					mimeType: "application/json",
					
					success: function(vt){
						
						$('input[name=vtIdUpdate]').val(vt.vtId);
					  	$('input:text[name=vtTenUpdate]').val(vt.vtTen);
					  	
					  	showForm(formId, check);
					}
					
				});
			}
		}
		function confirmDelete(){
			vtId = $('input:checkbox[name=vtId]:checked').val();
			var vtMaList = [];
			$.each($("input[name='vtId']:checked"), function(){            
				vtMaList.push($(this).val());
		    });
			var str = vtMaList.join(", ");
			if (vtMaList.length == 0)
				alert('Bạn phải chọn 1 hoặc nhiều vai trò để xóa!!');
			else if (confirm('Bạn có chắc xóa vai trò có mã ' + str))
				deleteVt(str);
		}
 		
	 	 function deleteVt(str) {
			 
			$.ajax({
				url: "/QLVatTuYeuCau/deleteVt.html",	
			  	type: "GET",
			  	dateType: "JSON",
			  	data: { "vtList": str},
			  	contentType: 'application/json',
			    mimeType: 'application/json',
			  	success: function() {
							$('table tr').has('input[name="vtId"]:checked').remove();
							alert('Bộ phận có mã ' + str + " đã bị xóa");
			    } 
			});  
		} 
 	 	function addVt() {
 			vtId = $('#add-form input[name=vtId]').val();
 			vtTen = $('#add-form input:text[name=vtTen]').val();
 			if(vtId == '') {
 				$('#requirevtId').html('Vui lòng nhập mã vai trò');
 			}
 			else if (vtTen == '')
	 			{
	 				$('#requirevtTen').html('Vui lòng nhập tên vai trò');
	 			}
 			else {

		 			$.ajax({
		 				url: "/QLVatTuYeuCau/addVt.html",	
					  	type: "GET",
		 			  	dateType: "JSON",
		 			  	data: { "vtId": vtId, "vtTen": vtTen},
		 			  	contentType: 'application/json',
		 			    mimeType: 'application/json',
					  	
		 			  	success: function(result) {
					  		if(result == "success")
			 				{
							$('#view-table table tr:first').after('<tr class="rowContent"><td class=\"left-column\"><input type=\"checkbox\" name=\"vtId\" value=\"' +vtId + '\"</td><td class=\"col\">'+ vtId +'</td><td class=\"col\">' + vtTen+'</td></tr>');
					  		$('#add-form input[name=vtId]').val('');
							$('#add-form input:text[name=vtTen]').val('');
					  		showForm("add-form", false);	
					  		alert("Vai trò "+vtId + " đã được thêm ");	
						}
				  		else{
				  			alert("Vai trò "+vtId + " đã tồn tại ");
				  		}
					  	
		 			  	}
		 			});
 			}
 		}
 	 	function confirmUpdateVt(){
			var vtIdUpdate = $('input[name=vtIdUpdate]').val();
			var vtTenUpdate = $('input:text[name=vtTenUpdate]').val();
			if (confirm('Bạn có chắc thay đổi vai trò có mã ' + vtIdUpdate))
				updateVt(vtIdUpdate, vtTenUpdate);
		}
 	 	function updateVt(vtIdUpdate, vtTenUpdate) {
 	 		if (vtTenUpdate == '')
	 			{
	 				$('#requirevtTenUp').html('Vui lòng nhập tên vai trò');
	 			}
 			
 			else {

					$.ajax({
						url: "/QLVatTuYeuCau/updateVt.html",	
					  	type: "GET",
					  	dateType: "JSON",
					  	data: { "vtIdUpdate": vtIdUpdate, "vtTenUpdate": vtTenUpdate},
					  	contentType: 'application/json',
					    mimeType: 'application/json',
					  	
					  	success: function(vt) {
					  		$('table tr').has('input[name="vtId"]:checked').remove();
					  		$('#view-table table tr:first').after('<tr class="rowContent"><td class=\"left-column\"><input type=\"checkbox\" name=\"vtId\" value=\"' +vtIdUpdate + '\"</td><td class=\"col\">'+ vtIdUpdate +'</td><td class=\"col\">' + vtTenUpdate+'</td></tr>');
					  		$('input[name=vtIdUpdate]').val('');
							vtTenUpdate = $('input:text[name=vtTenUpdate]').val('');
					  		showForm("update-form", false);	
					  		alert("Thay đổi thành công vai trò có mã "+ vtIdUpdate);
					  	}
					});
 			}
		}
 	 	function resetUpdateVt(){
 	 		$('#update-form input:text[name=vtTenUpdate]').val('');
 	 	}
 	 	function changeVtId(){
 	  		$('#requirevtId').html('');
 	  		$('#add-form input[name=vtId]').focus();
 	 	} 	
 	  	
 	  	function changeVtTen(){
 	  		$('#requirevtTen').html('');
 	  		$('#add-form input:text[name=vtTen]').focus();
 	 	}	
 	  	
 	
 	  	
 	  	function changeVtTenUp(){
 	  		$('#requirevtTenUp').html('');
 	  		$('#update-form input:text[name=vtTenUpdate]').focus();
 	 	}
 	  	
 	  	$(document).ready(function() {
 		  	$('.page').click(function(){
 			var pageNumber = $(this).val();
 		    	$.ajax({
 					url: "/QLVatTuYeuCau/loadPageVt.html",	
 				  	type: "GET",
 				  	dateType: "JSON",
 				  	data: { "pageNumber": pageNumber},
 				  	contentType: 'application/json',
 				    mimeType: 'application/json',
 				  	
 				  	success: function(vtList) {
 				  		$('#view-table table .rowContent').remove();
 						if(vtList.length>0){
 							for(i = 0;i < vtList.length; i++ ) {
 								var vt = vtList[i] ;
 								var style = '';	
 								if (i % 2 == 0)
 									style = 'style=\"background : #CCFFFF;\"';
 								var str = '';
 								str = '<tr class=\"rowContent\" ' + style + '>'
 									+ '<td class=\"left-column\"><input type=\"checkbox\" name=\"vtId\" value=\"' 
 									+ vt.vtId +'\" class=\"checkbox\"></td>'
 									+ '<td class=\"col\">' + vt.vtId + '</td>'
 									+ '<td class=\"col\">' + vt.vtTen + '</td>'
 									+ '</tr>';
 								$('#view-table table tr:first').after(str);
 							}
 						}
 				  	}
 				});
 		    });	
 		})   
 		$(document).ready(function() {
 	$('#add-form').keypress(function(e) {
 	 var key = e.which;
 	 if(key == 13)  // the enter key code
 	  {
 		 addVt();
 	    return false;  
 	  }
 	});   
 	});   
 	$(document).ready(function() {
 	$('#update-form').keypress(function(e) {
 	 var key = e.which;
 	 if(key == 13)  // the enter key code
 	  {
 	    updateVt();
 	    return false;  
 	  }
 	});   
 	});