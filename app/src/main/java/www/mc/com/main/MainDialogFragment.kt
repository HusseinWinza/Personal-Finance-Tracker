package www.mc.com.main

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import www.mc.com.R
import www.mc.com.databinding.FragmentMainDialogBinding
import www.mc.com.utils.AutoCompleteOptions
import www.mc.com.utils.AutoCompleteOptions.*
import www.mc.com.utils.Result.*
import www.mc.com.utils.disable
import www.mc.com.utils.enable
import www.mc.com.utils.showToast

/**
 * Created by SegunFrancis
 */

class MainDialogFragment(private val option: AutoCompleteOptions) :
    DialogFragment(), TextWatcher {

    private lateinit var binding: FragmentMainDialogBinding
    private var categories = mutableListOf<String>()
    private var titleText: String = ""
    private val viewModel: FinanceViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMainDialogBinding.inflate(layoutInflater)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        binding.etAmount.addTextChangedListener(this)
        binding.etTitle.addTextChangedListener(this)
        binding.autocompleteCategory.addTextChangedListener(this)
        binding.submitButton.disable()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        when (option) {
            EARNINGS -> {
                categories = mutableListOf("Salary", "Business", "Others")
                titleText = getString(R.string.text_earning_dialog_title)
            }
            EXPENSES -> {
                titleText = getString(R.string.text_expense_dialog_title)
                categories = mutableListOf(
                    "Food",
                    "Rent",
                    "Health care",
                    "Tax",
                    "Fun",
                    "Education",
                    "Internet",
                    "Entertainment",
                    "Others"
                )
            }
        }
        val categoryAdapter = ArrayAdapter<String>(
            requireContext(),
            android.R.layout.simple_spinner_dropdown_item,
            categories
        )
        binding.autocompleteCategory.setAdapter(categoryAdapter)
        binding.dialogTitleText.text = titleText
        binding.submitButton.setOnClickListener {
            viewModel.addData(
                option = option,
                title = binding.etTitle.text.toString(),
                amount = binding.etAmount.text.toString().toDouble(),
                category = binding.autocompleteCategory.text.toString(),
                details = binding.etDetails.text.toString()
            )
        }

        viewModel.addDataResponse.observe(viewLifecycleOwner, Observer { result ->
            when (result) {
                is Loading -> {
                    binding.submitButton.showLoadingIndicator()
                }
                is Success -> {
                    binding.submitButton.hideLoadingIndicator()
                    requireDialog().dismiss()
                }
                is Error -> {
                    binding.submitButton.hideLoadingIndicator()
                    requireContext().showToast(result.error?.localizedMessage)
                }
            }
        })
    }

    private fun validateFields(): Boolean {
        if (binding.etTitle.text.isNullOrEmpty()) return false
        if (binding.etAmount.text.isNullOrEmpty()) return false
        if (binding.autocompleteCategory.text.isNullOrEmpty()) return false
        return true
    }

    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

    }

    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        if (validateFields()) {
            binding.submitButton.enable()
        } else {
            binding.submitButton.disable()
        }
    }

    override fun afterTextChanged(p0: Editable?) {

    }
}