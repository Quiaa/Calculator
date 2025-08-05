package com.example.test

class Calculator : Calculation {
    override fun calculate(number1: Double, number2: Double, operation: Operation): Result<Double> {
        return when (operation) {
            is Operation.Add -> Result.success(number1 + number2)
            is Operation.Subtract -> Result.success(number1 - number2)
            is Operation.Multiply -> Result.success(number1 * number2)
            is Operation.Divide -> {
                if (number2 == 0.0) {
                    Result.failure(IllegalArgumentException("Cannot divide by zero!"))
                }
                else {
                    Result.success(number1 / number2)
                }
            }
        }
    }
}