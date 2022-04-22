package com.task.locatorptm.presentation.absence.list;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.AsyncListDiffer;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.task.locatorptm.data.models.absence.AbsenceData;
import com.task.locatorptm.data.utils.AppUtil;
import com.task.locatorptm.databinding.AbsenceItemBinding;
import com.task.locatorptm.presentation.absence.detail.DetailAbsenceActivity;

import java.io.IOException;
import java.util.List;

public class AbsenceListAdapter extends RecyclerView.Adapter<AbsenceListAdapter.ViewHolder> {

    private final DiffUtil.ItemCallback<AbsenceData> callback = new DiffUtil.ItemCallback<AbsenceData>() {
        @Override
        public boolean areItemsTheSame(@NonNull AbsenceData oldItem, @NonNull AbsenceData newItem) {
            return oldItem.getLatLng().equals(newItem.getLatLng());
        }

        @SuppressLint("DiffUtilEquals")
        @Override
        public boolean areContentsTheSame(@NonNull AbsenceData oldItem, @NonNull AbsenceData newItem) {
            return oldItem.equals(newItem);
        }
    };

    AsyncListDiffer<AbsenceData> differ = new AsyncListDiffer<>(this, callback);

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        AbsenceItemBinding view = AbsenceItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(view);
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
        private final AbsenceItemBinding binding;

        public ViewHolder(AbsenceItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(AbsenceData data) {
            final Geocoder geocoder = new Geocoder(binding.getRoot().getContext());
            try {
                String[] split = data.getLatLng().split(",");
                List<Address> addressList = geocoder.getFromLocation(
                        Double.parseDouble(split[0]), Double.parseDouble(split[1]), 5);
                binding.tvAddress.setText(addressList.get(0).getAddressLine(0));
                binding.tvAbsenceDate.setText(AppUtil.convertDateTime(data.getCreatedAt()));
            } catch (IOException e) {
                e.printStackTrace();
            }
            binding.getRoot().setOnClickListener(view -> {
                Intent i = new Intent(binding.getRoot().getContext(), DetailAbsenceActivity.class);
                i.putExtra(DetailAbsenceActivity.ABSENCE_DATA, data);
                binding.getRoot().getContext().startActivity(i);
            });
        }
    }
}
