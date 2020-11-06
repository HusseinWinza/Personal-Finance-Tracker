package www.mc.com.main

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import www.mc.com.main.model.Finance
import www.mc.com.utils.AutoCompleteOptions
import www.mc.com.utils.Result
import www.mc.com.utils.asLiveData
import java.util.*

/**
 * Created by SegunFrancis
 */

class FinanceViewModel : ViewModel() {

    private var firebaseUser = FirebaseAuth.getInstance().currentUser
    private var database = FirebaseDatabase.getInstance().reference

    private val _addDataResponse = MutableLiveData<Result<Nothing>>()
    val addDataResponse = _addDataResponse.asLiveData()

    private val _getDataResponse = MutableLiveData<Result<List<Finance?>>>()
    val getDataResponse = _getDataResponse.asLiveData()

    private lateinit var dataListener: ValueEventListener

    fun addData(
        option: AutoCompleteOptions,
        title: String,
        category: String,
        amount: Double,
        details: String?
    ) {
        val finance = Finance(
            title = title,
            category = category,
            amount = amount,
            details = details,
            dataAdded = Calendar.getInstance().timeInMillis
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

    fun getData(option: AutoCompleteOptions) {
        _getDataResponse.postValue(Result.Loading)

        dataListener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val items = mutableListOf<Finance?>()
                for (dataSnapshot: DataSnapshot in snapshot.children) {
                    items.add(dataSnapshot.getValue(Finance::class.java))
                    _getDataResponse.postValue(Result.Success(items))
                    Log.d("FinanceViewModel", "onChildAdded: $dataSnapshot")
                }
            }

            override fun onCancelled(error: DatabaseError) {
                _getDataResponse.postValue(Result.Error(error.toException()))
                Log.e("FinanceViewModel", "onCancelled: ${error.message}")
            }
        }

        database.child(firebaseUser!!.uid).child(option.name)
            .addValueEventListener(dataListener)
    }
}