<%@page import="model.NguoiDung"%>
<%@page import="model.CTVatTu"%>
<%@page import="model.VatTu"%>
<%@page import="model.DonViTinh"%>
<%@page import="model.NoiSanXuat"%>
<%@page import="model.ChatLuong"%>
<%@page import="map.siteMap"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>Văn phòng điện tử công ty điện lực Cần Thơ</title>
<link rel="stylesheet" href="style/style-giao-dien-chinh.css"
	type="text/css">
<link rel="stylesheet" href="style/style.css" type="text/css">
<link href="style/style-vat-tu.css" type="text/css" rel="stylesheet">
<link
	href="style/font-awesome-4.3.0/font-awesome-4.3.0/css/font-awesome.min.css"
	type="text/css" rel="stylesheet">
<script type="text/javascript" src="js/jquery.min.js"></script>
<script type="text/javascript" src="js/vattu.js"></script>
<script type="text/javascript" src="js/chi-tiet-vat-tu.js"></script>
<!-- <script> -->

<!-- 	</script> -->
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<link rel="Shortcut Icon" href="img/logo16.png" type="image/x-icon" />

<link rel="stylesheet" type="text/css" href="style/jquery.autocomplete.css" />
	<script src="http://www.google.com/jsapi"></script>  
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
<body>
	<%
		String adminMa = request.getServletContext().getInitParameter("adminMa");
   		NguoiDung authentication = (NguoiDung) session.getAttribute("nguoiDung");
   		if (authentication == null) {
   			request.setAttribute("url", siteMap.vattuManage + "?action=manageVattu");
   			RequestDispatcher dispatcher = request.getRequestDispatcher(siteMap.login + ".jsp");
   			dispatcher.forward(request, response);
   			return;
   		}
   	%>
	<%
    	ArrayList<VatTu> listVatTu = (ArrayList<VatTu>) request.getAttribute("vatTuList");
		if (listVatTu ==  null) {
			int index = siteMap.vtManage.lastIndexOf("/");
			String url = siteMap.vtManage.substring(index);
			RequestDispatcher dispatcher =  request.getRequestDispatcher(url + "?action=manageVattu");
			dispatcher.forward(request, response);
			return;
		}
     	ArrayList<CTVatTu> listCTVatTu = (ArrayList<CTVatTu>) request.getAttribute("ctVatTuList");
   		ArrayList<NoiSanXuat> listNoiSanXuat = (ArrayList<NoiSanXuat>) request.getAttribute("noiSanXuatList");
   		ArrayList<ChatLuong> listChatLuong = (ArrayList<ChatLuong>) request.getAttribute("chatLuongList");
   		ArrayList<DonViTinh> listDonViTinh = (ArrayList<DonViTinh>) request.getAttribute("donViTinhList");
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
<!--
							<span class="search-select">
								<select name="" ><option disabled selected>--Tùy chọn kiếm kiềm--</option></select>
								<option value=""></option>
							</span>
