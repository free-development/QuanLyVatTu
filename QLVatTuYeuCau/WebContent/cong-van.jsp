<?xml version="1.0" encoding="UTF-8"?>
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
<script type="text/javascript" src="js/jquery.min.js"></script>
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
    	HashMap<Integer, File> fileHash = (HashMap<Integer, File>) request.getAttribute("fileHash");
    	ArrayList<DonVi> donViList = (ArrayList<DonVi>) request.getAttribute("donViList");
    	ArrayList<MucDich> mucDichList = (ArrayList<MucDich>) request.getAttribute("mucDichList");
    	ArrayList<TrangThai> trangThaiList = (ArrayList<TrangThai>) request.getAttribute("trangThaiList");
    	Long size = (Long) request.getAttribute("size");
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
								<li><a href="<%=siteMap.dvtManage + "?action=manageDvt"%>">Danh mục đơn vị tính</a></li>
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
				<li><a href="">Quản lý người dùng</a>
					<ul>
						<li><a href="<%=siteMap.ndManage + "?action=manageNd"%>">Thêm người dùng</li>
						<li><a href=""/>Khôi phục mật khẩu</li>
					</ul>
				</li>
				<li><a href="<%=siteMap.changePass + "?action=changePassWord"%>">Đổi mật khẩu</a></li>
