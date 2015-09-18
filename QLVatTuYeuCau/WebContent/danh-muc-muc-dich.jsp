<%@page import="model.NguoiDung"%>
<%@page import="model.MucDich"%>
<%@page import="java.util.ArrayList"%>
<%@page import="map.siteMap"%>
<%@ page language="java" contentType="text/html; charset=utf-8"%>
<!DOCTYPE html>
<html>
<head>
<title>Văn phòng điện tử công ty điện lực Cần Thơ</title>
<link rel="stylesheet" href="style/style-giao-dien-chinh.css"
	type="text/css">
<link rel="stylesheet" href="style/style.css" type="text/css">
<link href="style/style-muc-dich.css" type="text/css" rel="stylesheet">
<script type="text/javascript" src="js/jquery-1.6.3.min.js"></script>
<script type="text/javascript" src="js/jquery.min.js"></script>
<link
	href="style\font-awesome-4.3.0\font-awesome-4.3.0\css\font-awesome.min.css"
	type="text/css" rel="stylesheet">
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
<script type="text/javascript" src="js/mucdich.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="Shortcut Icon" href="img/logo16.png" type="image/x-icon" />
</head>
<body>
	<%
		String adminMa = request.getServletContext().getInitParameter("adminMa");
   		NguoiDung authentication = (NguoiDung) session.getAttribute("nguoiDung");
   		if (authentication == null) {
   			request.setAttribute("url", siteMap.mdManage + "?action=manageMd");
   			RequestDispatcher dispatcher = request.getRequestDispatcher(siteMap.login + ".jsp");
   			dispatcher.forward(request, response);
   			return;
   		}
		String chucDanh = authentication.getChucDanh().getCdMa();
		String truongPhongMa = request.getServletContext().getInitParameter("truongPhongMa");
		String vanThuMa = request.getServletContext().getInitParameter("vanThuMa");
   	%>
	<%
    		ArrayList<MucDich> listMucDich = (ArrayList<MucDich>) request.getAttribute("mucDichList");
			if (listMucDich ==  null) {
				int index = siteMap.mdManage.lastIndexOf("/");
				String url = siteMap.mdManage.substring(index);
				RequestDispatcher dispatcher =  request.getRequestDispatcher(url + "?action=manageMd");
				dispatcher.forward(request, response);
				return;
			}
			long pageNum = (Long) request.getAttribute("page");
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
								<select name="" required>
								<option disabled selected>--Tùy chọn kiếm kiềm</option>
									<option>ABBB</option>
									<option>ABBB</option>
									<option>ABBB</option>
									<option>ABBB</option>
								</select>
							</span>
