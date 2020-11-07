package www.mc.com.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import www.mc.com.databinding.FragmentEarningBinding
import www.mc.com.main.adapter.FinanceRecyclerAdapter
import www.mc.com.utils.AutoCompleteOptions
import www.mc.com.utils.MarginItemDecoration
import www.mc.com.utils.Result.*
import www.mc.com.utils.showToast

class EarningFragment : Fragment() {

    private lateinit var binding: FragmentEarningBinding
    private val viewModel: FinanceViewModel by viewModels()
    private val financeAdapter = FinanceRecyclerAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentEarningBinding.inflate(layoutInflater)
        binding.addEarningButton.setOnClickListener {
            MainDialogFragment(
                AutoCompleteOptions.EARNINGS
            ).show(
                childFragmentManager,
                "earning_fragment"
            )
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.earningRecyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = financeAdapter
            addItemDecoration(MarginItemDecoration(32))
        }
        viewModel.getEarningResponse.observe(requireActivity(), Observer { result ->
            when (result) {
                is Loading -> {

                }
                is Success -> {
                    financeAdapter.submitList(result.data)
                }
                is Error -> {
                    requireContext().showToast(result.error?.localizedMessage)
                }
            }
        })
    }
}