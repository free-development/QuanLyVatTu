<%@page import="model.NguoiDung"%>
<%@page import="javax.persistence.criteria.CriteriaBuilder.In"%>
<%@page import="model.YeuCau"%>
<%@page import="model.NoiSanXuat"%>
<%@page import="model.ChatLuong"%>
<%@page import="model.VatTu"%>
<%@page import="model.CTVatTu"%>
<%@page import="model.TrangThai"%>
<%@page import="model.MucDich"%>
<%@page import="model.DonVi"%>
<%@page import="util.DateUtil"%>
<%@page import="model.CongVan"%>
<%@page import="model.File"%>
<%@page import="map.siteMap"%>
<%@page import="java.util.HashMap"%> 
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<title>Văn phòng điện tử công ty điện lực Cần Thơ</title>

<link rel="stylesheet" href="style/style-giao-dien-chinh.css"
	type="text/css">
<link rel="stylesheet" href="style/style.css" type="text/css">
<link rel="stylesheet" href="style/jquery.autocomplete.css" type="text/css">
<link href="style/style-yeu-cau.css"type="text/css" rel="stylesheet">
<link rel="stylesheet" href="style/style-menu-tree.css" type="text/css">
<link
	href="style/font-awesome-4.3.0/font-awesome-4.3.0/css/font-awesome.min.css"
	type="text/css" rel="stylesheet">
<script type="text/javascript" src="js/jquery.min.js"></script>
<script type="text/javascript" src="js/check.js"></script>
<script src="js/jsapi.js"></script>  
	<script>  
		google.load("jquery", "1");
	</script>
	<script src="js/jquery.autocomplete.js"></script>
