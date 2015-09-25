<%@page import="model.VaiTro"%>
<%@page import="dao.VaiTroDAO"%>
<%@page import="model.NguoiDung"%>
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
<link href="style/style-cong-van.css" type="text/css" rel="stylesheet">
<link rel="stylesheet" href="style/style-menu-tree.css" type="text/css">
<link
	href="style/font-awesome-4.3.0/font-awesome-4.3.0/css/font-awesome.min.css"
	type="text/css" rel="stylesheet">
<script type="text/javascript" src="js/location.js"></script>
<script type="text/javascript" src="js/jquery.min.js"></script>

<%
		String truongPhongMa = request.getServletContext().getInitParameter("truongPhongMa");
		String vanThuMa = request.getServletContext().getInitParameter("vanThuMa");
		String adminMa = request.getServletContext().getInitParameter("adminMa");
		String hosting = request.getServletContext().getInitParameter("hosting");
		int capPhatMa = Integer.parseInt(request.getServletContext().getInitParameter("capPhatId"));
   		NguoiDung authentication = (NguoiDung) session.getAttribute("nguoiDung");
   		if (authentication == null) {
   			session.setAttribute("url", siteMap.cvManage+ "?action=manageCv");
   			RequestDispatcher dispatcher = request.getRequestDispatcher(siteMap.login + ".jsp");
   			dispatcher.forward(request, response);
   			return;
   		}
   	%>
<script type="text/javascript">
<% 
String chucDanh = authentication.getChucDanh().getCdMa();
String chucDanhMa = chucDanh;
%>
check = <% if (vanThuMa.equals(chucDanhMa) ) out.print("false"); else out.print("true");%>;
capVatTuId = '<%=capPhatMa  %>';
chucDanhMa = '<%=chucDanhMa  %>';
vanThuMa = '<%=vanThuMa  %>';
truongPhongMa = '<%=truongPhongMa  %>';
hosting = '<%=hosting  %>';
// || capPhatMa.equals(chucDanhMa)

</script>
<script type="text/javascript" src="js/cong-van.js"></script>
<meta charset="utf-8">
<link rel="Shortcut Icon" href="img/logo16.png" type="image/x-icon" />

