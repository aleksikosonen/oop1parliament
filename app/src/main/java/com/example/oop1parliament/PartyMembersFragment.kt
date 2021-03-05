package com.example.oop1parliament

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.*
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.oop1parliament.databinding.FragmentPartyMembersBinding
import com.example.oop1parliament.recyclerview.MemberListAdapter
import com.example.oop1parliament.viewmodels.PartyMembersViewModel


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [PartyFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class PartyMembersFragment : Fragment() {
    lateinit var binding: FragmentPartyMembersBinding
    lateinit var partyMembersViewModel : PartyMembersViewModel
    private lateinit var adapter: MemberListAdapter

    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_party_members, container, false)

        partyMembersViewModel = ViewModelProvider(this).get(PartyMembersViewModel::class.java)

        var selectedParty = arguments?.getString("party") ?: "all"

        val context = requireContext().applicationContext

        adapter = MemberListAdapter(context)
        binding.memberView.adapter = adapter
        binding.memberView.layoutManager = LinearLayoutManager(context)

        ArrayAdapter.createFromResource(context,
                R.array.parties_array,
                R.layout.support_simple_spinner_dropdown_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.partySpinner.adapter = adapter
        }

        partyMembersViewModel.parliamentMembers.observe(viewLifecycleOwner, {
            if (selectedParty == "all" ) adapter.submitList(partyMembersViewModel.parliamentMembers.value)
            else adapter.submitList(partyMembersViewModel.parliamentMembers.value?.filter { it.party==selectedParty })
        })

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


        binding.buttonToMember.setOnClickListener { view: View ->
            view.findNavController().navigate(R.id.action_global_memberDetailsFragment)
        }

        setHasOptionsMenu(true)

        return binding.root
    }

}