-->
							
							<span class="search-text">
								&nbsp;
							<input type="search" class="search" name="search_box" name="search" placeholder="Tìm kiếm" />
							</span>
							<span class="search-button">
							&nbsp;
							<button class="btn-search"><i class="fa fa-search" ></i></button>
							</span>
						</form>
					</div>
					
				</div>
				<div class="main_menu">
					<ul>
				<li><a href="<%=siteMap.homePageManage%>">Trang chủ</a></li>
				<%if (adminMa.equalsIgnoreCase(authentication.getChucDanh().getCdMa())) {%>
				
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
				<%if (adminMa.equalsIgnoreCase(authentication.getChucDanh().getCdMa())) {%>
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
						<li><a href="<%=siteMap.changePassPage + ".jsp"%>">Đổi mật khẩu</a></li>
						<li><a href="<%=siteMap.logout + "?action=logout"%>">Đăng xuất</a></li>
					</ul>
				</li>		
			</ul>
					<div class="clear"></div>
				</div>
		<div id="greeting">Chào <%=authentication.getHoTen() %></div>
		<div id="main-vattu">
			<div id="title-content">Danh mục vật tư</div>
<!-- 					<h3>* Tìm kiếm mã</h3> -->
<!-- 						<input type="text" id="country" name="country"/> -->
						
<!-- 						<script> -->
<!--  							$("#country").autocomplete("getdata.jsp"); -->
<!-- 						</script> -->
		
		<form id="vattu">
			<table>		
					<tr>		
					<th  style="text-align: left; color: black; font-size: 19px;">* Tìm kiếm mã</th>
								<td>
									<div class="search_form1" id="search">		
										<form>	
<!-- 											<span class="search-text"> &nbsp; <input type="search" class="text" name="search_box" name="search" placeholder="Tìm kiếm" /> 												 -->
<!-- 												<td><input type="checkbox" class="checkbox" style="text-align: center;"/></td> -->
<!-- 												<td  style="text-align: center; color: black; font-size: 19px;">Theo tên</td>&nbsp;&nbsp;&nbsp; -->
<!-- 											</span> -->
											
											<span> &nbsp; <input type="search" id="searchName" class="text-search" name="vattu"/>						
														<script>
														$("#searchName").autocomplete("getdataMa.jsp");
														$("#searchName").autocomplete("getdata.jsp");
// 														i = 0;
// 															$('#checkTen').check(function() {
// 																var check = $(this).val();
// 																alert(check);
// 																if(i % 2 == 0)
// 																	$("#searchName").autocomplete("getdata.jsp");
// 																else 
// 																	$("#searchName").autocomplete("getdataMa.jsp");
															//});
// 															$('#checkTen').uncheck(function() {
// 																	$("#searchName").autocomplete("getdataMa.jsp");
// 															});
															
														</script> 												
												<td><input type="checkbox" value="check" class="checkbox" style="text-align: center;" id="checkTen"/></td>
												<td  style="text-align: center; color: black; font-size: 19px;">Theo tên</td>&nbsp;&nbsp;&nbsp;
											</span>
												<td> <span class="search-button"> &nbsp; <button type="button" class="btn-search" style="background-color: #00A69B;" onclick="timKiemVattu();"><i class="fa fa-search"></i></button></span></td>						
										</form>
									</div>
					</tr>					
				</table>
			
			
			<div id="view-table-vat-tu" style="height: 400px; margin: 0 auto;">

				<table>
					<tr style="background: #199e5e; height: 30px">
						<th class="left-column"><input type="checkbox"
							class="checkAll"></th>
						<th class="two-column" style="text-align: left;">Mã vật tư</th>
						<th class="three-column"style="text-align: left;">Tên vật tư</th>					
						<th class="four-column">Đơn vị tính</th>
						<th class="four-column">Xem chi tiết</th>
					</tr>
					<%
							if(listVatTu != null) {
							int count = 0;
							for(VatTu vatTu : listVatTu) { count++;%>

					<tr class="rowContent"
						<%if (count % 2 == 0) out.println("style=\"background : #CCFFFF;\"");%>>
						<td class="left-column"><input type="checkbox" name="vtMa"
							value="<%=vatTu.getVtMa() %>" class="checkbox"></td>
						<td class="col" style="text-align: left;" ><%=vatTu.getVtMa() %></td>
						<td class="col" style="text-align: left;"><%=vatTu.getVtTen() %></td>
						<td class="col" style="text-align: center;"><%=vatTu.getDvt().getDvtTen() %></td>
						<td style="text-align: center;"><button type="button" class="button-xem" value="Xem" onclick="showCTVatTu(<%=vatTu.getVtMa()%>);">Xem</button></td>
					</tr>
					<%} }%>
				</table>
 			</div>

<!-- 			<div id = "paging" > -->
<!-- 							<table style ="border-style: none;"> -->
<!-- 								<tr> -->
<!-- 									<td><a href=""> Previous<< </a></td> -->
<!-- 									<td> -->
<%-- 										<% --%>
<!-- // 										long p = (pageNum < 10 ? pageNum : 10); -->
<%-- 									for(int i = 0; i <= p; i++) { %> --%>
<%-- 										<input type="button" value="<%=i+1%>" class="page"> --%>
<%-- 								<%} %> --%>
<!-- 									</td> -->
<!-- 									<td><a href="">>>Next </a> </td> -->
<!-- 								</tr> -->
<!-- 							</table> -->
<!-- 						</div> -->
			<div id = "paging" >
								<%
										String str = "";
										String pages = ""; 
										long p = (pageNum < 10 ? pageNum : 10);
									for(int i = 0; i < p; i++) {
										str += "<input type=\"button\" value=\"" + (i+1) + "\" class=\"page\" onclick= \"loadPageVatTu(" + i +")\">&nbsp;";
									}
									if (pageNum > 10)
										// str = "<input type=\"button\" value=\"<<Previous\" onclick= \"loadPageCtvtYc(\'Previous\')\">&nbsp;"  + str + "<input type=\"button\" value=\"Next>>\" onclick= \"loadPageCtvtYc(\'Next\');\">";
										str += "<input type=\"button\" value=\"Sau >>\" onclick= \"loadPageVatTu(\'Next\');\">";
									out.println(str);	
								%>
<!-- 									<input type="button" value="Next>>"></td> -->
					
			</div>
			<div class="group-button">
				<input type="hidden" name="action" value="deleteVatTu">
				<button type="button" class="button"
					onclick="showForm('add-form', true)">
					<i class="fa fa-plus-circle"></i>&nbsp;Thêm
				</button>
				<button type="button" class="button"
					onclick="preEditVattu('update-form', true);">
					<i class="fa fa-pencil fa-fw"></i>&nbsp;Thay đổi
				</button>
				<button type="button" class="button" onclick="confirmDeleteVT();">
					<i class="fa fa-trash-o"></i>&nbsp;&nbsp;Xóa
				</button>
				&nbsp;
				<button class="button" type="reset">
					<i class="fa fa-spinner"></i>&nbsp;&nbsp;Bỏ qua
				</button>
				&nbsp;
<!-- 				<button class="button" type="button"> -->
<!-- 					<i class="fa fa-print"></i>&nbsp;&nbsp;In -->
<!-- 				</button> -->
				&nbsp;
				<button type="button" class="button" onclick="location.href='<%=siteMap.home%>'">
					<i class="fa fa-sign-out"></i>&nbsp;&nbsp;Thoát
				</button>
			</div>
			</form>
			
</div>
					<!-- add-form-->
			
			<form id="add-form" method="get" action="<%=siteMap.vattuManage + "?action=manageVattu"%>">
				<div class="input-table">
					<table>
						<div class="form-title" style="padding: 10px">Thêm vật tư</div>
						<tr>
							<th style="text-align: left"><label for="MVT">Mã vật
									tư</label></th>
							<td><input name="vtMa" size="5px" align=right type="text" onkeypress="changeVtMa();"
								class="text" required autofocus size="8" maxlength="8" id="vtMa"
								title="Mã vật tư không được để trống"><div id="requireVtMa" style="color: red"></div></td>
						</tr>
						<tr>
							<th style="text-align: left"><label for="MVT">Tên vật tư</label></th>
							<td><input name="vtTen" size="30px" align=right type="text" onkeypress="changeVtTen();"
								class="text" required title="Mã vật tư không được để trống"><div id="requireVtTen" style="color: red"></div></td>
						</tr>
						
						<tr>
							<th style="text-align: left"><label for="DVT">Đơn vị tính</label></th>
							<td>
									<select onkeypress="changedvt();"
									title="" class="select" id="donvitinh" name="dvt" style="margin-top: 10px;">
										<option disabled selected value="">-- Chọn đơn vị tính --</option>
										<%						  
		 								
		 								for (DonViTinh donViTinh : listDonViTinh)
		 								{%>  
		 								<option value=<%=donViTinh.getDvtId()%>><%=donViTinh.getDvtTen()%></option>
		 								<%}
		  								%>  
									</select><div id="requireDvt" style="color: red"></div>
								</td>
							
						</tr>
					
					</table>
				</div>
				<div class="group-button">
<!-- 					<input type="hidden" name="action" value="addVatTu"> -->
					<button type="button" class="button" onclick="addVattu();">
						<i class="fa fa-plus-circle"></i>&nbsp;Thêm
					</button>
<!-- 					<button class="button" onclick="showForm('add-chitiet', true)" > -->
<!-- 						<i class="fa fa-plus-circle"></i>&nbsp;Thêm chi tiết -->
					</button> 
					<button type="reset" class="button"><i class="fa fa-refresh"></i>&nbsp;&nbsp;Nhập lại</button>
					<button type="button" class="button" onclick="showForm('add-form', false)"><i class="fa fa-sign-out"></i>&nbsp;&nbsp;Thoát</button>
				</div>
			</form>
			
			<form id="update-form">
				<div class="input-table">
					<table>
						<div class="form-title">Cập nhật Vật tư</div>
						<tr>
							<th style="text-align: left"><label for="MVT">Mã vật
									tư</label></th>
							<td><input name="vtMaUpdate" size="5px" align=right type="text" readonly style="background-color: #D1D1E0;"
								class="text" value="10102345"></td>
						</tr>
						<tr>
							<th style="text-align: left"><label for="MVT">Tên
									vật tư</label></th>
							<td><input name="vtTenUpdate" size="30px" align=right type="text"  id="aa" onkeypress="changeVtTenUp();"
								class="text" value="10102345"><div id="requireVtTenUp" style="color: red"></div></td>
						</tr>
<!-- 						<tr> -->
<!-- 							<th style="text-align: left"><label for="DVT">Đơn vị -->
<!-- 									tính</label></th> -->
<!-- 							<td><select id="dvtUp" class="select"  name="dvtUpdate" onkeypress="changeVtDvtUp();"> -->
<!-- 									<option value="m">m</option> -->
<!-- 									<option value="cai">cai</option> -->
<!-- 									<option value="cuon">cuộn</option> -->
<!-- 							</select><div id="requireDvtUp" style="color: red"></div></td> -->
<!-- 						</tr> -->
						<tr>
							<th style="text-align: left"><label for="MVT">Đơn vị tính</label></th>
								<td>
									<select onkeypress="changedvtUp();"
									title="" class="select" id="donvitinhUp" name="dvtUpdate" style="margin-top: 10px;">
										<option disabled selected value="">-- Chọn đơn vị tính --</option>
										<%						  
		 								
		 								for (DonViTinh donViTinh : listDonViTinh)
		 								{%>  
		 								<option value=<%=donViTinh.getDvtId()%>><%=donViTinh.getDvtTen()%></option>
		 								<%}
		  								%>  
									</select><div id="requireDvtUp" style="color: red"></div>
								</td>
						</tr>
					</table>
				</div>
				<div class="group-button">
					<button type="button" class="button" onclick="confirmUpdateVattu();" ><i class="fa fa-floppy-o"></i>&nbsp;Lưu lại</button> 
					<button type="button" class="button" onclick="resetUpdateVT();"><i class="fa fa-refresh"></i>&nbsp;&nbsp;Nhập lại</button>
					<button type="button" class="button" onclick="showForm('update-form',false)"><i class="fa fa-sign-out"></i>&nbsp;&nbsp;Thoát</button>
				</div>
			</form>
			
			
	<form id="chitiet" >
	
			<table>		
					<tr>		
						<th  style="text-align: left; color: black; font-size: 19px;">*Mã vật tư</th>
						<th  style="text-align: left; color: black; font-size: 19px;">*Tên vật tư</th>
						<th  style="text-align: left; color: black; font-size: 19px;">*Nơi sản xuất</th>
						<th  style="text-align: left; color: black; font-size: 19px;">*Chất lượng</th>
											
					</tr>		
					<tr>
						<td><input type="text" class="text" name="Ma"/></td>	
						<td><input type="text" class="text" name="Ten"/></td>	
						<td>
									<select onkeypress="changeNsx();"
									title="" class="select" id="noisanxuat" name="noiSanXuat" style="margin-top: 10px;">
										<option disabled selected value="">-- Chọn nơi sản xuất --</option>
										<%						  
		 								
		 								for (NoiSanXuat noiSanXuat : listNoiSanXuat)
		 								{%>  
		 								<option value=<%=noiSanXuat.getNsxMa()%>><%=noiSanXuat.getNsxTen()%></option>
		 								<%}
		  								%>  
									</select><div id="requireNsx" style="color: red"></div>
						</td>
						<td>
									<select onkeypress="changeCl();" 
											title="" class="select" id="chatluong" name="chatLuong" style="margin-top: 10px;">
												<option disabled selected value="">-- Chọn chất lượng --</option>
												<%						  
				 								
				 								for (ChatLuong chatLuong : listChatLuong)
				 								{%>
				 								<option value=<%=chatLuong.getClMa()%>><%=chatLuong.getClTen()%></option> 
				 								<%}  
				  								%>  
									</select><div id="requireCl" style="color: red"></div>
						</td>
						<td> <span class="search-button"> &nbsp; <button type="button" class="btn-search" style="background-color: #00A69B;">
						<i class="fa fa-search"></i></button></span></td>	
					</tr>			
				</table>
			
				<div id="view-table-chi-tiet" style="height: 350px; width: 910px; overflow:auto">

				<table>
					<tr style="background: #199e5e">
						<th class="left-column"><input type="checkbox" class="checkCTAll"></th>
						<th class="four-column">Mã vật tư</th>
						<th class="three-col">Tên vật tư</th>
						<th class="six-column">Nơi sản xuất</th>
						<th class="six-column">Chất lượng</th>
						<th class="four-column">Đơn vị tính</th>
						<th class="five-column">Định mức</th>
						<th class="seven-column">Số lượng tồn</th>
					</tr>
					<%
							if(listCTVatTu != null){
								
							}
							if(listCTVatTu != null) {
							int count = 0;
							for(CTVatTu ctVatTu : listCTVatTu) { count++;%>

					<tr
						<%if (count % 2 == 0) out.println("style=\"background : #CCFFFF;\"");%>>
						<td class="left-column"><input type="checkbox" name="ctvtId"
							value="<%=ctVatTu.getCtvtId() %>" id ="checkbox" class="checkbox"></td>
						<td class="col"><%=ctVatTu.getVatTu().getVtMa() %></td>
						<td class="col"><%=ctVatTu.getVatTu().getVtTen() %></td>
						<td class="col"><%=ctVatTu.getNoiSanXuat().getNsxTen() %></td>
						<td class="col"><%=ctVatTu.getChatLuong().getClTen() %></td>
						<td class="col"><%=ctVatTu.getVatTu().getDvt().getDvtTen() %></td>
						<td class="col"><%=ctVatTu.getDinhMuc() %></td>
						<td class="col"><%=ctVatTu.getSoLuongTon() %></td>

					</tr>
					<%} }%>
				</table>	
					
			</div>
	
				<div id = "paging" >
			</div>
					<div class="group-button">
				<input type="hidden" name="action" value="deleteVatTu">
				<button type="button" class="button"
					onclick="preAddCTVatTu('add-chitiet', true)">
					<i class="fa fa-plus-circle"></i>&nbsp;Thêm chi tiết
				</button>
				<button type="button" class="button"
					onclick="preEditCTVattu('update-chitiet', true)">
					<i class="fa fa-pencil fa-fw"></i>&nbsp;Thay đổi
				</button>
				<button class="button" type="button" onclick="confirmDeleteCTVT();">
					<i class="fa fa-trash-o"></i>&nbsp;&nbsp;Xóa
				</button>
				&nbsp;
				<button class="button" type="reset">
					<i class="fa fa-spinner"></i>&nbsp;&nbsp;Bỏ qua
				</button>
				&nbsp;
				<button type="button" class="button" onclick="showForm2('vattu' ,'chitiet', false)">
					<i class="fa fa-sign-out"></i>&nbsp;&nbsp;Thoát
				</button>
			</div>
		</form>
		
			<!-- add-chitiet -->
			<form id="add-chitiet">
									<div class="input-table">
					<table>
						<div class="form-title" style="padding: 10px">Thêm chi tiết vật tư</div>
						<tr>
							<th style="text-align: left"><label for="MVT">Mã vật
									tư</label></th>
							<td><input name="vtMa" size="5px" align=right type="text" readonly style="background-color: #D1D1E0;"
								class="text"></td>
						</tr>
						<tr>
							<th style="text-align: left"><label for="MVT">Tên vật
									tư</label></th>
							<td><input name="vtTen" size="30px" align=right type="text" readonly style="background-color: #D1D1E0;"
								class="text" ></td>
						</tr>
						<tr>
							<th style="text-align: left"><label for="MVT">Đơn vị tính</label></th>
							<td><input name="dvt" size="5px" align=right type="text" readonly style="background-color: #D1D1E0;"
								class="text"></td>
						</tr>
						<tr>
							<th style="text-align: left"><label for="MVT">Nơi
									sản xuất</label></th>
								<td>
									<select onkeypress="changeNsx();"
									title="" class="select" id="noisanxuat" name="noiSanXuat" style="margin-top: 10px;">
										<option disabled selected value="">-- Chọn nơi sản xuất --</option>
										<%						  
		 								
		 								for (NoiSanXuat noiSanXuat : listNoiSanXuat)
		 								{%>  
		 								<option value=<%=noiSanXuat.getNsxMa()%>><%=noiSanXuat.getNsxTen()%></option>
		 								<%}
		  								%>  
									</select><div id="requireNsx" style="color: red"></div>
								</td>
						</tr>
						
						
						<tr>
							<th style="text-align: left"><label for="DM">Định mức</label></th>
							<td><input name="dinhMuc" style="width: 100px" align=right type="number" onkeypress="changeDM();"
								class="text" required title="Định mức không được để trống"><div id="requireDM" style="color: red"></div></td>
						</tr>
						<tr>
							<th style="text-align: left"><label for="DVT">Chất lượng</label></th>
								<td>
									<select onkeypress="changeCl();" 
											title="" class="select" id="chatluong" name="chatLuong" style="margin-top: 10px;">
												<option disabled selected value="">-- Chọn chất lượng --</option>
												<%						  
				 								
				 								for (ChatLuong chatLuong : listChatLuong)
				 								{%>
				 								<option value=<%=chatLuong.getClMa()%>><%=chatLuong.getClTen()%></option> 
				 								<%}  
				  								%>  
									</select><div id="requireCl" style="color: red"></div>
								</td>
						</tr>
						<tr>
							<th style="text-align: left"><label for="Sl">Số lượng</label></th>
							<td>
								<input name="soLuongTon" style="width: 100px" type="number" class="text" required title="Số lượng tồn không được để trống" onkeypress="changeSL();"><div id="requireSl" style="color: red"></div>
							</td>
						</tr>
					</table>
				</div>
				<div class="group-button">
					<button type="button" class="button" onclick="addCTVattu();"><i class="fa fa-plus-circle"></i>&nbsp;Thêm</button>
					<button type="button" class="button" onclick="resetAddCTVT();"><i class="fa fa-refresh"></i>&nbsp;&nbsp;Nhập lại</button>
					<button type="button" class="button" onclick="showForm('add-chitiet', false)"><i class="fa fa-sign-out"></i>&nbsp;&nbsp;Thoát</button>
				</div>
			</form>
			
			
			<!-- update-chitiet -->
			<form id="update-chitiet">
									<div class="input-table">
					<table>
						<div id="tua" class="form-title" style="padding: 10px">Cập nhật chi tiết vật tư</div>
						<tr>
							<th style="text-align: left"><label for="MVT">Mã vật tư</label></th>
							<td><input name="vtMaUpdate" size="5px" type="text" readonly style="background-color: #D1D1E0;"
								class="text"></td>
						</tr>
						<tr>
							<th style="text-align: left"><label for="MVT">Tên vật
									tư</label></th>
							<td><input name="vtTenUpdate" size="30px" type="text" readonly style="background-color: #D1D1E0;"
								class="text" ></td>
						</tr>
						<tr>
							<th style="text-align: left"><label for="MVT">Đơn vị tính</label></th>
							<td><input name="dvtUpdate" size="5px" type="text" readonly style="background-color: #D1D1E0;"
								class="text"></td>
						</tr>
						<tr>
							<th style="text-align: left"><label for="MVT">Nơi sản xuất</label></th>
							<td><input name="nsxUpdate" size="15px" type="text" readonly style="background-color: #D1D1E0;"
							class="text"><input name="maNsx" type="hidden"></td>
						</tr>
						
						<tr>
							<th style="text-align: left"><label for="DVT">Chất lượng</label></th>
								<td><input name="clUpdate" size="15px" type="text" readonly style="background-color: #D1D1E0;"
								class="text"><input name="maCl" type="hidden"></td>
						</tr>
						<tr>
							<th style="text-align: left"><label for="DM">Định mức</label></th>
							<td><input name="dinhMucUpdate" size="5px" type="number" onkeypress="changeDMUp();"
								class="text" required title="Định mức không được để trống"><div id="requireDMUp" style="color: red"></div></td>
						</tr>
						<tr>
							<th style="text-align: left"><label for="Sl">Số lượng</label></th>
							<td>
								<input name="soLuongTonUpdate" size="5px" type="number" class="text" required title="Số lượng tồn không được để trống" onkeypress="changeSLUp();"><div id="requireSlUp" style="color: red"></div>
							</td>
						</tr>
					</table>
				</div>
				<div class="group-button">
					<button type="button" class="button" onclick="confirmUpdateCTVattu();"><i class="fa fa-plus-circle"></i>&nbsp;Lưu</button>
					<button type="button" class="button" onclick="resetUpdateCTVT();"><i class="fa fa-refresh"></i>&nbsp;&nbsp;Nhập lại</button>
					<button type="button" class="button" onclick="showForm('update-chitiet', false)"><i class="fa fa-sign-out"></i>&nbsp;&nbsp;Thoát</button>
				</div>
			</form>
		</div>
		</div>
</body>
</html>