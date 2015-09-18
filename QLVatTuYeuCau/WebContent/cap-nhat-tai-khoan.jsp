<%@page import="model.NguoiDung"%>
<%@page import="model.ChucDanh"%>
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
<link href="style/style-cap-nhat-tai-khoan.css" type="text/css"
	rel="stylesheet">
<link href="style/style-chia-se.css" type="text/css"
	rel="stylesheet">
<!-- <link href="style/style-vat-tu.css" type="text/css" -->
<!-- 	rel="stylesheet"> -->
<link rel="stylesheet" type="text/css" href="style/jquery.autocomplete.css" />
	<script type="text/javascript" src="js/jquery.min.js"></script>
<script type="text/javascript" src="js/check.js"></script>
<script src="js/jsapi.js"></script>  
	<script>  
		google.load("jquery", "1");
	</script>
	<script src="js/jquery.autocomplete.js"></script>
	<style>
		input {
			font-size: 120%;
		}
	</style>
	<script type="text/javascript">
	
	</script>
</head>
<link
	href="style/font-awesome-4.3.0/font-awesome-4.3.0/css/font-awesome.min.css"
	type="text/css" rel="stylesheet">
<!--		<script type="text/javascript" src="js/check.js"></script>-->
<!-- <script type="text/javascript" src="js/jquery-1.6.3.min.js"></script> -->
<!-- <script type="text/javascript" src="js/jquery.min.js"></script> -->
<script type="text/javascript" src="js/nguoidung.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="Shortcut Icon" href="img/logo16.png" type="image/x-icon" />
</head>
<body>
	<%
		String adminMa = request.getServletContext().getInitParameter("adminMa");
		String truongPhongMa = request.getServletContext().getInitParameter("truongPhongMa");
		String vanThuMa = request.getServletContext().getInitParameter("vanThuMa");
   		NguoiDung authentication = (NguoiDung) session.getAttribute("nguoiDung");
   		if (authentication == null) {
   			request.setAttribute("url", siteMap.ndManage + "?action=manageNd");
   			RequestDispatcher dispatcher = request.getRequestDispatcher(siteMap.login + ".jsp");
   			dispatcher.forward(request, response);
   			return;
   		}
   		String chucDanh = authentication.getChucDanh().getCdMa();
   	%>
	<%
    		ArrayList<ChucDanh> listChucDanh = (ArrayList<ChucDanh>) request.getAttribute("chucDanhList");
			ArrayList<NguoiDung> listNguoiDung = (ArrayList<NguoiDung>) request.getAttribute("nguoiDungList");
			if (listChucDanh ==  null) {
				int index = siteMap.ndManage.lastIndexOf("/");
				String url = siteMap.ndManage.substring(index);
				RequestDispatcher dispatcher =  request.getRequestDispatcher(url + "?action=manageNd");
				dispatcher.forward(request, response);
				return;
			}
			Long pageNum = (Long) request.getAttribute("size")/10;
    	%>
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
						<li><a href="<%=siteMap.homePageManage%>">Trang chủ</a></li>
						<%if (adminMa.equalsIgnoreCase(chucDanh)) {%>
						
						<li><a>Danh mục</a>
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
										<li><a href="<%=siteMap.dvtManage + "?action=manageDvt"%>">Danh mục đơn vị tính</a></li>
										<li><a href="<%=siteMap.cdManage + "?action=manageCd"%>">Danh
												mục chức danh</a></li>
										
									</ul>
						</li>
						<%} %>
						<%if (!chucDanh.equalsIgnoreCase(adminMa)) {%>
							<li><a href="<%=siteMap.cvManage+ "?action=manageCv" %>">Công văn</a></li>
							<%if (!chucDanh.equalsIgnoreCase(vanThuMa)){ %>
							<li><a>Báo cáo</a>
								<ul>
									<li><a href="<%=siteMap.bcvttManage+ "?action=manageBcvtt" %>"/>Báo cáo vật tư thiếu</li>
									<li><a href="<%=siteMap.bcbdnManage+ "?action=manageBcbdn" %>"/>Báo cáo bảng đề nghị cấp vật tư</li>
								</ul>
							</li>
							<%}} %>
						<%if (adminMa.equalsIgnoreCase(chucDanh)) {%>
						<li><a>Quản lý người dùng</a>
							<ul>
								<li><a href="<%=siteMap.ndManage + "?action=manageNd"%>">Thêm người dùng</li>
								<li><a href="<%=siteMap.updateNguoiDung%>"/>Cập nhật thông tin</li>
								<li><a href="<%=siteMap.resetPassword%>"/>Khôi phục mật khẩu</li>
								<li><a href="<%=siteMap.lockNguoiDung%>"/>Khóa tài khoản</li>
								<li><a href="<%=siteMap.resetNguoiDung%>"/>Khôi phục tài khoản</li>
							</ul>
						</li>
						<%} %>
						<li><a>Tài khoản</a>
							<ul>
								<li><a href="<%=siteMap.changePassPage + ".jsp"%>">Đổi mật khẩu</a></li>
								<li><a href="<%=siteMap.logout + "?action=logout"%>">Đăng xuất</a></li>
							</ul>
						</li>		
					</ul>
					<div class="clear"></div>
				</div>
		<div id="greeting"style="color: #6600FF;height:20px;"><b>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Chào:&nbsp;<%=authentication.getHoTen() %></b></div>
		<div id="main-content">
				<form id="main-form">
				<div id="title-content">Danh sách tài khoản</div>
				<table style="margin-left: 60px;margin-bottom: 10px;">		
					<tr>		
					<th  style="text-align: left; color: black; font-size: 19px;">*Tìm kiếm mã</th>
								<td>
									<div class="search_form1" id="search">		
										
										
										<form>												
											<span> &nbsp; <input type="search" id="searchName" class="text-search" name="nguoidung"/>						
														 												
												<td><input type="checkbox" value="check" class="checkbox" style="text-align: center;" id="checkTen"/></td>
												<td  style="text-align: center; color: black; font-size: 19px;">Theo tên</td>&nbsp;&nbsp;&nbsp;
											</span>
											
												<td> <span class="search-button"> &nbsp; <button type="button" class="btn-search" style="background-color: #00A69B;" onclick="timKiemNguoidung()"><i class="fa fa-search"></i></button></span></td>						
										</form>
										<script>
														$('#searchName').autocomplete("getdataMsnv.jsp");
														$('#searchName').autocomplete("getdataHoten.jsp");	
														</script>
									</div>
									</td>
					</tr>					
				</table>
				<div id="view-table-chia-se">
					<table >
						<tr bgcolor= "#199e5e">
						<th style="text-align: center;">Chọn</th>
						<th>Msnv</th><th>Họ tên</th><th>Chức danh</th><th>Email</th><th>Địa chỉ</th><th>Số điện thoại</th>
						</tr>
						<%
						int i = 0;
						for(NguoiDung nguoiDung : listNguoiDung)
						{i++;%>
						<tr class="rowContent"
						 <% if (i % 2 ==0) out.println("style=\"background : #CCFFFF;\"");%> >
							<td style="text-align: center;"><input type = "checkbox" class="checkbox" name ="msnv" value="<%=nguoiDung.getMsnv()%>"></td>
							<td><%=nguoiDung.getMsnv()%></td>
							<td><%=nguoiDung.getHoTen()%></td>
							<td><%=nguoiDung.getChucDanh().getCdTen()%></td>
							<td><%=nguoiDung.getEmail()%></td>
							<td><%=nguoiDung.getDiaChi()%></td>
							<td><%=nguoiDung.getSdt()%></td>
							
						</tr>
						<%}%>
					</table>
					</div>
					<div id = "paging" >
							<table style ="border-style: none;">
								<tr>
										<td>Trang</td>
										<td>
												<%
												for(int j = 0; j <= pageNum; j++) { %>
												<input type="button" value="<%=j+1%>" class="page">
												<%} %>
										</td>
									
								</tr>
							</table>
						</div>
					<div class="group-button">
					<input type="hidden" value="save" name="action">
						<button class="button" id="update" type="button"onclick="preUpdateNd('add-form', true)">
							<i class="fa fa-pencil fa-fw"></i>&nbsp;sửa
						</button>
						<button type="reset" class="button" type="button">
							<i class="fa fa-refresh"></i>&nbsp;&nbsp;Bỏ qua
						</button>
						<button type="button" class="button" onclick="location.href='<%=siteMap.home%>'">
						<i class="fa fa-sign-out"></i>&nbsp;&nbsp;Thoát
					</button>
					</div>
				</form>
				</div>
			<form id="add-form">
				<div class="input-table">
					<table>
						<div class="form-title">Cập nhật thông tin</div>
						<tr>
						<td class="input"><label for="msnv">Mã số nhân viên</label></td>
						<td><input type="text" autofocus required size="12"
							maxlength="10" placeholder="Mã số nhân viên"
							title="Mã số nhân viên đủ 10 ký tự, không chứa ký tự đặc biệt"
							pattern="[a-zA-Z0-9]*" class="text" id="msnv" name="msnv" onkeypress="changeMsnv();"readonly><div id="requireMsnv" style="color: red"></div></td>
					</tr>
					<tr>
						<td class="input"><label for="hoten">Họ tên</label></td>
						<td><input type="text" required size="20" maxlength="50" placeholder="Họ tên"
							title="Họ tên không được chứa chữ số và ký tự đặc biệt"
							pattern="[a-zA-Z]*" class="text" id="hoTen" name="hoten" onkeypress="changeHoten();"style="margin-top: 5px;"><div id="requireHoten" style="color: red"></div></td>
					</tr>
					<tr>
						<td class="input"><label for="chucdanh">Chức danh</label></td>
						<td><select required title="Chức danh phải được chọn"
							class="select" id="chucDanh" name="chucdanh" placeholder="Chọn chức danh" onchange="changeChucdanh();">
								<option disabled selected value="">--Chọn chức danh--</option>
								<%
							
									int count = 0;
									for(ChucDanh cd : listChucDanh)
									{%>
								<option value=<%=cd.getCdMa()%>><%=cd.getCdTen()%></option>
								<%}
								%>
						</select><div id="requireChucdanh" style="color: red"></div></td>
					</tr>

					<tr>
						<td class="input"><label for="email">Email</label></td>
						<td><input type="text" required size="25" maxlength="50" placeholder="Email"
							title="Email phải được nhập" class="text" id="email" name="email" onkeypress="changeEmail();"><div id="requireEmail" style="color: red"></div></td>
					</tr>


					<tr>
						<td class="input"><label for="diachi">Địa chỉ</label></td>
						<td><input type="text" required size="20" maxlength="50" placeholder="Địa chỉ"
							title="Địa chỉ phải được nhập" class="text" name="diachi"
							id="diachi" onkeypress="changeDiachi();"><div id="requireDiachi" style="color: red"></div></td>
					</tr>
					<tr>
						<td class="input"><label for="sdt">Số điện thoại</label></td>
						<td><input type="text" required size="11" maxlength="11" placeholder="Số điện thoại"
							title="Phải nhập đúng định dạng. Ví dụ: 01234567890"
							pattern="[0-9]{10,11}" class="text" id="sdt" name="sdt" onkeypress="changeSdt();"><div id="requireSdt" style="color: red"></div></td>
					</tr>

					</table>
				</div>
				<div class="button-group">
					<button class="button" type="button" onclick="confirmUpdateNd();">
						<i class="fa fa-plus-circle"></i>&nbsp;Lưu lại
					</button>
					&nbsp;
					<button class="button" type="reset">
						<i class="fa fa-refresh"></i>&nbsp;Nhập lại
					</button>
					&nbsp;
					<button type="button" class="button" onclick="showForm2('add-form', false);">
							<i class="fa fa-sign-out"></i>&nbsp;&nbsp;Thoát
						</button>
				</div>
			</form>
		</div>
	
</body>
</html>