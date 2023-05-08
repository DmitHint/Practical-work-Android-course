package com.example.m19_location

import androidx.lifecycle.ViewModel
import com.google.android.gms.maps.model.CameraPosition

class MapsViewModel : ViewModel() {
    var mapPosition: CameraPosition? = null
    var needMoveCamera: Boolean = true
    var showPlaces = listOf(
        ShowPlace(
            48.85901950019212,
            2.2955112681964214,
            "Эйфелева башня",
            "Металлическая башня с лестницами и лифтами к смотровым площадкам, построенная в 1889 году Гюставом Эйфелем.",
        ),
        ShowPlace(
            55.78017416,
            38.11627432,
            "ЦЕРКОВЬ БОГОЯВЛЕНИЯ",
            "1811-1814 гг."
        ),
        ShowPlace(
            40.68925780993948,
            -74.04449943742974,
            "Statue of Liberty",
            "Знаменитый национальный монумент, открытый в 1886 году, с экскурсиями, музеем и видом на бухту и город"
        ),
    )

    override fun onCleared() {
        super.onCleared()
        needMoveCamera = true
    }
}