package com.fivehundredpx.showcase.data;

import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;

public class PhotosDataFactory extends DataSource.Factory {

    private MutableLiveData<PhotosDataSource> mutableLiveData;
    private PhotosDataSource photosDataSource;

    public PhotosDataFactory() {
        this.mutableLiveData = new MutableLiveData<PhotosDataSource>();
    }
    @Override
    public DataSource create() {
        photosDataSource = new PhotosDataSource();
        mutableLiveData.postValue(photosDataSource);
        return photosDataSource;
    }

    public MutableLiveData<PhotosDataSource> getMutableLiveData() {
        return mutableLiveData;
    }
}
