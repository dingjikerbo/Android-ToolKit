package com.inuker.library;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * Created by liwentian on 2017/10/16.
 */

public class HttpUtils {

    private static OkHttpClient sClient;

    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    private static final MediaType IMAGE = MediaType.parse("image/png");

    private static OkHttpClient client() {
        if (sClient == null) {
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
            sClient = new OkHttpClient.Builder().addInterceptor(logging).build();
        }
        return sClient;
    }

    public static Response execute(String url, HashMap<String, String> params, HttpType type, File... files) {
        try {
            return executeSafe(url, params, type, files);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static Response executeSafe(String url, HashMap<String, String> params, HttpType type, File... files) throws IOException, JSONException {
        switch (type) {
            case GET:
                return get(url, params);

            case POST_FORM:
                return postForm(url, params);

            case POST_JSON:
                return postJson(url, params);

            case POST_MULTIPART:
                return postMultipart(url, params, files);

            default:
                return null;
        }
    }

    private static Response get(String url) throws IOException {
        return get(url, null);
    }

    private static Response get(String url, HashMap<String, String> params) throws IOException {
        HttpUrl.Builder builder = HttpUrl.parse(url).newBuilder();
        if (params != null) {
            for (Map.Entry<String, String> entry : params.entrySet()) {
                builder.addQueryParameter(entry.getKey(), entry.getValue());
            }
        }
        Request request = new Request.Builder().url(builder.build()).build();
        return client().newCall(request).execute();
    }

    private static Response postForm(String url, HashMap<String, String> params) throws IOException {
        FormBody.Builder builder = new FormBody.Builder();
        if (params != null) {
            for (Map.Entry<String, String> entry : params.entrySet()) {
                builder.add(entry.getKey(), entry.getValue());
            }
        }
        Request request = new Request.Builder()
                .post(builder.build())
                .url(url)
                .build();

        return client().newCall(request).execute();
    }

    private static Response postJson(String url, HashMap<String, String> params) throws JSONException, IOException {
        JSONObject jsonObject = new JSONObject();
        if (params != null) {
            for (Map.Entry<String, String> entry : params.entrySet()) {
                jsonObject.put(entry.getKey(), entry.getValue());
            }
        }
        RequestBody body = RequestBody.create(JSON, jsonObject.toString());
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();

        return client().newCall(request).execute();
    }

    private static Response postMultipart(String url, HashMap<String, String> params, File[] files) throws IOException {
        MultipartBody.Builder builder = new MultipartBody.Builder();
        builder.setType(MultipartBody.FORM);

        if (params != null) {
            for (Map.Entry<String, String> entry : params.entrySet()) {
                builder.addFormDataPart(entry.getKey(), entry.getValue());
            }
        }

        for (File file : files) {
            builder.addFormDataPart(System.currentTimeMillis() + "", file.getName(), RequestBody.create(IMAGE, file));
        }

        RequestBody body = builder.build();
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();

        return client().newCall(request).execute();
    }
}
