package com.hardwork.fg607.wordassistant.utils;

import android.os.Environment;

import java.io.File;
import java.util.Map;

/**
 * Created by fg607 on 16-1-6.
 */
public class StringUtils {

    public static String getStrFromMap(Map<String,String> map){

        if(map.size() > 0){

            StringBuffer sb = new StringBuffer();

            for(String key:map.keySet()){

                sb.append(key+"  "+map.get(key).replace(" ","").replace("\r\n","")+"\r\n");
            }


            return new String(sb.substring(0,sb.length()-2));

        }else {

            return new String("");
        }

    }

    public static String getSdcardDir(){

        return Environment.getExternalStorageDirectory().getAbsolutePath();
    }

    public static boolean isFileExist(String filePath){

        File file = new File(filePath);

        return  file.exists();
    }

    public static boolean hasSameDate(String url,String date){

        String[] dates = date.split("-");

        String year = dates[0];

        int indexYear = url.indexOf(year);

        String urlDate = url.substring(indexYear,indexYear+10);


        String[] urlDates = urlDate.split("-");

        if(urlDates[1].contains(dates[1])){

            return urlDates[2].contains(dates[2]);
        }

        return false;
    }
}
