package com.fivehundredpx.showcase.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import com.fivehundredpx.showcase.Settings;
import com.fivehundredpx.showcase.data.NetworkState;
import com.fivehundredpx.showcase.data.PhotosDataFactory;
import com.fivehundredpx.showcase.model.Photo;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class PhotoViewModel extends ViewModel {

    private Executor executor;
    private LiveData<NetworkState> networkState;
    private LiveData<PagedList<Photo>> photoLiveData;

    public PhotoViewModel() {

        executor = Executors.newFixedThreadPool(5);
        PhotosDataFactory dataFactory = new PhotosDataFactory();
        networkState = Transformations.switchMap(dataFactory.getMutableLiveData(), dataSource -> dataSource.getNetworkState());

        PagedList.Config pagedListConfig =
                new PagedList.Config.Builder()
                .setEnablePlaceholders(true)
                .setInitialLoadSizeHint(10)
                .setPageSize(Settings.PAGE_SIZE)
                .build();

        photoLiveData =
                new LivePagedListBuilder(dataFactory, pagedListConfig)
                .setFetchExecutor(executor)
                .build();
    }

    public LiveData<NetworkState> getNetworkState() {
        return networkState;
    }

    public LiveData<PagedList<Photo>> getPhotoLiveData() {
        return photoLiveData;
    }
}
