package com.cafeapp.di.orders_list

import com.cafeapp.data.orders.orders_list.repository.OrderListRepositoryImpl
import com.cafeapp.data.orders.remote.orders_list.repository.RemoteOrdersListRepository
import com.cafeapp.data.orders.remote.orders_list.repository.RemoteOrdersListRepositoryImpl
import com.cafeapp.domain.orders.orders_list.repository.OrdersListRepository
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class RepositoryModule {

    @Singleton
    @Provides
    fun provideRemoteOrdersListRepository(firestore: FirebaseFirestore): RemoteOrdersListRepository =
        RemoteOrdersListRepositoryImpl(firestore)

    @Singleton
    @Provides
    fun provideOrdersListRepository(remoteOrdersListRepository: RemoteOrdersListRepository): OrdersListRepository =
        OrderListRepositoryImpl(remoteOrdersListRepository)
}