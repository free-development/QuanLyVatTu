function showForm(formId1, formId2, check){
    if (check)
        document.getElementById(formId2).style.display="block";
    else 
        document.getElementById(formId2).style.display="none";
    var f = document.getElementById(formId1), s, opacity;
    s = f.style;
    opacity = check? '10' : '100';
    s.opacity = s.MozOpacity = s.KhtmlOpacity = opacity/100;
    s.filter = 'alpha(opacity='+opacity+')';
    for(var i=0; i<f.length; i++) f[i].disabled = check;
}
function checkAdd(){
	var cvSo = $('#add-form input:text[name=cvSo]').val();
	var ngayNhan = $('#add-form input:text[name=ngayNhan]').val();
	var mucDich = $('#add-form select[name=mucDich]').val();
	var donVi = $('#add-form select[name=donVi]').val();
	var file = $('#add-form input[name=file]').val();
	var moTa = $('#add-form textarea[name=moTa]').val();

	if(cvSo == ''){
		$('#requireSoCv').html('Vui lòng nhập số công văn');
		return false;
	}
	else if(ngayNhan == ''){
		$('#requireNgayNhan').html('Vui lòng chọn ngày nhận công văn');
		return false;
	}
	else if(mucDich == null){
		$('#requireMucDich').html('Vui lòng chọn mục đích');
		return false;
	}
	else if(donVi == null){
		$('#requireDonVi').html('Vui lòng chọn đơn vị');
		return false;
	}
	else if(file == ''){
		$('#requireFile').html('Vui lòng đính kèm file');
		return false;
	}
	else if(moTa == ''){
		$('#requireMoTa').html('Vui lòng nhập mô tả tóm tắt file');
		return false;
	}
	return true;
}
function changeSoCv(){
	$('#requireSoCv').html('');
} 	
function changeNgayNhan(){
	$('#requireNgayNhan').html('');
} 	
function changeMucDich(){
	$('#requireMucDich').html('');
} 	
function changeDonVi(){
	$('#requireDonVi').html('');
} 	
function changeFile(){
	$('#requireFile').html('');
} 	
function changeMoTa(){
	$('#requireMoTa').html('');
} 	

function changeNgayNhanUp(){
	$('#requireNgayNhanUp').html('');
} 	
function changeMucDichUp(){
	$('#requireMucDichUp').html('');
} 	
function changeDonViUp(){
	$('#requireDonViUp').html('');
} 	
function changeFileUp(){
	$('#requireFileUp').html('');
} 	
function changeTrangThaiUp(){
	$('#requireTrangThaiUp').html('');
}
function checkUp(){
	var ngayNhan = $('#update-form input:text[name=ngayNhanUpdate]').val();
	var mucDich = $('#update-form select[name=mucDichUpdate]').val();
	var donVi = $('#update-form select[name=donViUpdate]').val();
	var file = $('#update-form input[name=fileUpdate]').val();
	var trangThai = $('#update-form radio[name=trangThaiUpdate]').val();

	if(ngayNhan == ''){
		$('#requireNgayNhanUp').html('Vui lòng chọn ngày nhận công văn');
		return false;
	}
	else if(mucDich == null){
		$('#requireMucDichUp').html('Vui lòng chọn mục đích');
		return false;
	}
	else if(donVi == null){
		$('#requireDonViUp').html('Vui lòng chọn đơn vị');
		return false;
	}
	else if(file == ''){
		$('#requireFileUp').html('Vui lòng đính kèm file');
		return false;
	}
	else if(trangThai == ''){
		$('#requireTrangThaiUp').html('Vui lòng cập nhật trạng thái');
		return false;
	}
	return true;
}
function checkCongVan() {
	var congVanList = [];
	$.each($("input[name='cvId']:checked"), function(){            
		congVanList.push($(this).val());
    });
	if (congVanList.length == 1)
		return true;
	if (congVanList.length == 0)
		alert('Bạn phải chọn 1 công văn để cập nhật yêu cầu!!');
	else if (congVanList.length > 1)
		alert('Bạn chỉ được chọn 1 công văn để  cập nhật yêu cầu!!');
		return false;
}


