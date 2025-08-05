package com.example.test

class Calculator : Calculation {
    override fun calculate(number1: Double, number2: Double, operation: Operation): Result<Double> {
        val result = when (operation) {
            is Operation.Add -> number1 + number2
            is Operation.Subtract -> number1 - number2
            is Operation.Multiply -> number1 * number2
            is Operation.Divide -> {
                if (number2 == 0.0) {
                    return Result.failure(IllegalArgumentException("Cannot divide by zero!"))
                } else {
                    number1 / number2
                }
            }
        }
        // --- NEW: OVERFLOW CHECK ---
        if (result.isInfinite()) {
            return Result.failure(IllegalArgumentException("Result is too large (overflow)!"))
        }

        return Result.success(result)
    }
}