package com.example.oop1parliament

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import kotlinx.coroutines.launch

class MemberViewModel(application: Application) : AndroidViewModel(application) {


    val p = Parliament(ParliamentMembersData.members)

    val likeCounter = memberLikeCounter()

    val positiveSum: LiveData<Int> = Transformations.distinctUntilChanged(
            Transformations.map(likeCounter.likesList) { it.sum() }
    )
}