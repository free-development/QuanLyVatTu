
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
		alert(msnv + matkhau);
			$.ajax({
				url: "/QLVatTuYeuCau/login.html",	
			  	type: "GET",
			  	dateType: "JSON",
			  	data: { "msnv": msnv, "matkhau": matkhau},
			  	contentType: 'application/json',
			    mimeType: 'application/json',
			  	success: function(result) {
			  		alert("OK1");
			  		if(result == "success")
	 				{
 				  		alert("Xin chào "+ msnv + " bạn đã đăng nhập thành công");	
 				  		window.location.assign("/QLVatTuYeuCau");
 					}
 			  		else{
 			  			alert("Mã số nhân viên và mật khẩu chưa đúng vui lòng kiểm tra lại");
 			  		}
 			  			
			  	}
			});
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
 				  		window.location.assign("home");
 					}
 			  		else{
 			  			alert("Người dùng có mã số"+"'"+msnv+"'"+ "đã tồn tại");	
 			  		}
 			  			
			  	}
			});
	}
 
 	