<script type="text/javascript" src="js/yeu-cau-vat-tu.js"></script>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="Shortcut Icon" href="img/logo16.png" type="image/x-icon" />
</head>
<body>
	<%
   		NguoiDung authentication = (NguoiDung) session.getAttribute("nguoiDung");
   		if (authentication == null) {
   			request.setAttribute("url", siteMap.cvManage+ "?action=manageCv");
   			RequestDispatcher dispatcher = request.getRequestDispatcher(siteMap.login + ".jsp");
   			dispatcher.forward(request, response);
   			return;
   		}
   	%>
	<%
    	ArrayList<CTVatTu> ctVatTuList = (ArrayList<CTVatTu>) request.getAttribute("ctVatTuList");
		if (ctVatTuList ==  null) {
			int index = siteMap.cvManage.lastIndexOf("/");
    		String url = siteMap.cvManage.substring(index);
    		RequestDispatcher dispatcher =  request.getRequestDispatcher(url+"?action=manageCv");
    		dispatcher.forward(request, response);
			return;
		}
		ArrayList<YeuCau> yeuCauList = (ArrayList<YeuCau>) request.getAttribute("yeuCauList");
		ArrayList<NoiSanXuat> nsxList = (ArrayList<NoiSanXuat>) request.getAttribute("nsxList");
		ArrayList<ChatLuong> chatLuongList = (ArrayList<ChatLuong>) request.getAttribute("chatLuongList");
		Long pageNum = (Long) request.getAttribute("page");
		Integer cvId = (Integer) session.getAttribute("cvId");
		if (cvId == null)
			response.sendRedirect(siteMap.congVan);
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
					<!--							<span class="bg-search">-->
					<span class="search-text"> &nbsp; <input type="search"
						class="search" name="search_box" name="search"
						placeholder="Tìm kiếm" />
					</span> <span class="search-button"> &nbsp;
						<button class="btn-search">
							<i class="fa fa-search"></i>
						</button>
					</span>
					<!--                            </span>-->
				</form>
			</div>

		</div>
		<div class="main_menu">
			<ul>
				<li><a href="<%=siteMap.homePageManage%>">Trang chủ</a></li>
				<%if ("admin".equalsIgnoreCase(authentication.getChucDanh().getCdTen())) {%>
				
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
				<li><a href="<%=siteMap.cvManage+ "?action=manageCv" %>">Công văn</a></li>
				<li><a>Báo cáo</a>
					<ul>
						<li><a href="<%=siteMap.bcvttManage+ "?action=manageBcvtt" %>"/>Báo cáo vật tư thiếu</li>
						<li><a href="<%=siteMap.bcbdnManage+ "?action=manageBcbdn" %>"/>Báo cáo bảng đề nghị cấp vật tư</li>
					</ul>
				</li>
				<%if ("admin".equalsIgnoreCase(authentication.getChucDanh().getCdTen())) {%>
				<li><a>Quản lý người dùng</a>
					<ul>
						<li><a href="<%=siteMap.ndManage + "?action=manageNd"%>">Thêm người dùng</li>
						<li><a href="<%=siteMap.updateNguoiDung%>"/>Cập nhật thông tin</li>
						<li><a href="<%=siteMap.resetPassword%>"/>Khôi phục mật khẩu</li>
						<li><a href="<%=siteMap.lockNguoiDung%>"/>Khóa tài khoản</li>
					</ul>
				</li>
				<%} %>
				<li><a>Tài khoản</a>
					<ul>
						<li><a href="<%=siteMap.changePass + "?action=changePassWord"%>">Đổi mật khẩu</a></li>
						<li><a href="<%=siteMap.logout + "?action=logout"%>">Đăng xuất</a></li>
					</ul>
				</li>		
			</ul>
			<div class="clear"></div>
		</div>
		<div id="greeting"></div>

		<div id="main-content">
			
			
			<form id="add-yeu-cau-form">
<!-- 			<div class="form-title">Cập nhật yêu cầu vật tư</div> -->
<!-- 			<fieldset style="width: 70%;margin:0 auto;"> -->
<!-- 				<legend style="padding-left: 20px; ">Tìm kiếm</legend> -->
				<div id="yc-table">
				<table>
					<tr>
						<td><label for="vtMa">Mã vật tư: </label></td>
						<td class="column-mavt" colspan=3><input type="search" maxlength="16" size="19px" name="search" id="searchName" class="text" autocomplete="off">
						&nbsp;&nbsp;&nbsp;<input type="checkbox" value="check" class="checkbox" style="text-align: center;" id="checkTen"/>Theo tên
						<script>
														$("#searchName").autocomplete("getdataMa.jsp");
														$("#searchName").autocomplete("getdata.jsp");
						</script>
						</td>
						<td style="text-align: left;"><button class="button" type="button" id="search">Tìm kiếm</button></td>
<!-- 						<td><label for="vtTen">Tên vật tư: </label></td> -->
<!-- 						<td class="column-tenvt"><input type="text" maxlength="3" size="3px" name="vtTen" id="vtMa" class="text"></td> -->
					</tr>
<!-- 					<tr> -->
<!-- 						<td><label for="nsx">Nơi sản xuất: </label></td> -->
<!-- 						<td class="column-noisx"> -->
<!-- 							<select name="nsx" id="nsx" class="select"> -->
<!-- 							<option selected disabled value="" style="text-decoration: none;">-- Chọn nơi sản xuất --</option> -->
<%-- 							<% for(NoiSanXuat nsx : nsxList) {%> --%>
<%-- 								<option value=<%=nsx.getNsxMa() %>><%=nsx.getNsxTen() %></option> --%>
<%-- 							<%} %> --%>
<!-- 							</select> -->
<!-- 						</td> -->
<!-- 						<td><label for="chatLuong">Chất lượng: </label></td> -->
<!-- 						<td> -->
<!-- 							<select name="chatLuong" id="chatLuong" class="select"> -->
<!-- 							<option selected disabled value="" style="text-decoration: none;">-- Chọn chất lượng --</option> -->
<%-- 							<% for(ChatLuong chatLuong : chatLuongList) {%> --%>
<%-- 								<option value=<%=chatLuong.getClMa() %>><%=chatLuong.getClTen()  %></option> --%>
<%-- 							<%} %> --%>
<!--  							</select>  -->
<!-- 						</td> -->
<!-- 					</tr> -->
					
				</table>
				</div>
<!-- 				<div class="group-button"> -->
<!-- 					<button class="button" type="button" onclick="searchCtvt();">Tìm kiếm</button> -->
<!-- 				</div> -->
<!-- 				</div> -->
<!-- 			</fieldset> -->
			</form>
			<form id="danh-sach-vat-tu">
			<div class="form-title">Danh sách vật tư</div>
				<div id="view-search">
				<div id="view-table-ds">
				<table style="width:960px;margin:0 auto;">
<!-- 					<tr><th >Ma vat tu</th><th >Ten vat tu</th><th >Noi san xuat</th><th >Chat luong</th><th >Don vi tinh</th><th ></th></tr> -->
					<tr style="background-color: #199e5e"><th style="text-align: center;" >Mã vật tư</th><th style="text-align: center;" >Tên vật tư</th><th style="text-align: center;">Nơi sản xuất</th><th style="text-align: center;" >Chất lượng</th><th style="text-align: center;" >Đơn vị tính</th><th style="text-align: center;">Thêm</th></tr>
					<tr></tr>
					<%
						int countCtvt = 0;
						for(CTVatTu ctVatTu : ctVatTuList) { 
							countCtvt++;
							VatTu vatTu = ctVatTu.getVatTu();
							NoiSanXuat nsx = ctVatTu.getNoiSanXuat();
							ChatLuong chatLuong = ctVatTu.getChatLuong();
						%>
						<tr id="row" class = "rowContent" <%if (countCtvt % 2 == 1) out.println("style=\"background : #CCFFFF;\"");%>>
							<td style="text-align: center;"><%=vatTu.getVtMa() %></td>
							<td><%=vatTu.getVtTen() %></td>
							<td style="text-align: center;"><%=nsx.getNsxTen() %></td>
							<td style="text-align: center;"><%=chatLuong.getClTen() %></td>
							<td style="text-align: center;"><%=vatTu.getDvt().getDvtTen() %></td>
							<td style="text-align: center;"><input class="radio"  type="radio" id="a" name="ctvtId" value="<%=ctVatTu.getCtvtId() %>" onchange="preAddSoLuong();"> </td>
							
						</tr>
					<%}%>
				</table>
				</div>
				</div>
			</form>
					<div id = "paging" >
								<%
									
										String str = "";
										String pages = ""; 
										long p = (pageNum < 10 ? pageNum : 10);
									for(int i = 0; i < p; i++) {
										str += "<input type=\"button\" value=\"" + (i+1) + "\" class=\"page\" onclick= \"loadPageCtvtYc(" + i +")\">&nbsp;";
									}
									if (pageNum > 10)
								str += "<input type=\"button\" value=\">>\" onclick= \"loadPageCtvtYc(\'Next\');\">";
									out.println(str);	
								%>
<!-- 									<input type="button" value="Next>>"></td> -->

					</div>
			</div>
					</div>
			

			<form id="main-form">
			<div class="form-title" style="padding-top: 10px;">Yêu cầu vật tư đã cập nhật</div> 
					<div id="view-table-yc" class="scroll-vat-tu">
							<table style= "width:960px; margin: 0 auto;s" >
								<tr>
									<th class="a-column"style= "text-align: center;">Chọn</th>
									<th class="b-column" style="text-align: center;">Mã vật tư</th>
									<th class="c-column"style="text-align: center;">Tên vật tư</th>
									<th class="e-column"style="text-align: center;">Nơi sản xuất</th>
									<th class="f-column"style="text-align: center;">Chất lượng</th>
									<th class="g-column"style="text-align: center;">Đơn vị tính</th>
									<th class="d-column"style="text-align: center;">Số lượng yêu cầu</th>
									<th >Đã cấp</th>
								</tr>
								<%
									int count = 0;
									for(YeuCau yeuCau : yeuCauList) {count++;
									CTVatTu ctVatTu = yeuCau.getCtVatTu();
									NoiSanXuat nsx = ctVatTu.getNoiSanXuat();
									VatTu vatTu = ctVatTu.getVatTu();
									ChatLuong chatLuong = ctVatTu.getChatLuong();
								%>
								<tr <%if (count % 2 == 0) out.println("style=\"background : #CCFFFF;\"");%> id="<%=yeuCau.getYcId()	%>">
									<td><input id="<%=yeuCau.getYcId() %>" type="checkbox" class="checkbox" name = "yeuCau" value=<%=yeuCau.getYcId()%>> </td>
									<td><%=vatTu.getVtMa()%></td>
									<td><%=vatTu.getVtTen()%></td>
									<td><%=nsx.getNsxTen()%></td>
									<td><%=chatLuong.getClTen()%></td>
									<td><%=vatTu.getDvt().getDvtTen()%></td>
									<td id="soLuong<%=yeuCau.getYcId()%>"><%=yeuCau.getYcSoLuong()%></td>
									<td id="soLuongCap<%=yeuCau.getYcId()%>"><%=yeuCau.getCapSoLuong()%></td>
								<%} %>
							</table>
							</div>
							<div class="group-button">
								<button type="button" class="button" id="pre-update-yc">
									<i class="fa fa-pencil fa-fw"></i>&nbsp;Sửa
								</button>
								<button type="button" class="button" id="preCapVatTu">
									<i class="fa fa-refresh"></i>&nbsp;&nbsp;Cấp phát
								</button>
								<button class="button" type="button" onclick="return confirmDelete();">
									<i class="fa fa-trash-o"></i>&nbsp;&nbsp;Xóa
								</button>
								
								<button type="button" class="button"
									onclick="showForm('main-form', 'update-yc-vat-tu',false)">
									<i class="fa fa-sign-out"></i>&nbsp;&nbsp;Thoát
								</button>
							</div>
				</form>
			<br>
			<br>
			<br>
			<br>
			<form id="add-so-luong-form" onsubmit="return false">
			<div class="form-title" style="margin-top: 10px;">Thêm yêu cầu vật tư</div>
			<div id="view-table-them">
			<table style= "width:900px; margin: 0 auto;margin-top: 20px;"  >
				<tr><th >Mã vật tư</th><th >Tên vật tư</th><th >Nơi sản xuất</th><th >Chất lượng</th><th >Đơn vị tính</th><th >Số lượng</th></tr>
				<tr>
					<td><div id="vtMaAdd"></div></td>
					<td><div id="vtTenAdd"></div></td>
					<td><div id="clTenAdd"></div></td>
					<td><div id="nsxTenAdd"></div></td>
					<td><div id="dvtAdd"></div></td>
<!-- 					<td><div id="vtMaAdd"></div></td> -->

					<td><input type="number" min=0 autofocus  name="soLuongAdd" title="So luong phai la so!!!"  class="text" style="width: 80px;"></td>
					<td><button class="button" type="button" onclick="addSoLuong();">Thêm</button></td>
				</tr>
			</table>
			</div>
			
			</form>
			<br>
			<br>
			<br>
			<br>
			<form id="update-so-luong-form" onsubmit="return false">
			<div class = "form-title" style="margin-top: 10px;">Thay đổi số lượng yêu cầu</div>
			<div id="view-table-doi" class="scroll-vat-tu">
			<table style= "width:900px; margin: 0 auto;" >
				<tr><th >Mã vật tư</th><th >Tên vật tư</th><th >Nơi sản xuất</th><th >Chất lượng</th><th >Đơn vị tính</th><th >Số lượng</th></tr>
				<tr>
					<td><div id="vtMaUpdate"></div></td>
					<td><div id="vtTenUpdate"></div></td>
					<td><div id="clTenUpdate"></div></td>
					<td><div id="nsxTenUpdate"></div></td>
					<td><div id="dvtUpdate"></div></td>
					<td><input type="number" min=0 autofocus  name="soLuongUpdate" title="So luong phai la so!!!"  class="text" style="width: 80px;"></td>
					<td><button class="button" type="button" id="updateYc">Lưu lại</button></td>
				</tr>
			</table>
			</div>
			</form>
			<form id="cap-so-luong-form" onsubmit="return false">
			<div class = "form-title"style="margin-top: 10px;">Cấp phát vật tư</div>
			<div id="view-table-cap" class="scroll-vat-tu">
			<table style= "width:900px; margin: 0 auto;" >
				<tr><th >Mã vật tư</th><th >Tên vật tư</th><th >Nơi sản xuất</th><th >Chất lượng</th><th >Đơn vị tính</th><th >Số lượng</th></tr>
				<tr>
					<td><div id="vtMaCap"></div></td>
					<td><div id="vtTenCap"></div></td>
					<td><div id="clTenCap"></div></td>
					<td><div id="nsxTenCap"></div></td>
					<td><div id="dvtCap"></div></td>
					<td><input type="number" min=0 autofocus  name="soLuongCap" title="So luong phai la so!!!"  class="text" style="width: 80px;"></td>
					<td><button class="button" type="button" id="capVatTu">Lưu lại</button></td>
				</tr>
			</table>
			</div>
			</form>
			

							</div>

							</div>
</body>
</html>
