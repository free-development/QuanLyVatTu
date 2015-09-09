/**
 * 
 */
package dao;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author quoioln
 *
 */
public class BackupDB {
	public boolean backupDataWithOutDatabase(String dumpExePath, String host, String port, String user, String password, String database, String backupPath) {
		boolean status = false;
		try {
			Process p = null;
			DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
			Date date = new Date();
			String filepath = "backup-" + database + "-" + host + "-(" + dateFormat.format(date) + ").sql";
			 
			String batchCommand = "";
			if (password != "") {
				//only backup the data not included create database
				batchCommand = dumpExePath + " -h " + host + " --port " + port + " -u" + user + " -p" + password + " " + database + " -r \"" + backupPath + "" + filepath + "\"";
			} else {
				batchCommand = dumpExePath + " -h " + host + " --port " + port + " -u " + user + " " + database + " -r \"" + backupPath + "" + filepath + "\"";
			}
			// 
			 System.out.println(batchCommand);
			Runtime runtime = Runtime.getRuntime();
			p = runtime.exec(batchCommand);
			int processComplete = p.waitFor();
			 
			if (processComplete == 0) {
				status = true;
			} else {
				status = false;
			}
			 
		} catch (IOException ioe) {
			System.out.println("loi file");
		} catch (Exception e) {
				
		}
		return status;
	}
	public boolean backupDataWithDatabase(String dumpExePath, String host, String port, String user, String password, String database, String backupPath) {
        boolean status = false;
        try {
            Process p = null;
 
            DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
            Date date = new Date();
            String filepath = "backup-" + database + "-" + host + "-(" + dateFormat.format(date) + ").sql";
 
            String batchCommand = "";
            if (password != "") {
                //Backup with database
                batchCommand = dumpExePath + " -h " + host + " --port " + port + " -u" + user + " -p" + password + " --add-drop-database -B " + database + " -r \"" + backupPath + "" + filepath + "\"";
            } else {
                batchCommand = dumpExePath + " -h " + host + " --port " + port + " -u " + user + " --add-drop-database -B " + database + " -r \"" + backupPath + "" + filepath + "\"";
            }
 
            Runtime runtime = Runtime.getRuntime();
            p = runtime.exec(batchCommand);
            int processComplete = p.waitFor();
            System.out.println(batchCommand);
            if (processComplete == 0) {
                status = true;
            } else {
                status = false;
            }
 
        } catch (IOException ioe) {
        	
        } catch (Exception e) {
        	
        }
        return status;
    }
}
