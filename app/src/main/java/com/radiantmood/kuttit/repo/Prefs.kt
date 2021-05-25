package com.radiantmood.kuttit.repo

import android.content.Context
import com.radiantmood.kuttit.kuttApp

val prefs by lazy { kuttApp.getSharedPreferences("kutt.prefs", Context.MODE_PRIVATE) }