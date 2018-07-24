package com.greylabs.weathercities.fragments

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.transition.TransitionManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import com.greylabs.weathercities.R
import com.greylabs.weathercities.dbtools.DBController
import com.greylabs.weathercities.models.CityModel
import com.greylabs.weathercities.models.SeasonModel
import kotlinx.android.synthetic.main.f_weather.*
import kotlinx.android.synthetic.main.v_weather_card.view.*

class WeatherFragment : Fragment() {
	
	var selectedCity: CityModel? = null
	var selectedCitySeasons: List<SeasonModel>? = null
	private var avgTemp = 999
	private var tempSum = 999
	
	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		
		Thread() {
			DBController.addTestDataToDatabase()
			DBController.getCitiesNames()?.let {
				clMainWeatherContainer.post {
					var citiesAdapter: ArrayAdapter<String> = ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, it)
					citiesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
					spCities.adapter = citiesAdapter
					
					var seasonsAdapter: ArrayAdapter<String> = ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, arrayOf("Summer", "Autumn", "Winter", "Spring"))
					seasonsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
					spSeasons.adapter = seasonsAdapter
				}
				updateWeatherCardAtStart()
			}
		}.start()
		
		if (clWeatherCardContainer.visibility == View.VISIBLE) {
			clWeatherCardContainer.visibility = View.INVISIBLE
		}
		
		spCities.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
			override fun onItemSelected(parentView: AdapterView<*>, selectedItemView: View, position: Int, id: Long) {
				var cityName = (selectedItemView as TextView).text.toString()
				Thread() {
					DBController.getCityByName(cityName)?.let {
						selectedCity = it
						DBController.getCitySeasons(cityName)?.let {
							selectedCitySeasons = it
							it.forEach {
								Log.d("SeasonsByCity", "$cityName ${it.seasonsTempByMonth.toString()}")
							}
							var season = (spSeasons.selectedView as TextView).text.toString()
							updateWeatherCard(season)
						} ?: `else` {
							clMainWeatherContainer.post {
								TransitionManager.beginDelayedTransition(clMainWeatherContainer)
								clWeatherCardContainer.visibility = View.INVISIBLE
								tvEmptySeasonData.visibility = View.VISIBLE
							}
						}
					}
				}.start()
			}
			
			override fun onNothingSelected(parentView: AdapterView<*>) {
			}
		}
		
		spSeasons.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
			override fun onItemSelected(parentView: AdapterView<*>, selectedItemView: View, position: Int, id: Long) {
				var season = (selectedItemView as TextView).text.toString()
				updateWeatherCard(season)
			}
			
			override fun onNothingSelected(parentView: AdapterView<*>) {
			}
		}
		
	}
	
	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
		return inflater.inflate(R.layout.f_weather, container, false)
	}
	
	private fun updateWeatherCardAtStart() {
		var selectedCityInit = spCities?.selectedItem.toString()
		Thread() {
			DBController.getCityByName(selectedCityInit)?.let {
				selectedCity = it
				DBController.getCitySeasons(selectedCityInit)?.let {
					selectedCitySeasons = it
					it.forEach {
						Log.d("SeasonsByCity", "$selectedCityInit ${it.seasonsTempByMonth.toString()}")
					}
					var season = spSeasons.selectedItem.toString()
					updateWeatherCard(season)
				} ?: `else` {
					TransitionManager.beginDelayedTransition(clMainWeatherContainer)
					clWeatherCardContainer.visibility = View.INVISIBLE
					tvEmptySeasonData.visibility = View.VISIBLE
				}
			}
		}.start()
	}
	
	private fun updateWeatherCard(selectedSeason: String) {
		selectedCity?.let {
			selectedCitySeasons?.let {
				it.find {
					it.seasonType == selectedSeason
				}?.apply {
					seasonsTempByMonth?.let {
						tempSum = 0
						it.forEach {
							tempSum += it.value
						}
					} ?: `else` {
						TransitionManager.beginDelayedTransition(clMainWeatherContainer)
						clWeatherCardContainer.visibility = View.INVISIBLE
						tvEmptySeasonData.visibility = View.VISIBLE
						return@`else`
					}
					if (tempSum != 999) {
						avgTemp = tempSum / 3
						Log.d("AverageTemp", "$avgTemp")
						
						(context?.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater)?.let {
							clWeatherCardContainer.post {
								if (clWeatherCardContainer.visibility != View.VISIBLE) {
									TransitionManager.beginDelayedTransition(clMainWeatherContainer)
									tvEmptySeasonData.visibility = View.INVISIBLE
									clWeatherCardContainer.visibility = View.VISIBLE
									clWeatherCardContainer.tvCityName.text = selectedCity?.cityName
									clWeatherCardContainer.tvCityType.text = selectedCity?.cityType
									clWeatherCardContainer.tvSeasonName.text = selectedSeason
									clWeatherCardContainer.tvTemperature.text = "$avgTemp"
								} else {
									TransitionManager.beginDelayedTransition(clMainWeatherContainer)
									tvEmptySeasonData.visibility = View.INVISIBLE
									clWeatherCardContainer.tvCityName.text = selectedCity?.cityName
									clWeatherCardContainer.tvCityType.text = selectedCity?.cityType
									clWeatherCardContainer.tvSeasonName.text = selectedSeason
									clWeatherCardContainer.tvTemperature.text = "$avgTemp"
								}
							}
						}
					}
				} ?: `else` {
					clMainWeatherContainer.post {
						TransitionManager.beginDelayedTransition(clMainWeatherContainer)
						clWeatherCardContainer.visibility = View.INVISIBLE
						tvEmptySeasonData.visibility = View.VISIBLE
					}
				}
			}
		}
	}
	
	companion object {
		@JvmStatic
		fun newInstance() =
				WeatherFragment().apply {
					arguments = Bundle().apply {}
				}
	}
	
	/**
	 * Some "magic" to make .let more useful
	 * https://gist.github.com/petitJAM/62141534a0b672bc4526234843791920
	 */
	fun <T, R> T.`else`(block: T.() -> R) = run(block)
}