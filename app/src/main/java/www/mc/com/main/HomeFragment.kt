package www.mc.com.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.auth.FirebaseAuth
import www.mc.com.R
import www.mc.com.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private lateinit var auth: FirebaseAuth
    private lateinit var binding: FragmentHomeBinding

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
        return binding.root
    }
}