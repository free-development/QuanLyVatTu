
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
function showForm3(formId1, formId2, check){
	if (check)
		document.getElementById(formId2).style.display="block";
	else document.getElementById(formId2).style.display="none";
	var f = document.getElementById(formId1), s, opacity;
	s = f.style;
	opacity = check? '10' : '100';
	s.opacity = s.MozOpacity = s.KhtmlOpacity = opacity/100;
	s.filter = 'alpha(opacity='+opacity+')';
	for(var i=0; i<f.length; i++) f[i].disabled = check;
}	
function showForm2(formId, check){
	if (check)
		document.getElementById(formId).style.display="block";
	else document.getElementById(formId).style.display="none";
	var f = document.getElementById('main-form'), s, opacity;
	s = f.style;
	opacity = check? '10' : '100';
	s.opacity = s.MozOpacity = s.KhtmlOpacity = opacity/100;
	s.filter = 'alpha(opacity='+opacity+')';
	for(var i=0; i<f.length; i++) f[i].disabled = check;
	$('#main-form').show();
	
}

function confirmResetNd(){
	var msnv = $('input:checkbox[name=msnv]:checked').val();
	var ndMaList = [];
	$.each($("input[name='msnv']:checked"), function(){            
		ndMaList.push($(this).val());
    });
	var str = ndMaList.join(", ");
	if (ndMaList.length == 0)
		alert('Bạn phải chọn 1 Tài khoản để mở khóa!!');
	else if (confirm('Bạn có chắc mở tài khoản có mã ' + str))
		resetNd(str);
}
function resetNd(str) {
	$.ajax({
		url: "/QLVatTuYeuCau/lockNd.html",	
	  	type: "GET",
	  	dateType: "JSON",
	  	data: { "ndList": str},
	  	contentType: 'application/json',
	    mimeType: 'application/json',
	  	success: function() {
					$('table tr').has('input[name="msnv"]:checked').remove();
			  		$('#view-table-chia-se table tr:first').after('<tr class="rowContent"><td style=\"text-align: center;\"><input type=\"checkbox\" name=\"msnv\" value=\"' 
			  				+msnv + '\"</td><td class=\"col\">'+ msnv +'</td><td class=\"col\">' + hoten+'</td><td class=\"col\">' + chucdanh+'</td><td class=\"col\">' 
			  				+ email+'</td><td class=\"col\">' + diachi+'</td><td class=\"col\">' + sdt+'</td></tr>');
			  		
					alert('Tài khoản có mã ' + str + " đã mở khóa");
	    } 
	});  
}
function confirmLockNd(){
	var msnv = $('input:checkbox[name=msnv]:checked').val();
	var ndMaList = [];
	$.each($("input[name='msnv']:checked"), function(){            
		ndMaList.push($(this).val());
    });
	var str = ndMaList.join(", ");
	if (ndMaList.length == 0)
		alert('Bạn phải chọn 1 hoặc nhiều Tài khoản để khóa!!');
	else if (confirm('Bạn có chắc khóa tài khoản có mã ' + str))
		lockNd(str);
}
	 function lockNd(str) {
	$.ajax({
		url: "/QLVatTuYeuCau/lockNd.html",	
	  	type: "GET",
	  	dateType: "JSON",
	  	data: { "ndList": str},
	  	contentType: 'application/json',
	    mimeType: 'application/json',
	  	success: function() {
					$('table tr').has('input[name="msnv"]:checked').remove();
					alert('Tài khoản có mã ' + str + " đã bị khóa");
	    } 
	});  
}

