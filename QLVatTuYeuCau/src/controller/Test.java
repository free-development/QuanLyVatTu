/**
 * 
 */
package controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @author quoioln
 *
 */
public class Test {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
//		File file = new File
		FileOutputStream fileOut = new FileOutputStream("test.txt");
		File file = new File("test.txt");
//		file.
		
		System.out.println(file.getAbsolutePath());
		System.out.println(file.getParent());
		File file1 = new File("File1");
		System.out.println(file1.getAbsolutePath());
		if(file1.createNewFile())
			System.out.println("ok");
//		file1.
//		file.
	}

}
