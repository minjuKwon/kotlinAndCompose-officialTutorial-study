package com.example.juicetracker

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.navArgs
import com.example.juicetracker.data.Juice
import com.example.juicetracker.data.JuiceColor
import com.example.juicetracker.databinding.FragmentEntryDialogBinding
import com.example.juicetracker.ui.AppViewModelProvider
import com.example.juicetracker.ui.EntryViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.launch

class EntryDialogFragment : BottomSheetDialogFragment() {

    private val entryViewModel by viewModels<EntryViewModel>{ AppViewModelProvider.Factory}
    var selectedColor:JuiceColor= JuiceColor.Red

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return FragmentEntryDialogBinding.inflate(inflater, container, false).root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val colorLabelMap=JuiceColor.values().associateBy{getString(it.label)}
        val binding=FragmentEntryDialogBinding.bind(view)
        val args: EntryDialogFragmentArgs by navArgs()
        val juiceId=args.itemId

        if(args.itemId>0){
            viewLifecycleOwner.lifecycleScope.launch {
                repeatOnLifecycle(Lifecycle.State.STARTED){
                    entryViewModel.getJuiceStream(args.itemId).filterNotNull().collect{item->
                        with(binding){
                            name.setText(item.name)
                            description.setText(item.description)
                            ratingBar.rating=item.rating.toFloat()
                            colorSpinner.setSelection(findColorIndex(item.color))
                        }
                    }
                }
            }
        }

        binding.name.doOnTextChanged{_, start, _, count->
            binding.saveButton.isEnabled=(start+count)>0
        }

//        binding.colorSpinner.adapter = ArrayAdapter(
//            requireContext(),
//            layout.support_simple_spinner_dropdown_item,
//            colorLabelMap.map { it.key }
//        )

        binding.colorSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {

            override fun onItemSelected(parent: AdapterView<*>, view: View?, pos: Int, id: Long) {
                // Get the label of the selected item
                val selected = parent.getItemAtPosition(pos).toString()
                // Get the enum value from string
                selectedColor = colorLabelMap[selected] ?: selectedColor
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                //if nothing is selected, assign the first color choice as the selectedColor
                selectedColor = JuiceColor.Red
            }
        }

        binding.saveButton.setOnClickListener{
            entryViewModel.saveJuice(
                juiceId,
                binding.name.text.toString(),
                binding.description.text.toString(),
                selectedColor.name,
                binding.ratingBar.rating.toInt()
            )
            dismiss()
        }

        binding.cancelButton.setOnClickListener {
            dismiss()
        }

    }

    private fun findColorIndex(color:String):Int{
        val juiceColor=JuiceColor.valueOf(color)
        return JuiceColor.values().indexOf(juiceColor)
    }

}