package com.task.locatorptm.presentation.order.list;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.AsyncListDiffer;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.task.locatorptm.data.models.order.OrderData;
import com.task.locatorptm.data.utils.AppUtil;
import com.task.locatorptm.databinding.OrderItemBinding;
import com.task.locatorptm.presentation.order.create.CreateOrderActivity;

public class OrderListAdapter extends RecyclerView.Adapter<OrderListAdapter.ViewHolder> {

    private final DiffUtil.ItemCallback<OrderData> callback = new DiffUtil.ItemCallback<OrderData>() {
        @Override
        public boolean areItemsTheSame(@NonNull OrderData oldItem, @NonNull OrderData newItem) {
            return oldItem.getOrderName().equals(newItem.getOrderName());
        }

        @SuppressLint("DiffUtilEquals")
        @Override
        public boolean areContentsTheSame(@NonNull OrderData oldItem, @NonNull OrderData newItem) {
            return oldItem.equals(newItem);
        }
    };

    AsyncListDiffer<OrderData> differ = new AsyncListDiffer<>(this, callback);

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        OrderItemBinding binding = OrderItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
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

        private final OrderItemBinding binding;

        public ViewHolder(OrderItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(OrderData data) {
            binding.tvOrderName.setText(data.getOrderName());
            binding.tvQuantity.setText(String.valueOf(data.getQuantity()));
            binding.tvCreatedOrder.setText(AppUtil.convertDateTime(data.getCreatedAt()));
            binding.getRoot().setOnClickListener(root -> {
                Intent i = new Intent(binding.getRoot().getContext(), CreateOrderActivity.class);
                i.putExtra(CreateOrderActivity.EDIT_KEY, true);
                i.putExtra(CreateOrderActivity.PARCELABLE, data);
                i.putExtra(CreateOrderActivity.STORE_ID, data.getStore().getId());
                binding.getRoot().getContext().startActivity(i);
            });
        }
    }
}
