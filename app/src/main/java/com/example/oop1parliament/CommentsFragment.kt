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

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class CommentsFragment : Fragment() {
    private lateinit var commentsViewModel: CommentsViewModel
    private lateinit var binding: FragmentCommentsBinding

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
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_comments, container, false)
        commentsViewModel = ViewModelProvider(this).get(CommentsViewModel::class.java)

        //Get name and heteka from bundle
        var selectedHeteka = arguments?.getInt("Selected heteka") ?: 1297
        var selectedName = arguments?.getString("Name") ?: "Ei nimeä"

        binding.commentsTitle.append(selectedName)

        //Observe membersToVote-livedata to display the comments given to member
        commentsViewModel.membersToVote.observe(viewLifecycleOwner) {
            binding.detailsName.text = "${it.find { it.hetekaId==selectedHeteka }?.comments ?: "Ei kommentteja"}"
        }

        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
                CommentsFragment().apply {
                    arguments = Bundle().apply {
                        putString(ARG_PARAM1, param1)
                        putString(ARG_PARAM2, param2)
                    }
                }
    }
}