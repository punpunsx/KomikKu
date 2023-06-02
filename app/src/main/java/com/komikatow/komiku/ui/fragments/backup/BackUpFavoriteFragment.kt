package com.komikatow.komiku.ui.fragments.backup

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.komikatow.komiku.databinding.FragmentFavoriteBinding
import com.komikatow.komiku.ui.fragments.BaseFragment

class BackUpFavoriteFragment : BaseFragment <FragmentFavoriteBinding>()  {

    override fun createBinding(inflater: LayoutInflater, container: ViewGroup): FragmentFavoriteBinding {
        return FragmentFavoriteBinding.inflate(layoutInflater)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.parentLottie.visibility = View.VISIBLE
    }
}