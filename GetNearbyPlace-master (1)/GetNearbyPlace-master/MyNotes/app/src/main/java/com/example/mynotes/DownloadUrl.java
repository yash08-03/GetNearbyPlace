package com.example.mynotes;

import android.util.Log;
// API for sending log output.
import java.io.BufferedReader;
// https://www.javatpoint.com/java-bufferedreader-class
// Java BufferedReader is a public Java class that reads text, using buffering to enable large reads at a time for efficiency, storing what is not needed immediately in memory for later use. Buffered readers are preferable for more demanding tasks, such as file and streamed readers.
import java.io.IOException;
// This exception is related to Input and Output operations in the Java code. It happens when there is a failure during reading, writing, and searching file or directory operations. IOException is a checked exception. A checked exception is handled in the java code by the developer.
import java.io.InputStream;
// The Java InputStream class, java. io. InputStream , represents an ordered stream of bytes. In other words, you can read data from a Java InputStream as an ordered sequence of bytes. This is useful when reading data from a file, or received over the network.
import java.io.InputStreamReader;
// An InputStreamReader is a bridge from byte streams to character streams: It reads bytes and decodes them into characters using a specified charset .
import java.net.HttpURLConnection;
// HttpURLConnection class is an abstract class directly extending from URLConnection class. It includes all the functionality of its parent class with additional HTTP-specific features. HttpsURLConnection is another class that is used for the more secured HTTPS protocol. 
import java.net.MalformedURLException;
// MalformedURLException is thrown when the built-in URL class encounters an invalid URL; specifically, when the protocol that is provided is missing or invalid
import java.net.URL;
//Creates a URL object from the specified protocol , host , port number, and file . 
public class DownloadUrl {

    public String readUrl(String myurl) throws IOException {

        String data="";
        InputStream inputStream = null;
        HttpURLConnection httpURLConnection=null;

        try {
            URL url=new URL(myurl);
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.connect();

            inputStream = httpURLConnection.getInputStream();
            BufferedReader br =new BufferedReader(new InputStreamReader(inputStream));
            StringBuffer sb=new StringBuffer();

            String line="";
            while ((line = br.readLine())!=null) {
                sb.append(line);
            }

            data = sb.toString();
            br.close();
            inputStream.close();
            httpURLConnection.disconnect();
        }
        catch (MalformedURLException e) {
            Log.i("DownloadUrl","readUrl: "+e.getMessage());
        }

        catch (IOException e)
        {
            e.printStackTrace();
        }
//        finally {
//
//        }
        return data;
    }

}

























