package com.visitor.tengli.face.util;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class FileUtils {

    public static String getLine(String path) {

        BufferedReader br = null;
        String line = "";
        try {
             br = new BufferedReader(new FileReader(path));
            line = br.readLine();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {

        ;
        }

        if (br != null) {
            try {
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


        return  line;
    }
}
