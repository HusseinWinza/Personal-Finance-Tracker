package www.mc.com.main

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import www.mc.com.R
import www.mc.com.databinding.FragmentMainDialogBinding
import www.mc.com.utils.AutoCompleteOptions
import www.mc.com.utils.AutoCompleteOptions.*

/**
 * Created by SegunFrancis
 */

class MainDialogFragment(private val option: AutoCompleteOptions) :
    DialogFragment() {

    private lateinit var binding: FragmentMainDialogBinding
    private var categories = mutableListOf<String>()
    private var titleText: String = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMainDialogBinding.inflate(layoutInflater)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        when (option) {
            EARNINGS -> {
                categories = mutableListOf("Salary", "Businesses", "Others")
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
                    "Entertainment"
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
            Toast.makeText(requireContext(), "Clicked!", Toast.LENGTH_SHORT).show()
        }
    }
}