-->

					<span class="search-text"> &nbsp; <input type="search"
						required class="search" name="search_box" name="search"
						placeholder="Tìm kiếm" title="Bạn phải nhập nội dung tìm kiếm" />
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
		<div id="content">
			<div id="content-wrapper">
				<div id="title-content">Danh mục mục đích</div>
				<div id="main-content">

					<form id="main-form">
						<div id="view-table">
							<table>
								<tr style="background: #199e5e">
									<td class="left-column"><input type="checkbox" name=""
										class="checkAll"></td>
									<th class="mid-column">Mã mục đích</th>
									<th class="right-column">Tên mục đích</th>
								</tr>
								<%
							if(listMucDich != null) {
							int count = 0;
							for(MucDich mucDich : listMucDich) { count++;%>
								<tr class="rowContent"
									<%if (count % 2 == 0) out.println("style=\"background : #CCFFFF;\"");%>>
									<td class="left-column"><input type="checkbox" name="mdMa"
										value="<%=mucDich.getMdMa() %>" class="checkbox"></td>
									<td class="col"><%=mucDich.getMdMa() %></td>
									<td class="col"><%=mucDich.getMdTen() %></td>
								</tr>
								<%} }%>
							</table>
						</div>

						<div id = "paging" >
							<table style ="border-style: none;">
								<tr>
									<%if(pageNum >1) {%>
											<td>Trang</td>
											<td>
												<%
		 											//long pageNum = size / 10;
													for(int i = 0; i < pageNum; i++) { %>
														<input type="button" value="<%=i+1%>" class="page">
												<%} %>
											</td>
									<%} %>
								</tr>
							</table>
						</div>

						<div class="group-button">
							<input type="hidden" name="action" value="deleteMd">
							<button type="button" class="button"
								onclick="showForm('add-form', true)">
								<i class="fa fa-plus-circle"></i>&nbsp;Thêm
							</button>
							<button type="button" class="button"
								onclick="preUpdateMd('update-form', true)">
								<i class="fa fa-pencil fa-fw"></i>&nbsp;Thay đổi
							</button>
							<button class="button" type="button" onclick="confirmDeleteMd();">
								<i class="fa fa-trash-o"></i>&nbsp;&nbsp;Xóa
							</button>
							&nbsp;
							<button class="button" type="reset">
								<i class="fa fa-spinner"></i>&nbsp;&nbsp;Bỏ qua
							</button>
							&nbsp;
							<button type="button" class="button" onclick="location.href='<%=siteMap.home%>'">
								<i class="fa fa-sign-out"></i>&nbsp;&nbsp;Thoát
							</button>
						</div>
					</form>
	
				
	
					<form id="add-form" method="get"
						action="<%=siteMap.mdManage + "?action=manageMd" %>">
						<div class="input-table">
							<table>
								<div class="form-title">Thêm mục đích</div>
								<tr>
									<th><label for="MMD">Mã mục đích:</label></th>
									<td><input name="mdMa" type="text" class="text" required
										autofocus size="2" maxlength="3" onkeypress="changeMdMa();" pattern="[a-zA-Z0-9]{3}"
										title="Mã mục đích chỉ gồm 3 ký tự, không chứ khoảng trắng và ký tự đặc biệt"><div id="requireMdMa" style="color: red"></div></td>
								</tr>
								<tr>
									<th><label for="MMD">Tên mục đích:</label></th>
									<td><input name="mdTen" size="30px" align=left type="text"
										class="text" onkeypress="changeMdTen();" required title="Tên mục đích không được để trống"><div id="requireMdTen" style="color: red"></div></td>
								</tr>
							</table>
						</div>
						<div class="group-button">
							<!-- 						<input type="hidden" name="action" value = "AddMd">  -->
							<button class="button" type="button" onclick="addMd()">
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
								<div class="form-title">Cập nhật mục đích</div>
								<tr>
									<th><label for="MMD">Mã mục đích: </label></th>
									<td><input name="mdMaUpdate" type="text" class="text"
										required size="2" maxlength="3" pattern="[a-zA-Z0-9]{3}"
										title="Mã mục đích chỉ gồm 3 ký tự, không chứa khoảng trắng và ký tự đặc biệt"
										value="MMA" readonly style="background-color: #D1D1E0;"></td>
								</tr>
								<tr>
									<th><label for="MMD">Tên mục đích: </label></th>
									<td><input name="mdTenUpdate" autofocus size="30px"
										align=left type="text" class="text" onkeypress="changeMdTen();" required
										title="Tên mục đích không được để trống"><div id="requireMdTenUpdate" style="color: red"></div></td>
								</tr>
							</table>
						</div>
						<div class="group-button">
							<input type="hidden" name="action" value="UpdateMd">
							<button class="button" type="button" onclick="confirmUpdateMd()">
								<i class="fa fa-floppy-o"></i>&nbsp;Lưu lại
							</button>
							<button class="button" type="button" onclick="resetUpdateMd()">
								<i class="fa fa-refresh"></i>&nbsp;&nbsp;Nhập lại
							</button>
							<button type="button" class="button"
								onclick="showForm('update-form')">
								<i class="fa fa-sign-out"></i>&nbsp;&nbsp;Thoát
							</button>
						</div>
					</form>
				</div>
			</div>
</div>
		</div>
</body>
</html>