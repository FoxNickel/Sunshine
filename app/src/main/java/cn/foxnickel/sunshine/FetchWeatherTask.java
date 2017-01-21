package cn.foxnickel.sunshine;

import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Administrator on 2017/1/20.
 */

public class FetchWeatherTask extends AsyncTask<String, Void, Void> {

    private final String LOG_TAG = FetchWeatherTask.class.getSimpleName();

    @Override
    protected Void doInBackground(String... params) {

        if (params.length == 0) {
            return null;
        }
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        String forecastJsonStr = null;//查询得到的字符串
        /*查询数据需要的参数*/
        String format = "json";
        String units = "metric";
        int numDays = 7;
        String appid = "97056b3b38944b922083c074a9e119da";
        try {
            /*构建uri时需要用到的参数*/
            final String FORECAST_BASE_URL = "http://api.openweathermap.org/data/2.5/forecast/daily?";
            final String QUERY_PARAM = "q";
            final String FORMAT_PARAM = "mode";
            final String UNITS_PARAM = "units";
            final String DAYS_PARAM = "cnt";
            final String APPID_PARAM = "APPID";
            /*构建Uri*/
            Uri builtUri = Uri.parse(FORECAST_BASE_URL).buildUpon()
                    .appendQueryParameter(QUERY_PARAM, params[0])
                    .appendQueryParameter(FORMAT_PARAM, format)
                    .appendQueryParameter(UNITS_PARAM, units)
                    .appendQueryParameter(DAYS_PARAM, Integer.toString(numDays))
                    .appendQueryParameter(APPID_PARAM, appid)
                    .build();
            URL url = new URL(builtUri.toString());
            Log.i(LOG_TAG, "Built URI " + builtUri.toString());
            /*开始Http请求*/
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            InputStream inputStream = urlConnection.getInputStream();
            StringBuffer buffer = new StringBuffer();
            if (inputStream == null) {
                return null;
            }
            reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {
                buffer.append(line + "\n");
            }

            if (buffer.length() == 0) {
                forecastJsonStr = null;
            }
            forecastJsonStr = buffer.toString();
            Log.i(LOG_TAG, forecastJsonStr);


            /*解析json数据*/
            JSONObject data = new JSONObject(forecastJsonStr);
            Log.i(LOG_TAG, "maxTemp:" + data.getJSONArray("list").
                    getJSONObject(0).
                    getJSONObject("temp").
                    getString("max"));
            Log.i(LOG_TAG, "minTemp:" + data.getJSONArray("list").
                    getJSONObject(0).
                    getJSONObject("temp").
                    getString("min"));
            Log.i(LOG_TAG, "weather:" + data.getJSONArray("list").
                    getJSONObject(0).
                    getJSONArray("weather").
                    getJSONObject(0).
                    getString("main"));
            Log.i(LOG_TAG, "weather description:" + data.getJSONArray("list").
                    getJSONObject(0).
                    getJSONArray("weather").
                    getJSONObject(0).
                    getString("description"));
        } catch (IOException e) {
            Log.e("ForecastFragment", "Error ", e);
            forecastJsonStr = null;
        } catch (JSONException e) {
            e.printStackTrace();
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (final IOException e) {
                    Log.e("ForecastFragment", "Error closing stream", e);
                }
            }
        }
        return null;
    }
}
