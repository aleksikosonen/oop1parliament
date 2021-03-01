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
        //binding.viewModel = viewModel

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

        fun updateParty(party: String): String {
            selectedParty = party
            return selectedParty
        }

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
                //adapter.submitList(partyMembersViewModel.parliamentMembers.value?.filter { it.party == selectedParty })
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                //updateParty( when(position) { 1 -> "vihr" else -> "all" })
                when (position) {
                    //0 -> adapter.submitList(viewModel.parliamentMembers.value)
                    1 -> adapter.submitList(partyMembersViewModel.parliamentMembers.value?.filter { it.party=="vihr" })
                    2 -> adapter.submitList(partyMembersViewModel.parliamentMembers.value?.filter { it.party=="vas" })
                    3 -> adapter.submitList(partyMembersViewModel.parliamentMembers.value?.filter { it.party=="kesk" })
                    4 -> adapter.submitList(partyMembersViewModel.parliamentMembers.value?.filter { it.party=="ps" })
                    5 -> adapter.submitList(partyMembersViewModel.parliamentMembers.value?.filter { it.party=="sd" })
                    6 -> adapter.submitList(partyMembersViewModel.parliamentMembers.value?.filter { it.party=="kd" })
                    7 -> adapter.submitList(partyMembersViewModel.parliamentMembers.value?.filter { it.party=="kok" })
                    8 -> adapter.submitList(partyMembersViewModel.parliamentMembers.value?.filter { it.party=="r" })
                    9 -> adapter.submitList(partyMembersViewModel.parliamentMembers.value?.filter { it.party=="liik" })
                    10 -> adapter.submitList(partyMembersViewModel.parliamentMembers.value)
                }

            }
        }


        binding.buttonToMember.setOnClickListener { view: View ->
            view.findNavController().navigate(R.id.action_partyMembersFragment_to_memberDetailsFragment)
        }

        setHasOptionsMenu(true)

        return binding.root
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment PartyFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
                PartyMembersFragment().apply {
                    arguments = Bundle().apply {
                        putString(ARG_PARAM1, param1)
                        putString(ARG_PARAM2, param2)
                    }
                }
    }
}

class MemberListAdapter(private val context: Context): ListAdapter<ParliamentMember, MemberViewHolder>(MemberDiffCallback()) {

    var selectedHeteka : Int = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MemberViewHolder {
        val itemView = LayoutInflater.from(context).inflate(R.layout.member_item_view, parent, false)
        return MemberViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MemberViewHolder, position: Int) {
        holder.itemView.findViewById<TextView>(R.id.membername).text = getItem(position).firstname + " " +  getItem(position).lastname
        holder.itemView.findViewById<TextView>(R.id.memberparty).text = getItem(position).party
        holder.itemView.findViewById<ImageView>(R.id.thumbnail).setImageResource(R.drawable.vihrea)
        Glide.with(context).load("https://avoindata.eduskunta.fi/" + getItem(position).pictureUrl).into(holder.itemView.findViewById<ImageView>(R.id.thumbnail))
        holder.itemView.setOnClickListener{
            selectedHeteka = getItem(position).hetekaId
            val bundle = bundleOf("heteka" to selectedHeteka)
            it.findNavController().navigate(R.id.action_partyMembersFragment_to_memberDetailsFragment, bundle)
        }
    }
}

class MemberViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)

class MemberDiffCallback: DiffUtil.ItemCallback<ParliamentMember>() {
    override fun areItemsTheSame(oldItem: ParliamentMember, newItem: ParliamentMember): Boolean {
        return oldItem.hetekaId == newItem.hetekaId
    }
    override fun areContentsTheSame(oldItem: ParliamentMember, newItem: ParliamentMember): Boolean {
        return oldItem.firstname == newItem.firstname
    }
}

