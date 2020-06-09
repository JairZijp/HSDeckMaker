package com.example.hsdeckmaker

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager

fun FragmentManager.replaceFragment(fragment: Fragment) {
    beginTransaction().replace(R.id.frameLayout, fragment).commit()
}