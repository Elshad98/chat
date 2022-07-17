package com.example.chat.extensions

import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction

inline fun FragmentManager.inTransaction(
    func: FragmentTransaction.() -> FragmentTransaction
): Int {
    return beginTransaction()
        .func()
        .commit()
}