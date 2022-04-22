package com.task.locatorptm.presentation.di;

import com.task.locatorptm.presentation.activity.create.CreateActionActivity;
import com.task.locatorptm.presentation.activity.create.CreateActionContract;
import com.task.locatorptm.presentation.activity.list.ActionListActivity;
import com.task.locatorptm.presentation.activity.list.ActionListContract;
import com.task.locatorptm.presentation.absence.create.CreateAbsenceActivity;
import com.task.locatorptm.presentation.absence.create.CreateAbsenceContract;
import com.task.locatorptm.presentation.absence.list.AbsenceListActivity;
import com.task.locatorptm.presentation.absence.list.AbsenceListContract;
import com.task.locatorptm.presentation.account.AccountContract;
import com.task.locatorptm.presentation.account.AccountFragment;
import com.task.locatorptm.presentation.auth.login.LoginActivity;
import com.task.locatorptm.presentation.auth.login.LoginContract;
import com.task.locatorptm.presentation.history.ActivityLogContract;
import com.task.locatorptm.presentation.history.ActivityLogFragment;
import com.task.locatorptm.presentation.history.pinlocation.GoogleMapsActivity;
import com.task.locatorptm.presentation.history.pinlocation.MapsContract;
import com.task.locatorptm.presentation.order.create.CreateOrderActivity;
import com.task.locatorptm.presentation.order.create.CreateOrderContract;
import com.task.locatorptm.presentation.order.list.OrderListActivity;
import com.task.locatorptm.presentation.order.list.OrderListContract;
import com.task.locatorptm.presentation.schedule.create.CreateScheduleActivity;
import com.task.locatorptm.presentation.schedule.create.CreateScheduleContract;
import com.task.locatorptm.presentation.schedule.list.ScheduleListActivity;
import com.task.locatorptm.presentation.schedule.list.ScheduleListContract;
import com.task.locatorptm.presentation.store.list.StoreListContract;
import com.task.locatorptm.presentation.store.list.StoreListFragment;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;

@Module
@InstallIn(SingletonComponent.class)
public interface ContractModule {

    @Provides
    @Singleton
    static ActivityLogContract provideActivityLogContract() {
        return new ActivityLogFragment();
    }

    @Provides
    @Singleton
    static LoginContract provideLoginContract() {
        return new LoginActivity();
    }

    @Provides
    @Singleton
    static AccountContract provideAccountContract() {
        return new AccountFragment();
    }

    @Provides
    @Singleton
    static OrderListContract provideOrderListContract() {
        return new OrderListActivity();
    }

    @Provides
    @Singleton
    static CreateOrderContract provideCreateOrderContract() {
        return new CreateOrderActivity();
    }

    @Provides
    @Singleton
    static MapsContract provideMapsContract() {
        return new GoogleMapsActivity();
    }

    @Provides
    @Singleton
    static StoreListContract provideStoreListContract() {
        return new StoreListFragment();
    }

    @Provides
    @Singleton
    static AbsenceListContract provideAbsenceListContract() {
        return new AbsenceListActivity();
    }

    @Provides
    @Singleton
    static CreateAbsenceContract provideCreateAbsenceContract() {
        return new CreateAbsenceActivity();
    }

    @Provides
    @Singleton
    static ActionListContract provideActionListContract() {
        return new ActionListActivity();
    }


    @Provides
    @Singleton
    static CreateActionContract provideCreateActionContract() {
        return new CreateActionActivity();
    }

    @Provides
    @Singleton
    static ScheduleListContract provideScheduleListContract() {
        return new ScheduleListActivity();
    }

    @Provides
    @Singleton
    static CreateScheduleContract provideCreateScheduleContract() {
        return new CreateScheduleActivity();
    }
}

