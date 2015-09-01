package controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.print.attribute.standard.NumberOfInterveningJobs;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.CTNguoiDung;
import model.ChatLuong;
import model.ChucDanh;
import model.DonVi;
import model.MucDich;
import model.NguoiDung;
import model.NoiSanXuat;
import util.JSonUtil;
import util.StringUtil;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import dao.CTNguoiDungDAO;
import dao.CTVatTuDAO;
import dao.ChatLuongDAO;
import dao.ChucDanhDAO;
import dao.DonViDAO;
import dao.NguoiDungDAO;
import dao.NoiSanXuatDAO;
import map.siteMap;

/**
 * Servlet implementation class NdController
 */
@Controller
public class NdController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	@RequestMapping("/ndManage")
	public ModelAndView ndManage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		NguoiDungDAO nguoiDungDAO = new NguoiDungDAO();
		ChucDanhDAO chucDanhDAO = new ChucDanhDAO();
		CTNguoiDungDAO ctNguoiDungDAO = new CTNguoiDungDAO();
		String action = request.getParameter("action");
		if ("manageNd".equalsIgnoreCase(action)) {
			ArrayList<ChucDanh> chucDanhList = (ArrayList<ChucDanh>) new ChucDanhDAO().getAllChucDanh();
			return new ModelAndView("them-nguoi-dung","chucDanhList", chucDanhList);
		}
		if ("changePassWord".equalsIgnoreCase(action)) {
			return new ModelAndView("doi-mat-khau");
		}
		if("AddNd".equalsIgnoreCase(action)) {
			String msnv = request.getParameter("msnv");
			String chucdanh = request.getParameter("chucdanh");
			String matkhau = request.getParameter("matkhau");
			String hoten = request.getParameter("hoten");
			String sdt = request.getParameter("sdt");
			String email = request.getParameter("email");
			String diachi = request.getParameter("diachi");
			nguoiDungDAO.addNguoiDung(new NguoiDung(msnv, hoten, diachi, email, sdt, new ChucDanh(chucdanh)));
			ctNguoiDungDAO.addCTNguoiDung(new CTNguoiDung(msnv, StringUtil.encryptMD5(matkhau)));
			
			ArrayList<NguoiDung> nguoiDungList =  (ArrayList<NguoiDung>) nguoiDungDAO.getAllNguoiDung();
			return new ModelAndView("them-nguoi-dung", "nguoiDungList", nguoiDungList);
			
		}
		if("manageNd".equalsIgnoreCase(action)) {
			ArrayList<ChucDanh> chucDanhList = (ArrayList<ChucDanh>) new ChucDanhDAO().getAllChucDanh();
			ArrayList<NguoiDung> nguoiDungList =  (ArrayList<NguoiDung>) nguoiDungDAO.getAllNguoiDung();
			request.setAttribute("chucDanhList", chucDanhList);
			return new ModelAndView("them-nguoi-dung", "nguoiDungList", nguoiDungList);
		}
		
		return new ModelAndView(siteMap.login);
	}
	
	@RequestMapping(value="/addNd", method=RequestMethod.GET, 
			produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	 public @ResponseBody String addNd(@RequestParam("msnv") String msnv, @RequestParam("chucdanh") String chucdanh, @RequestParam("matkhau") String matkhau
	 , @RequestParam("hoten") String hoten, @RequestParam("sdt") String sdt,@RequestParam("email") String email, @RequestParam("diachi") String diachi){
		String result = "";
		System.out.println("MA: "+msnv);
		if((new NguoiDungDAO().getNguoiDung(msnv)==null)&&(new CTNguoiDungDAO().getCTNguoiDung(msnv)==null))
		{
			new NguoiDungDAO().addNguoiDung(new NguoiDung(msnv, hoten, diachi, email, sdt, new ChucDanh(chucdanh)));
			new CTNguoiDungDAO().addCTNguoiDung(new CTNguoiDung(msnv, StringUtil.encryptMD5(matkhau)));
			
			System.out.println("success");
			result = "success";	
			
		}
		else
		{
			System.out.println("fail");
			result = "fail";
		}
			return JSonUtil.toJson(result);
			
	}
	
	@RequestMapping(value="/preUpdateNd", method=RequestMethod.GET, 
			produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	 public @ResponseBody String preUpdateNd(@RequestParam("clMa") String clMa) {
		ChatLuongDAO chatLuongDAO = new ChatLuongDAO();
		ChatLuong cl = chatLuongDAO.getChatLuong(clMa);
		return JSonUtil.toJson(cl);
	}
	
	
	@RequestMapping(value="/changePass", method=RequestMethod.POST, 
			produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody String changePass(@RequestParam("msnv") String msnv, @RequestParam("passOld") String passOld
			, @RequestParam("passNew") String passNew) {

		String result = "";
		if (new CTNguoiDungDAO().login(msnv, StringUtil.encryptMD5(passOld))) {
			new CTNguoiDungDAO().updateCTNguoiDung(new CTNguoiDung(msnv, StringUtil.encryptMD5(passNew)));
			result = "success";
		}
		else
		{
			result = "fail";
		}
		return JSonUtil.toJson(result);
	}
	/*
	@RequestMapping("/login")
	public ModelAndView login (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		String msnv = request.getParameter("msnv");
		String matKhau = request.getParameter("matkhau");
		CTNguoiDungDAO ctndDAO = new CTNguoiDungDAO();
		NguoiDungDAO ndDAO = new NguoiDungDAO();
		boolean check = ctndDAO.login(msnv, StringUtil.encryptMD5(matKhau));
		if (check) {
			NguoiDung nguoiDung =  ndDAO. getNguoiDung(msnv);
			session.setAttribute("nguoiDung", nguoiDung);
			return new ModelAndView("index");
		} else {
			return new ModelAndView("login", "status", "fail");
		}
	}
	*/
	@RequestMapping(value="/login", method=RequestMethod.GET, 
			produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody String login(@RequestParam("msnv") String msnv, @RequestParam("matkhau") String matkhau)
	{
//		HttpSession session =  
		String result = "";
		if (new CTNguoiDungDAO().login(msnv, StringUtil.encryptMD5(matkhau))) {
			result = "success";
			HttpServletResponse response;
			response.sendRedirect("home.jsp");
		}
		else {
			result = "fail";
		}
		return JSonUtil.toJson(result);
	}
	
}
