package helpers;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

public class ParseCSV {

    public static HashMap<String, String> getCsvAsMap(String path) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(path));
        String line;
        HashMap<String,String> map = new HashMap<>();

        while((line=br.readLine())!=null){
            String[] str = line.split(",");
            for(int i=1;i<str.length;i++){
                String[] arr = str[i].split(":");
                map.put(arr[0], arr[1]);
            }
        }

        return map;
    }
}
