package com.homework

sealed class StatusForView {
    object AllSeatsAreFree : StatusForView()
    object SeatsLeft : StatusForView()
    object AllSeatsAreOccupied : StatusForView()
}