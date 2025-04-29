package com.luciano.horoscapp.ui.horoscope

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.luciano.horoscapp.databinding.FragmentHoroscopeBinding

class HoroscopeFragment : Fragment() {

    // variable que no se va a poder acceder, si se quiere usar tienen que acceder al val binding
    private var _binding: FragmentHoroscopeBinding? = null

    //sobreescribe el get con el _binding
    private val binding get() = _binding!!

    // metodo que crea la vista del fragment
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHoroscopeBinding.inflate(layoutInflater, container, false)
        // Inflate the layout for this fragment
        return binding.root
    }
}