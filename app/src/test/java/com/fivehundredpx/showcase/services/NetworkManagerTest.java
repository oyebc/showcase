package com.fivehundredpx.showcase.services;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.*;

import java.net.HttpURLConnection;


public class NetworkManagerTest {

    @Test
    public void makeHttpRequest() {

        NetworkManager.HttpResponse response =
                NetworkManager.makeHttpRequest("https://httpbin.org/get", NetworkManager.HTTP_GET, "", null);

        assertThat(response.getResponseCode(), equalTo(HttpURLConnection.HTTP_OK));
    }
}