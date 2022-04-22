package com.task.locatorptm.presentation.di;

import com.task.locatorptm.data.repository.AppRepositoryImpl;
import com.task.locatorptm.data.repository.datasource.RemoteDataSource;
import com.task.locatorptm.domain.repository.AppRepository;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;

@Module
@InstallIn(SingletonComponent.class)
public class RepositoryModule {

    @Singleton
    @Provides
    public AppRepository provideAppRepository(RemoteDataSource remoteDataSource) {
        return new AppRepositoryImpl(remoteDataSource);
    }
}
