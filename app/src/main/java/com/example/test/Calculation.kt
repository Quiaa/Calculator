package com.example.test

interface Calculation {
    fun calculate(number1: Double, number2: Double, operation: Operation): Result<Double>
}