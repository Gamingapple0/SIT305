package com.example.personalizedlearningexperiences;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

@Database(entities = {User.class}, version = 2)
@TypeConverters({Converters.class, QuestionConverter.class})
public abstract class UserDatabase extends RoomDatabase {
    public abstract UserDAO userDao();
}