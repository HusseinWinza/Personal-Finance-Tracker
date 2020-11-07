package www.mc.com.utils

/**
 * Created by SegunFrancis
 */

sealed class Result<out T> {
    data class Success<out T>(val data: T? = null) : Result<T>()
    class Error(val error: Exception?) : Result<Nothing>()
    object Loading : Result<Nothing>()
}