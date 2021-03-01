package com.example.oop1parliament

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.navigation.findNavController
import androidx.recyclerview.widget.*
import com.example.oop1parliament.databinding.FragmentPartySelectBinding
import com.example.oop1parliament.viewmodels.PartySelectViewModel

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [PartySelect.newInstance] factory method to
 * create an instance of this fragment.
 */
class PartySelect : Fragment() {
    private lateinit var binding: FragmentPartySelectBinding
    private lateinit var partySelectViewModel: PartySelectViewModel
    private lateinit var adapter: PartyListAdapter

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

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_party_select, container, false)
        binding.moveToParty.setOnClickListener { view : View -> view.findNavController().navigate(R.id.action_partySelect_to_partyMembersFragment) }

        partySelectViewModel = ViewModelProvider(this).get(PartySelectViewModel::class.java)

        //binding.partyView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding.partyView.layoutManager = GridLayoutManager(context, 3)
        adapter = PartyListAdapter(requireContext().applicationContext)
        binding.partyView.adapter = adapter

        partySelectViewModel.parliamentParties.observe(viewLifecycleOwner) {
            it?.let {
                adapter.submitList(it)
            }
        }

        return binding.root
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment PartySelect.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
                PartySelect().apply {
                    arguments = Bundle().apply {
                        putString(ARG_PARAM1, param1)
                        putString(ARG_PARAM2, param2)
                    }
                }
    }
}


class PartyListAdapter(private val context: Context): ListAdapter<String, ViewHolder2>(MyDiffCallback()) {
    override fun onCreateViewHolder(vg: ViewGroup, vt: Int): ViewHolder2 {
        val itemView = LayoutInflater.from(context).inflate(R.layout.party_list_view, vg, false)
        return ViewHolder2(itemView)
    }

    override fun onBindViewHolder(vh: ViewHolder2, pos: Int) {
        vh.itemView.findViewById<ImageView>(R.id.partyImage).setImageResource(R.drawable.kokoomus)
        when (getItem(pos).toString()) {
            "vihr" -> vh.itemView.findViewById<ImageView>(R.id.partyImage).setImageResource(R.drawable.vihrea)
            "vas" -> vh.itemView.findViewById<ImageView>(R.id.partyImage).setImageResource(R.drawable.vas)
            "kd" -> vh.itemView.findViewById<ImageView>(R.id.partyImage).setImageResource(R.drawable.kd)
            "liik" -> vh.itemView.findViewById<ImageView>(R.id.partyImage).setImageResource(R.drawable.liik)
            "kok" -> vh.itemView.findViewById<ImageView>(R.id.partyImage).setImageResource(R.drawable.kokoomus)
            "kesk" -> vh.itemView.findViewById<ImageView>(R.id.partyImage).setImageResource(R.drawable.keskusta_logo_2020)
            "ps" -> vh.itemView.findViewById<ImageView>(R.id.partyImage).setImageResource(R.drawable.peruss_logo_rgb)
            "r" -> vh.itemView.findViewById<ImageView>(R.id.partyImage).setImageResource(R.drawable.rkp)
            "sd" -> vh.itemView.findViewById<ImageView>(R.id.partyImage).setImageResource(R.drawable.sdp)
        }
        vh.itemView.setOnClickListener{
            val selectedParty = getItem(pos)
            val bundle = bundleOf("party" to selectedParty)
            it.findNavController().navigate(R.id.action_partySelect_to_partyMembersFragment, bundle)
        }
    }
}

class ViewHolder2(itemView: View): RecyclerView.ViewHolder(itemView)

class MyDiffCallback : DiffUtil.ItemCallback<String>() {
    override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
        return oldItem == newItem
    }
}