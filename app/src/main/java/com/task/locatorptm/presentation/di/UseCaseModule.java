package com.task.locatorptm.presentation.di;

import com.task.locatorptm.domain.repository.AppRepository;
import com.task.locatorptm.domain.usecase.absence.AbsenceUseCase;
import com.task.locatorptm.domain.usecase.auth.AuthUseCase;
import com.task.locatorptm.domain.usecase.history.ActivitiesUseCase;
import com.task.locatorptm.domain.usecase.order.OrderUseCase;
import com.task.locatorptm.domain.usecase.schedule.ScheduleUseCase;
import com.task.locatorptm.domain.usecase.store.StoreUseCase;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;

@Module
@InstallIn(SingletonComponent.class)
public class UseCaseModule {

    @Singleton
    @Provides
    public ActivitiesUseCase provideGetActivitiesUseCase(AppRepository repository) {
        return new ActivitiesUseCase(repository);
    }

    @Provides
    @Singleton
    public AuthUseCase provideAuthUseCase(AppRepository repository) {
        return new AuthUseCase(repository);
    }

    @Provides
    @Singleton
    public OrderUseCase provideOrderUseCase(AppRepository repository) {
        return new OrderUseCase(repository);
    }

    @Singleton
    @Provides
    public AbsenceUseCase provideAbsenceUseCase(AppRepository repository) {
        return new AbsenceUseCase(repository);
    }

    @Singleton
    @Provides
    public ScheduleUseCase provideScheduleUseCase(AppRepository repository) {
        return new ScheduleUseCase(repository);
    }

    @Singleton
    @Provides
    public StoreUseCase provideStoreUseCase(AppRepository repository) {
        return new StoreUseCase(repository);
    }
}
