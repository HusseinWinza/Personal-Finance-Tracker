package www.mc.com.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import www.mc.com.databinding.FragmentExpenseBinding
import www.mc.com.utils.AutoCompleteOptions

class ExpenseFragment : Fragment() {

    private lateinit var binding: FragmentExpenseBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentExpenseBinding.inflate(layoutInflater)
        binding.addExpenseButton.setOnClickListener {
            MainDialogFragment(AutoCompleteOptions.EXPENSES).show(
                childFragmentManager,
                "expense_fragment"
            )
        }
        return binding.root
    }
}