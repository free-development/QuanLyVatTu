<%@page import="model.NguoiDung"%>
<%@page import="model.VaiTro"%>
<%@page import="map.siteMap"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=utf-8"%>
<!DOCTYPE html>
<html>
<head>
<title>Văn phòng điện tử công ty điện lực Cần Thơ</title>
<link rel="stylesheet" href="style/style-giao-dien-chinh.css"
	type="text/css">

<link rel="stylesheet" href="style/style.css" type="text/css">
<link href="style/don-vi-tinh.css" type="text/css" rel="stylesheet">

<link
	href="style/font-awesome-4.3.0/font-awesome-4.3.0/css/font-awesome.min.css"
	type="text/css" rel="stylesheet">

<script type="text/javascript" src="js/jquery.min.js"></script>
<script type="text/javascript" src="js/vaitro.js"></script>
<script>
    $(document).ready(function() {
        $('.checkAll').click(function(event) {  //on click 
            if(this.checked) { // check select status
                $('.checkbox').each(function() { //loop through each checkbox
                    this.checked = true;  //select all checkboxes with class "checkbox1"               
                });
            }else{
                $('.checkbox').each(function() { //loop through each checkbox
                    this.checked = false; //deselect all checkboxes with class "checkbox1"                       
                });         
            }
        });
        
    });
	</script>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="Shortcut Icon" href="img/logo16.png" type="image/x-icon" />
</head>
<body>
	<%
		String adminMa = request.getServletContext().getInitParameter("adminMa");
   		NguoiDung authentication = (NguoiDung) session.getAttribute("nguoiDung");
   		if (authentication == null) {
   			request.setAttribute("url", siteMap.vtManage + "?action=manageVt");
   			RequestDispatcher dispatcher = request.getRequestDispatcher(siteMap.login + ".jsp");
   			dispatcher.forward(request, response);
   			return;
   		}
		String chucDanh = authentication.getChucDanh().getCdMa();
		String truongPhongMa = request.getServletContext().getInitParameter("truongPhongMa");
		String vanThuMa = request.getServletContext().getInitParameter("vanThuMa");
   	%>
	<%
    		ArrayList<VaiTro> listVaiTro = (ArrayList<VaiTro>) request.getAttribute("vaiTroList");
			if (listVaiTro ==  null) {
				int index = siteMap.vtManage.lastIndexOf("/");
				String url = siteMap.vtManage.substring(index);
				RequestDispatcher dispatcher =  request.getRequestDispatcher(url +  "?action=manageVt");
				dispatcher.forward(request, response);
				return;
			}
// 			Long size = (Long) request.getAttribute("size");
			long pageNum = (Long) request.getAttribute("size")/10;
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
					<!--
							<span class="search-select">
								<select name="" ><option disabled selected>--Tùy chọn kiếm kiềm--</option></select>
								<option value=""></option>
							</span>
-->

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
		<div id="greeting">Chào <%=authentication.getHoTen() %></div>
		<div id="main-content">
				<div id="greeting"style="color: #6600FF;height:20px;"><b>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Chào:&nbsp;<%=authentication.getHoTen() %></b></div>
			<div id="title-content">Danh mục vai trò</div>
				<form id="main-form">
					<div id="view-table" style="height: 600px; margin: 0 auto;">
						<table>
							<tr style="background: #199e5e">
								<th class="left-column"><input type="checkbox"
									class="checkAll"></th>
								<th class="right-column">Tên vai trò</th>
							</tr>
							<%
							if(listVaiTro != null) {
							int count = 0;
							for(VaiTro vaiTro : listVaiTro) {count++ ;%>
							<tr class="rowContent"
								<%if (count % 2 == 0) out.println("style=\"background : #CCFFFF;\"");%>>
								<td class="left-column"><input type="checkbox" name="vtId"
									value="<%=vaiTro.getVtTen() %>" class="checkbox"></td>
								<td class="col"><%=vaiTro.getVtTen() %></td>
							</tr>
							<%} }%>
						</table>
					</div>
					
					<div id = "paging" >
							<table style ="border-style: none;">
								<tr>
								<td>Trang</td>
									<td>
										<%
 											//long pageNum = size / 10;
											for(int i = 0; i <= pageNum; i++) { %>
												<input type="button" value="<%=i+1%>" class="page">
										<%} %>
									</td>
<!-- 									<td><input type="button" value=">>"></td> -->
								</tr>
							</table>
						</div>
					
					<div class="group-button">
						<button type="button" class="button"
							onclick="showForm('add-form', true)">
							<i class="fa fa-plus-circle"></i>&nbsp;Thêm
						</button>
						<button type="button" class="button"
							onclick="preUpdatevt('update-form', true);">
							<i class="fa fa-pencil fa-fw"></i>&nbsp;Thay đổi
						</button>
						<button type="button" class="button" onclick="confirmDelete();">
							<i class="fa fa-trash-o"></i>&nbsp;&nbsp;Xóa
						</button>
						&nbsp;
						<button class="button" type="reset">
							<i class="fa fa-spinner"></i>&nbsp;&nbsp;Bỏ qua
						</button>
						&nbsp;
						<button type="button" class="btn" onclick="location.href='<%=siteMap.home%>'">
							<i class="fa fa-sign-out"></i>&nbsp;&nbsp;Thoát
						</button>
					</div>
				</form>
				<!-------------- --add-form-------------- -->
				<form id="add-form" method="get" action="<%=siteMap.vtManage + "?action=manageVt"%>">
					<div class="input-table">
						<table >
							<div class="form-title">Thêm vai trò</div>
							<tr>
								<th class="label"><label for="tenvaitro">Tên vai trò</label></th>
								<td><input name="vtTen" size="30px" type="text" onkeypress="changevtTen();"
									class="text" required title="Tên vai trò không được để trống"><div id="requirevtTen" style="color: red"></div></td>
							</tr>
						</table>
					</div>
					<div class="group-button">
				<button class="button" type="button" onclick="addvt();">
					<i class="fa fa-plus-circle"></i>&nbsp;Thêm
				</button>
				<button type="reset" class="button">
					<i class="fa fa-refresh"></i>&nbsp;&nbsp;Nhập lại
				</button>
				<button type="button" class="button"
					onclick="showForm('add-form', false)">
					<i class="fa fa-sign-out"></i>&nbsp;&nbsp;Thoát
				</button>
			</div>
				</form>

				<!-- ---------------Update form-------------- -->
				<form id="update-form">
					<div class="input-table">
						<table>
							<div class="form-title">Cập nhật vai trò</div>
							<tr>
								<th><label for="tenvaitro">Tên vai trò</label></th>
								<td><input name="vtTenUpdate" size="30px" type="text" onkeypress="changevtTenUp();"
									class="text" required title="Tên vai trò không được để trống"><div id="requirevtTenUp" style="color: red"></div></td>
							</tr>
						</table>
					</div>
					<div class="group-button">
						
						<button type="button" class="button" onclick="confirmUpdatevt();">
							<i class="fa fa-floppy-o"></i>&nbsp;Lưu lại
						</button>
						<button  type="button" class="button" onclick="resetUpdatevt();">
							<i class="fa fa-refresh"></i>&nbsp;&nbsp;Nhập lại
						</button>
						<button type="button" class="button"
							onclick="showForm('update-form',false)">
							<i class="fa fa-sign-out"></i>&nbsp;&nbsp;Thoát
						</button>
					</div>
				</form>
			</div>
			</div>
</body>
</html>