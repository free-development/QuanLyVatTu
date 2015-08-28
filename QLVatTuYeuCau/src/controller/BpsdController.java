package controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.ChatLuong;
import model.DonVi;
import model.VaiTro;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import util.JSonUtil;

import com.sun.org.apache.xerces.internal.impl.dv.DVFactoryException;

import dao.ChatLuongDAO;
import dao.DonViDAO;
import dao.NoiSanXuatDAO;
import dao.VaiTroDAO;

@Controller
public class BpsdController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	int page = 1;
	@RequestMapping("/manageBpsd")
	public ModelAndView manageBpsd(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		DonViDAO donViDAO = new DonViDAO();
		
		String action = request.getParameter("action");
		if("AddBp".equalsIgnoreCase(action)) {
			String dvMa = request.getParameter("dvMa");
			String dvTen = request.getParameter("dvTen");
			String sdt = request.getParameter("sdt");
			String diaChi = request.getParameter("diaChi");
			String email = request.getParameter("email");
			donViDAO.addDonVi(new DonVi(dvMa, dvTen, sdt, diaChi, email,0 ));			
			ArrayList<DonVi> donViList =  (ArrayList<DonVi>) donViDAO.getAllDonVi();
			return new ModelAndView("danh-muc-bo-phan", "donViList", donViList);
		}
		
		if("deleteBpsd".equalsIgnoreCase(action)) {
			String[] idList = request.getParameterValues("dvMa");
			for(String s : idList) {
				if(s != null) {
					donViDAO.deleteDonVi(s);
				}
			}
			ArrayList<DonVi> donViList =  (ArrayList<DonVi>) donViDAO.getAllDonVi();
			return new ModelAndView("danh-muc-bo-phan", "donViList", donViList);
		}
		
		if("manageBpsd".equalsIgnoreCase(action)) {
			long size = donViDAO.size();
			ArrayList<DonVi> donViList =  (ArrayList<DonVi>) donViDAO.limit(page - 1, 10);
			request.setAttribute("size", size);
			return new ModelAndView("danh-muc-bo-phan", "donViList", donViList);
		}
		return new ModelAndView("login");
	}
	@RequestMapping(value="/preEditBp", method=RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	 public @ResponseBody String preEditBp(@RequestParam("dvMa") String dvMa) {
	//		System.out.println("****" + vtId + "****");
			DonViDAO donViDAO = new DonViDAO();
			DonVi dv = donViDAO.getDonVi(dvMa);
			return JSonUtil.toJson(dv);
		}
	@RequestMapping(value="/addBp", method=RequestMethod.GET, 
			produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	 public @ResponseBody String addBp(@RequestParam("dvMa") String dvMa, @RequestParam("dvTen") String dvTen, 
			 @RequestParam("sdt") String sdt, @RequestParam("diaChi") String diaChi, @RequestParam("email") String email ) {
		String result = "";
		System.out.println("MA: "+dvMa);
		if(new DonViDAO().getDonVi(dvMa)==null)
		{
			new DonViDAO().addDonVi(new DonVi(dvMa, dvTen, sdt, diaChi, email,0 ));
			System.out.println("success");
			result = "success";	
		}
		else
		{
			System.out.println("fail");
			result = "fail";
		}
			return JSonUtil.toJson(result);
//		DonVi dv = new DonVi(dvMa, dvTen,sdt, diaChi, email);
//		new DonViDAO().addDonVi(dv);
//		return JSonUtil.toJson(dv);
	}
	@RequestMapping(value="/updateBp", method=RequestMethod.GET, 
		produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	 public @ResponseBody String updateBp(@RequestParam("dvMaUpdate") String dvMaUpdate, @RequestParam("dvTenUpdate") String dvTenUpdate, 
			 @RequestParam("sdtUpdate") String sdtUpdate, @RequestParam("diaChiUpdate") String diaChiUpdate, @RequestParam("emailUpdate") String emailUpdate ) {

		DonVi dv = new DonVi(dvMaUpdate, dvTenUpdate, sdtUpdate, diaChiUpdate, emailUpdate,0);
		new DonViDAO().updateDonVi(dv);
		return JSonUtil.toJson(dv);
	}
	@RequestMapping(value="/deleteBp", method=RequestMethod.GET, 
			produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	 public @ResponseBody String deleteBp(@RequestParam("dvList") String dvList) {
		String[] str = dvList.split("\\, ");
		
		DonViDAO dvDAO =  new DonViDAO();
		for(String dvMa : str) {
			dvDAO.deleteDonVi(dvMa);
		}
		return JSonUtil.toJson(dvList);
	}
	
	@RequestMapping(value="/loadPageDv", method=RequestMethod.GET, 
			produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	 public @ResponseBody String loadPageDv(@RequestParam("pageNumber") String pageNumber) {
		String result = "";
		System.out.println("MA: " + pageNumber);
		DonViDAO dvDAO = new DonViDAO();
		int page = Integer.parseInt(pageNumber);
		ArrayList<DonVi> dvList = (ArrayList<DonVi>) dvDAO.limit((page -1 ) * 10, 10);
		

			return JSonUtil.toJson(dvList);
	}
}
