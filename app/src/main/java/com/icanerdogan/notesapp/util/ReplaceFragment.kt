package com.icanerdogan.notesapp.util

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.icanerdogan.notesapp.R

class ReplaceFragment {
    companion object {
        fun replaceFragment(manager: FragmentManager, fragment: Fragment, istransition:Boolean){
            val fragmentTransition = manager.beginTransaction()

            if (istransition){
                fragmentTransition.setCustomAnimations(android.R.anim.slide_out_right,android.R.anim.slide_in_left)
            }
            fragmentTransition.add(R.id.frameLayout,fragment).addToBackStack(fragment.javaClass.simpleName).commit()
        }
    }
}