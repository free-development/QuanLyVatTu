package controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import map.siteMap;
import model.CTNguoiDung;
import model.ChatLuong;
import model.ChucDanh;
import model.NguoiDung;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import util.JSonUtil;
import util.StringUtil;
import dao.CTNguoiDungDAO;
import dao.ChatLuongDAO;
import dao.ChucDanhDAO;
import dao.NguoiDungDAO;



@Controller
public class NdController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	@Autowired
	private   ServletContext context;
	
	@RequestMapping("/ndManage")
	public ModelAndView ndManage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		if (session.getAttribute("nguoiDung") == null)
			response.sendRedirect("login.jsp");
		
		NguoiDungDAO nguoiDungDAO = new NguoiDungDAO();
		ChucDanhDAO chucDanhDAO = new ChucDanhDAO();
		CTNguoiDungDAO ctNguoiDungDAO = new CTNguoiDungDAO();
		String action = request.getParameter("action");
		if ("manageNd".equalsIgnoreCase(action)) {
			ArrayList<ChucDanh> chucDanhList = (ArrayList<ChucDanh>) new ChucDanhDAO().getAllChucDanh();
			return new ModelAndView("them-nguoi-dung","chucDanhList", chucDanhList);
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
			
			ArrayList<NguoiDung> nguoiDungList =  (ArrayList<NguoiDung>) nguoiDungDAO.getAllNguoiDung(new ArrayList<String>());
			return new ModelAndView("them-nguoi-dung", "nguoiDungList", nguoiDungList);
			
		}
		if("manageNd".equalsIgnoreCase(action)) {
			ArrayList<ChucDanh> chucDanhList = (ArrayList<ChucDanh>) new ChucDanhDAO().getAllChucDanh();
			ArrayList<NguoiDung> nguoiDungList =  (ArrayList<NguoiDung>) nguoiDungDAO.getAllNguoiDung(new ArrayList<String>());
			request.setAttribute("chucDanhList", chucDanhList);
			request.setAttribute("nguoiDungList", nguoiDungList);
			return new ModelAndView("them-nguoi-dung", "nguoiDungList", nguoiDungList);
		}
		
		return new ModelAndView(siteMap.login);
	}
	
	@RequestMapping(value="/addNd", method=RequestMethod.GET, 
			produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	 public @ResponseBody String addNd(@RequestParam("msnv") String msnv, @RequestParam("chucdanh") String chucdanh, @RequestParam("matkhau") String matkhau
	 , @RequestParam("hoten") String hoten, @RequestParam("sdt") String sdt,@RequestParam("email") String email, @RequestParam("diachi") String diachi){
		NguoiDungDAO nguoiDungDAO = new NguoiDungDAO();
		CTNguoiDungDAO ctNguoiDungDAO = new CTNguoiDungDAO();
		String result = "";
		//System.out.println("MA: "+msnv);
		if((nguoiDungDAO.getNguoiDung(msnv)==null)&&(ctNguoiDungDAO.getCTNguoiDung(msnv)==null))
		{
			nguoiDungDAO.addNguoiDung(new NguoiDung(msnv, hoten, diachi, email, sdt, new ChucDanh(chucdanh)));
			ctNguoiDungDAO.addCTNguoiDung(new CTNguoiDung(msnv, StringUtil.encryptMD5(matkhau)));
			
//			System.out.println("success");
			result = "success";	
			
		}
		else
		{
//			
			result = "fail";
		}
		nguoiDungDAO.disconnect();
		ctNguoiDungDAO.disconnect();
			return JSonUtil.toJson(result);
			
	}
	
	@RequestMapping(value="/preUpdateNd", method=RequestMethod.GET, 
			produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	 public @ResponseBody String preUpdateNd(@RequestParam("clMa") String clMa) {
		ChatLuongDAO chatLuongDAO = new ChatLuongDAO();
		ChatLuong cl = chatLuongDAO.getChatLuong(clMa);
		chatLuongDAO.disconnect();
		return JSonUtil.toJson(cl);
	}
	
	
	@RequestMapping(value="/changePass", method=RequestMethod.GET, 
			produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody String changePass(@RequestParam("msnv") String msnv, @RequestParam("passOld") String passOld
			, @RequestParam("passNew") String passNew) {
		CTNguoiDungDAO ctNguoiDungDAO = new CTNguoiDungDAO();
		String result = "";
		if (ctNguoiDungDAO.login(msnv, StringUtil.encryptMD5(passOld))) {
			ctNguoiDungDAO.updateCTNguoiDung(new CTNguoiDung(msnv, StringUtil.encryptMD5(passNew)));
			result = "success";
		}
		else
		{
			result = "fail";
		}
		ctNguoiDungDAO.disconnect();
		return JSonUtil.toJson(result);
	}
	@RequestMapping(value="/logout")
	public void logout(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		session.removeAttribute("nguoiDung");
		response.sendRedirect("login.jsp");
	}
	
	@RequestMapping("/login")
	public ModelAndView login (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		String msnv = request.getParameter("msnv");
		String matKhau = request.getParameter("matkhau");
		CTNguoiDungDAO ctndDAO = new CTNguoiDungDAO();
		NguoiDungDAO ndDAO = new NguoiDungDAO();
		boolean check = ctndDAO.login(msnv, StringUtil.encryptMD5(matKhau));
		ctndDAO.disconnect();
		if (check) {
			NguoiDung nguoiDung =  ndDAO. getNguoiDung(msnv);
			session.setAttribute("nguoiDung", nguoiDung);
//			int index = siteMap.cvManage.lastIndexOf("/");
//    		String url = siteMap.cvManage.substring(index);
			String forward = "index";
//			String url = (String) session.getAttribute("url");
//			if (url != null) {
//				int index = url.lastIndexOf("/");
//				String page = url.substring(index);
//				forward = page;
//			}
			return new ModelAndView(forward);
//			ctndDAO.disconnect();
//			ndDAO.disconnect();
//			return new ModelAndView("index");
		} else {
			return new ModelAndView("login", "status", "fail");
		}
		
	}
	
	/*
	@RequestMapping(value="/login", method=RequestMethod.POST) 
//			produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody String login(@RequestParam("msnv") String msnv, @RequestParam("matkhau") String matkhau)
	{
//		HttpSession session =  
		String result = "";
		if (new CTNguoiDungDAO().login(msnv, StringUtil.encryptMD5(matkhau))) {
			result = "success";
			HttpServletResponse response;
//			response.sendRedirect("home.jsp");
		}
		else {
			result = "fail";
		}
		return JSonUtil.toJson(result);
	}
	*/
	@RequestMapping("lockNguoiDung")
	public ModelAndView lockNguoiDung(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		NguoiDungDAO nguoiDungDAO = new NguoiDungDAO();
		ArrayList<String> ignoreList = new ArrayList<String>();
		String adminMa = context.getInitParameter("adminMa");
		NguoiDungDAO ndDAO = new NguoiDungDAO();
		ignoreList.add(adminMa);
		ArrayList<NguoiDung> nguoiDungList =  (ArrayList<NguoiDung>) nguoiDungDAO.getAllNguoiDung(ignoreList);
	
		request.setAttribute("nguoiDungList", nguoiDungList);
		nguoiDungDAO.disconnect();
		return new ModelAndView(siteMap.lockNguoiDungPage, "nguoiDungList", nguoiDungList);
	}
	@RequestMapping("updateNguoiDung")
	public ModelAndView updateNguoiDung(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		ChucDanhDAO chucDanhDAO = new ChucDanhDAO();
		ArrayList<String> ignoreList = new ArrayList<String>();
		String adminMa = context.getInitParameter("adminMa");
		NguoiDungDAO ndDAO = new NguoiDungDAO();
		ignoreList.add(adminMa);
		ArrayList<ChucDanh> chucDanhList = (ArrayList<ChucDanh>) chucDanhDAO.getAllChucDanh();
		request.setAttribute("chucDanhList", chucDanhList);
		chucDanhDAO.disconnect();
		return new ModelAndView(siteMap.updateNguoiDungPage, "chucDanhList", chucDanhList);
	}
	
	@RequestMapping(value="/loadHoten", method=RequestMethod.GET, 
			produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody String loadHoten(@RequestParam("msnv") String msnv) {
		NguoiDungDAO nguoiDungDAO = new NguoiDungDAO();
		NguoiDung nguoiDung =  nguoiDungDAO.getNguoiDung(msnv);
		nguoiDungDAO.disconnect();
		String hoTen=nguoiDung.getHoTen();
		return JSonUtil.toJson(hoTen);
	}
}
