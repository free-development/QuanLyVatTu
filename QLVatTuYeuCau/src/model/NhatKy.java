/**
 * 
 */
package model;

/**
 * @author quoioln
 *
 */
public class NhatKy {
	private int nkId;
	private String msnv;
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
		this.noiDung = "";
	}
	
	/**
	 * @param msnv
	 * @param cvId
	 * @param noiDung
	 */
	public NhatKy(String msnv, String noiDung) {
		this.msnv = msnv;
		this.noiDung = noiDung;
	}
	
	/**
	 * @param nkId
	 * @param msnv
	 * @param cvId
	 * @param noiDung
	 */
	public NhatKy(int nkId, String msnv, String noiDung) {
		this.nkId = nkId;
		this.msnv = msnv;
		this.noiDung = noiDung;
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
