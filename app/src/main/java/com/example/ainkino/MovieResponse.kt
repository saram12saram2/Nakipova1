package com.example.ainkino

import android.os.Parcel
import android.os.Parcelable

data class MovieResponse(val films: List<Movie>)

data class Movie(
    val filmId: Int,
    val nameRu: String?,
    val posterUrl: String?,
    val year: Int,
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString(),
        parcel.readString(),
        parcel.readInt()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(filmId)
        parcel.writeString(nameRu)
        parcel.writeString(posterUrl)
        parcel.writeInt(year)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Movie> {
        override fun createFromParcel(parcel: Parcel): Movie {
            return Movie(parcel)
        }

        override fun newArray(size: Int): Array<Movie?> {
            return arrayOfNulls(size)
        }
    }


}