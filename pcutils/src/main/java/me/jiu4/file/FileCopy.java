package me.jiu4.file;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @author djf
 * @version $Id: FileCopy.java v0.1, 2019/12/24 djf Exp $$
 */
public class FileCopy {

    public static void copy(File source){
        if(source == null){
            return;
        }
        File[] fileList = source.listFiles();
        if(fileList == null || fileList.length <= 0){
            return;
        }
        for(File file : fileList){
            if(file.exists()){
                if(file.isFile()){
                    try {
                        String dest = StringUtils.replaceOnceIgnoreCase(source.getAbsolutePath(),"C", "G");
                        IOUtils.copy(new FileInputStream(file), new FileOutputStream(dest));
                    } catch (IOException e) {
                        // TODO log
                        e.printStackTrace();
                    }
                }
                if(file.isDirectory()){
                    copy(file);
                }
            }

        }
    }

}
