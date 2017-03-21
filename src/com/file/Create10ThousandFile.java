package com.file;

import com.method.Time;

import java.io.*;

/**
 * Created by eason on 2017/1/6.
 */
public class Create10ThousandFile {

    public Create10ThousandFile(String input, String output, int duration) throws Exception{
        Time TIME = new Time();
        long startTIME = 0;
        long endTIME = 0;

        File f = new File(input);
        FileReader fr = new FileReader(f);
        BufferedReader br = new BufferedReader(fr);

        File writeFile = new File(output);
        FileWriter fileWriter = new FileWriter(writeFile);
        BufferedWriter bw = new BufferedWriter(fileWriter);

        String line = "";
        int count = 0;

        while ( (line = br.readLine()) != null){
            String[] context = line.split("-->");
            String user = context[0];
            String[] time = context[1].split(",");
            String start = time[0].substring(1);
            String end = time[1].substring(0, time[1].length()-1);



            startTIME = TIME.uniformTime(start);
            endTIME = TIME.uniformTime(end);

            if( (endTIME - startTIME) >= duration ){
                if (count < 100000) {

                    String write = "";
                    write += user + "-->" + "[" + start + "," + end + "]" + "\n";
                    bw.write(write);
                    ++count;
                }else {
                    break;
                }
            }

        }
        bw.close();

        System.out.println("共切出"+  count + "条记录");

    }


}