function confirmDelete(){
	var cvId = $('input:checkbox[name=cvId]:checked').val();
	var congVanList = [];
	$.each($("input[name='cvId']:checked"), function(){            
		congVanList.push($(this).val());
    });
	var str = congVanList.join(', ');
//	alert('1');
//	confirm('Bạn có chắc xóa' + cvId);
	if (confirm('Bạn có chắc xóa cong van?'))
		deleteCv(str);
}
function checkUpdate() {
	var congVanList = [];
	$.each($("input[name='cvId']:checked"), function(){            
		congVanList.push($(this).val());
    });
	
	if (congVanList.length == 0)
		alert('Bạn phải chọn 1 công văn để sửa đổi!!');
	else if (congVanList.length > 1)
		alert('Bạn chỉ được chọn 1 công văn để  sửa đổi!!');
	else if (congVanList.length == 1)
		preUpdateCv(congVanList[0]);
}
function preUpdateCv(cv) {
	$.ajax({
		url: "/QLVatTuYeuCau/preUpdateCv.html",	
	  	type: "GET",
	  	dateType: "JSON",
	  	data: { "congVan": cv},
	  	contentType: 'application/json',
	    mimeType: 'application/json',
	  	success: function(congVan) {
//			$('table').has('input[name="cvId"]:checked').remove();
//			alert("Cong van da bi xoa");
	  		//alert(congVan.trangThai.ttMa);
	  		$('#update-form input:text[name=soDen]').val(congVan.soDen);
	  		$('#update-form input:text[name=cvSo]').val(congVan.cvSo);
	  		$('#update-form input:text[name=ngayGoiUpdate]').val(congVan.cvNgayGoi);
	  		$('#update-form input:text[name=ngayNhanUpdate]').val(congVan.cvNgayNhan);
	  		$('#update-form select[name=donViUpdate] option[value=' + congVan.donVi.dvMa+']').prop('selected',true);
//	  		$('#dvtUp option[value='+vt.dvt.dvtTen+']').prop('selected',true);
	  		$('#update-form select[name=mucDichUpdate] option[value=' + congVan.mucDich.mdMa+']').prop('selected',true);
	  		$('#update-form textarea[name=trichYeuUpdate]').val(congVan.trichYeu);
	  		$('#update-form textarea[name=butPheUpdate]').val(congVan.butPhe);
	  		$('#update-form input:radio[name=ttMaUpdate][value='+congVan.trangThai.ttMa+']').prop('checked',true);
	  		showForm('main-form','update-form', true);
	    }
	});  
}
function deleteCv(cvId) {
	$.ajax({
		url: "/QLVatTuYeuCau/deleteCv.html",	
	  	type: "GET",
	  	dateType: "JSON",
	  	data: { "cvId": cvId},
	  	contentType: 'application/json',
	    mimeType: 'application/json',
	  	success: function() {
			$('table').has('input[name="cvId"]:checked').remove();
			alert("Cong van da bi xoa");
	    } 
	});  
}
function loadDataCv() {
	showForm('main-form', 'add-form', true);
}
function chiaSeCv() {
//	var cvId = $('input:checkbox[name=cvId]:checked').val();
	
		$.ajax({
			url: "/QLVatTuYeuCau/chiaSeCv.html",	
		  	type: "GET",
		  	dateType: "JSON",
		  	data: { "cvId": cvId},
		  	contentType: 'application/json',
		    mimeType: 'application/json',
		   
		});  
}
function loadMonth(year) {
	$.ajax({
		url: "/QLVatTuYeuCau/loadMonth.html",	
	  	type: "GET",
	  	dateType: "JSON",
	  	data: { "year": year},
	  	contentType: 'application/json',
	    mimeType: 'application/json',
	    success: function(monthList) {
	    	var content = '';
	    	var length = monthList.length;
	    	var monthLi = '';
	    	var monthOl = '';
	    	for(var i = 0; i< length; i++) {
	    		monthLi += 	'<li id = \"month' + monthList[i] + '\">' 
							+ '<label for="m' + monthList[i] + '\">' + 'Tháng ' + monthList[i]  + '</label>' 
							+' <input type="checkbox" class=\"month\" id=\"m' + monthList[i] + '\" onchange="loadDate(' + monthList[i] + '); ' + '\"/>' 
							+ '<ol></ol> </li>'; 
	    	}
	    	var yearLi = '';
	    	$('#year'+year + ' ol').html(monthLi);
	    } 
	});  
}
function loadDate(month) {
	$.ajax({
		url: "/QLVatTuYeuCau/loadDate.html",	
	  	type: "GET",
	  	dateType: "JSON",
	  	data: { "month": month},
	  	contentType: 'application/json',
	    mimeType: 'application/json',
	    success: function(dateList) {
	    	var content = '';
	    	var length = dateList.length;
	    	var dateLi = '';
	    	var dateOl = '';
	    	for(var i = 0; i< length; i++) {
	    		dateLi += 	'<li id = \"date' + dateList[i] + '\">' 
							+ '<label for="d' + dateList[i] + '\">' + 'Ngày ' + dateList[i]  + '</label>' 
							+' <input type="button" class=\"date\" id=\"d' + dateList[i] +'\" value =\"' + dateList[i] + '\" onclick=\"showMesseage(' + dateList[i] + ');' + '\"/>' 
							+ '</li>'; 
	    	}
	    	$('#month'+month + ' ol').html(dateLi);
	    } 
	});  
}
function showMesseage(date) {
	alert(date);
}
$(document).ready(function() {
	  	$('.page').click(function(){
		var pageNumber = $(this).val();
	    	$.ajax({
				url: "/QLVatTuYeuCau/loadPageCv.html",	
			  	type: "GET",
			  	dateType: "JSON",
			  	data: { "pageNumber": pageNumber},
			  	contentType: 'application/json',
			    mimeType: 'application/json',
			  	
			  	success: function(cvList) {
			  		$('#view-table table .rowContent').remove();
					if(cvList.length>0){
						for(i = 0;i < cvList.length; i++ ) {
							var cv = cvList[i] ;
							var style = '';	
							if (i % 2 == 0)
								style = 'style=\"background : #CCFFFF;\"';
							var str = '';
							str = '<tr class=\"rowContent\" ' + style + '>'
								+ '<td class=\"left-column\"><input type=\"checkbox\" name=\"cvId\" value=\"' 
								+ cv.cvId +'\" class=\"checkbox\"></td>'
								+ '<td class=\"col\">' + cv.cvId + '</td>'
								+ '<td class=\"col\">' + cv.donVi.dvMa + '</td>'
								+ '<td class=\"col\">' + cv.trangThai.ttMa + '</td>'
								+ '<td class=\"col\">' + cv.mucDich.mdMa + '</td>'
								+ '<td class=\"col\">' + cv.soDen + '</td>'
								+ '<td class=\"col\">' + cv.ngayNhan + '</td>'
								+ '<td class=\"col\">' + cv.cvSo + '</td>'
								+ '<td class=\"col\">' + cv.ngayGoi + '</td>'
								+ '<td class=\"col\">' + cv.trichYeu + '</td>'
								+ '<td class=\"col\">' + cv.butPhe + '</td>'
								+ '</tr>';
							$('#view-table table tr:first').after(str);
						}
					}
			  	}
			});
	    });	
	})   

