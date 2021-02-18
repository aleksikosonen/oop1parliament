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

        var memberIndex = 0
        val selectedHeteka = arguments?.getInt("heteka") ?: 1297

        val binding: FragmentMemberBinding = DataBindingUtil.inflate(inflater,
            R.layout.fragment_member,container,false)

        viewModel = ViewModelProvider(this).get(MemberViewModel::class.java)
        binding.viewModel = viewModel

        Log.d("FFF", "${viewModel.members.value?.filter { it.party=="vas" }?.map { it.firstname }}")
        Log.d("FFF", "${viewModel.memberList}")

        val memberDetail = viewModel.p.members.filter { it.hetekaId==selectedHeteka }.mapNotNull { it }

        binding.memberName.text = memberDetail[0].firstname + " " + memberDetail[0].lastname

        when (memberDetail[0].party) {
            "kesk" -> binding.logoView.setImageResource(R.drawable.keskusta_logo_2020)
            "ps" -> binding.logoView.setImageResource(R.drawable.peruss_logo_rgb)
            "sd" -> binding.logoView.setImageResource(R.drawable.sdp)
            "kd" -> binding.logoView.setImageResource(R.drawable.kd)
            "vas" -> binding.logoView.setImageResource(R.drawable.vas)
            "kok" -> binding.logoView.setImageResource(R.drawable.kokoomus)
            "r" -> binding.logoView.setImageResource(R.drawable.rkp)
            "vihr" -> binding.logoView.setImageResource(R.drawable.vihrea)
        }

        viewModel.positiveSum.observe(viewLifecycleOwner, {
            binding.likeCount.text = it.toString()
        })

        binding.getRandomMember.setOnClickListener {
            memberIndex = Random.nextInt(viewModel.p.members.size)
            binding.memberName.text = viewModel.p.members[memberIndex].firstname + " " + viewModel.p.members[memberIndex].lastname
            when (viewModel.p.members[memberIndex].party) {
                 "kesk" -> binding.logoView.setImageResource(R.drawable.keskusta_logo_2020)
                 "ps" -> binding.logoView.setImageResource(R.drawable.peruss_logo_rgb)
                 "sd" -> binding.logoView.setImageResource(R.drawable.sdp)
                 "kd" -> binding.logoView.setImageResource(R.drawable.kd)
                 "vas" -> binding.logoView.setImageResource(R.drawable.vas)
                 "kok" -> binding.logoView.setImageResource(R.drawable.kokoomus)
                 "r" -> binding.logoView.setImageResource(R.drawable.rkp)
                 "vihr" -> binding.logoView.setImageResource(R.drawable.vihrea)
            }
        }


        binding.likeButton.setOnClickListener {
            viewModel.likeCounter.inc(1)
        }

        binding.dislikeButton.setOnClickListener{
            viewModel.likeCounter.inc(-1)
        }

        binding.toDetails.setOnClickListener{view : View ->
            view.findNavController().navigate(R.id.action_memberFragment_to_detailsFragment)
        }

        setHasOptionsMenu(true)
        return binding.root
    }

    private fun getPartyLogo(list: List<ParliamentMember>) {
                //TODO
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