package www.mc.com.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import www.mc.com.main.model.Finance
import www.mc.com.utils.AutoCompleteOptions
import www.mc.com.utils.Result
import www.mc.com.utils.asLiveData

/**
 * Created by SegunFrancis
 */

class FinanceViewModel : ViewModel() {

    private var firebaseUser = FirebaseAuth.getInstance().currentUser
    private var database = FirebaseDatabase.getInstance().reference
    private val _addDataResponse = MutableLiveData<Result<Nothing>>()
    val addDataResponse = _addDataResponse.asLiveData()

    fun addData(option: AutoCompleteOptions, title: String, category: String, amount: Double, details: String?) {
        val finance = Finance(
            title = title,
            category = category,
            amount = amount,
            details = details
        )

        _addDataResponse.postValue(Result.Loading)
        database.child(firebaseUser!!.uid).child(option.name).push().setValue(finance)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    _addDataResponse.postValue(Result.Success())
                } else {
                    _addDataResponse.postValue(Result.Error(task.exception))
                }
            }
    }
}