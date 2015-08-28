package model;

import java.io.Serializable;


public class VTCongVan implements Serializable{

	private int cvId;

	private int vtId;

	private String msnv;
	
	public VTCongVan() {
		this.cvId = 0;
		this.vtId = 0;
		this.msnv = "";
	}

	/**
	 * @param cvId
	 * @param vtId
	 * @param msnv
	 */
	public VTCongVan(int cvId, int vtId, String msnv) {
		this.cvId = cvId;
		this.vtId = vtId;
		this.msnv = msnv;
	}

	/**
	 * @return the cvId
	 */
	public int getCvId() {
		return cvId;
	}

	/**
	 * @param cvId the cvId to set
	 */
	public void setCvId(int cvId) {
		this.cvId = cvId;
	}

	/**
	 * @return the vtId
	 */
	public int getVtId() {
		return vtId;
	}

	/**
	 * @param vtId the vtId to set
	 */
	public void setVtId(int vtId) {
		this.vtId = vtId;
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

	
}
