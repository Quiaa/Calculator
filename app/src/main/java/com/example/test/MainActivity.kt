package com.example.test

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.test.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.addButton.setOnClickListener {
            performOperation("add")
        }

        binding.subtractButton.setOnClickListener {
            performOperation("subtract")
        }

        binding.multiplyButton.setOnClickListener {
            performOperation("multiply")
        }

        binding.divideButton.setOnClickListener {
            performOperation("divide")
        }
    }
    private fun performOperation(operation: String) {
        // Get the text from the EditText fields.
        val number1Str = binding.number1Input.text.toString()
        val number2Str = binding.number2Input.text.toString()

        // Validate that input fields are not empty.
        if (number1Str.isEmpty() || number2Str.isEmpty()) {
            binding.resultTextView.text = "Result: Please enter both numbers!"
            return
        }

        // Try to convert the input strings to Double.
        // toDoubleOrNull returns null if the conversion fails.
        val number1 = number1Str.toDoubleOrNull()
        val number2 = number2Str.toDoubleOrNull()

        // Check if the conversion was successful.
        if (number1 == null || number2 == null) {
            binding.resultTextView.text = "Result: Invalid number format!"
            return
        }

        // Perform the calculation based on the operation parameter.
        val result = when (operation) {
            "add" -> number1 + number2
            "subtract" -> number1 - number2
            "multiply" -> number1 * number2
            "divide" -> {
                // Handle division by zero.
                if (number2 == 0.0) {
                    Toast.makeText(this, "Cannot divide by zero!", Toast.LENGTH_LONG).show()
                    null // Return null to indicate an error
                } else {
                    number1 / number2
                }
            }
            else -> null // Should not happen
        }

        // Display the result if the calculation was successful.
        if (result != null) {
            binding.resultTextView.text = "Result: $result"
        }
    }

}