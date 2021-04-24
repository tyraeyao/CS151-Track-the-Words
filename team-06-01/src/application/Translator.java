package application;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class Translator {

//    public static void main(String[] args) throws IOException {
//        String text = "Hello world!";
//        //Translated text: Hallo Welt!
//        System.out.println("Translated text: " + translate("en", "de", text));
//    }

    static String translate(String langFrom, String langTo, String words) throws IOException {
        // INSERT YOU URL HERE
    	// Using google's api to translate our words
    	// The translation is not perfect because special characters cannot be printed in JavaFX
        String urlStr = "https://script.google.com/macros/s/AKfycby69yJsk0Dl0eTmd1_ImwhhI_Beu-7dP-CDqyA9_eS4oDa90bATftxhy54St57krsk4YA/exec" +
                "?q=" + URLEncoder.encode(words, "UTF-8") +
                "&target=" + langTo +
                "&source=" + langFrom;
        URL url = new URL(urlStr);
        StringBuilder response = new StringBuilder();
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestProperty("User-Agent", "Mozilla/5.0");
        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
        return response.toString();
    }

}