<!-- 				<li><a href="login.html">Đăng xuất</a></li> -->
			</ul>
			<div class="clear"></div>
		</div>
		<div id="greeting">Chào Nguyễn Văn An</div>

		<div id="main-content">
			<div id="content-form">
			<div id="title-content">Công văn</div>
			<div id="main-content">
				<!--            <form id="main-form">-->
				<div class="left-content" >
					<div id="scroll_time">
						<ol class="tree">
							<li><label for="year">Năm 2015</label> <input
								type="checkbox" id="year" />
								<ol>
									<li><label for="month">Tháng 08</label> <input
										type="checkbox" id="month" />
										<ol>
											<div class>
											<li class="date"><input type="button"></input><a href="">Ngày 31</a></li>
											</div>
										</ol>
									</li>
								</ol>
							</li>
						</ol>
					</div>
				<div id="Link-vbd">
						<div class="vbd-column">--Văn bản đến--</div><br>
						<div class="tt-column">
						<a href="" >Chưa giải quyết</a><br>
						<a href="">Đang giải quyết</a><br>
						<a href="">Đã giải quyết</a>
						</div>
				</div>
					<br> <br>
					
			</div>
				<div class="right-content">
					<form id="search-form">
						<div id="title-table">
						<table>
							<tr>
								<td class="column-loc">Chỉ tiêu lọc dữ liệu:</td>
								<td><select class="select">
										<option selected disabled>-- Chỉ tiêu lọc --</option>
										<option>Ngày đến</option>
										<option>Số đến</option>
										<option>Mục đích nhận</option>
										<option>Nơi gửi</option>
										<option>Trích yếu</option>
										<option>Bút phê</option>
										<option>Nơi GQ chính</option>

								</select></td>
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
										type="search" class="text" name="search_box" name="search"
										placeholder="Nội dung tìm kiếm" />
								</span> <span class="search-button">
										<button class="btn-search">
											<i class="fa fa-search"></i>
										</button>
								</span> <!--                                 </did="test"iv>-->
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
                     <div class="scroll_content">
						<%
                     	int count = 0;
                     	for(CongVan congVan : congVanList) {
                     		count ++;
                     %>
					<table class="rowContent" <%if (count % 2 == 1){ out.println("style=\"background : #CCFFFF;\"");}else{out.println("style=\"background : Blush;\"");}%>style="font-size: 16px;width:900px;" class="border-congvan">
								<tr >
									<td class="column-check" rowspan="7">
										<input title="Click để chọn công văn"type="checkbox" name="cvId" value="<%=congVan.getCvId()%>">
									</td>
									<td class="left-column-soden">Số đến: &nbsp;&nbsp;</td>
									<td class="column-so-den" style="text-align: left"><%=congVan.getSoDen() %></td>

									<td class="left-column-socv">Số công văn: &nbsp;&nbsp;</td>
									<td class="column-socv" style="text-align: left;color:red;"><%=congVan.getCvSo() %></td>
									
									<td class="left-column-first" >Ngày đến: &nbsp;&nbsp;</td>

									<td class="column-date"style="text-align: left"><%=congVan.getCvNgayNhan() %></td>
									
								</tr>
								<tr>

									<td class="left-column-first">Mục đích: &nbsp;&nbsp;</td>

									<td class="column-color" colspan="3" style="text-align: left"><%=congVan.getMucDich().getMdTen() %></td>

									<td class="left-column-ngdi">Ngày công văn đi:&nbsp;&nbsp;</td>
									<td class="column-date" style="text-align: left"><%=congVan.getCvNgayDi()%></td>
									
								</tr>
								<tr>

									<td class="left-column-first">Nơi gửi: &nbsp;&nbsp;</td>
									<td class="column-color" colspan="6" style="text-align: left"><%= congVan.getDonVi().getDvTen()%></td>
									
								</tr>
								<tr>

									<td class="left-column-first">Trích yếu: &nbsp;&nbsp;</td>
									<td class="column-color"colspan="6" style="text-align: left"><%= congVan.getTrichYeu()%></td>
								</tr>
								<tr>

									<td class="left-column-first">Bút phê: &nbsp;&nbsp;</td>

									<td class="column-color" colspan="6"><%= congVan.getButPhe()%></td>
								</tr>
								<tr>

									<td class="left-column-first">Nơi GQ chính</td>

									<td class="column-color"colspan="3"><%=congVan.getDonVi().getDvTen() %></td>
									<td colspan=3 style="float: right;"><button  class="button" type="button" onclick="location.href='<%=siteMap.cscvManage + "?action=chiaSeCv&congVan=" + congVan.getCvId()%>'">
								<i class="fa fa-spinner"></i>&nbsp;&nbsp;Chia sẻ công văn
							</button></td>
								</tr>
								
								
								<tr>
								<td class="left-column-first">Link file công văn: </td>
								<td colspan="6"><a
												href="<%=siteMap.cvManage + "?action=download&file=" + congVan.getCvId()%>">
												<div class="mo-ta"><%=fileHash.get(congVan.getCvId()).getMoTa() %></div>
											</a></td>
								</tr>
							</table>
<!-- 							<div class="chi-tiet"> -->
<!-- 								<a -->
<%-- 									href="<%=siteMap.ycvtManage + "action=manageYcvt&congVan="+congVan.getCvId()%>">*Xem --%>
<!-- 									chi tiết</a> -->
<!-- 							</div> -->
							<%} %>

						</div>
						
						
						<div class="group-button">
							<input type="hidden" name="action" value="update-yeu-cau">
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
<!-- 							<button class="button" "> -->
<!-- 								<i class="fa fa-trash-o"></i>&nbsp;&nbsp;Xóa -->
<!-- 							</button> -->
							&nbsp;
							<button class="button" onclick="return checkCongVan();">
								<i class="fa fa-spinner"></i>&nbsp;&nbsp;Cập nhật yêu cầu vật tư
							</button>
							&nbsp;
							<button type="button" class="button" onclick="location.href='<%=siteMap.home%>'">
						<i class="fa fa-sign-out"></i>&nbsp;&nbsp;Thoát
					</button>
						</div>
					</form>
				</div>

				</div>

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
					<div class="group-button">
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
								<td><input type="date" class="text" name="ngayNhanUpdate" id="ngayNhan" value=<%=DateUtil.convertToSqlDate(new java.util.Date()) %>>
								</td>
							</tr>
							<tr>
								<th style="text-align: left"><label for="mucDich" class="input">Mục
										đích</label></th>
								<td><select class="select" name="mucDichUpdate" id="mucDich">
										<option disabled selected value="">Chọn mục đích</option>
										<%for(MucDich mucDich : mucDichList) {%>
										<option value="<%=mucDich.getMdMa()%>" name="mucDich"><%=mucDich.getMdTen()%></option>
										<%} %>
								</select></td>
								<th style="text-align: left;"><label
									for="noiGoi" class="input">Nơi gửi</label></th>
								<td><select class="select" name="donViUpdate" id="noiGoi">
										<option selected disabled value="">Chọn nơi gởi</option>
										<%for(DonVi donVi : donViList) {%>
										<option value="<%=donVi.getDvMa()%>" ><%=donVi.getDvTen() %></option>
										<%} %>
								</select></td>
							<tr>
								<th style="text-align: left;" colspan="1"><label id="trichYeu" class="input">Trích yếu</label>
								<td colspan="3"><textarea class="txtarea" name="trichYeuUpdate"></textarea></td>
							</tr>
							<tr>
								<th style="text-align: left;" colspan="1"><label id="butPhe" class="input">Bút phê</label>
								<td colspan="3"><textarea class="txtarea" name="butPheUpdate"></textarea></td>
							</tr>
							<tr>
								<th style="text-align: left;"><label
									for="file" class="input" name="file">Tệp đính kèm: </label></th>
								<td><input type="file" id="file" name="file">
							</tr>
							<tr>
								<th style="text-align: left"><label for="TT">Trạng
										thái</label></th>
								<td style="text-align: right; padding-left: 10px;">
									<% for (TrangThai trangThai : trangThaiList) {%>
									<input type="radio" name="ttMaUpdate" value="<%=trangThai.getTtMa()%>" id="<%=trangThai.getTtMa()%>"> 
									<label for="<%=trangThai.getTtMa()%>"><%=trangThai.getTtTen()%></label>&nbsp;&nbsp;&nbsp;
									<%}%>
								</td>
							</tr>	
						</table>
						
					</div>
						
					</div>
					<div class="group-button">
						<button class="button">
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
				<!--    update-vat-tu    -->
				<form id="update-yc-vat-tu">
					<div class="input-table-vt">
						<table>
							<div class="form-title">Cập nhật yêu cầu vật tư</div>
							<table>
								<tr>
									<th class="a-column">Chọn</th>
									<th class="b-column">Mã vật tư</th>
									<th class="c-column">Tên vật tư</th>

									<th class="e-column">Nơi sản xuất</th>
									<th class="f-column">Chất lượng</th>
									<th class="d-column">Số lượng</th>
									<th class="g-column">Đvt</th>
								</tr>
								<tr>
									<td class="a-column"><input type="checkbox" name=""></td>
									<td class="b-column">315422050</td>
									<td class="c-column">Cáp đòng bọc hạ áp CV 50m...</td>
									<td class="d-column">Viet Nam</td>
									<td class="e-column">Hàng thu hồi</td>
									<td class="f-column">17</td>
									<td class="g-column">m</td>
								</tr>
								<tr>
									<td class="a-column"><input type="checkbox" name=""></td>
									<td class="b-column">31582025</td>
									<td class="c-column">Cáp đồng bọc 24KV CX(CR)...</td>
									<td class="d-column">5</td>
									<td class="e-column">51091.313</td>
									<td class="f-column">255457</td>
									<td class="g-column">m</td>
								</tr>
								<tr>
									<td class="a-column"><input type="checkbox" name=""></td>
									<td class="b-column">32084025</td>
									<td class="c-column">Đầu cosse ép đồng 25cm2</td>
									<td class="d-column">1</td>
									<td class="e-column">19200</td>
									<td class="f-column">19200</td>
									<td class="g-column">cái</td>
								</tr>
								<tr>
									<td class="a-column"><input type="checkbox" name=""></td>
									<td class="b-column">32084050</td>
									<td class="c-column">Đầu cosse ép đồng 50cm2</td>
									<td class="d-column">4</td>
									<td class="e-column">23500</td>
									<td class="f-column">23500</td>
									<td class="g-column">cái</td>
								</tr>
								<tr>
									<td class="a-column"><input type="checkbox" name=""></td>
									<td class="b-column">32005814</td>
									<td class="c-column">Nối ép WR 259</td>
									<td class="d-column">4</td>
									<td class="e-column">11200</td>
									<td class="f-column">44800</td>
									<td class="g-column">cái</td>
								</tr>
							</table>
							</div>
							<div class="group-button">
								<button type="button" class="button"
									onclick="showForm('update-yc-vat-tu', 'add-form-ycvt', true)">
									<i class="fa fa-plus-circle"></i>&nbsp;Thêm mới
								</button>
								<button type="button" class="button"
									onclick="showForm('update-yc-vat-tu','update-form-ycvt', true)">
									<i class="fa fa-pencil fa-fw"></i>&nbsp;Sửa
								</button>
								<button class="button" onclick="return confirmDelete()">
									<i class="fa fa-trash-o"></i>&nbsp;&nbsp;Xóa
								</button>
								<button type="reset" class="button">
									<i class="fa fa-refresh"></i>&nbsp;&nbsp;Nhập lại
								</button>
								<button type="button" class="button"
									onclick="showForm('main-form', 'update-yc-vat-tu',false)">
									<i class="fa fa-sign-out"></i>&nbsp;&nbsp;Thoát
								</button>
							</div>
							</div>
							</div>
							</form>

							<form id="add-form-ycvt">
								<div class="input-table">
									<table>
										<div class="form-title" style="padding: 10px">Thêm yêu
											cầu vật tư</div>
										<tr>
											<th style="text-align: left"><label for="TKM">Tìm
													kiếm mã</label></th>
											<td><input name="" type="text" class="text" required
												autofocus size="2" maxlength="3" pattern="[a-zA-Z0-9]{3}"
												title="Mã mục đích chỉ gồm 3 ký tự, không chứ khoảng trắng và ký tự đặc biệt"></td>
											<td class="name" style="text-align: right"><input
												type="checkbox" name=""></td>
											<th style="text-align: left"><label for="TT">Theo
													tên</label></th>
										</tr>
										<tr>
											<th style="text-align: left"><label for="MVT">Mã
													vật tư</label></th>
											<td><input name="" size="3px" class="text" align=right
												type="text" class="text" required
												title="Mã vật tư không được để trống"></td>
										</tr>
										<tr>
											<th style="text-align: left"><label for="MVT">Nơi
													sản xuất</label></th>
											<td><select required
												title="Nơi sản xuất không được để trống">
													<option selected disabled></option>
													<option>aaaaaaaaaaaaaaa</option>
											</select></td>
										</tr>
										<tr>
											<th style="text-align: left"><label for="DVT">Chất
													lượng</label></th>
											<td><select required
												title="Nơi sản xuất không được để trống">
													<option selected disabled></option>
													<option>aaaaa</option>
											</select></td>

										</tr>
										<tr>
											<th style="text-align: left">Số lượng</th>
											<td><input class="text" type="text" size="2px"></td>
										</tr>
										<tr>
											<th style="text-align: left"><label for="DVT">Đơn
													vị tính</label></th>
											<td><select required
												title="Nơi sản xuất không được để trống">
													<option selected disabled></option>
													<option>aaaaa</option>
											</select></td>

										</tr>
									</table>
								</div>
								<div class="group-button">
									<button class="button">
										<i class="fa fa-plus-circle"></i>&nbsp;Thêm
									</button>
									<button type="reset" class="button">
										<i class="fa fa-refresh"></i>&nbsp;&nbsp;Nhập lại
									</button>
									<button type="button" class="button"
										onclick="showForm('update-yc-vat-tu','add-form-ycvt', false)">
										<i class="fa fa-sign-out"></i>&nbsp;&nbsp;Thoát
									</button>
								</div>
							</form>
							<form id="update-form-ycvt">
								<div class="input-table">
									<table>
										<div class="form-title" style="padding: 10px">Cập nhật
											yêu cầu vật tư</div>
										<tr>
											<th style="text-align: left"><label for="TKM">Tìm
													kiếm mã</label></th>
											<td><input name="" type="text" class="text" required
												autofocus size="2" maxlength="3" pattern="[a-zA-Z0-9]{3}"
												title="Mã mục đích chỉ gồm 3 ký tự, không chứ khoảng trắng và ký tự đặc biệt"></td>
											<td class="name" style="text-align: right"><input
												type="checkbox" name=""></td>
											<th style="text-align: left"><label for="TT">Theo
													tên</label></th>
										</tr>
										<tr>
											<th style="text-align: left"><label for="MVT">Mã
													vật tư</label></th>
											<td><input name="" size="3px" align=right class="text"
												type="text" class="text" required
												title="Mã vật tư không được để trống"></td>
										</tr>
										<tr>
											<th style="text-align: left"><label for="MVT">Nơi
													sản xuất</label></th>
											<td><select required
												title="Nơi sản xuất không được để trống">
													<option selected disabled></option>
													<option>aaaaaaaaaaaaaaa</option>
											</select></td>
										</tr>
										<tr>
											<th style="text-align: left"><label for="DVT">Chất
													lượng</label></th>
											<td><select required
												title="Nơi sản xuất không được để trống">
													<option selected disabled></option>
													<option>aaaaa</option>
											</select></td>

										</tr>
										<tr>
											<th style="text-align: left">Số lượng</th>
											<td><input class="text" type="text" size="2px"></td>
										</tr>
										<tr>
											<th style="text-align: left"><label for="DVT">Đơn
													vị tính</label></th>
											<td><select required
												title="Nơi sản xuất không được để trống">
													<option selected disabled></option>
													<option>aaaaa</option>
											</select></td>

										</tr>
									</table>
								</div>
								<div class="group-button">
									<button class="button">
										<i class="fa fa-floppy-o"></i>&nbsp;Lưu lại
									</button>
									<button type="reset" class="button">
										<i class="fa fa-refresh"></i>&nbsp;&nbsp;Nhập lại
									</button>
									<button type="button" class="button"
										onclick="showForm('update-yc-vat-tu', 'update-form-ycvt',false)">
										<i class="fa fa-sign-out"></i>&nbsp;&nbsp;Thoát
									</button>
								</div>
							</form>
						</table>
						</div>
						</form>
						</div>
						</div>
						</div>		
</body>
</html>
