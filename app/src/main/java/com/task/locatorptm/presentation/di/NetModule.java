package com.task.locatorptm.presentation.di;

import androidx.annotation.NonNull;

import com.task.locatorptm.BuildConfig;
import com.task.locatorptm.data.api.ApiService;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
@InstallIn(SingletonComponent.class)
public class NetModule {

    @Singleton
    @Provides
    public Retrofit provideRetrofit() {
        return new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BuildConfig.BASE_URL)
                .build();
    }

    @Singleton
    @Provides
    public ApiService provideApiServices(@NonNull Retrofit retrofit) {
        return retrofit.create(ApiService.class);
    }
}
