package com.fivehundredpx.showcase.model;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DiffUtil;

import com.google.gson.annotations.SerializedName;

public class Photo {

    private String id;
    private String name;
    private int width;
    private int height;
    @SerializedName("image_url")
    private String[] imageURL;
    private User user;

    public String[] getImageURL() {
        return imageURL;
    }

    public static DiffUtil.ItemCallback<Photo> DIFF_CALLBACK = new DiffUtil.ItemCallback<Photo>() {

        @Override
        public boolean areItemsTheSame(@NonNull Photo oldItem, @NonNull Photo newItem) {
            return oldItem.id.equalsIgnoreCase(newItem.id);
        }

        @Override
        public boolean areContentsTheSame(@NonNull Photo oldItem, @NonNull Photo newItem) {
            return oldItem.id.equalsIgnoreCase(newItem.id);
        }
    };


}
