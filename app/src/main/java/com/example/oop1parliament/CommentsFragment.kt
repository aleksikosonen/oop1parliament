package com.example.oop1parliament

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.oop1parliament.databinding.FragmentCommentsBinding
import com.example.oop1parliament.viewmodels.CommentsViewModel

class CommentsFragment : Fragment() {
    private lateinit var commentsViewModel: CommentsViewModel
    private lateinit var binding: FragmentCommentsBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        //Binding and viewmodel variables for Comments fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_comments, container, false)
        commentsViewModel = ViewModelProvider(this).get(CommentsViewModel::class.java)

        //Get name and heteka from bundle
        var selectedHeteka = arguments?.getInt("Selected heteka") ?: 1297
        var selectedName = arguments?.getString("Name") ?: "Ei nimeä"

        //Add selected membername to the title
        binding.commentsTitle.append(selectedName)

        //Observe membersToVote-livedata to display the comments given to member
        commentsViewModel.membersToVote.observe(viewLifecycleOwner) {
            binding.detailsName.text = "${it.find { it.hetekaId==selectedHeteka }?.comments ?: "Ei kommentteja"}"
        }

        return binding.root
    }

}