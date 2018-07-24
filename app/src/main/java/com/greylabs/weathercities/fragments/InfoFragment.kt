package com.greylabs.weathercities.fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.greylabs.weathercities.R
import com.greylabs.weathercities.utils.FunAnimator
import kotlinx.android.synthetic.main.f_info.*

class InfoFragment : Fragment() {
	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
		return inflater.inflate(R.layout.f_info, container, false)
	}
	
	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		
		btnGoToGithub.setOnClickListener {
			var browserIntent = Intent(Intent.ACTION_VIEW)
			browserIntent.data = Uri.parse(getString(R.string.link_github))
			context?.startActivity(browserIntent)
		}
		
		var buttonAnimator = FunAnimator(btnGoToGithub)
		buttonAnimator.startAnimator()
	}
	
	companion object {
		fun newInstance() =
				InfoFragment().apply {
					arguments = Bundle().apply {}
				}
	}
}