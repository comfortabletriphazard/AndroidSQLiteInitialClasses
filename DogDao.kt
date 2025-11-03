package com.example.app

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface DogDao {
    @Query("SELECT * FROM dogs")
    fun getAllDogs(): List<Dog>

    @Insert
    fun insertDog(dog: Dog)
}
