package com.fivehundredpx.showcase.data;

import android.accounts.NetworkErrorException;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.PageKeyedDataSource;

import com.fivehundredpx.showcase.model.Photo;
import com.fivehundredpx.showcase.services.PhotoService;

import org.json.JSONException;

import java.io.UnsupportedEncodingException;
import java.util.List;

public class PhotosDataSource extends PageKeyedDataSource<Integer, Photo> {


    private PhotoService photoService;

    private MutableLiveData networkState;

    private Integer currentPage = 1;

    public PhotosDataSource(){
        this.photoService = new PhotoService();

        networkState = new MutableLiveData();
    }

    public MutableLiveData getNetworkState() {
        return networkState;
    }

    @Override
    public void loadInitial(@NonNull final LoadInitialParams<Integer> params, @NonNull final LoadInitialCallback<Integer, Photo> callback) {
       networkState.postValue(NetworkState.LOADING);

        try {
            List<Photo> photos = photoService.getPhotosWithinRange(currentPage);
            callback.onResult(photos,  null, currentPage++);
            networkState.postValue(NetworkState.LOADED);
        } catch (UnsupportedEncodingException | JSONException | NetworkErrorException e) {
            e.printStackTrace();
            networkState.postValue(new NetworkState(NetworkState.Status.FAILED, e.getMessage()));
        }

    }

    @Override
    public void loadBefore(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, Photo> callback) {

    }

    @Override
    public void loadAfter(@NonNull LoadParams<Integer> params, @NonNull final LoadCallback<Integer, Photo> callback) {

            networkState.postValue(NetworkState.LOADING);
        try {
            List<Photo> photos = photoService.getPhotosWithinRange(currentPage);
            callback.onResult(photos, currentPage++);
            networkState.postValue(NetworkState.LOADED);
        } catch (UnsupportedEncodingException | JSONException | NetworkErrorException e) {
            e.printStackTrace();
            networkState.postValue(new NetworkState(NetworkState.Status.FAILED, e.getMessage()));
        }

    }

}
