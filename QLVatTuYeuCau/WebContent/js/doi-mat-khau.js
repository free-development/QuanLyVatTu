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
				alert("Xin chào ");	
//				var msnv = $('input:text[name=msnv]').val();
//				alert(msnv);
				var passNew = $('input:password[name=passNew]').val();
				var rePassNew = $('input:password[name=rePassNew]').val();
					if(passNew != rePassNew)
					{
						alert("Mật khẩu nhập lại chưa chính xác. Vui lòng kiểm tra lại!");
						return false;
					}
					else
						changePassWord(passNew);
				}
			function changePassWord(passNew){
				var msnv = $('input:text[name=msnv]').val();
				var passOld = $('input:password[name=passOld]').val();
//				var passNew = $('input:text[name=passNew]').val();
//				var rePassNew = $('input:text[name=rePassNew]').val();
//				if (confirm('Bạn có chắc thay doi noi san xuat co ma ' + nsxMaUpdate))
//					updateNsx(nsxMaUpdate, nsxTenUpdate);
//				alert(passOld + "**" + passNew);
				$.ajax({
					url: "/QLVatTuYeuCau/changePass.html",	
				  	type: "GET",
				  	dateType: "JSON",
				  	data: { "msnv": msnv, "passOld": passOld, "passNew": passNew},
				  	contentType: 'application/json',
				    mimeType: 'application/json',
				  	
				  	success: function(result) {
//				  		alert(result);
				  		if(result === "success")
				  			window.location.assign("home");
				  		else 
				  			alert('Tai khoan hoac mat khau chua dung');
				  	}
				});
			}