package com.example.themoviedb.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.themoviedb.model.MovieEntity

@Database(entities = [MovieEntity::class], version = 1, exportSchema = false)
abstract class MovieDatabase: RoomDatabase(){
    abstract fun movieDao(): MovieDao
}
