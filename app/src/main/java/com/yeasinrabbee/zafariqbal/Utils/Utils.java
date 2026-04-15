package com.yeasinrabbee.zafariqbal.Utils;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.TypedValue;
import android.widget.Toast;

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class Utils {


    public static String getJson(int type) {

        if (type == Constant.Constant1) {
            return "json1.json";
        }
        if (type == Constant.Constant2) {
            return "json2.json";

        }
        if (type == Constant.Constant3) {
            return "json3.json";
        }
        if (type == Constant.Constant4) {
            return "json4.json";
        }
        if (type == Constant.Constant5) {
            return "json5.json";

        }
        if (type == Constant.Constant6) {
            return "json6.json";
        }
        if (type == Constant.Constant7) {
            return "json7.json";
        }
        if (type == Constant.Constant8) {
            return "json8.json";
        }
        if (type == Constant.Constant9) {
            return "json9.json";
        }
        if (type == Constant.Constant10) {
            return "json10.json";
        }
        if (type == Constant.Constant11) {
            return "json11.json";
        }

        return null;
    }


    public static String conNum(String num) {

        String n = "";

        for (int i = 0; i < num.length(); i++) {

            n = n + bangla(num.charAt(i));
        }

        return n;

    }

    static String bangla(char c) {

        if (c == '1') return "১";
        if (c == '2') return "২";
        if (c == '3') return "৩";
        if (c == '4') return "৪";
        if (c == '5') return "৫";
        if (c == '6') return "৬";
        if (c == '7') return "৭";
        if (c == '8') return "৮";
        if (c == '9') return "৯";
        if (c == '0') return "০";

        return "0";

    }

    /* // Load json From Assets
  public static String loadJsonfromcachdir(Context context, String file_name) {
      String json = null;
      try {
          InputStream is = context.getAssets().open(file_name);
          int size = is.available();
          byte[] buffer = new byte[size];
          is.read(buffer);
          is.close();
          json = new String(buffer, "UTF-8");
      } catch (IOException ex) {
          ex.printStackTrace();
          return null;
      }
      return json;
  }
*/
    public  static void extract(Context context,OnComplete complete){

        File f = new File(context.getFilesDir(),"assets.zip");

        if (f.exists()){

            if (complete != null) complete.done("Already has been extracted");

        }else {

            try {




                Loader loader = new Loader(context);

                loader.show();


                AssetManager am = context.getAssets();
                InputStream inputStream = am.open("assets.zip");
                File file = createFileFromInputStream(context, inputStream);

                if (file != null) {

                    ZipFile zipFile = null;
                    zipFile = new ZipFile(file.getAbsolutePath());
                    if (zipFile.isEncrypted()) {
                        zipFile.setPassword("q:3+?m>,>G_~s"); // pass daw ai jaiga aitai password
                    }

                    zipFile.extractAll(context.getFilesDir().getAbsolutePath());

                    if (complete != null) complete.done("Extracted successfully");
                }

                loader.dismiss();


            } catch (ZipException | IOException e) {

                Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();

            }

        }

    }

    public static String loadJsonfromcachdir(Context context, String file_name) {





        File file = new File(context.getFilesDir(),file_name);
        FileReader fileReader = null;
        try {
            fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            StringBuilder stringBuilder = new StringBuilder();
            String line = bufferedReader.readLine();
            while (line != null){
                stringBuilder.append(line).append("\n");
                line = bufferedReader.readLine();
            }
            bufferedReader.close();

            String responce = stringBuilder.toString();
            return responce;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    private static File createFileFromInputStream(Context context, InputStream inputStream) {

        try{
            File f = new File(context.getFilesDir(),"assets.zip");
            OutputStream outputStream = new FileOutputStream(f);
            byte buffer[] = new byte[1024];
            int length = 0;

            while((length=inputStream.read(buffer)) > 0) {
                outputStream.write(buffer,0,length);
            }

            outputStream.close();
            inputStream.close();

            return f;
        }catch (IOException e) {
            //Logging exception
        }

        return null;
    }


    public static int dpToPx(float dp, Context context) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, context.getResources().getDisplayMetrics());
    }

    public interface  OnComplete{
        void done(String name);
    }



}
