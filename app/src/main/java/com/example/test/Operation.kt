package com.example.test

sealed class Operation {
    object Add : Operation()
    object Subtract : Operation()
    object Multiply : Operation()
    object Divide : Operation()
}