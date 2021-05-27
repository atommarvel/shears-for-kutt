package com.radiantmood.kuttit.data

sealed class ModelContainer<T>(val key: String)

class LoadingModelContainer<T> : ModelContainer<T>("Loading")
class ErrorModelContainer<T>(val errorMessage: String? = null) : ModelContainer<T>("Error")
open class FinishedModelContainer<T> : ModelContainer<T>("Finished")