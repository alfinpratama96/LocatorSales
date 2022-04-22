package com.task.locatorptm.presentation.account;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.snackbar.Snackbar;
import com.task.locatorptm.R;
import com.task.locatorptm.data.utils.AppPreference;
import com.task.locatorptm.data.utils.AppUtil;
import com.task.locatorptm.databinding.FragmentAccountBinding;
import com.task.locatorptm.domain.usecase.auth.AuthUseCase;
import com.task.locatorptm.presentation.auth.login.LoginActivity;

import java.util.HashMap;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class AccountFragment extends Fragment implements AccountContract{

    private AppPreference preference;
    private AccountPresenter presenter;
    private FragmentAccountBinding binding;
    private final HashMap<String, String> map = new HashMap<>();

    @Inject
    AuthUseCase useCase;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        preference = new AppPreference(getActivity());
        presenter = new AccountPresenter(this, useCase);
        return inflater.inflate(R.layout.fragment_account, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding = FragmentAccountBinding.bind(view);
        binding.tvIdCard.setText(preference.getIdCard());
        binding.tvName.setText(preference.getName());
        binding.tvEmail.setText(preference.getEmail());
        Glide.with(binding.ivIdCard.getContext())
                .load(preference.getImg())
                .fitCenter()
                .apply(RequestOptions.bitmapTransform(new RoundedCorners(16)))
                .into(binding.ivIdCard);
        map.put("username", preference.getUsername());
        binding.btnLogout.setOnClickListener(btn -> {
            if (AppUtil.isNetworkAvailable(requireActivity())) {
                Thread thread = new Thread(() -> presenter.doLogout(map));
                thread.start();
            } else {
                errConn();
            }
        });
    }

    @Override
    public void showLoading() {
        requireActivity().runOnUiThread(() -> {
            binding.btnLogout.setVisibility(View.GONE);
            binding.pbLoadingAccount.setVisibility(View.VISIBLE);
        });
    }

    @Override
    public void hideLoading() {
        requireActivity().runOnUiThread(() -> {
            binding.btnLogout.setVisibility(View.VISIBLE);
            binding.pbLoadingAccount.setVisibility(View.GONE);
        });
    }

    @Override
    public void successLogout(String message) {
        requireActivity().runOnUiThread(() -> {
            hideLoading();
            preference.removeLogin();
            Intent i = new Intent(requireActivity(), LoginActivity.class);
            startActivity(i);
            requireActivity().finish();
        });
    }

    @Override
    public void showError(String message) {
        requireActivity().runOnUiThread(() -> {
            hideLoading();
            View parentLayout = requireActivity().findViewById(android.R.id.content);
            Snackbar.make(parentLayout, message, Snackbar.LENGTH_LONG)
                    .setBackgroundTint(ContextCompat.getColor(requireActivity(), R.color.red))
                    .show();
        });
    }

    private void errConn() {
        requireActivity().runOnUiThread(() -> {
            hideLoading();
            View parentLayout = requireActivity().findViewById(android.R.id.content);
            Snackbar.make(parentLayout, "No connection. Please try again", Snackbar.LENGTH_LONG)
                    .setBackgroundTint(ContextCompat.getColor(requireActivity(), R.color.red))
                    .setAction("Try Again", view -> {
                        Thread thread = new Thread(() ->
                                presenter.doLogout(map));
                        thread.start();
                    })
                    .show();
        });
    }
}