package com.fivehundredpx.showcase.view;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.fivehundredpx.showcase.R;
import com.fivehundredpx.showcase.databinding.PhotoActivityBinding;
import com.fivehundredpx.showcase.model.Photo;
import com.fivehundredpx.showcase.viewmodel.PhotoViewModel;

public class PhotosActivity extends AppCompatActivity {

    private PhotoListAdapter adapter;
    private PhotoViewModel photoViewModel;
    private PhotoActivityBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // setup layout for activity with databinding
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        //initialize viewmodel
        photoViewModel = new PhotoViewModel();

        binding.listFeed.setLayoutManager(new LinearLayoutManager(this));
        adapter = new PhotoListAdapter(this, Photo.DIFF_CALLBACK);

        photoViewModel.getPhotoLiveData().observe(this, pagedList->{
            adapter.submitList(pagedList);
            adapter.notifyDataSetChanged();
        });

        photoViewModel.getNetworkState().observe(this, networkState -> {
            adapter.setNetworkState(networkState);
        });

        binding.listFeed.setAdapter(adapter);
    }
}
