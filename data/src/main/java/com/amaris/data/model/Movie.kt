package com.amaris.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.amaris.data.local.entities.BaseEntity
import com.google.gson.annotations.SerializedName

/**
 * Immutable model class for a Movie.
 * Objects of this type are received from the OMDb API, therefore all the fields are annotated
 * with the serialized name.
 * This class also defines the Room movies table, where the movie [imdbId] is the primary key.
 */

@Entity(tableName = "movies")
data class Movie(
    @PrimaryKey @field:SerializedName("imdbID") val imdbId: String,
    @field:SerializedName("Title") val title: String,
    @field:SerializedName("Year") val year: String,
    @field:SerializedName("Type") val type: String,
    @field:SerializedName("Poster") val poster: String,
) : BaseEntity