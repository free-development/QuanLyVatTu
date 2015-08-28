/**
 * 
 */
package test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import dao.FileDAO;

/**
 * @author quoioln
 *
 */
public class TestFile {
	public static void main(String[] args) {
		//System.out.println(new FileDAO().getByCongVanId(1).getDiaChi());
		byte[] b = new byte[600000];
		try {
			File file = new File("/FileUpload/File/bophan.js");
			//file.ge
			FileInputStream fi = new FileInputStream(file);
			fi.read(b);
			System.out.println(b);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
