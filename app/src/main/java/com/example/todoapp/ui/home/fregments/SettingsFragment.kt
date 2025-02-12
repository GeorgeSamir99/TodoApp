package com.example.todoapp.ui.home.fregments

import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import com.example.todoapp.R
import com.example.todoapp.databinding.FragmentSettingsBinding

class SettingsFragment : Fragment(){
    private lateinit var binding: FragmentSettingsBinding

    override fun onCreateView(

        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSettingsBinding.inflate(inflater , container , false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        changeMode()


    }

    private fun changeMode() {
        val modeList: Array<String> = resources.getStringArray(R.array.mode_List)
        val arrayAdapter =ArrayAdapter<String>(requireActivity(),android.R.layout.simple_list_item_1,modeList)
        binding.autoCompleteTVModes.setAdapter(arrayAdapter)

        binding.autoCompleteTVModes.setOnItemClickListener { parent, view, position, id ->
            if(position == 0){
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
            else{
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            }
        }
    }
}