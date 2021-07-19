package com.example.movie_mvi_compose.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.movie_mvi_compose.data.db.entity.MovieEntity

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Provider


@Database(
    entities = [MovieEntity::class],
    version =1
)
abstract class MyDataBase : RoomDatabase() {
    abstract fun getMyDao(): MovieDao



//     class Callback @Inject constructor(
//         private val database: Provider<MyDataBase>,
//         @ApplicationScope private val applicationScope: CoroutineScope
//    ) : RoomDatabase.Callback() {
//
//        override fun onCreate(db: SupportSQLiteDatabase) {
//            super.onCreate(db)
//
//            val dao = database.get().checkPingDao()
//
//            applicationScope.launch {
//               // dao.insert(CheckStatusEntity("dsfs","sef","seesf"))
////            dao.insertNewMessage(MessageNotifyEntity("463456","Notify"))
//
//          //      dao.insert(CheckStatusEntity("null","null","null"))
////                dao.insert(MessageNotifyEntity("fgdfgdfg","Notify"))
////                dao.insert(MessageNotifyEntity("46356456456","Command"))
////                dao.insert(MessageNotifyEntity("fgdcffg456456dfg","Notify"))
////                dao.insert(MessageNotifyEntity("fgd46zdgfsf84fgdfg","Mac"))
////                dao.insert(MessageNotifyEntity("fgdsdfgfgdfg","Notify"))
////                dao.insert(MessageNotifyEntity("463456","Mac"))
////                dao.insert(MessageNotifyEntity("fgdcffgdfg","Notify"))
////                dao.insert(MessageNotifyEntity("fgd4684fgdfg","Command"))
//
//
//            }
//        }
//    }




}