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
function loadByYear(year) {
	$.ajax({
		url: "/QLVatTuYeuCau/loadByYear.html",	
	  	type: "GET",
	  	dateType: "JSON",
	  	data: { "year": year},
	  	contentType: 'application/json',
	    mimeType: 'application/json',
	    success: function(objectList) {
	    	// load month 
	    	var monthList = objectList[2];
	    	var content = '';
	    	var length = monthList.length;
	    	var monthLi = '';
	    	var monthOl = '';
	    	for(var i = 0; i< length; i++) {
	    		monthLi += 	'<li id = \"month' + monthList[i] + '\">' 
							+ '<label for="m' + monthList[i] + '\">' + 'Tháng ' + monthList[i]  + '</label>' 
							+' <input type="checkbox" class=\"month\" id=\"m' + monthList[i] + '\" onchange="loadByMonth(' + year + ', ' + monthList[i] + '); ' + '\"/>' 
							+ '<ol></ol> </li>'; 
	    	}
	    	var yearLi = '';
	    	$('#year'+year + ' ol').html(monthLi);
	    	// load congVan
	    	var congVanList = objectList[0];
	    	var fileList = objectList[1];
	    	var size = objectList[3];
	    	var unknownList = objectList[4];
	    	loadCongVan(congVanList, fileList, unknownList);
	    	loadPageNumber(0, '',size);
	    } 
	});  
}
function loadByMonth(year, month) {
	$.ajax({
		url: "/QLVatTuYeuCau/loadByMonth.html",	
	  	type: "GET",
	  	dateType: "JSON",
	  	data: { "year": year, "month": month },
	  	contentType: 'application/json',
	    mimeType: 'application/json',
	    success: function(objectList) {
	    	var content = '';
	    	var dateList = objectList[2];
	    	var length = dateList.length;
	    	var dateLi = '';
	    	var dateOl = '';
	    	for(var i = 0; i< length; i++) {
	    		dateLi += 	'<li id = \"date' + dateList[i] + '\">' 
							+ '<label for="d' + dateList[i] + '\">' + 'Ngày ' + dateList[i]  + '</label>' 
							+' <input type="button" class=\"date\" id=\"d' + dateList[i] +'\" value =\"' + dateList[i] + '\" onclick=\"loadByDate(' + year + ', ' + month  + ', ' + dateList[i] + ');' + '\"/>' 
							+ '</li>'; 
	    	}
	    	$('#month'+month + ' ol').html(dateLi);
	    	// load Cong van
	    	var congVanList = objectList[0];
	    	var fileList = objectList[1];
	    	var size = objectList[3];
	    	var unknownList = objectList[4];
	    	loadCongVan(congVanList, fileList, unknownList);
	    	loadPageNumber(0, '',size);
	    } 
	});  
}
function loadCongVan(congVanList, fileList, unknownList) {
	$('.tableContent').remove();
	var length = congVanList.length;
	var tables = '';
	if(length > 0) {
		for (var i = 0; i < length; i++) {
			var style = 'style=\"';
			var congVan = congVanList[i];
			var file = fileList[i];
			if (i % 2 == 1)
				style += 'background : #CCFFFF; ';
			else
				style += 'background : #FFFFFF; ';
			style += ' font-size: 16px; width: 900px;\"';
			tables +=     '<table class=\"tableContent\" ' + style + ' class=\"border-congvan\">'
						+ '<tr >';
						if(chucDanhMa == vanThuMa) {
						tables += '<td class=\"column-check\" rowspan=\"7\">'
						+ '<input title=\"Click để chọn công văn\" type=\"checkbox\" name=\"cvId\" value=\"' + congVan.cvId + '\">'
						+ '</td>';
						}
						tables += '<td class=\"left-column-soden\" style=\"font-weight: bold;\">Số đến: &nbsp;&nbsp;</td>'
						+ '<td class=\"column-so-den\" style=\"text-align: left\">' + congVan.soDen + '</td>'
						+ '<td class=\"left-column-socv\" style=\"font-weight: bold;\">Số công văn: &nbsp;&nbsp;</td>'
						+ '<td class=\"column-socv\" style=\"text-align: left;color:red;\">' + congVan.cvSo + '</td>'
						+ '<td class=\"left-column-first\" style=\"font-weight: bold;\">Ngày đến: &nbsp;&nbsp;</td>'
						+ '<td class=\"column-date\" style=\"text-align: left;color:blue;\">' + congVan.cvNgayNhan + '</td>'
						+ '</tr>'
						+ '<tr>'
						+ '<td class=\"left-column-first\" style=\"font-weight: bold;\">Mục đích: &nbsp;&nbsp;</td>'
						+ '<td class=\"column-color\" colspan=\"3\" style=\"text-align: left\">' + congVan.mucDich.mdTen + '</td>'
						+ '<th class=\"left-column-ngdi\" style=\"font-weight: bold;\">Ngày công văn đi:&nbsp;&nbsp;</th>'
						+ '<td class=\"column-date\" style=\"text-align: left;color:blue;\">' + congVan.cvNgayDi+ '</td>'
						+ '</tr>'
						+ '<tr>'
						+ '<td class=\"left-column-first\" style=\"font-weight: bold;\">Nơi gửi: &nbsp;&nbsp;</td>'
						+ '<td class=\"column-color\" colspan=\"3\" style=\"text-align: left\">' +  congVan.donVi.dvTen + '</td>'
						+ '<td colspan=\"1\" style=\"font-weight: bold;\">Trạng thái</td>'
						+ '<td colspan=\"1\" style=\"color:red;font-weight: bold;font-style: oblique;\">' + congVan.trangThai.ttTen + '</td>'
						
						+ '</tr>'
						+ '<tr>'
						+ '<td class=\"left-column-first\" style=\"font-weight: bold;\">Trích yếu: &nbsp;&nbsp;</td>'
						+ '<td class=\"column-color\" colspan=\"6\" style=\"text-align: left;font-weight: bold;\">' +  congVan.trichYeu + '</td>'
						+ '</tr>'
						+ '<tr>'
						+ '<td class=\"left-column-first\" style=\"font-weight: bold;\">Bút phê: &nbsp;&nbsp;</td>'
						+ '<td class=\"column-color\" colspan=\"6\">' +  congVan.butPhe + '</td>'
						+ '</tr>'
						+ '<tr>';
					if (chucDanhMa == truongPhongMa || chucDanhMa == vanThuMa) {
						var cellNguoiXl = '';
						if (unknownList.length > 0) {
							var nguoiXlList = unknownList[i];  
							cellNguoiXl = nguoiXlList.join(', ');
//							for (var j = 0; j < nguoiXlList.length; j++) {
//								cellNguoiXl += nguoiXlList[j];
//							}
						}
						tables += '<td class=\"left-column-first\" style=\"font-weight: bold;\">Người xử lý</td>'
						+ '<td class=\"column-color\" colspan=\"3\">' + cellNguoiXl + '</td>';
						if (chucDanhMa == truongPhongMa) {
							tables += '<td colspan=\"3\" style=\"float: right;\">'
							+ '<button  class=\"button-chia-se\" id=\"chiaSe\" type=\"button\" style=\"width: 170px; height: 30px;\"' 
							+ '  onclick=\"location.href=\'/QLVatTuYeuCau/cscvManage.html?action=chiaSeCv&congVan=' + congVan.cvId + '\'\">'
							+ '<i class=\"fa fa-spinner\"></i>&nbsp;&nbsp;Chia sẻ công văn'
							+ '</button>'
							+ '</td>';
						}
					} else {
						var cellVaiTro = '';
						var capPhat = false;
						if (unknownList.length > 0) {
							var vtCongVanList = unknownList[i];  
//							cellVaiTro = vtCongVanList.vtTen.join(', ');
							for (var j = 0; j < vtCongVanList.length; j++) {
								var vtTen = vtCongVanList[j].vtTen;
								cellVaiTro += vtTen;
								if (vtCongVanList[j].vtId == capVatTuId)
									capPhat = true;
							}
						}
						tables += '<td class=\"left-column-first\" style=\"font-weight: bold;\">Vai trò</td>'
							+ '<td class=\"column-color\" colspan=\"3\">' + cellVaiTro + '</td>';
							
						if (capPhat == true) {
							tables += '<td colspan=\"3\" style=\"float: right;\">' 
							+ '<button class=\"button\" type=\"button\" style=\"width: 200px; height: 30px;\"' 
							+ '  onclick=\"location.href=\'/QLVatTuYeuCau/ycvtManage.html?cvId=' + congVan.cvId + '\"\'>'
							+ '<i class="fa fa-spinner"></i>&nbsp;&nbsp;Cập vật tư yêu cầu'
							+ '</button>'
							+ '</td>';
						}
					}
				tables	+= '</tr>'
						+ '<tr>'
						+ '<td class=\"left-column-first\" style=\"font-weight: bold;\">Xem công văn: </td>'
						+ '<td colspan=\"1\">'
						+ '<a href=\"' + '/QLVatTuYeuCau/cvManage.html' + '?action=download&file=' + congVan.cvId + '\">'
						+ '<div class=\"mo-ta\">' + file.moTa + '</div>'
						+ '</a> '
						+ '</td>'
						+ '</tr>'
						+ '</table>'
						+'<br>'
						+'<hr>';
			
		}
	} else{
		alert('Không tồn tại công văn');
	}
	$('.scroll_content').html(tables);
	if (check == false) {
		$('.button-chia-se').hide();;
	}
}
function loadByDate(year, month, date) {
	$.ajax({
		url: "/QLVatTuYeuCau/loadByDate.html",	
	  	type: "GET",
	  	dateType: "JSON",
	  	data: { "year": year, "month": month, "date": date},
	  	contentType: 'application/json',
	    mimeType: 'application/json',
	    success: function(objectList) {
	    	var congVanList = objectList[0];
	    	var fileList = objectList[1];
	    	var size = objectList[2];
	    	var unknownList = objectList[3];
	    	loadCongVan(congVanList, fileList, unknownList);
	    	loadPageNumber(0, '',size);
	    } 
	});
};
function filterData(filter, filterValue) {

	$.ajax({
		url: "/QLVatTuYeuCau/filter.html",	
	  	type: "GET",
	  	dateType: "JSON",
	  	data: {"filter": filter, "filterValue": filterValue},
	  	contentType: 'application/json',
	    mimeType: 'application/json',
	    success: function(objectList) {
	    	var congVanList = objectList[0];
	    	var fileList = objectList[1];
	    	var size = objectList[2];
	    	var unknownList = objectList[3];
	    	loadCongVan(congVanList, fileList, unknownList);
	    	loadPageNumber('',size);
	    } 
	});
}
function searchByTrangThai(trangThai) {

	$.ajax({
		url: "/QLVatTuYeuCau/searchByTrangThai.html",	
	  	type: "GET",
	  	dateType: "JSON",
	  	data: { "trangThai": trangThai},
	  	contentType: 'application/json',
	    mimeType: 'application/json',
	    success: function(objectList) {
	    	var congVanList = objectList[0];
	    	var fileList = objectList[1];
	    	var size = objectList[2];
	    	var unknownList = objectList[3];
	    	loadCongVan(congVanList, fileList, unknownList);
			 loadPageNumber(0, '',size)
	    } 
	});
}
/*
$(document).ready(function() {
	  	$('.page').click(function(){
		var pageNumber = $(this).val();
	    	$.ajax({
				url: "/QLVatTuYeuCau/loadPageCongVan.html",	
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
	}) ; 
	*/
