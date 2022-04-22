package com.task.locatorptm.presentation.activity.list;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.AsyncListDiffer;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.task.locatorptm.data.models.activity.ActivityData;
import com.task.locatorptm.data.utils.AppUtil;
import com.task.locatorptm.databinding.ActivityItemBinding;
import com.task.locatorptm.presentation.activity.create.CreateActionActivity;

public class ActionListAdapter extends RecyclerView.Adapter<ActionListAdapter.ViewHolder>{

    private final DiffUtil.ItemCallback<ActivityData> callback = new DiffUtil.ItemCallback<ActivityData>() {
        @Override
        public boolean areItemsTheSame(@NonNull ActivityData oldItem, @NonNull ActivityData newItem) {
            return oldItem.getActivity().equals(newItem.getActivity());
        }

        @SuppressLint("DiffUtilEquals")
        @Override
        public boolean areContentsTheSame(@NonNull ActivityData oldItem, @NonNull ActivityData newItem) {
            return oldItem.equals(newItem);
        }
    };

    AsyncListDiffer<ActivityData> differ = new AsyncListDiffer<>(this, callback);

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ActivityItemBinding binding = ActivityItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(differ.getCurrentList().get(position));
    }

    @Override
    public int getItemCount() {
        return differ.getCurrentList().size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private final ActivityItemBinding binding;

        public ViewHolder(ActivityItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(ActivityData data) {
            binding.tvActivity.setText(data.getActivity());
            binding.tvActivityDate.setText(AppUtil.convertDateTime(data.getCreatedAt()));
            binding.getRoot().setOnClickListener(view -> {
                Intent i = new Intent(binding.getRoot().getContext(), CreateActionActivity.class);
                i.putExtra(CreateActionActivity.IS_UPDATE, true);
                i.putExtra(CreateActionActivity.STORE_ID, data.getStore().getId());
                i.putExtra(CreateActionActivity.ACTIVITY_DATA, data);
                binding.getRoot().getContext().startActivity(i);
            });
        }
    }

}
