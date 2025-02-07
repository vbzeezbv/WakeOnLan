package de.florianisme.wakeonlan.persistence;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import de.florianisme.wakeonlan.persistence.entities.DeviceEntity;

@Database(entities = {DeviceEntity.class}, version = 3, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public abstract DeviceDao deviceDao();
}