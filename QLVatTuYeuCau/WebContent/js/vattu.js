		function showForm(formId, check){
			if (check)
				document.getElementById(formId).style.display="block";
			else document.getElementById(formId).style.display="none";
			var f = document.getElementById('vattu'), s, opacity;
			s = f.style;
			opacity = check? '10' : '100';
			s.opacity = s.MozOpacity = s.KhtmlOpacity = opacity/100;
			s.filter = 'alpha(opacity='+opacity+')';
			for(var i=0; i<f.length; i++) f[i].disabled = check;
		}	
		function timKiemVattu(){
			var vtTen = '';
			var vtMa = '';
			var check = $('#checkTen:checked').val();
			if (check != null)
				vtTen = $('#search input[name=vattu]').val();
			else 
				vtMa = $('#search input[name=vattu]').val();
			/*
			alert(check);
			alert(vtTen);
			alert(vtMa);
			*/
			$.ajax({
				url: "/QLVatTuYeuCau/timKiemVattu.html",	
			  	type: "GET",
 			  	dateType: "JSON",
 			  	data: { "vtMa": vtMa, "vtTen": vtTen},
 			  	contentType: 'application/json',
 			    mimeType: 'application/json',
			  	
 			  	success: function(vtList){
 			  		
 			  		if(vtList.length>0){
 			  			$('#view-table-vat-tu table .rowContent').remove();
						for(i = 0;i < vtList.length; i++ ) {
							vattu = vtList[i];
							//alert(vtList[i].vtMa);
		 			  		
					  				$('#view-table-vat-tu table tr:first').after('<tr class=\"rowContent\"><td class=\"left-column\"><input type=\"checkbox\" name=\"vtMa\" value=\"' +vattu.vtMa 
									+ '\"</td><td class=\"col\">'+ vattu.vtMa +'</td><td class=\"col\">' + vattu.vtTen
									+'</td><td class=\"col\" style=\"text-align: center;\">' + vattu.dvt.dvtTen
									+'</td><td style=\"text-align: center;\"><button type=\"button\" class=\"button-xem\" value=\"Xem\" onclick=\"showCTVatTu(\'chitiet\',true,\''
									+vattu.vtMa+'\');\">Xem</button></td></tr>');
						}
 			  		}
 			  		else
 			  			{
 			  				alert("Không tìm thấy vật tư!");
 			  			}
 			  	}
			});
			
		}
		function addVattu() {
 			vtMa = $('#add-form input:text[name=vtMa]').val();
 			vtTen = $('#add-form input:text[name=vtTen]').val();
 			dvt = $('#add-form select[name=dvt]').val();
 			if(vtMa == '') {
 				$('#requireVtMa').html('Vui lòng nhập mã vật tư');
 			}
 			else if (vtTen == '')
	 			{
	 				$('#requireVtTen').html('Vui lòng nhập tên vật tư');
	 			}
 			else if(dvt == '')
 				{
 					$('#requireDvt').html('Vui lòng chọn đơn vị tính');
 				}
 			else {

		 			$.ajax({
		 				url: "/QLVatTuYeuCau/addVattu.html",	
					  	type: "GET",
		 			  	dateType: "JSON",
		 			  	data: { "vtMa": vtMa, "vtTen": vtTen, "dvt": dvt},
		 			  	contentType: 'application/json',
		 			    mimeType: 'application/json',
					  	
		 			  	success: function(result) {
					  		if(result == "success")
			 				{
							$('#view-table-vat-tu table tr:first').after('<tr><td class=\"left-column\"><input type=\"checkbox\" name=\"vtMa\" value=\"' +vtMa 
									+ '\"</td><td class=\"col\">'+ vtMa +'</td><td class=\"col\">' + vtTen
									+'</td><td class=\"col\" style=\"text-align: center;\">' + dvt
									+'</td><td style=\"text-align: center;\"><button type=\"button\" class=\"button-xem\" value=\"Xem\" onclick=\"showCTVatTu(\'chitiet\',true,\''
									+vtMa+'\');\">Xem</button></td></tr>');
					  		$('#add-form input:text[name=vtMa]').val('');
							$('#add-form input:text[name=vtTen]').val('');
							$('#add-form select[name=dvt]').val('');
							$('#add-chitiet input:text[name=vtMa]').val(vtMa);
							$('#add-chitiet input:text[name=vtTen]').val(vtTen);
							$('#add-chitiet input:text[name=dvt]').val(dvt);
							alert("Vật tư "+ vtMa + " đã được thêm ");
					  		showForm("add-form", false);			
					  			
						}
				  		else{
				  			alert("Vật tư "+ vtMa + " đã tồn tại ");
				  		}
					  	
		 			  	}
		 			});
 			}
 		}

		function preEditVattu(formId, check){
			vtMa = $('input:checkbox[name=vtMa]:checked').val();
			//alert(vtMa);
			var vtMaList = [];
			$.each($("input[name='vtMa']:checked"), function(){            
				vtMaList.push($(this).val());
		    });
			if (vtMaList.length == 0)
				alert('Bạn phải chọn 1 vật tư để thay đổi!!');
			else if (vtMaList.length > 1)
				alert('Bạn chỉ được chọn 1 vật tư để thay đổi!!');
			else {
				$.ajax({
					url: "/QLVatTuYeuCau/preEditVattu.html",
					type: "GET",
					dataType: "JSON",
					data: {"vtMa": vtMa},
					contentType: "application/json",
					mimeType: "application/json",
					
					success: function(vt){
						
						$('input:text[name=vtMaUpdate]').val(vt.vtMa);
					  	$('input:text[name=vtTenUpdate]').val(vt.vtTen);
						$('#dvtUp option[value='+vt.dvt.dvtTen+']').prop('selected',true);
						$('#aa').focus();
					  	showForm(formId, check);
					}
					
				});
		}
		}
		function confirmUpdateVattu(){
			var vtMaUpdate = $('input:text[name=vtMaUpdate]').val();
			var vtTenUpdate = $('input:text[name=vtTenUpdate]').val();
			var dvtUpdate = $('select[name=dvtUpdate]').val();
			if (confirm('Bạn có chắc thay đổi vật tư có mã ' + vtMaUpdate))
				updateVattu(vtMaUpdate, vtTenUpdate, dvtUpdate);
		}
 		function updateVattu(vtMaUpdate, vtTenUpdate, dvtUpdate){
 			
 			if (vtTenUpdate == '')
	 			{
	 				$('#requireVtTenUp').html('Vui lòng nhập tên vật tư');
	 			}
 			else if(dvtUpdate == '')
 				{
 					$('#requireDvtUp').html('Vui lòng chọn đơn vị tính');
 				}
 			else {

		 			$.ajax({
						url: "/QLVatTuYeuCau/updateVattu.html",	
					  	type: "GET",
					  	dateType: "JSON",
					  	data: { "vtMaUpdate": vtMaUpdate, "vtTenUpdate": vtTenUpdate, "dvtUpdate": dvtUpdate},
					  	contentType: 'application/json',
					    mimeType: 'application/json',
						  	
						  	success: function(vt) {
						  		$('table tr').has('input[name="vtMa"]:checked').remove();
						  		$('#view-table-vat-tu table tr:first').after('<tr class=\"rowContent\"><td class=\"left-column\"><input type=\"checkbox\" name=\"vtMa\" value=\"' +vtMaUpdate + '\"</td><td class=\"col\">'+ vtMaUpdate +'</td><td class=\"col\">' + vtTenUpdate+'</td><td class=\"col\">' 
						  				+ dvtUpdate+'</td><td style=\"text-align: center;\"><button type=\"button\" class=\"button-xem\" value=\"Xem\" onclick=\"showCTVatTu(\'chitiet\',true,\''
										+vtMaUpdate+'\');\">Xem</button></td></tr>');
						  		$('input:text[name=vtMaUpdate]').val('');			 
						  		$('input:text[name=vtTenUpdate]').val('');
								$('select[name=dvtUpdate]').val('');
						  		showForm("update-form", false);	
						  		alert("Thay đổi thành công vật tư có mã "+vtMaUpdate+ " !")
						  	}
						});
 			}
 	}
 		function resetUpdateVT(){
 			$('#update-form input:text[name=vtTenUpdate]').val('');
			$('#update-form select[name=dvtUpdate]').val('');
 		}
 	function confirmDeleteVT(){
 		vtMa = $('input:checkbox[name=vtMa]:checked').val();	
 		$.ajax({
 			url: "/QLVatTuYeuCau/comfirmdeleteVattu.html",	
 		  	type: "GET",
 		  	dateType: "JSON",
 		  	data: { "vtMa": vtMa},
 		  	contentType: 'application/json',
 		    mimeType: 'application/json',
 		    
 		    
 		  	success: function(result) {
 		  		if(result == "success")
 		  			{
 		  			if (confirm('Bạn có chắc xóa ' + vtMa))
 		  	 			deleteVattu(vtMa);  			
 		  			}
 		  		else
 		  			{
 		  				alert("Không thể xóa!");
 		  			}
 		    } 
 		});  
 		
 		var vtMa = $('input:checkbox[name=vtMa]:checked').val();
		var vtMaList = [];
		$.each($("input[name='vtMa']:checked"), function(){            
			vtMaList.push($(this).val());
	    });
		var str = vtMaList.join(", ");
		if (vtMaList.length == 0)
			alert('Bạn phải chọn 1 hoặc nhiều vật tư để xóa!!');
		else if (confirm('Bạn có chắc xóa vật tư có mã ' + str))
			deleteVattu(str);

 	}
	
  	 function deleteVattu(str) {
		 
 		$.ajax({
 			url: "/QLVatTuYeuCau/deleteVattu.html",	
 		  	type: "GET",
 		  	dateType: "JSON",
 		  	data: { "vtList": str},
		  	contentType: 'application/json',
		    mimeType: 'application/json',
 		  	success: function(result) {
 		  		
	 		  			$('#view-table-vat-tu table tr').has('input[name="vtMa"]:checked').remove();
	 		  			alert('Vật tư có mã ' + str + " đã bị xóa");	  			
 		    } 
 		});  
 	}
  	 


  	function showCTVatTu(formId, check, vtMa){ 
  		var f = document.getElementById('vattu'), s, opacity;
		s = f.style;
		opacity = check? '10' : '100';
		s.opacity = s.MozOpacity = s.KhtmlOpacity = opacity/100;
		s.filter = 'alpha(opacity='+opacity+')';
		for(var i=0; i<f.length; i++) f[i].disabled = check;
  		$.ajax({
  			url: "/QLVatTuYeuCau/showCTVatTu.html",
			type: "GET",
		  	dateType: "JSON",
		  	data: {"vtMa": vtMa},
		  	contentType: 'application/json',
		    mimeType: 'application/json',
		 
			success: function(listCTVatTu){
				
				$('#view-table-chi-tiet table .rowContent').remove();
//				if(listCTVatTu.a == null)
//					alert("1");
//					else alert("2");
			//	alert(listCTVatTu.vtMa)
				if(listCTVatTu.vtMa != null) {
					$('#add-chitiet input:text[name=vtMa]').val(listCTVatTu.vtMa);
					$('#add-chitiet input:text[name=vtTen]').val(listCTVatTu.vtTen);
					$('#add-chitiet input:text[name=dvt]').val(listCTVatTu.dvt.dvtTen);
					alert("Không có chi tiết vật tư!");
				}
				else {
					if(listCTVatTu.length>0){
					
							for(i = 0;i < listCTVatTu.length; i++ ) {
			
							$('#view-table-chi-tiet table tr:first').after("<tr class=\"rowContent\"><td class=\"left-column\"><input type=\"checkbox\" name=\"ctvtId\" value=\""
									+ listCTVatTu[i].ctvtId + "\" id=\"checkbox\"></td>"
									+"<td class=\"col\">" +listCTVatTu[i].vatTu.vtMa+ "</td>"
									+"<td class=\"col\">" +listCTVatTu[i].vatTu.vtTen+ "</td>"
									+"<td class=\"col\">" +listCTVatTu[i].noiSanXuat.nsxTen+ "</td>"
									+"<td class=\"col\">" +listCTVatTu[i].chatLuong.clTen+ "</td>"
									+"<td class=\"col\">" +listCTVatTu[i].vatTu.dvt.dvtTen+ "</td>"
									+"<td class=\"col\">" +listCTVatTu[i].dinhMuc+ "</td>"
									+"<td class=\"col\">" +listCTVatTu[i].soLuongTon+ "</td></tr>");
							}
							vtMa = listCTVatTu[0].vatTu.vtMa;
							vtTen = listCTVatTu[0].vatTu.vtTen;
							dvt = listCTVatTu[0].vatTu.dvt.dvtTen;
							$('#add-chitiet input:text[name=vtMa]').val(vtMa);
							$('#add-chitiet input:text[name=vtTen]').val(vtTen);
							$('#add-chitiet input[name=dvt]').val(dvt);
							
						}
				}
				showForm(formId, check);
			}
		  	
  		});
  	}
  	
  	

  	function changeVtMa(){
  		$('#requireVtMa').html('');
  		$('#add-form input:text[name=vtMa]').focus();
 	} 	
  	
  	function changeVtTen(){
  		$('#requireVtTen').html('');
  		$('#add-form input:text[name=vtTen]').focus();
 	}
  	
  	function changeDvt(){
  		$('#requireDvt').html('');
  		$('#add-form input[name=dvt]').focus();
 	}
  	

  	function changeVtTenUp(){
  		$('#requireVtTenUp').html('');
  		$('#update-form input:text[name=vtTenUpdate]').focus();
 	}
  	
  	function changeDvtUp(){
  		$('#requireDvtUp').html('');
  		$('#update-form input[name=dvtUpdate]').focus();
 	}
  	
  	
    $(document).ready(function() {
        $('#view-table-vat-tu .checkAll').click(function(event) {  //on click 
            if(this.checked) { // check select status
                $('#view-table-vat-tu .checkbox').each(function() { //loop through each checkbox
                    this.checked = true;  //select all checkboxes with class "checkbox1"               
                });
            }else{
                $('#view-table-vat-tu .checkbox').each(function() { //loop through each checkbox
                    this.checked = false; //deselect all checkboxes with class "checkbox1"                       
                });
            }
        });
        
    }); 

    function test1(){
    	$('#test select option[value='+3+']').prop('selected',true);
    }
    
    
    $(document).ready(function() {
 	  	$('.page').click(function(){
 		var pageNumber = $(this).val();
 	    	$.ajax({
 				url: "/QLVatTuYeuCau/loadPageVatTu.html",	
 			  	type: "GET",
 			  	dateType: "JSON",
 			  	data: { "pageNumber": pageNumber},
 			  	contentType: 'application/json',
 			    mimeType: 'application/json',
 			  	
 			  	success: function(vtList) {
 			  		$('#view-table-vat-tu table .rowContent').remove();
 					if(vtList.length>0){
 						for(i = 0;i < vtList.length; i++ ) {
 							var vt = vtList[i] ;
 							var style = '';	
 							if (i % 2 == 0)
 								style = 'style=\"background : #CCFFFF;\"';
 							var str = '';
 							str = '<tr class=\"rowContent\" ' + style + '>'
 								+ '<td class=\"left-column\"><input type=\"checkbox\" name=\"vtMa\" value=\"' 
 								+ vt.vtMa +'\" class=\"checkbox\"></td>'
 								+ '<td class=\"col\">' + vt.vtMa + '</td>'
 								+ '<td class=\"col\">' + vt.vtTen + '</td>'
 								+ '<td class=\"col\">' + vt.dvt.dvtTen + '</td>'
 								+ '<td style=\"text-align: center;\"><button type=\"button\" class=\"button-xem\" value=\"Xem\" onclick=\"showCTVatTu(\'chitiet\',true,\''
								+vt.vtMa+'\');\">Xem</button></td></tr>';
 							$('#view-table-vat-tu table tr:first').after(str);
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
		 addVattu();
	    return false;  
	  }
	});   
});   
$(document).ready(function() {
	$('#update-form').keypress(function(e) {
	 var key = e.which;
	 if(key == 13)  // the enter key code
	  {
	    updateVattu();
	    return false;  
	  }
	});   
});  
