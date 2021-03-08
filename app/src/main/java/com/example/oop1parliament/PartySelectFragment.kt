package com.example.oop1parliament

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.navigation.findNavController
import androidx.recyclerview.widget.*
import com.example.oop1parliament.databinding.FragmentPartySelectBinding
import com.example.oop1parliament.recyclerview.PartyListAdapter
import com.example.oop1parliament.viewmodels.PartySelectViewModel

class PartySelect : Fragment() {
    private lateinit var binding: FragmentPartySelectBinding
    private lateinit var partySelectViewModel: PartySelectViewModel
    private lateinit var adapter: PartyListAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        //Binding and viewmodel variables for PartySelect
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_party_select, container, false)
        partySelectViewModel = ViewModelProvider(this).get(PartySelectViewModel::class.java)

        //Set GridLayout for displaying parties in a span of 3 images
        binding.partyView.layoutManager = GridLayoutManager(context, 3)
        adapter = PartyListAdapter(requireContext().applicationContext)
        binding.partyView.adapter = adapter

        //Observer for parliament parties data, where data is set for the recyclerview adapter
        partySelectViewModel.parliamentParties.observe(viewLifecycleOwner) {
            it?.let {
                adapter.submitList(it)
            }
        }

        //Button to show all members
        binding.showAllMembers.setOnClickListener { view : View -> view.findNavController().navigate(R.id.action_global_partyMembersFragment) }

        return binding.root
    }

}