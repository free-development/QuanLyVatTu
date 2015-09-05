/**
 * 
 */
package test;

import model.CTNguoiDung;
import model.ChucDanh;
import model.NguoiDung;
import dao.CTNguoiDungDAO;
import dao.ChucDanhDAO;
import dao.NguoiDungDAO;

/**
 * @author QUOI
 *
 */
public class TestNguoiDung {
	
	
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ChucDanh chucDanh = new ChucDanh("GD", "Giam Doc",0);
		NguoiDung nguoiDung = new NguoiDung("b1203959", "Vo Phu Quoi", "Can Tho", "quoipro94@gmail.com", "0979921380", chucDanh,0);
		
		new ChucDanhDAO().addChucDanh(chucDanh);
		new NguoiDungDAO().addNguoiDung(nguoiDung);
		new CTNguoiDungDAO().addCTNguoiDung(new CTNguoiDung(nguoiDung.getMsnv(),"1244788"));
	}
	
}
