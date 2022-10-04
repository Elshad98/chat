package com.example.chat.core.extension

import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction

inline fun FragmentManager.inTransaction(
    func: FragmentTransaction.() -> FragmentTransaction
): Int {
    return beginTransaction()
        .func()
        .commit()
}