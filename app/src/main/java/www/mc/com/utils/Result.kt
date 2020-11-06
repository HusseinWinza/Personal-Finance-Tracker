package www.mc.com.utils

/**
 * Created by SegunFrancis
 */

sealed class Result<T> {
    data class Success<T>(val data: T? = null) : Result<T>()
    class Error(val error: Exception?) : Result<Nothing>()
    object Loading : Result<Nothing>()
}