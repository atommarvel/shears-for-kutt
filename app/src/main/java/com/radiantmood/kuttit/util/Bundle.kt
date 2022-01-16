package com.radiantmood.kuttit.util

import android.os.Bundle
import androidx.core.os.bundleOf

fun bundleOfNotNull(vararg pairs: Pair<String, Any?>): Bundle {
    val validPairs = pairs.filter { it.second != null }.toTypedArray()
    return bundleOf(*validPairs)
}