package com.radiantmood.kuttit.data.local

/**
 * For representing common UI states
 */
sealed class ModelContainer<T>(val key: String)

class LoadingModelContainer<T> : ModelContainer<T>("Loading")
class ErrorModelContainer<T>(val errorMessage: String? = null) : ModelContainer<T>("Error")
open class FinishedModelContainer<T> : ModelContainer<T>("Finished")