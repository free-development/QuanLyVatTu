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
import model.DonViTinh;
import model.NguoiDung;
import model.NoiSanXuat;
import model.VatTu;

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
import dao.NoiSanXuatDAO;
import dao.VatTuDAO;



@Controller
public class NdController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	int page = 1;
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
			ctNguoiDungDAO.addCTNguoiDung(new CTNguoiDung(msnv, StringUtil.encryptMD5(matkhau), 0));
			
			ArrayList<NguoiDung> nguoiDungList =  (ArrayList<NguoiDung>) nguoiDungDAO.getAllNguoiDung(new ArrayList<String>());
			return new ModelAndView("them-nguoi-dung", "nguoiDungList", nguoiDungList);
			
		}
		if("manageNd".equalsIgnoreCase(action)) {
			ArrayList<ChucDanh> chucDanhList = (ArrayList<ChucDanh>) new ChucDanhDAO().getAllChucDanh();
			ArrayList<NguoiDung> nguoiDungList =  (ArrayList<NguoiDung>) nguoiDungDAO.getAllNguoiDung(new ArrayList<String>());
			request.setAttribute("chucDanhList", chucDanhList);
			return new ModelAndView("them-nguoi-dung", "nguoiDungList", nguoiDungList);
		}
		nguoiDungDAO.disconnect();
		chucDanhDAO.disconnect();
		ctNguoiDungDAO.disconnect();
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
			ctNguoiDungDAO.addCTNguoiDung(new CTNguoiDung(msnv, StringUtil.encryptMD5(matkhau), 0));
			
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
	 public @ResponseBody String preUpdateNd(@RequestParam("msnv") String msnv) {
		//System.out.println(msnv);
		NguoiDungDAO nguoiDungDAO = new NguoiDungDAO();
		NguoiDung nd = nguoiDungDAO.getNguoiDung(msnv);
		nguoiDungDAO.disconnect();
		return JSonUtil.toJson(nd);
	}
	@RequestMapping(value="/updateNd", method=RequestMethod.GET, 
			produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	 public @ResponseBody String updateNd(@RequestParam("msnv") String msnv, @RequestParam("hoten") String hoten, @RequestParam("chucdanh") String chucdanh, @RequestParam("email") String email, @RequestParam("diachi") String diachi, @RequestParam("sdt") String sdt) {
		System.out.println(msnv);
		System.out.println(hoten);
		System.out.println(chucdanh);
		System.out.println(email);
		System.out.println(diachi);
		System.out.println(sdt);
		NguoiDung nd = new NguoiDung(msnv, hoten,diachi,email,sdt,new ChucDanh(chucdanh));
		NguoiDungDAO nguoiDungDAO=new NguoiDungDAO();
		nguoiDungDAO.updateNguoiDung(nd);
		System.out.println(nd.getChucDanh().getCdTen());
		nguoiDungDAO.disconnect();
		return JSonUtil.toJson(nd);
	}
	
	@RequestMapping(value="/changePass", method=RequestMethod.GET, 
			produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody String changePass(@RequestParam("msnv") String msnv, @RequestParam("passOld") String passOld
			, @RequestParam("passNew") String passNew) {
		CTNguoiDungDAO ctNguoiDungDAO = new CTNguoiDungDAO();
		String result = "";
		int check = ctNguoiDungDAO.login(msnv, StringUtil.encryptMD5(passOld));
		if (check == 1) {
			ctNguoiDungDAO.updateCTNguoiDung(new CTNguoiDung(msnv, StringUtil.encryptMD5(passNew), 0));
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
	
	@RequestMapping(value="/lockNd", method=RequestMethod.GET, 
			produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	 public @ResponseBody String lockNd(@RequestParam("ndList") String ndList) {
		String[] str =ndList.split("\\, ");
		
		CTNguoiDungDAO ctndDAO =  new CTNguoiDungDAO();
		for(String msnv : str) {
			ctndDAO.lockNguoiDung(msnv);
		}
		ctndDAO.disconnect();
		return JSonUtil.toJson(ndList);
	}
	
	@RequestMapping("/login")
	public ModelAndView login (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		String msnv = request.getParameter("msnv");
		String matKhau = request.getParameter("matkhau");
		CTNguoiDungDAO ctndDAO = new CTNguoiDungDAO();
		
		int check = ctndDAO.login(msnv, StringUtil.encryptMD5(matKhau));
		ctndDAO.disconnect();
		if (check == 1) {
			NguoiDungDAO ndDAO = new NguoiDungDAO();
			NguoiDung nguoiDung =  ndDAO. getNguoiDung(msnv);
			session.setAttribute("nguoiDung", nguoiDung);
			String forward = "index";
			ndDAO.disconnect();
			ctndDAO.disconnect();
			return new ModelAndView(forward);
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
		CTNguoiDungDAO ctnguoiDungDAO = new CTNguoiDungDAO();
		NguoiDungDAO nguoiDungDAO = new NguoiDungDAO();
		long size = ctnguoiDungDAO.size();
		ArrayList<CTNguoiDung> ctnguoiDungList =  (ArrayList<CTNguoiDung>) ctnguoiDungDAO.limit(page - 1, 10);
		request.setAttribute("size", size);
		ArrayList<String> ignoreList = new ArrayList<String>();
		String adminMa = context.getInitParameter("adminMa");
		//NguoiDungDAO ndDAO = new NguoiDungDAO();
		ignoreList.add(adminMa);
		ArrayList<NguoiDung> nguoiDungList =  (ArrayList<NguoiDung>) nguoiDungDAO.getAllNguoiDung(ignoreList);
		request.setAttribute("ctnguoiDungList", ctnguoiDungList);
		request.setAttribute("nguoiDungList", nguoiDungList);
		ctnguoiDungDAO.disconnect();
		nguoiDungDAO.disconnect();
		return new ModelAndView(siteMap.lockNguoiDungPage, "ctnguoiDungList", ctnguoiDungList);
	}
	@RequestMapping("updateNguoiDung")
	public ModelAndView updateNguoiDung(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		CTNguoiDungDAO ctnguoiDungDAO = new CTNguoiDungDAO();
		NguoiDungDAO nguoiDungDAO = new NguoiDungDAO();
		ChucDanhDAO chucDanhDAO = new ChucDanhDAO();
		long size = ctnguoiDungDAO.size();
		ArrayList<CTNguoiDung> ctnguoiDungList =  (ArrayList<CTNguoiDung>) ctnguoiDungDAO.limit(page - 1, 10);
		request.setAttribute("size", size);
		ArrayList<String> ignoreList = new ArrayList<String>();
		String adminMa = context.getInitParameter("adminMa");
		ignoreList.add(adminMa);
		ArrayList<NguoiDung> nguoiDungList =  (ArrayList<NguoiDung>) nguoiDungDAO.getAllNguoiDung(ignoreList);
		ArrayList<ChucDanh> chucDanhList = (ArrayList<ChucDanh>) chucDanhDAO.getAllChucDanh();
		request.setAttribute("ctnguoiDungList", ctnguoiDungList);
		request.setAttribute("chucDanhList", chucDanhList);
		request.setAttribute("nguoiDungList", nguoiDungList);
		chucDanhDAO.disconnect();
		ctnguoiDungDAO.disconnect();
		nguoiDungDAO.disconnect();
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
	@RequestMapping(value="/loadPageNd", method=RequestMethod.GET, 
			produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	 public @ResponseBody String loadPageNd(@RequestParam("pageNumber") String pageNumber) {
		System.out.println("MA: " + pageNumber);
		CTNguoiDungDAO ctnguoiDungDAO = new CTNguoiDungDAO();
		int page = Integer.parseInt(pageNumber);
		ArrayList<CTNguoiDung> ndList = (ArrayList<CTNguoiDung>) ctnguoiDungDAO.limit((page -1 ) * 10, 10);
		ctnguoiDungDAO.disconnect();
			return JSonUtil.toJson(ndList);
	}
	@RequestMapping(value="/timKiemNguoidung", method=RequestMethod.GET, 
			produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody String timKiemNguoidung(@RequestParam("msnv") String msnv, @RequestParam("hoTen") String hoTen) {
		NguoiDungDAO nguoiDungDAO = new NguoiDungDAO();
		System.out.println("Ma goi qua " + msnv);
		System.out.println("Ten goi qua " + hoTen);
		if(msnv != ""){
			ArrayList<NguoiDung> ndList = (ArrayList<NguoiDung>) nguoiDungDAO.searchMsnv(msnv);
			nguoiDungDAO.disconnect();
			return JSonUtil.toJson(ndList);
		}
		else
		{
			ArrayList<NguoiDung> ndList = (ArrayList<NguoiDung>) nguoiDungDAO.searchHoten(hoTen);
			//System.out.println("Ten: "+vtTen);
			nguoiDungDAO.disconnect();
			return JSonUtil.toJson(ndList);
		}
	}
}
