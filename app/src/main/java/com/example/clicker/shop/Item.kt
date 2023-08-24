package com.example.clicker.shop

class Item (val itemName: ItemName, val START_PRICE: Long, var boughtTimes: Int, val pointsPerSecond: Int) {

    fun getCurrentPrice(): Long {
        //Complex percent
        return Math.ceil(START_PRICE*Math.pow(1.1, boughtTimes.toDouble())).toLong()
    }

    fun buy() {
        boughtTimes++
    }

}