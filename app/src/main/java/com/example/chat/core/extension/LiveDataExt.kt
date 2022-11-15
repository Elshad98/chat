package com.example.chat.core.extension

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations

fun <T, R> LiveData<T>.map(
    mapper: (T) -> R
): LiveData<R> = Transformations.map(this, mapper)

fun <T, R> LiveData<T>.switchMap(
    mapper: (T) -> LiveData<R>
): LiveData<R> = Transformations.switchMap(this, mapper)