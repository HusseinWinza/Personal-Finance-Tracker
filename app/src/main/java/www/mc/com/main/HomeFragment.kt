package www.mc.com.main

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import www.mc.com.R
import www.mc.com.databinding.FragmentHomeBinding
import www.mc.com.main.adapter.FinanceRecyclerAdapter
import www.mc.com.main.model.Finance
import www.mc.com.utils.MarginItemDecoration
import www.mc.com.utils.Result.*
import www.mc.com.utils.concatenate
import www.mc.com.utils.loadImage

class HomeFragment : Fragment() {

    private lateinit var auth: FirebaseAuth
    private lateinit var binding: FragmentHomeBinding
    private val viewModel: FinanceViewModel by viewModels()
    private val recentAdapter = FinanceRecyclerAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        auth = FirebaseAuth.getInstance()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(layoutInflater)
        val fullName = auth.currentUser?.displayName
        fullName?.let {
            val names: List<String> = it.split(" ") // Getting the first name
            binding.name.text = getString(R.string.text_welcome).plus(" ${names[0]},")
        }
        binding.profileImage.loadImage(auth.currentUser?.photoUrl.toString())
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recentRecyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = recentAdapter
            addItemDecoration(MarginItemDecoration(32))
        }

        viewModel.earned.observe(viewLifecycleOwner, Observer { result ->
            when (result) {
                is Success -> {
                    binding.textEarned.text = "$".plus(result.data)
                    viewModel.spent.observe(viewLifecycleOwner, Observer { result2 ->
                        when (result2) {
                            is Success -> {
                                binding.textSpent.text = "$".plus(result2.data)
                                val total = result.data!! - result2.data!!
                                binding.textTotalBalance.text = "$".plus(total)
                            }
                            is Error -> {
                                Log.e(
                                    "HomeFragment",
                                    "onViewCreated: ${result2.error?.localizedMessage}"
                                )
                            }
                        }
                    })
                }
                is Error -> {
                    Log.e("HomeFragment", "onViewCreated: ${result.error?.localizedMessage}")
                }
            }
        })

        viewModel.recentTransactions.observe(viewLifecycleOwner, Observer { result ->
            when (result) {
                is Success -> {
                    val items : List<Finance?>? = result.data
                    viewModel.recentTransactions2.observe(viewLifecycleOwner, Observer { res ->
                        when(res) {
                            is Success -> {
                                val items2 = res.data
                                val temps = concatenate(items, items2)
                                val oh = temps?.sortedBy {
                                    it?.dataAdded
                                }?.take(8)
                                recentAdapter.submitList(oh)
                            }
                        }
                    })
                }
                is Error -> {
                }
            }
        })
    }
}