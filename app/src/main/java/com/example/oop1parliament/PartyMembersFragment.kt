package com.example.oop1parliament

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.*
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.oop1parliament.databinding.FragmentPartyMembersBinding
import com.example.oop1parliament.recyclerview.MemberListAdapter
import com.example.oop1parliament.viewmodels.PartyMembersViewModel

class PartyMembersFragment : Fragment() {
    lateinit var binding: FragmentPartyMembersBinding
    lateinit var partyMembersViewModel : PartyMembersViewModel
    private lateinit var adapter: MemberListAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        //Binding and viewmodel variables for PartyMembersFragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_party_members, container, false)
        partyMembersViewModel = ViewModelProvider(this).get(PartyMembersViewModel::class.java)

        //Receive selected party string from bundle
        var selectedParty = arguments?.getString("party") ?: "all"

        //Variable for app context
        val context = requireContext().applicationContext

        //Set adapter and layoutmanager for memberView-recycler view
        adapter = MemberListAdapter(context)
        binding.memberView.adapter = adapter
        binding.memberView.layoutManager = LinearLayoutManager(context)

        //Set properties for spinner which is used in the fragment for re-selecting the viewable party
        ArrayAdapter.createFromResource(context,
                R.array.parties_array,
                R.layout.support_simple_spinner_dropdown_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.partySpinner.adapter = adapter
        }

        //Observer for parliamentMembers-livedata, which is filtered based on the selected party.
        //Only the members from the selected party are submitted to the adapter
        partyMembersViewModel.parliamentMembers.observe(viewLifecycleOwner, {
            if (selectedParty == "all" ) adapter.submitList(partyMembersViewModel.parliamentMembers.value)
            else adapter.submitList(partyMembersViewModel.parliamentMembers.value?.filter { it.party==selectedParty })
        })

        //When-claude for setting the right partylogo on the top of the fragment
        when (selectedParty) {
            "kesk" -> binding.partyLogo.setImageResource(R.drawable.keskusta_logo_2020)
            "ps" -> binding.partyLogo.setImageResource(R.drawable.peruss_logo_rgb)
            "sd" -> binding.partyLogo.setImageResource(R.drawable.sdp)
            "kd" -> binding.partyLogo.setImageResource(R.drawable.kd)
            "vas" -> binding.partyLogo.setImageResource(R.drawable.vas)
            "kok" -> binding.partyLogo.setImageResource(R.drawable.kokoomus)
            "r" -> binding.partyLogo.setImageResource(R.drawable.rkp)
            "vihr" -> binding.partyLogo.setImageResource(R.drawable.vihrea)
            "liik" -> binding.partyLogo.setImageResource(R.drawable.liik)
            "all" -> binding.partyLogo.setImageResource(R.drawable.eduskunta)
        }

        //Spinner listener, which takes the users actions and sets the selected party members on the recyclerview
        //Also changes the partylogo
        //
        //There is an issue with the spinner because the selected party is always assigned before the spinner, so the
        //users actions are assigned "on top" of the previously assigned data. This is an extra feature, therefore fixing
        //this wasn't my main purpose due to the deadline of this project

        binding.partySpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                when (position) {
                    1 -> {
                        adapter.submitList(partyMembersViewModel.parliamentMembers.value?.filter { it.party=="vihr" })
                        binding.partyLogo.setImageResource(R.drawable.vihrea)
                    }
                    2 -> {
                        adapter.submitList(partyMembersViewModel.parliamentMembers.value?.filter { it.party=="vas" })
                        binding.partyLogo.setImageResource(R.drawable.vas)
                    }
                    3 -> {
                        adapter.submitList(partyMembersViewModel.parliamentMembers.value?.filter { it.party=="kesk" })
                        binding.partyLogo.setImageResource(R.drawable.keskusta_logo_2020)
                    }
                    4 -> {
                        adapter.submitList(partyMembersViewModel.parliamentMembers.value?.filter { it.party=="ps" })
                        binding.partyLogo.setImageResource(R.drawable.peruss_logo_rgb)
                    }
                    5 -> {
                        adapter.submitList(partyMembersViewModel.parliamentMembers.value?.filter { it.party=="sd" })
                        binding.partyLogo.setImageResource(R.drawable.sdp)
                    }
                    6 -> {
                        adapter.submitList(partyMembersViewModel.parliamentMembers.value?.filter { it.party=="kd" })
                        binding.partyLogo.setImageResource(R.drawable.kd)
                    }
                    7 -> {
                        adapter.submitList(partyMembersViewModel.parliamentMembers.value?.filter { it.party=="kok" })
                        binding.partyLogo.setImageResource(R.drawable.kokoomus)
                    }
                    8 -> {
                        adapter.submitList(partyMembersViewModel.parliamentMembers.value?.filter { it.party=="r" })
                        binding.partyLogo.setImageResource(R.drawable.rkp)
                    }
                    9 -> {
                        adapter.submitList(partyMembersViewModel.parliamentMembers.value?.filter { it.party=="liik" })
                        binding.partyLogo.setImageResource(R.drawable.liik)
                    }
                }
            }
        }

        setHasOptionsMenu(true)

        return binding.root
    }

}
