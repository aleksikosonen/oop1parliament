package com.example.oop1parliament

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.observe
import androidx.lifecycle.*
import androidx.navigation.findNavController
import com.bumptech.glide.Glide
import com.example.oop1parliament.databinding.FragmentMemberDetailsBinding


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [MemberFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MemberDetailsFragment : Fragment() {
    //private lateinit var viewModel: MainViewModel
    private lateinit var memberDetailsViewModel: MemberDetailsViewModel
    private lateinit var binding: FragmentMemberDetailsBinding
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private var BASE_URL = "https://avoindata.eduskunta.fi/"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        var selectedHeteka = arguments?.getInt("heteka") ?: 1297

        val application = requireNotNull(activity).application
        val viewModelFactory = MemberViewModelFactory(application, selectedHeteka)
        memberDetailsViewModel = ViewModelProvider(this, viewModelFactory).get(MemberDetailsViewModel::class.java)

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_member_details, container, false)

        memberDetailsViewModel.parliamentMembers.observe(viewLifecycleOwner) { getMemberDetails(selectedHeteka) }

        memberDetailsViewModel.membersToVote.observe(viewLifecycleOwner) {
            binding.likeCount.text = it.find { it.hetekaId == selectedHeteka }?.likeCount.toString()
            if (binding.likeCount.text=="null") binding.likeCount.text = "0"
        }

        binding.likeButton.setOnClickListener {
            val addedComment = ""
            val likeAmount = binding.likeCount.text.toString().toIntOrNull() ?: 0
            memberDetailsViewModel.voteMember(selectedHeteka, likeAmount + 1, addedComment)
        }

        memberDetailsViewModel.membersToVote.observe(viewLifecycleOwner) {

            var previousComments = it.find { it.hetekaId==selectedHeteka }?.comments.toString()
            if (previousComments==null) previousComments = ""

            binding.comment.setOnClickListener {
                val addedComment = binding.addComment.text.toString() + "\n"
                val likeAmount = binding.likeCount.text.toString().toIntOrNull() ?: 0
                memberDetailsViewModel.voteMember(selectedHeteka, likeAmount + 0, previousComments + addedComment)
                Toast.makeText(requireContext().applicationContext, "Lisättiin kommentti ${addedComment}", Toast.LENGTH_SHORT).show()
            }
        }

        binding.dislikeButton.setOnClickListener{
            val addedComment = ""
            val likeAmount = binding.likeCount.text.toString().toIntOrNull() ?: 0
            memberDetailsViewModel.voteMember(selectedHeteka, likeAmount - 1, addedComment)
        }

        binding.toDetails.setOnClickListener{view : View ->
            view.findNavController().navigate(R.id.action_memberDetailsFragment_to_commentsFragment)
        }

        binding.toDetails.setOnClickListener{view : View ->
            val bundle = bundleOf("Selected heteka" to selectedHeteka, "Name" to  memberDetailsViewModel.getMemberName() )
            view.findNavController().navigate(R.id.action_memberDetailsFragment_to_commentsFragment, bundle)
        }

        setHasOptionsMenu(true)
        return binding.root
    }

    private fun getMemberDetails(selectedHeteka: Int) {
        Glide.with(this)
                .load(BASE_URL + memberDetailsViewModel.getUrl())
                        .into(binding.lennu)

        binding.memberName.text = memberDetailsViewModel.getMemberName()

        when (memberDetailsViewModel.getParty()) {
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
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            MemberDetailsFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}