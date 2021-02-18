package com.example.oop1parliament

import android.app.Application
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.*
import androidx.navigation.findNavController
import com.example.oop1parliament.databinding.FragmentTitleBinding
import kotlinx.coroutines.launch

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [TitleFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class TitleFragment : Fragment() {
    private lateinit var viewModel: AddMemberViewModel

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

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val binding: FragmentTitleBinding = DataBindingUtil.inflate(inflater,
            R.layout.fragment_title,container,false)

        viewModel = ViewModelProvider(this).get(AddMemberViewModel::class.java)

        binding.playButton.setOnClickListener { view : View ->
            Log.d("DDD", "Launched")
            view.findNavController().navigate(R.id.action_titleFragment_to_partyFragment)
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
         * @return A new instance of fragment TitleFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            TitleFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}

class AddMemberViewModel(application: Application) : AndroidViewModel(application) {
    private val memberRepository = MemberRepository(ParliamentMemberDB.getInstance(application.applicationContext))
    val memberList = memberRepository.members

    private val _members = MutableLiveData<List<ParliamentMember>>()
    val members : LiveData<List<ParliamentMember>>
        get() = _members

    init {
        insertMembers()
    }

    private fun insertMembers() {
        viewModelScope.launch {
            try {
                memberRepository.getMembers()

            } catch (e: Exception) {
                Log.d("***", e.toString())
            }
        }
    }
    //val p = Parliament(ParliamentMembersData.members)

    /*fun addMember(hetekaId: Int, seatNumber: Int, likes: Int, lastname: String, firstname: String, party: String, minister: Boolean, pictureUrl: String) {
        val context = getApplication<Application>().applicationContext
        viewModelScope.launch {
            ParliamentMemberDB.getInstance(context)
                .parliamentMemberDao
                .insertOrUpdate(ParliamentMember(hetekaId, seatNumber, likes, lastname, firstname, party, minister, pictureUrl))
        }
    }*/
}