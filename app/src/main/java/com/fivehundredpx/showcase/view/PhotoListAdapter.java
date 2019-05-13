package com.fivehundredpx.showcase.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.fivehundredpx.showcase.R;
import com.fivehundredpx.showcase.data.NetworkState;
import com.fivehundredpx.showcase.databinding.NetworkItemBinding;
import com.fivehundredpx.showcase.databinding.PhotoItemBinding;
import com.fivehundredpx.showcase.model.Photo;

public class PhotoListAdapter extends PagedListAdapter<Photo, RecyclerView.ViewHolder> {


    private static final int TYPE_PROGRESS = 0;
    private static final int TYPE_ITEM = 1;

    private Context context;
    private NetworkState networkState;
    private final View.OnClickListener photoClickListener;

    protected PhotoListAdapter(Context context, @NonNull DiffUtil.ItemCallback<Photo> diffCallback, View.OnClickListener photoClickListener) {
        super(diffCallback);
        this.context = context;
        this.photoClickListener = photoClickListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        if(viewType == TYPE_PROGRESS) {
            NetworkItemBinding headerBinding = NetworkItemBinding.inflate(layoutInflater, parent, false);
            NetworkStateItemViewHolder viewHolder = new NetworkStateItemViewHolder(headerBinding);
            return viewHolder;

        } else {
            PhotoItemBinding itemBinding = PhotoItemBinding.inflate(layoutInflater, parent, false);
            PhototItemViewHolder viewHolder = new PhototItemViewHolder(itemBinding);
            return viewHolder;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        if(holder instanceof  PhototItemViewHolder){
            ((PhototItemViewHolder)holder).bindTo(getItem(position));
        } else {
            ((NetworkStateItemViewHolder) holder).bindView(networkState);
        }

    }

    @Override
    public int getItemViewType(int position) {
        if (hasExtraRow() && position == getItemCount() - 1) {
            return TYPE_PROGRESS;
        } else {
            return TYPE_ITEM;
        }
    }

    public void setNetworkState(NetworkState newNetworkState) {
        NetworkState previousState = this.networkState;
        boolean wasPerformingLoading = hasExtraRow();
        this.networkState = newNetworkState;
        boolean isNowPerformingLoading = hasExtraRow();

        if (wasPerformingLoading != isNowPerformingLoading) {
            if (wasPerformingLoading) {
                notifyItemRemoved(getItemCount());
            } else {
                notifyItemInserted(getItemCount());
            }
        } else if (isNowPerformingLoading && previousState != newNetworkState) {
            notifyItemChanged(getItemCount() - 1);
        }
    }

    private boolean hasExtraRow() {
        return networkState != null && networkState != NetworkState.LOADED;
    }

    public class NetworkStateItemViewHolder extends RecyclerView.ViewHolder {

        private NetworkItemBinding binding;
        public NetworkStateItemViewHolder(NetworkItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bindView(NetworkState networkState) {
            if (networkState != null && networkState.getStatus() == NetworkState.Status.RUNNING) {
                binding.progressBar.setVisibility(View.VISIBLE);
            } else {
                binding.progressBar.setVisibility(View.GONE);
            }

            if (networkState != null && networkState.getStatus() == NetworkState.Status.FAILED) {
                binding.errorMsg.setVisibility(View.VISIBLE);
                binding.errorMsg.setText(networkState.getMsg());
            } else {
                binding.errorMsg.setVisibility(View.GONE);
            }
        }
    }

    public class PhototItemViewHolder extends RecyclerView.ViewHolder {


        private final PhotoItemBinding binding;

        public PhototItemViewHolder(@NonNull PhotoItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bindTo(Photo photo) {
            binding.itemImage.setVisibility(View.VISIBLE);
            binding.itemImage.setImageResource(R.drawable.ic_launcher_background);
            binding.getRoot().setOnClickListener(photoClickListener);
            binding.getRoot().setTag(this);

            Glide
                    .with(context)
                    .load(photo.getImageURL()[0])
                    .centerCrop()
                    .into(binding.itemImage);
        }
    }
}
