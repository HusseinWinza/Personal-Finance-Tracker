package www.mc.com.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import www.mc.com.databinding.FragmentEarningBinding
import www.mc.com.utils.AutoCompleteOptions

class EarningFragment : Fragment() {

    private lateinit var binding: FragmentEarningBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentEarningBinding.inflate(layoutInflater)
        binding.addEarningButton.setOnClickListener {
            MainDialogFragment(
                AutoCompleteOptions.EARNINGS).show(
                childFragmentManager,
                "earning_fragment"
            )
        }
        return binding.root
    }
}