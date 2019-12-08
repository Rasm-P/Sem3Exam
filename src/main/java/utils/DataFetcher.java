/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

/**
 *
 * @author Simon Norup
 * @param <T>
 */
public class DataFetcher<T> {

    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    public static <T> void submitFetchForExecution(ExecutorService es, Queue<Future<Object>> futures, String url, Class<T> type) {
        futures.add(es.submit(() -> {
            return fetchAsObject(type, url);
        }));
    }

    public static <T> void submitFetchesForExecution(ExecutorService es, Queue<Future<Object>> futures, List<String> urls, Class<T> type) {
        urls.forEach((url) -> {
            submitFetchForExecution(es, futures, url, type);
        });
    }

    public static <T> T fetchAsObject(Class<T> type, String url) throws IOException {
        String json = fetchJsonFromUrl(url);
        return GSON.fromJson(json, type);
    }

    public static String fetchJsonFromUrl(String urlStr) throws MalformedURLException, IOException {
        URL url = new URL(urlStr);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("Accept", "application/json;charset=UTF-8");
        con.setRequestProperty("User-Agent", "server"); //remember if you are using SWAPI
        String jsonStr;
        try (Scanner scan = new Scanner(con.getInputStream())) {
            jsonStr = null;
            if (scan.hasNext()) {
                jsonStr = scan.nextLine();
            }
        }
        return jsonStr;
    }
}
