package com.greylabs.weathercities.utils

import android.support.design.widget.Snackbar
import android.view.View

class SnacksMachine {
	
	companion object SnacksMachine{
		private val colorMap = HashMap<String,Int>()
		
		private var currentView: View? = null
		
		fun initColors() {
			colorMap.put("red", -0xbbcca)
			colorMap.put("green", -0xb350b0)
			colorMap.put("blue", -0xde6a0d)
			colorMap.put("orange", -0x3ef9)
		}
		
		fun setCurrentView(inputView: View) {
			currentView = inputView
		}
		
		fun showSnackbar(text: String, color: String) {
			currentView?.let {
				val msgSnackbar = Snackbar.make(it, text, Snackbar.LENGTH_SHORT)
				colorMap[color]?.let {
					msgSnackbar.view.setBackgroundColor(it)
				}
				msgSnackbar.show()
			}
		}
		
		fun showLongSnackbar(text: String, color: String) {
			currentView?.let {
				val msgSnackbar = Snackbar.make(it, text, Snackbar.LENGTH_LONG)
				colorMap[color]?.let {
					msgSnackbar.view.setBackgroundColor(it)
				}
				msgSnackbar.show()
			}
		}
		
		fun showActionSnackbar(text: String, color: String, button: String, clickListener: View.OnClickListener) {
			currentView?.let {
				val msgSnackbar = Snackbar.make(it, text, Snackbar.LENGTH_SHORT)
				msgSnackbar.setAction(button, clickListener)
				colorMap[color]?.let {
					msgSnackbar.view.setBackgroundColor(it)
				}
				msgSnackbar.show()
			}
		}
	}
}
