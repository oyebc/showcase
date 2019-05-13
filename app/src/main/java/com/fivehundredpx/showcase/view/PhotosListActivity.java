package com.fivehundredpx.showcase.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.fivehundredpx.showcase.R;
import com.fivehundredpx.showcase.databinding.PhotoActivityBinding;
import com.fivehundredpx.showcase.model.Photo;
import com.fivehundredpx.showcase.viewmodel.PhotoViewModel;
import com.google.gson.Gson;

public class PhotosListActivity extends AppCompatActivity {

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
        adapter = new PhotoListAdapter(this, Photo.DIFF_CALLBACK, photoClickListener);

        photoViewModel.getPhotoLiveData().observe(this, pagedList->{
            adapter.submitList(pagedList);
            adapter.notifyDataSetChanged();
        });

        photoViewModel.getNetworkState().observe(this, networkState -> {
            adapter.setNetworkState(networkState);
        });

        binding.listFeed.setAdapter(adapter);

    }

    final View.OnClickListener photoClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            RecyclerView.ViewHolder viewHolder = (RecyclerView.ViewHolder) view.getTag();
            int itemPosition = viewHolder.getAdapterPosition();
            Photo currentPhoto = adapter.getCurrentList().get(itemPosition);
            launchPhotoDetails(currentPhoto);
        }
    };

    private void launchPhotoDetails(Photo currentPhoto) {

        Intent intent = new Intent(this, PhotoDetailsActivity.class);
        Gson gson = new Gson();
        intent.putExtra(Photo.PHOTO_KEY, gson.toJson(currentPhoto));
        startActivity(intent);
    }
}
