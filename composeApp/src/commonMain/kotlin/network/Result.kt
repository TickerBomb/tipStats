package network

sealed class Result<out T> {
    open fun get(): T? = null

    fun getOrThrow() = when (this) {
        is Success -> get()
        is Error -> throw throwable
    }

    fun getThrow() = if (this is Error) throwable else null

    fun isSuccess(): Boolean {
        return this is Success
    }

    override fun toString(): String {
        return when (this) {
            is Success<*> -> "Success[data=$data]"
            is Error -> "Error[exception=$throwable]"
        }
    }

    data class Success<T>(val data: T, val responseModified: Boolean = true) : Result<T>() {
        override fun get(): T = data
    }

    data class Error(val throwable: Throwable) : Result<Nothing>()
}