//function addCongVan() {
//	var nsxMa = $('#add-form input:text[name=nsxMa]').val();
//	var nsxTen = $('#add-form input:text[name=nsxTen]').val();
//	$.ajax({
//		url: "/QLVatTuYeuCau/addNsx.html",	
//	  	type: "GET",
//	  	dateType: "JSON",
//	  	data: { "nsxMa": nsxMa, "nsxTen": nsxTen},
//	  	contentType: 'application/json',
//	    mimeType: 'application/json',
//	  	
//	  	success: function(nsx) {
//		  	$('input:text[name=nsxMa]').val(nsx.nsxMa);
//		  	$('input:text[name=nsxTen]').val(nsx.nsxTen);
//	  		$('#view-table table tr:first').after('<tr><td class=\"left-column\"><input type=\"checkbox\" name=\"nsxMa\" value=\"' +nsx.nsxMa + '\"</td><td class=\"col\">'+ nsxMa +'</td><td class=\"col\">' + nsxTen+'</td></tr>');
//	  		$('#add-form input:text[name=nsxMa]').val('');
//			$('#add-form input:text[name=nsxTen]').val('');
//	  		showForm("add-form", false);	
//	  	}
//	});
//}
//function preUpdateCv() {
//	
//	showForm('main-form','update-form', true);
//}
//function preUpdatecV(formId, check) {
//	var cvId = $('input:checkbox[name=cvId]:checked').val();
//	$.ajax({
//		url: "/QLVatTuYeuCau/preEditCongVan.html",	
//	  	type: "GET",
//	  	dateType: "JSON",
//	  	data: { "cvId": cvId},
//	  	contentType: 'application/json',
//	    mimeType: 'application/json',
//	  	
//	  	success: function(congVan) {
//		  	$('input:text[name=soDensUpdate]').val(nsx.soDen);
//		  	$('input:text[name=cvSoUpdate]').val(congVan.cvSo)
//		  	document.getElementById('personlist').getElementsByTagName('option')[11].selected = 'selected';
//		  	$('input:text[name=cvSoUpdate]').val(congVan.cvSo);
//	  		showForm(formId, check);	
//	  		
//	  	}
//	});
//	showForm('main-form','update-form', true);
//}
$(document).ready(function(){
	$('.year').change(function(){
//		alert($(this).val());
		var year = $(this).val();
		loadMonth(year);
	});
});
$(document).ready(function(){
	$('.month').change(function(){
//		alert($(this).val());
		var month = $(this).val();
		loadDate(month);
	});
});	
$(document).ready(function(){
	$('.date').click(function(){
//		alert($(this).val());
		var date = $(this).val();
//		loadDate(month);
		alert(date);
	});
});	
