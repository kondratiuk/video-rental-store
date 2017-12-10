/*
 * License header
 */
package info.kondratiuk.provider;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.net.URL;
import java.nio.charset.Charset;

/**
 * @author Oleksandr Kondratiuk
 */
public class JsonReader {

	public static JSONObject readJsonFromUrl(String url) {
		String jsonText = null;
		try {
			jsonText = getJson(url);
		} catch (IOException e) {
			e.printStackTrace();
		}
		JSONObject json = null;
		try {
			json = new JSONObject(jsonText);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return json;
	}
	
	public static JSONArray readJsonArrFromUrl(String url) {
		String jsonText = null;
		try {
			jsonText = getJson(url);
		} catch (IOException e) {
			e.printStackTrace();
		}
		JSONArray json = null;
		try {
			json = new JSONArray("[" + jsonText + "]");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return json;
	}

	private static String getJson(String url) throws IOException {
		InputStream is = new URL(url).openStream();
		BufferedReader rd = null;
		try {
			rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
			String jsonText = readAll(rd);
			return jsonText;
		} finally {
			is.close();
		}
	}

	private static String readAll(Reader rd) throws IOException {
		StringBuilder sb = new StringBuilder();
		int cp;
		while ((cp = rd.read()) != -1) {
			sb.append((char) cp);
		}
		return sb.toString();
	}

}
