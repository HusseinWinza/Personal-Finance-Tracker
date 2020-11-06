package www.mc.com.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import www.mc.com.databinding.FragmentExpenseBinding
import www.mc.com.main.adapter.FinanceRecyclerAdapter
import www.mc.com.utils.AutoCompleteOptions
import www.mc.com.utils.Result
import www.mc.com.utils.showToast

class ExpenseFragment : Fragment() {

    private lateinit var binding: FragmentExpenseBinding
    private val viewModel: FinanceViewModel by viewModels()
    private val financeAdapter = FinanceRecyclerAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentExpenseBinding.inflate(layoutInflater)
        binding.addExpenseButton.setOnClickListener {
            MainDialogFragment(
                AutoCompleteOptions.EXPENSES
            ).show(
                childFragmentManager,
                "expense_fragment"
            )
        }
        viewModel.getData(AutoCompleteOptions.EXPENSES)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.expenseRecyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = financeAdapter
        }
        viewModel.getDataResponse.observe(requireActivity(), Observer { result ->
            when (result) {
                is Result.Loading -> {

                }
                is Result.Success -> {
                    financeAdapter.submitList(result.data)
                }
                is Result.Error -> {
                    requireContext().showToast(result.error?.localizedMessage)
                }
            }
        })
    }
}