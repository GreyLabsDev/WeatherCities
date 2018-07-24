package com.greylabs.weathercities.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import com.greylabs.weathercities.R
import com.greylabs.weathercities.dbtools.DBController
import com.greylabs.weathercities.models.CityModel
import com.greylabs.weathercities.models.CityType
import com.greylabs.weathercities.models.SeasonModel
import com.greylabs.weathercities.models.SeasonType
import com.greylabs.weathercities.utils.SnacksMachine
import kotlinx.android.synthetic.main.v_add_city.*

class CityEditorFragment : Fragment() {
	var isEditor = false
	var seasonsCache: ArrayList<SeasonModel> = ArrayList()
	var lastPosition = 0
	var currentPosition = 0
	
	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
		return inflater.inflate(R.layout.v_add_city, container, false)
	}
	
	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		
		var cityTypesAdapter: ArrayAdapter<String> = ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, arrayOf(CityType.Small.toString(), CityType.Medium.toString(), CityType.Big.toString()))
		cityTypesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
		spCityType.adapter = cityTypesAdapter
		spCityType.setOnTouchListener(View.OnTouchListener { p0, p1 ->
			if (p1?.action == MotionEvent.ACTION_DOWN) {
				updateCashe()
			}
			false
		})
		
		var seasonsAdapter: ArrayAdapter<String> = ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, arrayOf("Summer", "Autumn", "Winter", "Spring"))
		seasonsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
		spSeasonsEditor.adapter = seasonsAdapter
		
		when (FragmentsController.getEditorFlag()) {
			EditorFlag.CREATE -> {
				button.text = getText(R.string.editor_create)
				spSeasonsEditor.setOnTouchListener(View.OnTouchListener { p0, p1 ->
					if (p1?.action == MotionEvent.ACTION_DOWN) {
						updateCashe()
					}
					false
				})
				spSeasonsEditor.onItemSelectedListener = object : OnItemSelectedListener {
					override fun onItemSelected(parentView: AdapterView<*>, selectedItemView: View, position: Int, id: Long) {
						when (position) {
							0 -> {
								currentPosition = 0
								tvMonthOne.text = "June"
								tvMonthTwo.text = "July"
								tvMonthThree.text = "August"
								if (seasonsCache.size > 0) {
									var thisSeason = seasonsCache.find {
										it.seasonType == SeasonType.Summer.toString()
									}?.let {
										etMonthOne.setText("${it.seasonsTempByMonth?.get("June")}")
										etMonthTwo.setText("${it.seasonsTempByMonth?.get("July")}")
										etMonthThree.setText("${it.seasonsTempByMonth?.get("August")}")
									}
									if (thisSeason == null) {
										etMonthOne.setText("0")
										etMonthTwo.setText("0")
										etMonthThree.setText("0")
									}
								}
								lastPosition = 0
							}
							1 -> {
								currentPosition = 1
								tvMonthOne.text = "September"
								tvMonthTwo.text = "October"
								tvMonthThree.text = "November"
								if (seasonsCache.size > 0) {
									var thisSeason = seasonsCache.find {
										it.seasonType == SeasonType.Autumn.toString()
									}?.let {
										etMonthOne.setText("${it.seasonsTempByMonth?.get("September")}")
										etMonthTwo.setText("${it.seasonsTempByMonth?.get("October")}")
										etMonthThree.setText("${it.seasonsTempByMonth?.get("November")}")
									}
									if (thisSeason == null) {
										etMonthOne.setText("0")
										etMonthTwo.setText("0")
										etMonthThree.setText("0")
									}
								}
								lastPosition = 1
							}
							2 -> {
								currentPosition = 2
								tvMonthOne.text = "December"
								tvMonthTwo.text = "January"
								tvMonthThree.text = "February"
								if (seasonsCache.size > 0) {
									var thisSeason = seasonsCache.find {
										it.seasonType == SeasonType.Winter.toString()
									}?.let {
										etMonthOne.setText("${it.seasonsTempByMonth?.get("December")}")
										etMonthTwo.setText("${it.seasonsTempByMonth?.get("January")}")
										etMonthThree.setText("${it.seasonsTempByMonth?.get("February")}")
									}
									if (thisSeason == null) {
										etMonthOne.setText("0")
										etMonthTwo.setText("0")
										etMonthThree.setText("0")
									}
								}
								lastPosition = 2
							}
							3 -> {
								currentPosition = 3
								tvMonthOne.text = "March"
								tvMonthTwo.text = "April"
								tvMonthThree.text = "May"
								if (seasonsCache.size > 0) {
									var thisSeason = seasonsCache.find {
										it.seasonType == SeasonType.Spring.toString()
									}?.let {
										etMonthOne.setText("${it.seasonsTempByMonth?.get("March")}")
										etMonthTwo.setText("${it.seasonsTempByMonth?.get("April")}")
										etMonthThree.setText("${it.seasonsTempByMonth?.get("May")}")
									}
									if (thisSeason == null) {
										etMonthOne.setText("0")
										etMonthTwo.setText("0")
										etMonthThree.setText("0")
									}
								}
								lastPosition = 3
							}
						}
					}
					
					override fun onNothingSelected(parentView: AdapterView<*>) {
					}
				}
			}
			EditorFlag.EDIT -> {
				
				button.text = getText(R.string.editor_save_changes)
				
				FragmentsController.getEditableCity()?.let {
					loadSeasonsFromDBtoCache(it)
					etCityName.setText(it.cityName)
					when (it.cityType) {
						"Small" -> spCityType.setSelection(0)
						"Medium" -> spCityType.setSelection(1)
						"Big" -> spCityType.setSelection(2)
					}
				}
				
				spSeasonsEditor.setOnTouchListener(View.OnTouchListener { p0, p1 ->
					if (p1?.action == MotionEvent.ACTION_DOWN) {
						updateCashe()
					}
					false
				})
				spSeasonsEditor.onItemSelectedListener = object : OnItemSelectedListener {
					override fun onNothingSelected(p0: AdapterView<*>?) {
					}
					
					override fun onItemSelected(parentView: AdapterView<*>, selectedItemView: View, position: Int, id: Long) {
						FragmentsController.getEditableCity()?.let {
							when (position) {
								0 -> {
									currentPosition = 0
									tvMonthOne.text = "June"
									tvMonthTwo.text = "July"
									tvMonthThree.text = "August"
									if (seasonsCache.size > 0) {
										var thisSeason = seasonsCache.find {
											it.seasonType == SeasonType.Summer.toString()
										}?.let {
											etMonthOne.setText("${it.seasonsTempByMonth?.get("June")}")
											etMonthTwo.setText("${it.seasonsTempByMonth?.get("July")}")
											etMonthThree.setText("${it.seasonsTempByMonth?.get("August")}")
										}
										if (thisSeason == null) {
											clMainEditorContainer.post {
												etMonthOne.setText("0")
												etMonthTwo.setText("0")
												etMonthThree.setText("0")
											}
										}
									}
									lastPosition = 0
								}
								1 -> {
									currentPosition = 1
									tvMonthOne.text = "September"
									tvMonthTwo.text = "October"
									tvMonthThree.text = "November"
									if (seasonsCache.size > 0) {
										var thisSeason = seasonsCache.find {
											it.seasonType == SeasonType.Autumn.toString()
										}?.let {
											etMonthOne.setText("${it.seasonsTempByMonth?.get("September")}")
											etMonthTwo.setText("${it.seasonsTempByMonth?.get("October")}")
											etMonthThree.setText("${it.seasonsTempByMonth?.get("November")}")
										}
										if (thisSeason == null) {
											clMainEditorContainer.post {
												etMonthOne.setText("0")
												etMonthTwo.setText("0")
												etMonthThree.setText("0")
											}
										}
									}
									lastPosition = 1
								}
								2 -> {
									currentPosition = 2
									tvMonthOne.text = "December"
									tvMonthTwo.text = "January"
									tvMonthThree.text = "February"
									if (seasonsCache.size > 0) {
										var thisSeason = seasonsCache.find {
											it.seasonType == SeasonType.Winter.toString()
										}?.let {
											etMonthOne.setText("${it.seasonsTempByMonth?.get("December")}")
											etMonthTwo.setText("${it.seasonsTempByMonth?.get("January")}")
											etMonthThree.setText("${it.seasonsTempByMonth?.get("February")}")
										}
										if (thisSeason == null) {
											clMainEditorContainer.post {
												etMonthOne.setText("0")
												etMonthTwo.setText("0")
												etMonthThree.setText("0")
											}
										}
									}
									lastPosition = 2
								}
								3 -> {
									currentPosition = 3
									tvMonthOne.text = "March"
									tvMonthTwo.text = "April"
									tvMonthThree.text = "May"
									if (seasonsCache.size > 0) {
										var thisSeason = seasonsCache.find {
											it.seasonType == SeasonType.Spring.toString()
										}?.let {
											etMonthOne.setText("${it.seasonsTempByMonth?.get("March")}")
											etMonthTwo.setText("${it.seasonsTempByMonth?.get("April")}")
											etMonthThree.setText("${it.seasonsTempByMonth?.get("May")}")
										}
										if (thisSeason == null) {
											clMainEditorContainer.post {
												etMonthOne.setText("0")
												etMonthTwo.setText("0")
												etMonthThree.setText("0")
											}
										}
									}
									lastPosition = 3
								}
							}
						}
					}
				}
			}
		}
		
		button.setOnClickListener {
			Log.d("CityEditorFragment", "${etCityName.text} ${spCityType.getSelectedItem().toString()}")
			updateCashe()
			saveCityToDatabase()
		}
	}
	
	fun saveCityToDatabase() {
		when (FragmentsController.getEditorFlag()) {
			EditorFlag.EDIT -> {
				SnacksMachine.showSnackbar("Changes saved", "green")
				FragmentsController.showFragment(FragmentType.SETTINGS)
				var city = CityModel(etCityName.text.toString(), spCityType.getSelectedItem().toString())
				Thread() {
					if (city.cityName != "" && city.cityType != "") {
						DBController.weatherDataBase?.cityModelDAO()?.insertAll(city)
						saveSeasonsCacheToDatabase()
					} else {
						SnacksMachine.showSnackbar("Some city fields is empty!", "orange")
					}
				}.start()
			}
			EditorFlag.CREATE -> {
				var city = CityModel(etCityName.text.toString(), spCityType.getSelectedItem().toString())
				Thread() {
					if (city.cityName != "" && city.cityType != "") {
						DBController.weatherDataBase?.cityModelDAO()?.getCityByName(city.cityName)?.let {
							if (it.isNotEmpty()) {
								SnacksMachine.showSnackbar("City already exists!", "red")
							} else {
								Log.d("CityEditorFragment", "${city.cityName} ${city.cityType}")
								SnacksMachine.showSnackbar("New city added", "green")
								FragmentsController.showFragment(FragmentType.SETTINGS)
								DBController.weatherDataBase?.cityModelDAO()?.insertAll(city)
								saveSeasonsCacheToDatabase()
							}
						}
					} else {
						SnacksMachine.showSnackbar("Some city fields is empty!", "orange")
					}
				}.start()
			}
		}
	}
	
	fun updateCashe() {
		when (lastPosition) {
			0 -> {
				saveSeasonToCashe(SeasonType.Summer)
			}
			1 -> {
				saveSeasonToCashe(SeasonType.Autumn)
			}
			2 -> {
				saveSeasonToCashe(SeasonType.Winter)
			}
			3 -> {
				saveSeasonToCashe(SeasonType.Spring)
			}
		}
		
	}
	
	fun saveSeasonToCashe(seasonType: SeasonType) {
		
		var city = CityModel(etCityName.text.toString(), spCityType.getSelectedItem().toString())
		
		Thread() {
			if (city.cityName != "" && city.cityType != "") {
				var season = SeasonModel(seasonType.toString(), city.cityName)
				var tempsByMonth: LinkedHashMap<String, Int> = LinkedHashMap()
				
				if (etMonthOne.text.toString() != "") {
					tempsByMonth[tvMonthOne.text.toString()] = Integer.parseInt(etMonthOne.text.toString())
					Log.d("CityEditorFragment", "1. ${tvMonthOne.text.toString()} ${tempsByMonth[tvMonthOne.text.toString()]} ")
				} else {
					tempsByMonth[tvMonthOne.text.toString()] = 0
				}
				
				if (etMonthTwo.text.toString() != "") {
					tempsByMonth[tvMonthTwo.text.toString()] = Integer.parseInt(etMonthTwo.text.toString())
					Log.d("CityEditorFragment", "2. ${tvMonthTwo.text.toString()} ${tempsByMonth[tvMonthTwo.text.toString()]} ")
				} else {
					tempsByMonth[tvMonthTwo.text.toString()] = 0
				}
				
				if (etMonthThree.text.toString() != "") {
					tempsByMonth[tvMonthThree.text.toString()] = Integer.parseInt(etMonthThree.text.toString())
					Log.d("CityEditorFragment", "3. ${tvMonthThree.text.toString()} ${tempsByMonth[etMonthThree.text.toString()]} ")
				} else {
					tempsByMonth[tvMonthThree.text.toString()] = 0
				}
				
				season.seasonsTempByMonth = tempsByMonth
				var hasThisSeason = false
				seasonsCache.forEach {
					if (it.type == season.type) {
						hasThisSeason = true
					}
				}
				if (!hasThisSeason) {
					seasonsCache.add(season)
				} else {
					var seasonToReplace = seasonsCache.find {
						it.type == season.type
					}
					seasonsCache.remove(seasonToReplace)
					seasonsCache.add(season)
				}
				
				Log.d("CityEditorFragment", "${seasonsCache.size}")
				Log.d("CityEditorFragment", "${seasonsCache.last().seasonsTempByMonth}")
				Log.d("CityEditorFragment", "${seasonsCache.last().seasonType}")
			} else {
				SnacksMachine.showSnackbar("Some city fields is empty!", "orange")
			}
		}.start()
	}
	
	fun saveSeasonsCacheToDatabase() {
		Thread() {
			seasonsCache.forEach {
				DBController.weatherDataBase?.seasonModelDAO()?.insertAll(it)
			}
		}.start()
	}
	
	fun getCitySeasonMonthsByType(city: CityModel, type: SeasonType): Array<Int>? {
		DBController.weatherDataBase?.seasonModelDAO()?.getSeasonsForCity(city.cityName)?.let {
			if (it.isNotEmpty()) {
				it.forEach {
					if (it.type == type.toString()) {
						var outTemps: ArrayList<Int> = ArrayList()
						it.seasonsTempByMonth?.apply {
							forEach {
								outTemps.add(it.value)
							}
							return outTemps.toTypedArray()
						}
					}
				}
			}
		}
		return null
	}
	
	fun loadSeasonsFromDBtoCache(city: CityModel) {
		var seasons: ArrayList<SeasonType> = ArrayList()
		seasons.addAll(arrayOf(SeasonType.Summer, SeasonType.Autumn, SeasonType.Winter, SeasonType.Spring))
		seasonsCache.clear()
		Thread {
			DBController.weatherDataBase?.seasonModelDAO()?.getSeasonsForCity(city.cityName)?.let {
				it.forEach {
					seasonsCache.add(it)
				}
			}
		}.start()
	}
	
	companion object {
		@JvmStatic
		fun newInstance() =
				CityEditorFragment().apply {
					arguments = Bundle().apply {}
				}
	}
	
}