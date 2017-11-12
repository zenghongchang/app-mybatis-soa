package edu.hnust.application.common.util;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Dumper {
    public Dumper() {
    }
    
    public static void dump(Object object) {
        System.out.println("++++++++++++++++++++++++++++++++");
        try {
            Gson gson = new GsonBuilder().serializeSpecialFloatingPointValues().serializeNulls().setPrettyPrinting().create();
            System.out.println(gson.toJson(object));
        } catch (Exception e) {
            System.out.println(ToStringBuilder.reflectionToString(object, ToStringStyle.MULTI_LINE_STYLE));
        }
        System.out.println("++++++++++++++++++++++++++++++++");
    }
    
    public static String getStackTrace(Throwable aThrowable) {
        Writer result = new StringWriter();
        PrintWriter printWriter = new PrintWriter(result);
        aThrowable.printStackTrace(printWriter);
        
        return result.toString();
    }
}