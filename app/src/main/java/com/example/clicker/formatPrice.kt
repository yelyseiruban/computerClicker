package com.example.clicker

fun formatPrice(number: Long): String {
    var doubleNumber = number.toDouble()
    when {
        (number >= 1_000_000_000_000L) -> {
            doubleNumber /= 1_000_000_000_000
            val trillions: Double = String.format("%.1f", doubleNumber).toDouble()
            return "${trillions}B"
        }
        (number >= 1_000_000_000L) -> {
            doubleNumber /= 1_000_000_000
            val billions: Double = String.format("%.1f", doubleNumber).toDouble()
            return "${billions}B"
        }
        (number >= 1_000_000L) -> {
            doubleNumber /= 1_000_000
            val millions: Double = String.format("%.1f", doubleNumber).toDouble()
            return "${millions}M"
        }
        (number >= 1_000L) -> {
            doubleNumber /= 1_000
            val thousands: Double = String.format("%.1f", doubleNumber).toDouble()
            return "${thousands}k"
        }
        else -> {
            return number.toString()
        }
    }
}