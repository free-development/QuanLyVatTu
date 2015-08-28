
<%@page import="java.util.ArrayList"%>
<%@page import="map.siteMap"%>
<%@ page language="java" contentType="text/html; charset= UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>Văn phòng điện tử công ty điện lực Cần Thơ</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link rel="stylesheet" href="style/style-giao-dien-chinh.css"
	type="text/css">
<link rel="stylesheet" href="style/style.css" type="text/css">
<link href="style/style-tao-doi-pass.css" type="text/css"
	rel="stylesheet">
<link
	href="style/font-awesome-4.3.0/font-awesome-4.3.0/css/font-awesome.min.css"
	type="text/css" rel="stylesheet">
<!--		<script type="text/javascript" src="js/check.js"></script>-->
<script type="text/javascript" src="js/jquery-1.6.3.min.js"></script>

<script type="text/javascript">


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
					return true;
				}
		</script>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="Shortcut Icon" href="img/logo16.png" type="image/x-icon" />
</head>
<body>
	<div class="wrapper">
		<div class="header">
			<!--
					<img src="img/logo.png" alt="" id="logo" width=80 height=80/><br/>
					<img src="img/textlogo.png" alt="" id="logo" width=80 height=20/>
	-->
			<div id="top_title">Văn phòng điện tử</div>
			<div id="bottom-title">Công ty điện lực cần thơ</div>
			<div class="search_form" id="search">
				<form action="" method="post">

					<span class="search-text"> &nbsp; <input type="search"
						class="search" name="search_box" name="search"
						placeholder="Tìm kiếm" />
					</span> <span class="search-button"> &nbsp;
						<button class="btn-search">
							<i class="fa fa-search"></i>
						</button>
					</span>
				</form>
			</div>

		</div>
		<div class="main_menu">
			<ul>

				<li><a href="">Trang chủ</a></li>
				<li><a href="">Danh mục</a>
					<ul>
						<li><a href="<%=siteMap.nsxManage + "?action=manageNsx"%>">Danh
								mục nơi sản xuất</a></li>
						<li><a href="<%=siteMap.clManage + "?action=manageCl"%>">Danh
								mục chất lượng</a></li>
						<li><a href="<%=siteMap.vattuManage + "?action=manageVattu"%>">Danh
								mục vật tư</a></li>
						<li><a href="<%=siteMap.ctvtManage + "?action=manageCtvt"%>">Danh
								mục chi tiết vật tư</a></li>
						<li><a href="<%=siteMap.bpsdManage +  "?action=manageBpsd"%>">Danh
								mục bộ phận sử dụng</a></li>
						<li><a href="<%=siteMap.mdManage + "?action=manageMd"%>">Danh
								mục mục đích</a></li>
						<li><a href="<%=siteMap.vtManage + "?action=manageVt"%>">Danh mục vai trò</a></li>
						<li><a href="<%=siteMap.cdManage + "?action=manageCd"%>">Danh
								mục chức danh</a></li>
						
					</ul>
				</li>
				<li><a href="<%=siteMap.cvManage+ "?action=manageCv" %>">Công văn</a></li>
				<li><a href="<%=siteMap.bcManage +  "?action=manageBc"%>">Báo cáo</a>
					<ul>
						<li><a href="<%=siteMap.bcvttManage+ "?action=manageBcvtt" %>"/>Báo cáo vật tư thiếu</li>
						<li><a href="<%=siteMap.bcbdnManage+ "?action=manageBcbdn" %>"/>Báo cáo bảng đề nghị cấp vật tư</li>
					</ul>
				</li>
				<li><a href="">Quảnlý người dùng</a>
					<ul>
						<li><a href="<%=siteMap.ndManage + "?action=manageNd"%>">Thêm người dùng</li>
						<li><a href=""/>Khôi phục mật khẩu</li>
					</ul>
				</li>
				<li><a href="<%=siteMap.changePass + "?action=changePassWord"%>">Đổi mật khẩu</a></li>
			</ul>
			<div class="clear"></div>
		</div>
		<div id="main-content">
			<form id="add-form" action="<%=siteMap.changePass %>" method="post"
				name="taoTaiKhoan" action="/QLVatTuYeuCau/changePass.html">
				<div class="input-table">
					<table>
						<div class="form-title">Đổi mật khẩu</div>
						<tr>
						<td class="input"><label for="msnv">Mã số NV</label></td>
						<td><input type="text" autofocus required size="10"
							maxlength="10"
							title="Mã số nhân viên đủ 10 ký tự, không chứa ký tự đặc biệt"
							pattern="[a-zA-Z0-9]*" class="text" id="msnv" name="msnv"></td>
					</tr>
					<tr>
						<td class="input"><label for="matkhau">Mật khẩu cũ</label></td>
						<td><input type="password" required size="20" maxlength="20"
							title="Mật khẩu phải hơn 7 ký tự và nhỏ hơn 21" pattern=".{8,20}"
							class="text" id="matkhau" name="matkhau"></td>
					</tr>
					<tr>
						<td class="input"><label for="matkhau">Mật khẩu mới</label></td>
						<td><input type="password" required size="20" maxlength="20"
							title="Mật khẩu phải hơn 7 ký tự và nhỏ hơn 21" pattern=".{8,20}"
							class="text" id="matkhau" name="matkhau"></td>
					</tr>

					<tr>
						<td class="input"><label for="re-matkhau">Nhập lại
								mật khẩu mới</label></td>
						<td><input type="password" required size="20" maxlength="20"
							title="Mật khẩu phải hơn 7 ký tự và nhỏ hơn 21" pattern=".{8,20}"
							class="text" id="re-matkhau" name="re-matkhau"></td>
					</tr>
					</table>
				</div>
				<div class="button-group">
					<button class="button" type="submit"
						onclick="return checkPassword();">
						<i class="fa fa-plus-circle"></i>&nbsp;Lưu lại
					</button>
					&nbsp;
					<button class="button" type="reset">
						<i class="fa fa-refresh"></i>&nbsp;Nhập lại
					</button>
					&nbsp;
					<button class="button" type="button">
						<i class="fa fa-sign-out"></i>&nbsp;Thoát
					</button>
				</div>
			</form>
		</div>
	</div>
</body>
</html>