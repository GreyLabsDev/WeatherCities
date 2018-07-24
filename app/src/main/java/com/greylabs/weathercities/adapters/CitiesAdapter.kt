package com.greylabs.weathercities.adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.greylabs.weathercities.DataChangeListener
import com.greylabs.weathercities.R
import com.greylabs.weathercities.dbtools.DBController
import com.greylabs.weathercities.fragments.EditorFlag
import com.greylabs.weathercities.fragments.FragmentType
import com.greylabs.weathercities.fragments.FragmentsController
import com.greylabs.weathercities.models.CityModel
import kotlinx.android.synthetic.main.v_city_card.view.*

class CitiesAdapter(val inCities: ArrayList<CityModel>, val context: Context) : RecyclerView.Adapter<ViewHolder>() {
	private var dataChangeListener: DataChangeListener? = null
	
	fun setDataChangeListener(listener: DataChangeListener) {
		dataChangeListener = listener
	}
	
	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
		return ViewHolder(LayoutInflater.from(context).inflate(R.layout.v_city_card, parent, false))
	}
	
	override fun getItemCount(): Int {
		return inCities.size
	}
	
	override fun onBindViewHolder(holder: ViewHolder, position: Int) {
		holder.tvCityNameInCard.text = "${inCities[position].cityName} (${inCities[position].cityType})"
		holder.btnDelete.setOnClickListener {
			Thread() {
				DBController.weatherDataBase?.cityModelDAO()?.delete(inCities[position])
				inCities.remove(inCities[position])
				dataChangeListener?.onDataChanged()
				DBController.eraseLinkedSeasonsForCity(holder.tvCityNameInCard.text.toString())
			}.start()
		}
		
		holder.btnEdit.setOnClickListener {
			FragmentsController.setEditorFlag(EditorFlag.EDIT)
			FragmentsController.setEditableCity(inCities[position])
			FragmentsController.showFragment(FragmentType.EDITOR)
		}
	}
	
	override fun getItemId(position: Int): Long {
		return super.getItemId(position)
	}
	
}

class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
	val tvCityNameInCard = view.tvCityNameInCard
	var btnEdit = view.btnEdit
	var btnDelete = view.btnDelete
}