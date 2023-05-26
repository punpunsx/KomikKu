package com.komikatow.komiku.ui.activityes

import android.os.Bundle
import android.view.LayoutInflater
import com.komikatow.komiku.databinding.ActivityAboutBinding

class AboutDevActivity : BaseActivity<ActivityAboutBinding> (){


    override fun createBinding(layoutInflater: LayoutInflater?): ActivityAboutBinding {
       return ActivityAboutBinding.inflate(layoutInflater!!)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
}