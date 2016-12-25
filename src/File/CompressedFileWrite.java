package File;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class CompressedFileWrite {

	/**
	 * 读入未压缩过的bitmap文件,
	 * 压缩bitmap并写入 文件中
	 * @param bitmapread   未压缩的bitmap文件位置
	 * @param bitmapwrite  压缩后的bitmap文件位置
	 * @throws IOException
	 */
	public void compressedFileWrite(String bitmapread, String bitmapwrite) throws IOException{
		
		File readfile = new File(bitmapread);
    	FileReader fr = new FileReader(readfile);
    	BufferedReader br = new BufferedReader(fr);
    	
    	File writefile = new File(bitmapwrite);
    	FileWriter fw = new FileWriter(writefile);
    	BufferedWriter bw = new BufferedWriter(fw);
    	
    	
    	String line = "";
    	
    	while ((line = br.readLine()) != null) {
    		String[] context = line.split(",");
    		String time = context[0];
    		String bitmapString = context[1];
    		String compressedString = getCompressedString(bitmapString); 
    		
    		String write = "";
    		write += time + "-->" + compressedString + "\n";
    		bw.write(write);
		}
    	bw.close();
	}
	/**
	 * 压缩bitmap方法
	 * @param bitmap
	 * @return
	 */
	public String getCompressedString(String bitmap)
	{
		char preChar = bitmap.charAt(0);

		String compressed = "";
		int count = 0;
		for (int i = 0; i < bitmap.length(); i++) {
			char temp = bitmap.charAt(i);
			if (preChar == temp) {
				++count;
			}else{
				compressed += count;
				compressed += ',';
				compressed += preChar;
				compressed += ':';

				preChar = temp;
				count = 1;
			}
			if (i == bitmap.length() - 1) {
				compressed += count;
				compressed += ',';
				compressed += temp;
			}
		}
		return compressed;
	}
	

}
