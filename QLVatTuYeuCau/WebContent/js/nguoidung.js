
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
			  		alert("OK1");
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
		$('#requireMsnv').html('');
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
 
 // click event
 /*
$(document).ready(function(){
	$('#login').click(function(){
		login();
	});
}) ;
*/ 	
