package controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.DonVi;
import model.NoiSanXuat;
import model.VaiTro;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import util.JSonUtil;
import dao.CTVatTuDAO;
import dao.DonViDAO;
import dao.NoiSanXuatDAO;
import dao.VaiTroDAO;


@Controller
public class VtController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	int page = 1;
	@RequestMapping("/manageVt")
	public ModelAndView manageVt(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		VaiTroDAO vaiTroDAO = new VaiTroDAO();
		
		String action = request.getParameter("action");
		if("AddVaiTro".equalsIgnoreCase(action)) {
			int vtId = Integer.parseInt(request.getParameter("vtId"));
			String vtTen = request.getParameter("vtTen");
			vaiTroDAO.addVaiTro(new VaiTro(vtId, vtTen,0));
			
			ArrayList<VaiTro> vaiTroList =  (ArrayList<VaiTro>) vaiTroDAO.getAllVaiTro();
			return new ModelAndView("danh-muc-vai-tro", "vaiTroList", vaiTroList);
		}
		
		if("deleteVaiTro".equalsIgnoreCase(action)) {
			String[] vtIdList = request.getParameterValues("vtId");
			for(String s : vtIdList) {
					vaiTroDAO.deleteVaiTro(s);
			}
			ArrayList<VaiTro> vaiTroList =  (ArrayList<VaiTro>) vaiTroDAO.getAllVaiTro();
			return new ModelAndView("danh-muc-vai-tro", "vaiTroList", vaiTroList);
		}
		if("manageVt".equalsIgnoreCase(action)) {
			long size = vaiTroDAO.size();
			ArrayList<VaiTro> vaiTroList =  (ArrayList<VaiTro>) vaiTroDAO.limit(page - 1, 10);
			request.setAttribute("size", size);
			return new ModelAndView("danh-muc-vai-tro", "vaiTroList", vaiTroList);
		}
		return new ModelAndView("login");
	}
	@RequestMapping(value="/preEditVt", method=RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	 public @ResponseBody String preEditVt(@RequestParam("vtId") String vtId) {
			System.out.println("****" + vtId + "****");
			VaiTroDAO vaiTroDAO = new VaiTroDAO();
			VaiTro vt = vaiTroDAO.getVaiTro(Integer.parseInt(vtId));
			return JSonUtil.toJson(vt);
			/*ArrayList<NoiSanXuat> nsxList = (ArrayList<NoiSanXuat>) new NoiSanXuatDAO().getAllNoiSanXuat();
			return toJson(nsxList);*/
		}
	@RequestMapping(value="/addVt", method=RequestMethod.GET, 
			produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	 public @ResponseBody String addVt(@RequestParam("vtId") int vtId, @RequestParam("vtTen") String vtTen) {
		String result = "";
		System.out.println("MA: "+vtId);
		if(new VaiTroDAO().getVaiTro(vtId)==null)
		{
			new VaiTroDAO().addVaiTro(new VaiTro(vtId, vtTen,0));;
			System.out.println("success");
			result = "success";
			
			
		}
		else
		{
			System.out.println("fail");
			result = "fail";
		}
			return JSonUtil.toJson(result);
//		System.out.println("****" + vtId + "****");
//
//		VaiTro vt = new VaiTro(vtId, vtTen);
//		new VaiTroDAO().addVaiTro(vt);
//		return JSonUtil.toJson(vt);	
	}
	@RequestMapping(value="/updateVt", method=RequestMethod.GET, 
		produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	 public @ResponseBody String updateVt(@RequestParam("vtIdUpdate") String vtIdUpdate, @RequestParam("vtTenUpdate") String vtTenUpdate) {

		VaiTro vt = new VaiTro(Integer.parseInt(vtIdUpdate), vtTenUpdate,0);
		new VaiTroDAO().updateVaiTro(vt);
		return JSonUtil.toJson(vt);
	}
	@RequestMapping(value="/deleteVt", method=RequestMethod.GET, 
			produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	 public @ResponseBody String deleteVt(@RequestParam("vtList") String vtList) {
		String[] str = vtList.split("\\, ");
		
		VaiTroDAO vtDAO =  new VaiTroDAO();
		for(String vtId : str) {
			vtDAO.deleteVaiTro(vtId);
		}
		return JSonUtil.toJson(vtList);
	}
	
	@RequestMapping(value="/loadPageVt", method=RequestMethod.GET, 
			produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	 public @ResponseBody String loadPageVt(@RequestParam("pageNumber") String pageNumber) {
		String result = "";
		System.out.println("MA: " + pageNumber);
		VaiTroDAO vtDAO = new VaiTroDAO();
		int page = Integer.parseInt(pageNumber);
		ArrayList<VaiTro> vtList = (ArrayList<VaiTro>) vtDAO.limit((page -1 ) * 10, 10);
		
		/*
		if(new NoiSanXuatDAO().getNoiSanXuat(nsxMa)==null)
		{
			new NoiSanXuatDAO().addNoiSanXuat(new NoiSanXuat(nsxMa, nsxTen,0));
			System.out.println("success");
			result = "success";	
		}
		else
		{
			System.out.println("fail");
			result = "fail";
		}
		*/
			return JSonUtil.toJson(vtList);
	}
}