function preUpdateNd(formId, check) {
	var msnv = $('input:checkbox[name=msnv]:checked').val();
	
	var MsnvList = [];
	$.each($("input[name='msnv']:checked"), function(){            
		MsnvList.push($(this).val());
    });
	
	if (MsnvList.length == 0)
		alert('Bạn phải chọn 1 tài khoản để thay đổi!!');
	else if (MsnvList.length > 1)
		alert('Bạn chỉ được chọn 1 tài khoản để thay đổi!!');
	else {
	$.ajax({
		url: "/QLVatTuYeuCau/preUpdateNd.html",	
	  	type: "GET",
	  	dateType: "JSON",
	  	data: { "msnv": msnv},
	  	contentType: 'application/json',
	    mimeType: 'application/json',
	  	
	  	success: function(nd) {
		  	$('input:text[name=msnv]').val(nd.msnv);
		  	$('input:text[name=hoten]').val(nd.hoTen);
		  	$('input:text[name=email]').val(nd.email);
		  	$('input:text[name=diachi]').val(nd.diaChi);
		  	$('input:text[name=sdt]').val(nd.sdt);
		  	$('#chucDanh option[value='+nd.chucDanh.cdMa+']').prop('selected',true);
		  	$('#hoTen').focus();
		  	showForm3("main-form","add-form",true);
		  //	showForm(formId, check);	
		  	//$('#main-form').hide();
	  	}
	});
	}
}
function updateNd(msnv, hoten,chucdanh, email, diachi, sdt) {

	 if (hoten == '')
			{
				$('#requireHoten').html('Vui lòng nhập họ tên');
			}
	 if (chucdanh == null)
		{
			$('#requireChucdanh').html('Vui lòng chọn chức danh');
		}
	 if (email == '')
		{
			$('#requireEmail').html('Vui lòng nhập email');
		}
	 if (diachi == '')
		{
			$('#requireDiachi').html('Vui lòng nhập địa chỉ');
		}
	 if (sdt == '')
		{
			$('#requireSdt').html('Vui lòng nhập số điện thoại');
		}
	else{
	$.ajax({
		url: "/QLVatTuYeuCau/updateNd.html",	
	  	type: "GET",
	  	dateType: "JSON",
	  	data: { "msnv": msnv, "hoten": hoten, "chucdanh": chucdanh, "email": email, "diachi": diachi, "sdt": sdt},
	  	contentType: 'application/json',
	    mimeType: 'application/json',
	  	
	  	success: function(nd) {
	  		$('table tr').has('input[name="msnv"]:checked').remove();
	  		$('#view-table-chia-se table tr:first').after('<tr class="rowContent"><td style=\"text-align: center;\"><input type=\"checkbox\" name=\"msnv\" value=\"' 
	  				+msnv + '\"</td><td class=\"col\">'+ msnv +'</td><td class=\"col\">' + hoten+'</td><td class=\"col\">' + chucdanh+'</td><td class=\"col\">' 
	  				+ email+'</td><td class=\"col\">' + diachi+'</td><td class=\"col\">' + sdt+'</td></tr>');
	  		$('input:text[name=msnv]').val('');
			hoten = $('input:text[name=hoten]').val('');
			chucdanh = $('select[name=chucdanh]').val('');
			email = $('input:text[name=email]').val('');
			diachi = $('input:text[name=diachi]').val('');
			sdt = $('input:text[name=sdt]').val('');
	  		alert("Thay đổi thành công người dùng có mã "+msnv+ " !")	
	  		showForm2('add-form',false);	
	  		//$('#main-form').show();
	  		
	  	}
	});
	}
}
function confirmUpdateNd(){
	var msnv = $('input:text[name=msnv]').val();
	var hoten = $('input:text[name=hoten]').val();
	var chucdanh = $('select[name=chucdanh]').val();
	var email = $('input:text[name=email]').val();
	var diachi = $('input:text[name=diachi]').val();
	var sdt = $('input:text[name=sdt]').val();
	if (confirm('Bạn có chắc thay đổi người dùng có mã ' + msnv))
		updateNd(msnv, hoten, chucdanh, email, diachi,sdt);
	
		//$('#danh-sach-tai-khoan').show();
}
function confirmDelete(){
	return confirm('Bạn có chắc xóa');
}
function checkPassword()
	{
		var password = document.forms["taoTaiKhoan"]["matkhau"].value;
		var confirmPassword = document.forms["taoTaiKhoan"]["nlmatkhau"].value;
		if(password != confirmPassword)
		{
			alert("Mật khẩu nhập lại chưa chính xác. Vui lòng kiểm tra lại!");
			return false;
		}
		else
			{
				addNd();
			}
	}

