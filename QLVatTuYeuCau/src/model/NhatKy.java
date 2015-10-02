/**
 * 
 */
package model;

import java.sql.Date;

/**
 * @author quoioln
 *
 */
public class NhatKy {
	private int nkId;
	private String msnv;
	private Date thoiGian;
	private String noiDung;
	
	/**
	 * @param nkId
	 * @param msnv
	 * @param cvId
	 * @param noiDung
	 */
	public NhatKy() {
		this.nkId = 0;
		this.msnv = "";
		this.thoiGian = new Date(1,1,115);
		this.noiDung = "";
	}
	
	/**
	 * @param msnv
	 * @param cvId
	 * @param noiDung
	 */
	public NhatKy(String msnv, Date thoiGian, String noiDung) {
		this.msnv = msnv;
		this.thoiGian = thoiGian;
		this.noiDung = noiDung;
	}
	
	/**
	 * @param nkId
	 * @param msnv
	 * @param cvId
	 * @param noiDung
	 */
	public NhatKy(int nkId, String msnv, Date thoiGian, String noiDung) {
		this.nkId = nkId;
		this.msnv = msnv;
		this.thoiGian = thoiGian;
		this.noiDung = noiDung;
	}

	public Date getThoiGian() {
		return thoiGian;
	}

	public void setThoiGian(Date thoiGian) {
		this.thoiGian = thoiGian;
	}

	/**
	 * @return the nkId
	 */
	public int getNkId() {
		return nkId;
	}

	/**
	 * @param nkId the nkId to set
	 */
	public void setNkId(int nkId) {
		this.nkId = nkId;
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
	 * @return the noiDung
	 */
	public String getNoiDung() {
		return noiDung;
	}

	/**
	 * @param noiDung the noiDung to set
	 */
	public void setNoiDung(String noiDung) {
		this.noiDung = noiDung;
	}
	
	
}
