package com.fivehundredpx.showcase.data;

import com.fivehundredpx.showcase.services.PhotoService;

public class PhotoRepository {

    private int currentPhotosPage = 1; //keep track of the current page loaded

    private PhotoService photoService;

    public PhotoRepository(PhotoService photoService){
        this.photoService = photoService;
    }
}