function loadPage(pageNumber) {
	
//	var page = 0;
//	var p = 0;
	if (pageNumber == 'Next') {
		var lastPage = document.getElementsByClassName('page')[9].value;
//		var pageNext = lastPage
		
		var p = (lastPage) / 5;
		var page = lastPage;
	} else if (pageNumber == 'Previous') {
		var firstPage = document.getElementsByClassName('page')[0].value;
		var p = (firstPage -1) / 5;
		var page =  firstPage - 2;
	} else {
		var page = pageNumber;
	}

	$.ajax({
		url: "/QLVatTuYeuCau/loadPageCongVan.html",	
	  	type: "GET",
	  	dateType: "JSON",
	  	data: { "pageNumber": page},
	  	contentType: 'application/json',
	    mimeType: 'application/json',
	  	success: function(objectList) {
	  		var size = objectList[2];
	  		var congVanList = objectList[0];
	  		var fileList = objectList[1];
	  		var unknownList = objectList[3];
	    	loadCongVan(congVanList, fileList, unknownList);
	  		loadPageNumber(p, pageNumber,size) ;
					
	  	}
	});
};
function loadPageNumber(p, pageNumber, size) {
	var buttons = '';
	if(pageNumber == 'Next') {
		for (var i = 0; i < 10; i++) {
			var t = ((p - 1) * 5 + i + 1);
			buttons += '<input type=\"button\" value=\"' + t + '\" class=\"page\" onclick= \"loadPage(' + ((p -1)*5 + i)  +')\">&nbsp;';
			if (t == size)
				break;
		}
		buttons = '<input type=\"button\" class=\"pageMove\"  value=\"<< Trước\" onclick= \"loadPage(\'Previous\')\">&nbsp;'  + buttons;
		if ((p + 1) * 5 < size)
			buttons += '<input type=\"button\" class=\"pageMove\" value=\"Sau>>\" onclick= \"loadPage(\'Next\');\">';
		$('#paging').html(buttons);
		$('.page')[5].focus();
	} else if (pageNumber == 'Previous'){
		if (p > 0)
			p = p -1;
		for (var i = 0; i < 10; i++)
			buttons += '<input type=\"button\" value=\"' + (p * 5 + i + 1) + '\" class=\"page\" onclick= \"loadPage(' + (p * 5 + i)  +')\">&nbsp;';
		
		buttons = buttons + '<input type=\"button\" class=\"pageMove\" value=\"Sau>>\" onclick= \"loadPage(\'Next\');\">';
		if (p >= 1)
			buttons = '<input type=\"button\" class=\"pageMove\" value=\"<<Trước\" onclick= \"loadPage(\'Previous\')\">&nbsp;' + buttons;
		$('#paging').html(buttons);
		$('.page')[4].focus();
	} else if (pageNumber == '') {
		var buttons = '';
		var index = 0;
		if (size <= 10)
			index = size;
		else 
			index = 10;
		for (var i = 0; i < index; i++) {
//			var t = ((p - 1) * 5 + i + 1);
			buttons += '<input type=\"button\" value=\"' + (i + 1) + '\" class=\"page\" onclick= \"loadPage(' + i  +')\">&nbsp;';
		}
		if (size > 10)
			buttons += '<input type=\"button\" class=\"pageMove\" value=\"Sau>>\" onclick= \"loadPage(\'Next\');\">';
		$('#paging').html(buttons);
		$('.page')[0].focus();
	}
}
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
		var year = $(this).val();
			loadByYear(year);
	});
});
//$(document).ready(function(){
//	$('.month').change(function(){
//		var month = $(this).val();
//		loadByMonth(month);
//	});
//});	
//$(document).ready(function(){
//	$('.date').click(function(){
//		var date = $(this).val();
//		loadByDate(date);
//	});
//});	
$(document).ready(function(){
	$('#ttFilter').change(function(){
//		alert($(this).val());
		var trangThai = $(this).val();
		searchByTrangThai(trangThai);
	});
});	
$(document).ready(function(){
	$('#buttonSearch').click(function(){
//		alert($(this).val());
		var filterValue = $('#filterValue').val()+'';
		var filter = $('#filter').val();

//		alert(trangThai);
		//if (filterValue == '')
		
		filterData(filter, filterValue);
	});
});
$(document).ready(function(){
	$('#filter').change(function(){
		var filter = $(this).val();
		if (filter == '') {
			$('#filterValue').val('');
			$('#filterValue').prop('readonly', true);
			$('#filterValue').css('background-color', '#D1D1E0');
			filterData('','');
		}
		else {
			$('#filterValue').prop('readonly', false);
			$('#filterValue').css('background-color', '#FFFFFF');
		}
	});
});	
//function propCheckYear(yearId) {
//	$('.year').prop('checked', false);
//	$('#'+yearId).prop('checked', true);
//}
//function propCheckMonth(monthId) {
//	$('.month').prop('checked', false);
//	$('#'+monthId).prop('checked', true);
//}
//function propCheckDate(dateId) {
//	$('.date').prop('checked', false);
//	$('#'+dateId).prop('checked', true);
//}
