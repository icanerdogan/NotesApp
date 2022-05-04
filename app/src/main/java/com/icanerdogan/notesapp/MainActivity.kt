package com.icanerdogan.notesapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.icanerdogan.notesapp.util.ReplaceFragment.Companion.replaceFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        replaceFragment(supportFragmentManager, HomeFragment.newInstance(), false)
    }

    override fun onBackPressed() {
        finish()
    }
}