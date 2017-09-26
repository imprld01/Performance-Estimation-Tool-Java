package dataBean;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

/**
 * 
 * @author bowns
 * @email bowns1688@hotmail.com
 * @date 2017.01.17
 *
 */
public class FileReader {
	
	private File targetFile;
	private boolean parse;
	private Scanner sc;
	private BufferedReader br;
	
	public FileReader(String filePath) throws IOException {
		
            this.targetFile = new File(filePath);
            this.parse = false;

            FileInputStream fis = new FileInputStream(targetFile);
            InputStreamReader isr = new InputStreamReader(fis, StandardCharsets.UTF_8);
            this.br = new BufferedReader(isr);
            this.sc = null;
	}
	
	public FileReader(String filePath, Charset cs) throws IOException {
		
            this.targetFile = new File(filePath);
            this.parse = false;

            FileInputStream fis = new FileInputStream(targetFile);
            InputStreamReader isr = new InputStreamReader(fis, cs);
            this.br = new BufferedReader(isr);
            this.sc = null;
	}
	
	public FileReader(File targetFile) throws IOException {
		
            this.targetFile = targetFile;
            this.parse = false;

            FileInputStream fis = new FileInputStream(targetFile);
            InputStreamReader isr = new InputStreamReader(fis, StandardCharsets.UTF_8);
            this.br = new BufferedReader(isr);
            this.sc = null;
	}
	
	public FileReader(File targetFile, Charset cs) throws IOException {
		
            this.targetFile = targetFile;
            this.parse = false;

            FileInputStream fis = new FileInputStream(targetFile);
            InputStreamReader isr = new InputStreamReader(fis, cs);
            this.br = new BufferedReader(isr);
            this.sc = null;
	}
	
	public FileReader(String filePath, boolean parse) throws IOException {
		
            this.targetFile = new File(filePath);
            this.parse = parse;

            if(this.parse){
                    this.br = null;
                    this.sc = new Scanner(this.targetFile, "UTF-8");
            }
            else{
                    FileInputStream fis = new FileInputStream(targetFile);
            InputStreamReader isr = new InputStreamReader(fis, StandardCharsets.UTF_8);
            this.br = new BufferedReader(isr);
            this.sc = null;
            }
	}
	
	public FileReader(String filePath, boolean parse, Charset cs) throws IOException {

            this.targetFile = new File(filePath);
            this.parse = parse;

            if(this.parse){
                    this.br = null;
                    this.sc = new Scanner(this.targetFile, cs.name());
            }
            else{
                    FileInputStream fis = new FileInputStream(targetFile);
            InputStreamReader isr = new InputStreamReader(fis, cs);
            this.br = new BufferedReader(isr);
            this.sc = null;
            }
	}
	
	public FileReader(File targetFile, boolean parse) throws IOException {
		
            this.targetFile = targetFile;
            this.parse = parse;

            if(this.parse){
                    this.br = null;
                    this.sc = new Scanner(this.targetFile, "UTF-8");
            }
            else{
                    FileInputStream fis = new FileInputStream(targetFile);
            InputStreamReader isr = new InputStreamReader(fis, StandardCharsets.UTF_8);
            this.br = new BufferedReader(isr);
            this.sc = null;
            }
	}
	
	public FileReader(File targetFile, boolean parse, Charset cs) throws IOException {
		
            this.targetFile = targetFile;
            this.parse = parse;

            if(this.parse){
                    this.br = null;
                    this.sc = new Scanner(this.targetFile, cs.name());
            }
            else{
                    FileInputStream fis = new FileInputStream(targetFile);
            InputStreamReader isr = new InputStreamReader(fis, cs);
            this.br = new BufferedReader(isr);
            this.sc = null;
            }
	}
	
	public File getFile() {
		
            return this.targetFile;
	}
	
	public String getFilePath() {
		
            return this.targetFile.getAbsolutePath();
	}
	
	public boolean scannerUsed() {
		
            return this.parse;
	}
	
	public boolean bufferedReaderUsed() {
		
            return (!this.parse);
	}
	
	public String readLine() throws IOException {
		
            String line = null;

            if(this.parse){
                if(sc.hasNextLine()) return sc.nextLine();
                else return line;
            }
            else{
                line = br.readLine();
                return line;
            }
	}
	
	public void close() throws IOException {
		
            if(this.parse) sc.close();
            else br.close();
	}
}