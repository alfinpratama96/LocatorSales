package com.task.locatorptm.presentation.di;

import com.task.locatorptm.data.api.ApiService;
import com.task.locatorptm.data.repository.datasource.RemoteDataSource;
import com.task.locatorptm.data.repository.datasourceimpl.RemoteDataSourceImpl;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;

@Module
@InstallIn(SingletonComponent.class)
public class RemoteDataModule {

    @Singleton
    @Provides
    public RemoteDataSource provideRemoteDataSource(ApiService service) {
        return new RemoteDataSourceImpl(service);
    }
}