$(document).ready(function() {
  	$('.page').click(function(){
	var pageNumber = $(this).val();
    	$.ajax({
			url: "/QLVatTuYeuCau/loadPageNd.html",	
		  	type: "GET",
		  	dateType: "JSON",
		  	data: { "pageNumber": pageNumber},
		  	contentType: 'application/json',
		    mimeType: 'application/json',
		  	
		  	success: function(ndList) {
		  		$('#view-table-chia-se table .rowContent').remove();
				if(ndList.length>0){
					for(i = 0;i < ndList.length; i++ ) {
						var nd = ndList[i] ;
						var style = '';	
						if (i % 2 == 0)
							style = 'style=\"background : #CCFFFF;\"';
						var str = '';
						str = '<tr class=\"rowContent\" ' + style + '>'
							+ '<td class=\"left-column\"><input type=\"checkbox\" name=\"msnv\" value=\"' 
							+ nd.msnv +'\" class=\"checkbox\"></td>'
							+ '<td class=\"col\">' + nd.msnv + '</td>'
							+ '<td class=\"col\">' + nd.hoten + '</td>'
							+ '<td class=\"col\">' + nd.chucDanh.cdTen + '</td>'
							+ '<td class=\"col\">' + nd.email + '</td>'
							+ '<td class=\"col\">' + nd.diachi + '</td>'
							+ '<td class=\"col\">' + nd.sdt + '</td>'
							+ '</tr>';
						$('#view-table-chia-se table tr:first').after(str);
					}
				}
		  	}
		});
    });	
})   
 function login() {
		var msnv = $('input:text[name=msnv]').val();
		var matkhau = $('input:password[name=matkhau]').val();
			$.ajax({
				url: "/QLVatTuYeuCau/login.html",	
			  	type: "POST",
			  	dateType: "JSON",
			  	data: { "msnv": msnv, "matkhau": matkhau},
			  	contentType: 'application/json',
			    mimeType: 'application/json',
			  	success: function(result) {
			  		//alert("OK1");
			  		if(result == "success")
	 				{
 				  		alert("Xin chào "+ msnv + " bạn đã đăng nhập thành công");	
 				  		$('input:text[name=msnv]').val('');
 						$('input:password[name=matkhau]').val('');
 				  		window.location.assign("/QLVatTuYeuCau");
 					}
 			  		else{
 			  			alert("Mã số nhân viên và mật khẩu chưa đúng vui lòng kiểm tra lại");
 			  		}
 			  			
			  	}
			});
	}
 
 function checkAdd(){
	 	var msnv = $('#add-form input:text[name=msnv]').val();
		var matkhau = $('#add-form input:password[name=matkhau]').val();
		var nlmatkhau = $('#add-form input:password[name=nlmatkhau]').val();
		var hoten = $('#add-form input:text[name=hoten]').val();
		var chucdanh = $('#add-form select[name=chucdanh]').val();
		var sdt = $('#add-form input:text[name=sdt]').val();
		var email = $('#add-form input:text[name=email]').val();
		var diachi = $('#add-form input:text[name=diachi]').val();

		if(msnv == ''){
			$('#requireMsnv').html('Vui lòng nhập mã số nhân viên ');
			return false;
		}
		else if(matkhau == ''){
			$('#requireMatkhau').html('Vui lòng nhập mật khẩu');
			return false;
		}
		else if(nlmatkhau == ''){
			$('#requireNlmatkhau').html('Vui lòng nhập lại mật khẩu');
			return false;
		}
		else if(hoten == ''){
			$('#requireHoten').html('Vui lòng nhập họ tên');
			return false;
		}
		else if(chucdanh == null){
			$('#requireChucdanh').html('Vui lòng chọn chức danh');
			return false;
		}
		else if(sdt == ''){
			$('#requireSdt').html('Vui lòng nhập số điện thoại');
			return false;
		}
		else if(email == ''){
			$('#requireEmail').html('Vui lòng nhập email');
			return false;
		}
		else if(diachi == ''){
			$('#requireDiachi').html('Vui lòng nhập địa chỉ');
			return false;
		}
		return true;
	}
 
 function changeMsnv(){
	 var msnv = $('#msnv').val();
	 $.ajax({
			url: "/QLVatTuYeuCau/loadHoten.html",	
		  	type: "GET",
		  	dateType: "JSON",
		  	data: { "msnv": msnv},
		  	contentType: 'application/json',
		    mimeType: 'application/json',
		  	success: function(hoTen){
		  		$('#add-form input:text[name=hoten]').val(hoTen);
		  	}
		  	
		});
	 
	} 	
	function changeMatkhau(){
		$('#requireMatkhau').html('');
	} 	
	function changeNlmatkhau(){
		$('#requireNlmatkhau').html('');
	} 	
	function changeHoten(){
		$('#requireHoten').html('');
	} 	
	function changeChucdanh(){
		$('#requireChucdanh').html('');
	} 	
	function changeSdt(){
		$('#requireSdt').html('');
	} 
	function changeEmail(){
		$('#requireEmail').html('');
	} 
	function changeDiachi(){
		$('#requireDiachi').html('');
	} 
	
	function checkNd()
	{
		if(checkAdd()){
			checkPassword();
		}
	}
	
 function addNd() {
		var msnv = $('#add-form input:text[name=msnv]').val();
		var matkhau = $('#add-form input:password[name=matkhau]').val();
		var nlmatkhau = $('#add-form input:password[name=nlmatkhau]').val();
		var hoten = $('#add-form input:text[name=hoten]').val();
		var chucdanh = $('#add-form select[name=chucdanh]').val();
		var sdt = $('#add-form input:text[name=sdt]').val();
		var email = $('#add-form input:text[name=email]').val();
		var diachi = $('#add-form input:text[name=diachi]').val();
		
			$.ajax({
				url: "/QLVatTuYeuCau/addNd.html",	
			  	type: "GET",
			  	dateType: "JSON",
			  	data: { "msnv": msnv,"matkhau":matkhau,"nlmatkhau":nlmatkhau,"hoten":hoten,"chucdanh":chucdanh,"sdt":sdt,"email":email,"diachi":diachi},
			  	contentType: 'application/json',
			    mimeType: 'application/json',
			  	success: function(result) {
			  		if(result == "success")
	 				{
 				  		alert("Tạo người dùng với mã số "+"'"+msnv+"'"+ "thành công");	
 				  		$('#add-form input:text[name=msnv]').val('');
 						$('#add-form input:password[name=matkhau]').val('');
 						$('#add-form input:password[name=nlmatkhau]').val('');
 						$('#add-form input:text[name=hoten]').val('');
 						$('#add-form select[name=chucdanh]').val('');
 						$('#add-form input:text[name=sdt]').val('');
 						$('#add-form input:text[name=email]').val('');
 						$('#add-form input:text[name=diachi]').val('');
 				  		window.location.assign("home");
 					}
 			  		else{
 			  			alert("Người dùng có mã số"+"'"+msnv+"'"+ "đã tồn tại");	
 			  		}
 			  			
			  	}
			  	
			});
	}
 
 function timKiemNguoidung(){
		var hoTen = '';
		var msnv = '';
		var check = $('#checkTen:checked').val();
		if (check != null)
			hoTen = $('#search input[name=nguoidung]').val();
		else 
			msnv = $('#search input[name=nguoidung]').val();
//		
//		alert(hoten);
//		alert(msnv);
		
		$.ajax({
			url: "/QLVatTuYeuCau/timKiemNguoidung.html",	
		  	type: "GET",
		  	dateType: "JSON",
		  	data: { "msnv": msnv, "hoTen": hoTen},
		  	contentType: 'application/json',
		    mimeType: 'application/json',
		  	success: function(ndList){
		  		if(ndList.length>0){
		  			//alert('OKs');
		  			$('#view-table-chia-se table .rowContent').remove();
					for(i = 0;i < ndList.length; i++ ) {
						nd = ndList[i];
						//alert(ndList[i].msnv);
		  				$('#view-table-chia-se table tr:first').after('<tr class=\"rowContent\"><td class=\"left-column\"><input type=\"checkbox\" name=\"msnv\" value=\"' +nd.msnv 
						+ '\"</td><td class=\"col\">'+ nd.msnv +'</td><td class=\"col\">' + nd.hoTen
						+'</td><td class=\"col\">' + nd.chucDanh.cdTen
						+'</td><td class=\"col\">' + nd.email
						+'</td><td class=\"col\">' + nd.diaChi
						+'</td><td class=\"col\">' + nd.sdt
						+'</tr>');
					}
		  		}
		  		else
		  			{
		  				alert("Không tìm thấy tài khoản!");
		  			}
		  	}
		});
		
	}
 
 function timKiemNguoidungCs(){
		var hoTen = '';
		var msnv = '';
		var check = $('#checkTen:checked').val();
		if (check != null)
			hoTen = $('#search input[name=nguoidung]').val();
		else 
			msnv = $('#search input[name=nguoidung]').val();
//		
//		alert(hoten);
//		alert(msnv);
		
		$.ajax({
			url: "/QLVatTuYeuCau/timKiemNguoidung.html",	
		  	type: "GET",
		  	dateType: "JSON",
		  	data: { "msnv": msnv, "hoTen": hoTen},
		  	contentType: 'application/json',
		    mimeType: 'application/json',
		  	success: function(ndList){
		  		if(ndList.length>0){
		  			//alert('OKs');
		  			$('#view-table-chia-se table .rowContent').remove();
					for(i = 0;i < ndList.length; i++ ) {
						nd = ndList[i];
						//alert(ndList[i].msnv);
		  				$('#view-table-chia-se table tr:first').after('<tr class=\"rowContent\"><td class=\"left-column\"><input type=\"checkbox\" name=\"msnv\" value=\"' +nd.msnv 
						+ '\"</td><td class=\"col\">'+ nd.msnv +'</td><td class=\"col\">' + nd.hoTen
						+'</td><td class=\"col\">' + nd.chucDanh.cdTen
						+'</td><td class=\"col\">' + nd.vaiTro.vtTen
						+'</tr>');
					}
		  		}
		  		else
		  			{
		  				alert("Không tìm thấy tài khoản!");
		  			}
		  	}
		});
		
	}

 
 $(document).ready(function() {
		$('#add-form').keypress(function(e) {
		 var key = e.which;
		 if(key == 13)  // the enter key code
		  {
			 addNd();
		    return false;  
		  }
		});   
	});   
	$(document).ready(function() {
		$('#add-form').keypress(function(e) {
		 var key = e.which;
		 if(key == 13)  // the enter key code
		  {
		    updateNd();
		    return false;  
		  }
		});   
	});  

 // click event
 /*
$(document).ready(function(){
	$('#login').click(function(){
		login();
	});
}) ;
*/ 	
