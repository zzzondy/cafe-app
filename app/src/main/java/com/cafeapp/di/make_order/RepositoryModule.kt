package com.cafeapp.di.make_order

import com.cafeapp.data.make_order.remote.repository.RemoteMakeOrderRepository
import com.cafeapp.data.make_order.remote.repository.RemoteMakeOrderRepositoryImpl
import com.cafeapp.data.make_order.repository.MakeOrderRepositoryImpl
import com.cafeapp.domain.make_order.repository.MakeOrderRepository
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@InstallIn(ViewModelComponent::class)
@Module
class RepositoryModule {

    @ViewModelScoped
    @Provides
    fun provideRemoteMakeOrderRepository(firestore: FirebaseFirestore): RemoteMakeOrderRepository =
        RemoteMakeOrderRepositoryImpl(firestore)

    @ViewModelScoped
    @Provides
    fun provideMakeOrderRepository(remoteMakeOrderRepository: RemoteMakeOrderRepository): MakeOrderRepository =
        MakeOrderRepositoryImpl(remoteMakeOrderRepository)
}