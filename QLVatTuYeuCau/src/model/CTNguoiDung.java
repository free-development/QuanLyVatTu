/**
 * 
 */
package model;

import java.io.Serializable;

/**
 * @author QUOI
 *
 */
public class CTNguoiDung implements Serializable{
	
	private String msnv;
	private String matKhau;
	public CTNguoiDung() {
		this.msnv = "";
		this.matKhau = "";
	}
	/**
	 * @param msnv
	 * @param matKhau
	 */
	public CTNguoiDung(String msnv, String matKhau) {
		this.msnv = msnv;
		this.matKhau = matKhau;
	}
	/**
	 * @return the msnv
	 */
	public String getMsnv() {
		return msnv;
	}
	/**
	 * @param msnv the msnv to set
	 */
	public void setMsnv(String msnv) {
		this.msnv = msnv;
	}
	/**
	 * @return the matKhau
	 */
	public String getMatKhau() {
		return matKhau;
	}
	/**
	 * @param matKhau the matKhau to set
	 */
	public void setMatKhau(String matKhau) {
		this.matKhau = matKhau;
	}
	
}
