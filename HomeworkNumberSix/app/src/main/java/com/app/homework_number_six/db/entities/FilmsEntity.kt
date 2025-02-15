package com.app.homework_number_six.db.entities

import android.media.audiofx.AudioEffect.Descriptor
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "films",
    foreignKeys = [ForeignKey(
        entity = UserEntity::class,
        parentColumns = ["id"],
        childColumns = ["user_id"],
        onDelete = ForeignKey.CASCADE,
    )]
)
data class FilmsEntity (
    @PrimaryKey
    val id: String,
    @ColumnInfo(name = "user_id")
    val userId: String,
    @ColumnInfo(name = "film_name")
    val filmName: String,
    @ColumnInfo(name = "release_date")
    val releaseDate: String,
    @ColumnInfo(name = "rating")
    val rating: Float,
    @ColumnInfo(name = "descriptor")
    val descriptor: String
)