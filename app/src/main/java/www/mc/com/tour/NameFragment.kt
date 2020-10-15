package www.mc.com.tour

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import www.mc.com.databinding.FragmentNameBinding
import www.mc.com.main.MainActivity
import www.mc.com.utils.AppConstants.PREF_VALUE
import www.mc.com.utils.AppConstants.TOUR_PREF_KEY

class NameFragment : Fragment() {

    private lateinit var binding: FragmentNameBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNameBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonNext.setOnClickListener {
            // Add to shared preference because tour screen is meant to display only once
            val preference = requireActivity().getSharedPreferences(PREF_VALUE, Context.MODE_PRIVATE)
            val editor = preference.edit()
            editor.putBoolean(TOUR_PREF_KEY, true)
            editor.apply()
            startActivity(Intent(requireContext(), MainActivity::class.java))
            requireActivity().finish()
        }
    }
}