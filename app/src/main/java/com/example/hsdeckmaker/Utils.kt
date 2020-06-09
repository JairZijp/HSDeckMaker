package com.example.hsdeckmaker

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.example.hsdeckmaker.ui.fragments.CardFragment

fun FragmentManager.replaceFragment(fragment: Fragment) {
    beginTransaction().replace(R.id.frameLayout, fragment).commit()
}