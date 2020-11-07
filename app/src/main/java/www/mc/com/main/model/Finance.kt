package www.mc.com.main.model

/**
 * Created by SegunFrancis
 */

data class Finance(
    val title: String,
    val category: String,
    val amount: Double,
    val details: String?,
    val dataAdded: Long
) {
    constructor() : this("", "", 0.0, "", 0)
}