</head>
<body>
	
	<%
		request.getCharacterEncoding();
		response.getCharacterEncoding();
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		String error = (String) request.getAttribute("error");
		if(error != null)
			out.println("<script>alert('Số công văn đã tồn tại. Vui lòng nhập lại!!!')</script>");
    	ArrayList<CongVan> congVanList = (ArrayList<CongVan>) request.getAttribute("congVanList");
    	if (congVanList ==  null) {
    		int index = siteMap.cvManage.lastIndexOf("/");
    		String url = siteMap.cvManage.substring(index);
    		RequestDispatcher dispatcher =  request.getRequestDispatcher(url+"?action=manageCv");
    		dispatcher.forward(request, response);
    		return;
    	}
    		
    	HashMap<Integer, File> fileHash = (HashMap<Integer, File>) request.getAttribute("fileHash");
    	ArrayList<DonVi> donViList = (ArrayList<DonVi>) request.getAttribute("donViList");
    	ArrayList<MucDich> mucDichList = (ArrayList<MucDich>) request.getAttribute("mucDichList");
    	ArrayList<TrangThai> trangThaiList = (ArrayList<TrangThai>) request.getAttribute("trangThaiList");
    	ArrayList<Integer> yearList = (ArrayList<Integer>) request.getAttribute("yearList");
    	Long size = (Long) request.getAttribute("size");
    	ArrayList<ArrayList<VaiTro>> vtCongVanList = (ArrayList<ArrayList<VaiTro>>) request.getAttribute("vtCongVanList");
    	ArrayList<ArrayList<String>> nguoiXlCongVan = (ArrayList<ArrayList<String>>) request.getAttribute("nguoiXlCongVan");
    	
    	try {
    %>
    
	<div class="wrapper">
		<jsp:include page="header.jsp" /> 
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
						<li><a href="<%=siteMap.ndManage + "?action=manageNd"%>"/>Thêm người dùng</li>
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
			<div id="content-form">
			<div id="title-content">Công văn</div>
				<!--            <form id="main-form">-->
				<div class="left-content" >
					<div id="scroll_time">
						<ol class="tree">
						<% for (Integer year : yearList) {%>
							<li id="year<%=year%>"><label for="y<%=year%>"><%=year %></label> <input
									type="checkbox" id="y<%=year %>" value=<%=year %> class="year" name="year" />
<%-- 									onchange="propCheckYear('y<%=year %>');" --%>
									<ol>
									</ol>
<!-- 								<div class="month">	 -->
								
							</li>
							
							<%} %>
						</ol>		
					</div>
					<table style="margin-left:18px;">
						<tr>
						<th style="text-align: center;">
							--Văn bản đến--
						</th>
						</tr>
						<tr>
						<td style="text-align: center;">
						<select id = "ttFilter" class="select" name="trangThai">
							<option value = "" style="text-align: center;font-weight: bold;">Tất cả</option>
							<option value = "CGQ" style="text-align: center;font-weight: bold;">Chưa giải quyết</option>
							<option value = "DGQ" style="text-align: center;font-weight: bold;">Đang giải quyết</option>
							<option value = "DaGQ" style="text-align: center;font-weight: bold;">Đã giải quyết</option>
						</select>
						</td>
						</tr>
					</table>
					<br> <br>
					
			</div>
				<div class="right-content">
					<form id="search-form">
						<div id="title-table">
						<table>
							<tr>
								<th class="column-loc">Tìm kiếm: </th>
								<td><select class="select" name="filter" id="filter">
										<option value =""> Tất cả </option>
<!-- 										<option>Ngày đến</option> -->
										<option value="soDen">Số đến</option>
										<option value="mucDich">Mục đích nhận</option>
<!-- 										<option>Nơi gửi</option> -->
										<option value="trichYeu">Trích yếu</option>
										<option value="butPhe">Bút phê</option>
<!-- 										<option>Nơi GQ chính</option> -->

								</select>
								<div id="requireFilter"></div>
								</td>
								<td>&nbsp;&nbsp;</td>
								<!--<td>
                                 <select class="select" >
                                <option selected disabled>--Tìm kiếm--</option>
                                <option>Thời gian</option>
                                <option>Số CV</option>
                                <option>Đơn vị</option>
                            </select>
                            </td>-->
								<td>
									<!--                                 <div class="search-form">-->
									<span class="search-text"> <!--								&nbsp;--> <input
										type="search" class="text" name="filterValue" id="filterValue" readonly style="background: #D1D1E0;"
										placeholder="Nội dung tìm kiếm" />
								</span> <span class="search-button">
										<button class="btn-search" id = "buttonSearch" type="button">
											<i class="fa fa-search"></i>
										</button>
								</span> <!--                                 </did="test"iv>-->
								<div id="requireFilterValue"></div>
								</td>
								<!--
                            <td>
                                <span class="search-button">
							&nbsp;
							<button class="btn-search"><i class="fa fa-search" ></i></button>
							</span>
                            </td>
-->
							</tr>
						</table>
						</div>
					</form>	


                     <form name="main-form" method="get" action="<%=siteMap.ycvtManage%>">
                     <div style="height: 500px; width: 810px; overflow:auto" class="scroll_content ">
						<%
					
                     	int count = 0;
                     	for(CongVan congVan : congVanList) {
                     		count ++;
                     %>
					<table class="tableContent" <%if (count % 2 == 1){ out.println("style=\"background : #CCFFFF;\"");}else{out.println("style=\"background : #FFFFFF;\"");}%>style="font-size: 16px;width:900px;" class="border-congvan">
						<tr >
						<% if (chucDanhMa.equals(vanThuMa)) {%>
							<td class="column-check" rowspan="7" style="margin-right: 30px;">
								<input title="Click để chọn công văn"type="checkbox" name="cvId" value="<%=congVan.getCvId()%>">
							</td>
							<%} %>
							<td class="left-column-soden" style="font-weight: bold;">Số đến: &nbsp;&nbsp;</td>
							<td class="column-so-den" style="text-align: left;"><%=congVan.getSoDen() %></td>
							<td class="left-column-socv" style="font-weight: bold;">Số công văn: &nbsp;&nbsp;</td>
							<td class="column-socv" style="text-align: left;color:red;font-weight: bold;"><%=congVan.getCvSo() %></td>
							<td class="left-column-first" style="font-weight: bold;">Ngày đến: &nbsp;&nbsp;</td>
							<td class="column-date" style="text-align: left;color:blue;"><%=DateUtil.toString(congVan.getCvNgayNhan()) %></td>
						</tr>
						<tr>
							<td class="left-column-first" style="font-weight: bold;">Mục đích: &nbsp;&nbsp;</td>
							<td class="column-color" colspan="3" style="text-align: left"><%=congVan.getMucDich().getMdTen() %></td>
							<td class="left-column-ngdi" style="font-weight: bold;">Ngày công văn đi:&nbsp;&nbsp;</td>
							<td class="column-date" style="text-align: left;color:blue;"><%=DateUtil.toString(congVan.getCvNgayDi())%></td>
						</tr>
						<tr>
							
							<td class="left-column-first" style="font-weight: bold;">Nơi gửi: &nbsp;&nbsp;</td>
							<td class="column-color" colspan="3" style="text-align: left"><%= congVan.getDonVi().getDvTen()%></td>
							<td colspan="1" style="font-weight: bold;">Trạng thái:</td>
							<td colspan="1" style="color: red;font-weight: bold;font-style: oblique;"><%=congVan.getTrangThai().getTtTen() %></td>
						</tr>
						<tr>
						
							<td class="left-column-first" style="font-weight: bold;">Trích yếu: &nbsp;&nbsp;</td>
							<td class="column-color" colspan="6" style="text-align: left;font-weight: bold;"><%= congVan.getTrichYeu()%></td>
						</tr>
						<tr>
							<td class="left-column-first" style="font-weight: bold;">Bút phê: &nbsp;&nbsp;</td>
							<td class="column-color" colspan="6"><%= congVan.getButPhe()%></td>
						</tr>
						<tr>
							
							<%
								if (chucDanh.equals(truongPhongMa) || chucDanh.equals(vanThuMa)) { %>
									<td class="left-column-first" style="font-weight: bold;">Người xử lý</td>
									<td class="column-color"colspan="3">
									<%
										ArrayList<String> nguoiXlList = nguoiXlCongVan.get(count - 1);
										if (nguoiXlList.size() > 0) {
											System.out.println("size ngXlCv = " + nguoiXlList.size());
											StringBuilder cellHoTen = new StringBuilder("");   
											for (String hoTen : nguoiXlList) {
												cellHoTen.append(hoTen + ", ");
											}
											int len = cellHoTen.length();
											cellHoTen.delete(len -2, len);
											out.println(cellHoTen.toString());
										}%>
									</td>
									<%if (chucDanh.equals(truongPhongMa)) { %>
									<td colspan="2" style="float: right;">
										<button  class="button" type="button" style="width: 170px; height: 30px;" onclick="location.href='<%=siteMap.cscvManage + "?action=chiaSeCv&congVan=" + congVan.getCvId()%>'">
											<i class="fa fa-spinner"></i>&nbsp;&nbsp;Chia sẻ công văn
										</button>
									</td>
									<%} %>
								<%} else {%>
									<td class="left-column-first" style="font-weight: bold;">Vai trò</td>
									<td class="column-color"colspan="3">
									<%
									boolean capPhat = false;
									StringBuilder vaiTro = new StringBuilder("");
									ArrayList<VaiTro> vaiTroList = vtCongVanList.get(count - 1);
									if (vaiTroList.size() > 0) {
										
										for (VaiTro vt : vaiTroList) {
											vaiTro.append(vt.getVtTen() + ", ");
											if (vt.getVtId() == capPhatMa)
												capPhat = true;
										}
										int len = vaiTro.length();
										vaiTro.delete(len - 2, len);
										out.println(vaiTro.toString());%>
									</td>
									<%if (capPhat) { %>
										<td colspan="3" style="float: right;">
											<button  class="button" type="button" style="width: 170px; height: 37px;" onclick="location.href='<%=siteMap.ycvtManage + "?cvId=" + congVan.getCvId()%>'">
												<i class="fa fa-spinner"></i>&nbsp;&nbsp;Cập nhật yêu cầu vật tư
											</button>									
										</td>
								<% 	}}}%>
									
						</tr>
						<tr>
							<td class="left-column-first" style="font-weight: bold;">Xem công văn: </td>
							<td colspan="5">
								<a href="<%=siteMap.cvManage + "?action=download&file=" + congVan.getCvId()%>">
									<div class="mo-ta"><%=fileHash.get(congVan.getCvId()).getMoTa() %></div>
								</a>
							</td>
							
						</tr>
					</table>
					<br>
					<hr>

							<%} %>

						</div>
					
						<div id="paging">
						<%
							long pageNum = size / 3;
							long p = (pageNum <= 10 ? pageNum : 10);
						
							for (int i = 0; i < p; i++) {
						%>
							<input type="button" name = "page" class="page" value="<%=i+1 %>" onclick = "loadPage(<%=i%>)">
						<%}
							if(pageNum > 10) {
						%>
							<input type="button"  class="pageMove" value = "Sau >>" onclick = "loadPage('Next');">
						<%}%>	
						</div>
						<script type="text/javascript">$('.page')[0].focus();</script>
						<div class="group-button">
							<input type="hidden" name="action" value="update-yeu-cau">
							<%
								if (vanThuMa.equalsIgnoreCase(chucDanh)) {
							%>
							<button type="button" class="button" onclick="loadDataCv();">
								<i class="fa fa-plus-circle"></i>&nbsp;Thêm mới
							</button>
							<button type="button" class="button"title="Chỉ chọn một công văn để sửa"
								onclick="checkUpdate()">
								<i class="fa fa-pencil fa-fw"></i>&nbsp;Sửa
							</button>
							<button class="button" type="button" onclick="confirmDelete();">
								<i class="fa fa-trash-o"></i>&nbsp;&nbsp;Xóa
							</button>
							<%} %>
<!-- 							<button class="button" "> -->
<!-- 								<i class="fa fa-trash-o"></i>&nbsp;&nbsp;Xóa -->
<!-- 							</button> -->
							&nbsp;
							<button type="button" class="button" onclick="location.href='<%=siteMap.home%>'">
						<i class="fa fa-sign-out"></i>&nbsp;&nbsp;Thoát
					</button>
						</div>
					</form>
				</div>

<!-- 				</div> -->

				<!--    		</form>  -->
				<!--                add-form-->
				<form id="add-form" name="add-form" action="<%=siteMap.addCv %>" enctype="multipart/form-data" method="post">

					<div class="form-title">Thêm công văn</div>
					<div class="input-table">
						<table>
<!-- 							<tr style="margin-bottom: 20px;"> -->
<!-- 								<th colspan="1" style="text-align: left"><label for="soDen" style="text-align: left">Số đến</label></th> -->
<!-- 								<td colspan="3"><input type = "text" class="text" readonly value="123" style="background: #D1D1E0;" sise="5" name="soDen"></td> -->
<!-- 							</tr> -->
							<tr style="margin-bottom: 20px;">
								<th style="text-align: left" colspan="1"> <label for="soDen" style="text-align: left">Số công văn: </label></th>
								<td colspan="3"><input type="text" class="text" name="cvSo" id="cvSo" onkeypress="changeSoCv();"><div id="requireSoCv" style="color: red"></div></td>
							</tr>	
							<tr style="margin-bottom: 20px;">	
								<th style="text-align: left"><label for="ngayGoi" class="input">Ngày gởi: </label></th>
								<td><input type="date" class="text" name="ngayGoi" id="ngayGoi" value=<%=DateUtil.convertToSqlDate(new java.util.Date()) %> ></td>
								<th style="text-align: left"><label for="ngayNhan" class="input">Ngày nhận: </label></th>
								<td><input type="date" class="text" name="ngayNhan" id="ngayNhan" value=<%=DateUtil.convertToSqlDate(new java.util.Date()) %> onkeypress="changeNgayNhan();"><div id="requireNgayNhan" style="color: red"></div></td>
							</tr>
							<tr>
								<th style="text-align: left"><label for="mucDich" class="input">Mục
										đích</label></th>
								<td><select class="select" name="mucDich" id="mucDich" onchange="changeMucDich();">
										<option disabled selected value="">Chọn mục đích</option>
										<%for(MucDich mucDich : mucDichList) {%>
										<option value="<%=mucDich.getMdMa()%>" name="mucDich"><%=mucDich.getMdTen()%></option>
										<%} %>
								</select><div id="requireMucDich" style="color: red"></div></td>
								<th style="text-align: left;"><label
									for="noiGoi" class="input">Nơi gửi</label></th>
								<td><select class="select" name="donVi" id="noiGoi" onchange="changeDonVi();">
										<option selected disabled value="">Chọn nơi gởi</option>
										<%for(DonVi donVi : donViList) {%>
										<option value="<%=donVi.getDvMa()%>" ><%=donVi.getDvTen() %></option>
										<%} %>
								</select><div id="requireDonVi" style="color: red"></div></td>
							<tr>
								<th style="text-align: left;" colspan="1"><label id="trichYeu" class="input">Trích yếu</label>
								<td colspan="3"><textarea class="txtarea" name="trichYeu"></textarea></td>
							</tr>
							<tr>
								<th style="text-align: left;" colspan="1"><label id="butPhe" class="input">Bút phê</label>
								<td colspan="3"><textarea class="txtarea" name="butPhe"></textarea></td>
							</tr>
							</tr>
								<th  style="text-align: left;"><label
										for="file" class="input" name="file">Đính kèm công văn: </label></th>
								<td colspan="3"><input type="file" id="file" name="file" onchange="changeFile();"><div id="requireFile" style="color: red"></div></td>
							<tr>	
							</tr>
								<th  style="text-align: left;"><label
										for="moTa" class="input" >Mô tả file: </label></th>
								<td colspan="3"><textarea class="txtarea" name="moTa" onkeypress="changeMoTa();"></textarea><div id="requireMoTa" style="color: red"></div></td>
							<tr>
						</table>
					</div>
					<div class="button-group">
						<input type="hidden" name="action" value="addCongVan">
						<button class="button" type="submit"
							onclick="return checkAdd();">
							<i class="fa fa-plus-circle"></i>&nbsp;Lưu lại
						</button>
						<button type="reset" class="button">
							<i class="fa fa-refresh"></i>&nbsp;&nbsp;Nhập lại
						</button>
						<button type="button" class="button"
							onclick="showForm('main-form', 'add-form', false)">
							<i class="fa fa-sign-out"></i>&nbsp;&nbsp;Thoát
						</button>
					</div>

				</form>
				<!--            update-form-->
				<form id="update-form" method="post" action="<%=siteMap.updateCv %>" enctype="multipart/form-data">
					<div class="input-table">
						<div class="input-table">
						<table>
							<tr style="margin-bottom: 20px;">
								<th colspan="1" style="text-align: left"><label for="soDen" style="text-align: left">Số đến</label></th>
								<td colspan="3"><input type = "text" class="text" value="123" readonly style="background: #D1D1E0;" sise="5" name="soDen"></td>
							</tr>
							<tr style="margin-bottom: 20px;">
								<th style="text-align: left" colspan="1"> <label for="cvSo" style="text-align: left">Số công văn: </label></th>
								<td colspan="3"><input type="text" class="text" name="cvSo" id="cvSo" readonly style="background: #D1D1E0;"></td>
							</tr>	
							<tr style="margin-bottom: 20px;">	
								<th style="text-align: left"><label for="ngayGoi" class="input">Ngày gởi: </label></th>
								<td><input type="date" class="text" name="ngayGoiUpdate" id="ngayGoi" value=<%=DateUtil.convertToSqlDate(new java.util.Date()) %>></td>
								<th style="text-align: left"><label for="ngayNhan" class="input">Ngày nhận: </label></th>
								<td><input onchange="changeNgayNhanUp();" type="date" class="text" name="ngayNhanUpdate" id="ngayNhan" value=<%=DateUtil.convertToSqlDate(new java.util.Date()) %>>
								<div id="requireNgayNhanUp" style="color: red"></div></td>
							</tr>
							<tr>
								<th style="text-align: left"><label for="mucDich" class="input">Mục
										đích</label></th>
								<td><select class="select" name="mucDichUpdate" id="mucDich" onchange="changeMucDichUp();">
										<option disabled selected value="">Chọn mục đích</option>
										<%for(MucDich mucDich : mucDichList) {%>
										<option value="<%=mucDich.getMdMa()%>" name="mucDich"><%=mucDich.getMdTen()%></option>
										<%} %>
								</select><div id="requireMucDichUp" style="color: red"></div></td>
								<th style="text-align: left;"><label
									for="noiGoi" class="input">Nơi gửi</label></th>
								<td><select class="select" name="donViUpdate" id="noiGoi" onchange="changeDonViUp();">
										<option selected disabled value="">Chọn nơi gởi</option>
										<%for(DonVi donVi : donViList) {%>
										<option value="<%=donVi.getDvMa()%>" ><%=donVi.getDvTen() %></option>
										<%} %>
								</select><div id="requireDonViUp" style="color: red"></div></td>
							<tr>
								<th style="text-align: left;" colspan="1"><label id="trichYeu" class="input">Trích yếu</label>
								<td colspan="3"><textarea class="txtarea" name="trichYeuUpdate"></textarea></td>
							</tr>
							<tr>
								<th style="text-align: left;" colspan="1"><label id="butPhe" class="input">Bút phê</label>
								<td colspan="3"><textarea class="txtarea" name="butPheUpdate"></textarea></td>
							</tr>
							<tr>
								<th style="text-align: left;"><label for="file" class="input" name="file">Tệp đính kèm: </label></th>
								<td><input type="file" id="file" name="file" onchange="changeFileUp();"><div id="requireFileUp" style="color: red"></div></td>
							</tr>
							<tr>
								<th style="text-align: left"><label for="TT">Trạng
										thái</label></th>
								<td style="text-align: right; padding-left: 10px;">
									<% for (TrangThai trangThai : trangThaiList) {%>
									<input type="radio" name="ttMaUpdate" value="<%=trangThai.getTtMa()%>" id="<%=trangThai.getTtMa()%>" onchange="changeTrangThaiUp();"> 
									<label for="<%=trangThai.getTtMa()%>"><%=trangThai.getTtTen()%></label>&nbsp;&nbsp;&nbsp;
									<%}%>
								<div id="requireTrangThaiUp" style="color: red"></div></td>
							</tr>	
						</table>
						
					</div>
						
					</div>
					<div class="group-button">
						<input type="hidden" name="action" value="updateCv">
						<button class="button" type="submit"
							onclick="return checkUp();">
							<i class="fa fa-floppy-o"></i>&nbsp;Lưu lại
						</button>
						<button type="reset" class="button">
							<i class="fa fa-refresh"></i>&nbsp;&nbsp;Nhập lại
						</button>
						<button type="button" class="button"
							onclick="showForm('main-form', 'update-form', false)">
							<i class="fa fa-sign-out"></i>&nbsp;&nbsp;Thoát
						</button>
					</div>
				</form>
				
						</div>
			</div>
		</div>		
		<%} catch (NullPointerException e){
			response.sendRedirect("login.jsp");
		}
			%>
</body>
</html>
