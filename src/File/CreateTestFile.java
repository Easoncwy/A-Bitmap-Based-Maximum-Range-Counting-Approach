package File;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
/**
 * Created by eason on 2017/1/4.
 */
public class CreateTestFile {


    public CreateTestFile(String file) throws IOException{
        File f = new File(file);
        FileWriter fw = new FileWriter(f);
        BufferedWriter bw = new BufferedWriter(fw);

        String s1 = "u000000-->[2013-07-01 08:00:00,2013-07-01 09:00:00]\n";
        String s2 = "u000000-->[2013-07-01 13:00:00,2013-07-01 15:00:00]\n";
        String s3 = "u000001-->[2013-07-01 08:00:00,2013-07-01 10:00:00]\n";
        String s4 = "u000001-->[2013-07-01 14:00:00,2013-07-01 15:00:00]\n";
        String s5 = "u000002-->[2013-07-01 08:00:00,2013-07-01 10:00:00]\n";

        bw.write(s1);
        bw.write(s2);
        bw.write(s3);
        bw.write(s4);
        bw.write(s5);

        bw.close();
    }
}
