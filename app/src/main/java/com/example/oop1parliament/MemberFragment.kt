package com.example.oop1parliament

import android.app.Application
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.observe
import com.example.oop1parliament.databinding.FragmentMemberBinding
import androidx.lifecycle.*
import androidx.navigation.findNavController
import com.bumptech.glide.Glide
import kotlinx.coroutines.handleCoroutineException
import kotlin.random.Random


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [MemberFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MemberFragment : Fragment() {
    //private lateinit var viewModel: MainViewModel
    private lateinit var viewModel: MemberViewModel
    private lateinit var binding: FragmentMemberBinding
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

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_member, container, false)
        viewModel = ViewModelProvider(this).get(MemberViewModel::class.java)
        binding.viewModel = viewModel

        var selectedHeteka = arguments?.getInt("heteka") ?: 1297

        viewModel.parliamentMembers.observe(viewLifecycleOwner) {

            /*Glide.with(this).load("https://avoindata.eduskunta.fi/" + it.find{it.hetekaId==selectedHeteka }?.pictureUrl).into(binding.lennu)

            binding.memberName.text = viewModel.getMemberName(selectedHeteka)

            when (viewModel.getMemberParty(selectedHeteka)) {
                "kesk" -> binding.logoView.setImageResource(R.drawable.keskusta_logo_2020)
                "ps" -> binding.logoView.setImageResource(R.drawable.peruss_logo_rgb)
                "sd" -> binding.logoView.setImageResource(R.drawable.sdp)
                "kd" -> binding.logoView.setImageResource(R.drawable.kd)
                "vas" -> binding.logoView.setImageResource(R.drawable.vas)
                "kok" -> binding.logoView.setImageResource(R.drawable.kokoomus)
                "r" -> binding.logoView.setImageResource(R.drawable.rkp)
                "vihr" -> binding.logoView.setImageResource(R.drawable.vihrea)
                "liik" -> binding.logoView.setImageResource(R.drawable.liik)
            }*/

            getMemberDetails(selectedHeteka)

        }

        viewModel.membersToVote.observe(viewLifecycleOwner) {
            binding.likeCount.text = it.find { it.hetekaId == selectedHeteka }?.likeCount.toString()
            if (binding.likeCount.text=="null") binding.likeCount.text = "0"
        }

        binding.likeButton.setOnClickListener {
            val likeAmount = binding.likeCount.text.toString().toIntOrNull() ?: 0
            viewModel.voteMember(selectedHeteka, likeAmount + 1)
        }

        binding.dislikeButton.setOnClickListener{
            val likeAmount = binding.likeCount.text.toString().toIntOrNull() ?: 0
            viewModel.voteMember(selectedHeteka, likeAmount - 1)
        }

        binding.toDetails.setOnClickListener{view : View ->
            view.findNavController().navigate(R.id.action_memberFragment_to_detailsFragment)
        }

        binding.getRandomMember.setOnClickListener {
            val randomIndex = viewModel.parliamentMembers.value?.size ?: 0
            val random = Random.nextInt(randomIndex)
            selectedHeteka = viewModel.parliamentMembers.value?.get(random)?.hetekaId ?: 1297
            getMemberDetails(selectedHeteka)
        }

        setHasOptionsMenu(true)
        return binding.root
    }

    private fun getMemberDetails(selectedHeteka: Int) {
        Glide.with(this).load("https://avoindata.eduskunta.fi/" + viewModel.parliamentMembers.value?.find{it.hetekaId==selectedHeteka }?.pictureUrl).into(binding.lennu)

        binding.memberName.text = viewModel.getMemberName(selectedHeteka)

        when (viewModel.getMemberParty(selectedHeteka)) {
            "kesk" -> binding.logoView.setImageResource(R.drawable.keskusta_logo_2020)
            "ps" -> binding.logoView.setImageResource(R.drawable.peruss_logo_rgb)
            "sd" -> binding.logoView.setImageResource(R.drawable.sdp)
            "kd" -> binding.logoView.setImageResource(R.drawable.kd)
            "vas" -> binding.logoView.setImageResource(R.drawable.vas)
            "kok" -> binding.logoView.setImageResource(R.drawable.kokoomus)
            "r" -> binding.logoView.setImageResource(R.drawable.rkp)
            "vihr" -> binding.logoView.setImageResource(R.drawable.vihrea)
            "liik" -> binding.logoView.setImageResource(R.drawable.liik)
        }

    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment MemberFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            MemberFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
/*
class MainViewModel(application: Application) : AndroidViewModel(application) {
    val members: LiveData<List<ParliamentMember>> = ParliamentMemberDB.getInstance(application.applicationContext).parliamentMemberDao.getAll()
}*/