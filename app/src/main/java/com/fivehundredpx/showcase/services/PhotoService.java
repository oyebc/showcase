package com.fivehundredpx.showcase.services;

import android.accounts.NetworkErrorException;
import android.util.Pair;

import com.fivehundredpx.showcase.Secret;
import com.fivehundredpx.showcase.model.Photo;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.fivehundredpx.showcase.Settings.API_VERSION;
import static com.fivehundredpx.showcase.Settings.BASE_URL;
import static com.fivehundredpx.showcase.Settings.PAGE_SIZE;
import static com.fivehundredpx.showcase.Settings.PATH_PHOTOS;

public class PhotoService {

    //TODO: Update method name and params
    public List<Photo> getPhotosWithinRange(int page) throws UnsupportedEncodingException, JSONException, NetworkErrorException {

        //construct url
        //https://api.500px.com/v1/photos?feature=popular&page={page}&rpp={number_of_results}
        StringBuilder stringBuilder = new StringBuilder(BASE_URL);
        stringBuilder.append("/")
                .append(API_VERSION)
                .append("/")
                .append(PATH_PHOTOS)
                .append("?feature=popular&page=")
                .append(page)
                .append("&rpp=")
                .append(PAGE_SIZE)
                .append("&consumer_key=")
                .append(Secret.API_KEY);
        String url = stringBuilder.toString();

        //add api key to request
        Map<String, String> requestProperties = new HashMap<>();
        requestProperties.put("Content-Type", "application/json; charset=UTF-8");


        NetworkManager.HttpResponse httpResponse = NetworkManager.makeHttpRequest(url, "GET", null, requestProperties);

        if(httpResponse != null && httpResponse.getResponseCode() == 200) {

            String jsonResult = new String(httpResponse.getResponseData(), "UTF-8");
            JSONObject resultJSON = new JSONObject(jsonResult);
            JSONArray photosJSONArray = resultJSON.getJSONArray("photos");

            Gson gson = new Gson();
            Type photoListType = new TypeToken<ArrayList<Photo>>(){}.getType();

            List<Photo> photos = gson.fromJson(photosJSONArray.toString(), photoListType); //TODO: not terribly optimal

            return photos;
        }
        throw new NetworkErrorException("Something went wrong fetching photos...");
    }
}
