package www.mc.com.main

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import www.mc.com.main.model.Finance
import www.mc.com.utils.AutoCompleteOptions
import www.mc.com.utils.AutoCompleteOptions.*
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

    private val _getEarningResponse = MutableLiveData<Result<List<Finance?>>>()
    val getEarningResponse = _getEarningResponse.asLiveData()

    private val _getExpenseResponse = MutableLiveData<Result<List<Finance?>>>()
    val getExpenseResponse = _getExpenseResponse.asLiveData()

    private val _earned = MutableLiveData<Result<Double>>()
    val earned = _earned.asLiveData()

    private val _spent = MutableLiveData<Result<Double>>()
    val spent = _spent.asLiveData()

    private val _recentTransactions = MutableLiveData<Result<List<Finance?>>>()
    val recentTransactions = _recentTransactions.asLiveData()

    private val _recentTransactions2 = MutableLiveData<Result<List<Finance?>>>()
    val recentTransactions2 = _recentTransactions2.asLiveData()

    private lateinit var dataListener: ValueEventListener

    init {
        getExpense()
        getEarning()
    }

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

    private fun getEarning() {
        _getEarningResponse.postValue(Result.Loading)
        dataListener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val items = mutableListOf<Finance?>()
                var earned = 0.0
                for (dataSnapshot: DataSnapshot in snapshot.children) {
                    items.add(dataSnapshot.getValue(Finance::class.java))
                    _getEarningResponse.postValue(Result.Success(items.reversed()))
                    _recentTransactions.postValue(Result.Success(items))
                    dataSnapshot.getValue(Finance::class.java)?.amount?.let {
                        earned += it
                    }
                    _earned.postValue(Result.Success(earned))
                    Log.d("FinanceViewModel", "onChildAdded: $earned")
                }
            }

            override fun onCancelled(error: DatabaseError) {
                _getEarningResponse.postValue(Result.Error(error.toException()))
                Log.e("FinanceViewModel", "onCancelled: ${error.message}")
            }
        }

        database.child(firebaseUser!!.uid).child(EARNINGS.name)
            .addValueEventListener(dataListener)
    }

    private fun getExpense() {
        _getExpenseResponse.postValue(Result.Loading)
        dataListener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val items = mutableListOf<Finance?>()
                var spent = 0.0
                for (dataSnapshot: DataSnapshot in snapshot.children) {
                    items.add(dataSnapshot.getValue(Finance::class.java))
                    _getExpenseResponse.postValue(Result.Success(items.reversed()))
                    _recentTransactions2.postValue(Result.Success(items))
                    dataSnapshot.getValue(Finance::class.java)?.amount?.let {
                        spent += it
                    }
                    Log.d("FinanceViewModel", "onChildAdded: $dataSnapshot")
                }
                _spent.postValue(Result.Success(spent))
            }

            override fun onCancelled(error: DatabaseError) {
                _getExpenseResponse.postValue(Result.Error(error.toException()))
                Log.e("FinanceViewModel", "onCancelled: ${error.message}")
            }
        }

        database.child(firebaseUser!!.uid).child(EXPENSES.name)
            .addValueEventListener(dataListener)
    }
}