package com.example.test

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.test.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val calculator: Calculation = Calculator()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.addButton.setOnClickListener {
            performOperation(Operation.Add)
        }
        binding.subtractButton.setOnClickListener {
            performOperation(Operation.Subtract)
        }
        binding.multiplyButton.setOnClickListener {
            performOperation(Operation.Multiply)
        }
        binding.divideButton.setOnClickListener {
            performOperation(Operation.Divide)
        }
    }

    private fun performOperation(operation: Operation) {
        val number1Str = binding.number1Input.text.toString()
        val number2Str = binding.number2Input.text.toString()

        if (number1Str.isEmpty() || number2Str.isEmpty()) {
            binding.resultTextView.text = "Result: Please enter both numbers!"
            return
        }
        val number1 = number1Str.toDoubleOrNull()
        val number2 = number2Str.toDoubleOrNull()

        if (number1 == null || number2 == null) {
            binding.resultTextView.text = "Result: Invalid number format!"
            return
        }
        val result = calculator.calculate(number1, number2, operation)

        // Handle the Result to update the UI.
        result.onSuccess { calculatedValue ->
            // This block runs only if the calculation was successful.
            binding.resultTextView.text = "Result: $calculatedValue"
        }.onFailure { error ->
            // This block runs only if the calculation failed.
            binding.resultTextView.text = "Error: ${error.message}"
        }
    }
}