package com.fivehundredpx.showcase.services;

import android.accounts.NetworkErrorException;

import org.json.JSONException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import java.io.UnsupportedEncodingException;

import static org.junit.Assert.*;

@RunWith(RobolectricTestRunner.class)
public class PhotoServiceTest {

    @Test
    public void getPhotosWithinRange() throws UnsupportedEncodingException, JSONException, NetworkErrorException {

        PhotoService photoService = new PhotoService();

        assertNotNull(photoService.getPhotosWithinRange(5));
    }
}