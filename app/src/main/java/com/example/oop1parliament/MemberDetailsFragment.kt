package com.example.oop1parliament

import android.os.Bundle
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

class MemberDetailsFragment : Fragment() {

    private lateinit var memberDetailsViewModel: MemberDetailsViewModel
    private lateinit var binding: FragmentMemberDetailsBinding

    //base url for the api
    private var BASE_URL = "https://avoindata.eduskunta.fi/"

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        //Receive selected member heteka-number from bundle. Elvis returns (not the building) Sanna Marin
        var selectedHeteka = arguments?.getInt("heteka") ?: 1297

        //Get app context
        val application = requireNotNull(activity).application

        //Viewmodel factory for passing the selected hetekea for the viewmodel.
        // Could be provided as method parameters, but I wanted to experiment with transferring the data this way
        val viewModelFactory = MemberViewModelFactory(application, selectedHeteka)

        //Binding and viewmodel variables for MemberDetails fragment
        memberDetailsViewModel = ViewModelProvider(this, viewModelFactory).get(MemberDetailsViewModel::class.java)
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_member_details, container, false)

        //Observer for parliament members livedata, the function assings members details to screen
        memberDetailsViewModel.parliamentMembers.observe(viewLifecycleOwner) { getMemberDetails() }

        //Observer for members vote livedata
        memberDetailsViewModel.membersToVote.observe(viewLifecycleOwner) {
            //Likecount text is set to zero manually, because if the member has no likes, the value is null
            binding.likeCount.text = it.find { it.hetekaId == selectedHeteka }?.likeCount.toString()
            if (binding.likeCount.text=="null") binding.likeCount.text = "0"

            //Previous comments is also set manually because if there are no comments, it is null
            var previousComments = it.find { it.hetekaId==selectedHeteka }?.comments.toString()
            if (previousComments=="null") previousComments = ""

            //Comment is passed as string for the votemember-function. The function uses the DAO:s insert or update member function
            //and therefore both comments and likes need to be assigned on the function
            binding.comment.setOnClickListener {
                val addedComment = binding.addComment.text.toString() + "\n"
                val likeAmount = binding.likeCount.text.toString().toIntOrNull() ?: 0
                memberDetailsViewModel.voteMember(selectedHeteka, likeAmount + 0, previousComments + addedComment)
                Toast.makeText(requireContext().applicationContext, "Lisättiin kommentti ${addedComment}", Toast.LENGTH_SHORT).show()
            }

            //Like and dislike increase and decrease the likeamount by 1
            binding.dislikeButton.setOnClickListener{
                val addedComment = ""
                val likeAmount = binding.likeCount.text.toString().toIntOrNull() ?: 0
                memberDetailsViewModel.voteMember(selectedHeteka, likeAmount - 1, previousComments + addedComment)
            }

            binding.likeButton.setOnClickListener {
                val addedComment = ""
                val likeAmount = binding.likeCount.text.toString().toIntOrNull() ?: 0
                memberDetailsViewModel.voteMember(selectedHeteka, likeAmount + 1, previousComments + addedComment)
            }
        }

        //Button to moving to the comments. The selected member heteka and name are passed as bundle to the comment fragment
        binding.toDetails.setOnClickListener{view : View ->
            val bundle = bundleOf("Selected heteka" to selectedHeteka, "Name" to  memberDetailsViewModel.getMemberName() )
            view.findNavController().navigate(R.id.action_global_commentsFragment, bundle)
        }

        setHasOptionsMenu(true)
        return binding.root
    }

    //Function for displaying members details to the fragment
    private fun getMemberDetails() {
        Glide.with(this)
                .load(BASE_URL + memberDetailsViewModel.getUrl())
                        .into(binding.memberPhoto)

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
}