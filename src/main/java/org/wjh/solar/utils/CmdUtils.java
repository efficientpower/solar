package org.wjh.solar.utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * cmd工具
 * 
 * @author wangjihui
 *
 */
public class CmdUtils {

    public static String execS(String cmdLine) throws Exception {
        return execS(cmdLine, "");
    }

    public static String execS(String cmdLine, String split) throws Exception {
        Process proc  = Runtime.getRuntime().exec(cmdLine);
        BufferedReader in = new BufferedReader(new InputStreamReader(proc.getInputStream()));
        StringBuilder sb = new StringBuilder();
        String line;
        try {
            while ((line = in.readLine()) != null) {
                sb.append(line);
                sb.append(split);
            }
        } catch (Exception e) {
            throw e;
        } finally {
            in.close();
        }

        proc.waitFor();
        String date = sb.toString();
        return date;
    }

    public static Process execP(String cmdLine) throws Exception {
        Process proc  = Runtime.getRuntime().exec(cmdLine);
        proc.waitFor();
        if (proc.exitValue() != 0) {
            throw new Exception("cmd error!!");
        }
        return proc;
    }

    public static Matcher getMatcher(String regex, String source) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(source);
        matcher.find();
        return matcher;
    }
}
