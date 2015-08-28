<%@page import="map.siteMap"%>
<%@ page language="java" contentType="text/html; charset= UTF-8"
	pageEncoding="UTF-8"%>
<link href="style/style-login.css" type="text/css"rel="stylesheet">
<script type="text/javascript" src="js/jquery.min.js"></script>
<script type="text/javascript" src="js/nguoidung.js"></script>
<div class="module form-module">
  <div class="toggle"><i class="fa fa-times fa-pencil"></i>
  </div>
  <div class="form">
  	<%
  		String status = (String)request.getAttribute("status");
  		if (status != null)
  			out.println("<script>alert('Mã số nhân viên và mật khẩu chưa đúng vui lòng kiểm tra lại')</script>");
  	%>
    <h2>Đăng nhập</h2>
    <form method="post" action="<%=siteMap.loginAction%>">
      <input type="text" autofocus required size="10" maxlength="10" title="Mã số nhân viên đủ 10 ký tự, không chứa ký tự đặc biệt"
	pattern="[a-zA-Z0-9]*" class="text" id="msnv" name="msnv" placeholder="Tên tài khoản"/>
      <input type="password" required size="20" maxlength="20"title="Mật khẩu phải hơn 7 ký tự và nhỏ hơn 21" pattern=".{8,20}"
	class="text" id="matkhau" name="matkhau" placeholder="Mật khẩu"/>
      <button class="button" type="submit" >Đăng nhập</button>
    </form>
  </div>
  </div>
</div>