package com.greylabs.weathercities.fragments

import android.annotation.SuppressLint
import android.content.Context
import android.support.design.internal.BottomNavigationItemView
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentTransaction
import android.util.Log
import com.greylabs.weathercities.R
import com.greylabs.weathercities.models.CityModel

enum class FragmentType {WEATHER, SETTINGS, INFO, EDITOR}
enum class EditorFlag {CREATE, EDIT}

class FragmentsController() {
	companion object FragmentsController{
		private var state: ArrayList<FragmentType> = ArrayList()
		private var editorFlag: EditorFlag = EditorFlag.CREATE
		private var editableCity: CityModel? = null
		private var bnView: BottomNavigationView? = null
		
		@SuppressLint("StaticFieldLeak")
		private var context: Context? = null
		private var fragmentManager: FragmentManager? = null
		
		fun init(context: Context, fragmentManager : FragmentManager) {
			this.context = context
			this.fragmentManager = fragmentManager
		}
		
		fun showFragment(type: FragmentType) {
			when (type) {
				FragmentType.WEATHER -> {
					addFragmentToView(type)
					updateFragmentState(type)
				}
				FragmentType.SETTINGS -> {
					addFragmentToView(type)
					updateFragmentState(type)
				}
				FragmentType.INFO -> {
					addFragmentToView(type)
					updateFragmentState(type)
				}
				FragmentType.EDITOR -> {
					addFragmentToView(type)
					updateFragmentState(type)
				}
			}
			Log.d("FragmentsController", "state = $state")
		}
		
		fun setBottomNavigationView(inView: BottomNavigationView) {
			bnView = inView
		}
		
		fun showPreviousFragment() {
			if (state.size == 2 && state[0] != FragmentType.EDITOR) {
				when (state[0]) {
					FragmentType.WEATHER -> bnView?.menu?.findItem(R.id.item_weather)?.isChecked = true
					FragmentType.SETTINGS -> bnView?.menu?.findItem(R.id.item_settings)?.isChecked = true
					FragmentType.INFO -> bnView?.menu?.findItem(R.id.item_info)?.isChecked = true
				}
				showFragment(state[0])
			}
		}
		
		fun updateFragmentState(type: FragmentType) {
				if (state.size <= 1) {
					when (state.size) {
						0 -> state.add(type)
						1 -> {
							if (state.last() != type) {
								state.add(type)
							}
						}
					}
				} else if (state.size == 2 && state.last() != type) {
					state.add(type)
					state.removeAt(0)
				}
		}
		
		fun addFragmentToView(type: FragmentType) {
			if (state.size >= 1 && state.last() != type) {
				var currentFragment = when (type) {
					FragmentType.WEATHER -> WeatherFragment()
					FragmentType.SETTINGS -> SettingsFragment()
					FragmentType.INFO -> InfoFragment()
					FragmentType.EDITOR -> CityEditorFragment()
				}
				var fragmentTransaction: android.support.v4.app.FragmentTransaction? = fragmentManager?.beginTransaction()
						?.setTransition( FragmentTransaction.TRANSIT_FRAGMENT_OPEN )
				fragmentTransaction?.replace(R.id.clFragmentsContainer, currentFragment)
				fragmentTransaction?.commit()
			} else if (state.size == 0) {
				var currentFragment = when (type) {
					FragmentType.WEATHER -> WeatherFragment()
					FragmentType.SETTINGS -> SettingsFragment()
					FragmentType.INFO -> InfoFragment()
					FragmentType.EDITOR -> CityEditorFragment()
				}
				var fragmentTransaction: android.support.v4.app.FragmentTransaction? = fragmentManager?.beginTransaction()
						?.setTransition( FragmentTransaction.TRANSIT_FRAGMENT_OPEN )
				fragmentTransaction?.replace(R.id.clFragmentsContainer, currentFragment)
				fragmentTransaction?.commit()
			}
		}
		
		fun setEditorFlag(editorFlag: EditorFlag) {
			this.editorFlag = editorFlag
		}
		
		fun getEditorFlag() : EditorFlag {
			return editorFlag
		}
		
		fun setEditableCity(editableCity: CityModel) {
			this.editableCity = editableCity
		}
		
		fun getEditableCity() : CityModel?{
			return editableCity
		}
	}
}