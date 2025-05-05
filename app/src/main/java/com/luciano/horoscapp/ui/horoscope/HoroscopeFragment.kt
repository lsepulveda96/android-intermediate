package com.luciano.horoscapp.ui.horoscope

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.luciano.horoscapp.databinding.FragmentHoroscopeBinding
import com.luciano.horoscapp.domain.model.HoroscopeInfo
import com.luciano.horoscapp.domain.model.HoroscopeModel
import com.luciano.horoscapp.ui.horoscope.adapter.HoroscopeAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HoroscopeFragment : Fragment() {

    // para enganchar fragment al viewModel
    private val horoscopeViewModel by viewModels<HoroscopeViewModel>()

    private lateinit var horoscopeAdapter:HoroscopeAdapter

    // variable que no se va a poder acceder, si se quiere usar tienen que acceder al val binding
    private var _binding: FragmentHoroscopeBinding? = null

    //sobreescribe el get con el _binding
    private val binding get() = _binding!!

    // metodo cuando la vista ya ha sido creada. para metodos de config etc
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUi()
    }

    private fun initUi() {
        initList()
        initUiState()
    }

    private fun initList() {
        horoscopeAdapter = HoroscopeAdapter(onItemSelected = {
            // funcion lambda. Aca envio este troso de codigo, se va a ejecutar cuando lo llame el render de ViewHolder
            // en it ya sabe que es un objeto HoroscopeInfo xq lo declare en la funcion lambda
//            Toast.makeText(context, getString(it.name), Toast.LENGTH_LONG).show()

            // para no pasarle el objeto entero HoroscopeInfo. Enum es una forma secilla de definir los tipos
            val type = when(it){
                HoroscopeInfo.Aquarius -> HoroscopeModel.Aquarius
                HoroscopeInfo.Aries -> HoroscopeModel.Aries
                HoroscopeInfo.Cancer -> HoroscopeModel.Cancer
                HoroscopeInfo.Capricorn -> HoroscopeModel.Capricorn
                HoroscopeInfo.Gemini -> HoroscopeModel.Gemini
                HoroscopeInfo.Leo -> HoroscopeModel.Leo
                HoroscopeInfo.Libra -> HoroscopeModel.Libra
                HoroscopeInfo.Pisces -> HoroscopeModel.Pisces
                HoroscopeInfo.Sagittarius -> HoroscopeModel.Sagittarius
                HoroscopeInfo.Scorpio -> HoroscopeModel.Scorpio
                HoroscopeInfo.Taurus -> HoroscopeModel.Taurus
                HoroscopeInfo.Virgo -> HoroscopeModel.Virgo
            }

            // utiliza safeArg. obliga a mandarle lo que necesite siempre que lo necesite
            findNavController().navigate(
                // clase autogernerada del main graph. para ejecutar la accion que navega
                HoroscopeFragmentDirections.actionHoroscopeFragmentToHoroscopeDetailActivity(type)
            )
        })

        // a la vista le aplica todos los atributos que le declare
        binding.rvHoroscope.apply {
            layoutManager = GridLayoutManager(context,2) // para que se vean dos columnas
            //layoutManager = LinearLayoutManager(context)
            // al adapter del rvHoroscope le aplico el adapter que cree
            adapter = horoscopeAdapter
        }
    }

    private fun initUiState() {
        // se engancha al ciclo de vida del fragmento, si el fragmento muere, la corrutina tambien
        lifecycleScope.launch {
            // cuando inicie el ciclo de vida del fragmento
            repeatOnLifecycle(Lifecycle.State.STARTED){
                // se suscribe al viewModel
                horoscopeViewModel.horoscope.collect{
                    // pintar el recycler view. Cambios en horoscope
                    horoscopeAdapter.updateList(it)
                }
            }
        }
    }

    // metodo cuando se crea la vista del fragment
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHoroscopeBinding.inflate(layoutInflater, container, false)
        // Inflate the layout for this fragment
        return binding.root
    }
}