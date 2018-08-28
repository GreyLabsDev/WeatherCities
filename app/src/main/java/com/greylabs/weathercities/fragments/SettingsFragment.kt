package com.greylabs.weathercities.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.greylabs.weathercities.DataChangeListener
import com.greylabs.weathercities.R
import com.greylabs.weathercities.adapters.CitiesAdapter
import com.greylabs.weathercities.dbtools.DBController
import com.greylabs.weathercities.models.CityModel
import com.greylabs.weathercities.utils.SnacksMachineClass
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.f_settings.*
import javax.inject.Inject

class SettingsFragment : DaggerFragment(), DataChangeListener {

	@Inject
	lateinit var snacks : SnacksMachineClass

	override fun onDataChanged() {
		rvCitiesList.post {
			rvCitiesList.adapter.notifyDataSetChanged()
		}
	}
	
	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
		return inflater.inflate(R.layout.f_settings, container, false)
	}
	
	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		
		Thread() {
			DBController.weatherDataBase?.cityModelDAO()?.getAllCities()?.let {
				if (!it.isEmpty()) {
					rvCitiesList.post {
						var adapter = CitiesAdapter(it as ArrayList<CityModel>, context!!)
						adapter.setDataChangeListener(this)
						rvCitiesList.layoutManager = LinearLayoutManager(context)
						rvCitiesList.adapter = adapter
					}
				}
			}
		}.start()
		
		fabAddCity.setOnClickListener {
			FragmentsController.setEditorFlag(EditorFlag.CREATE)
			FragmentsController.showFragment(FragmentType.EDITOR)
		}
	}
	
	companion object {
		@JvmStatic
		fun newInstance() =
				SettingsFragment().apply {
					arguments = Bundle().apply {}
				}
	}
}