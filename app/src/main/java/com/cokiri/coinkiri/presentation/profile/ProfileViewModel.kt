package com.cokiri.coinkiri.presentation.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cokiri.coinkiri.data.local.entity.MemberInfoEntity
import com.cokiri.coinkiri.domain.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {

    private val _memberInfo = MutableStateFlow<MemberInfoEntity?>(null)
    val memberInfo: StateFlow<MemberInfoEntity?> = _memberInfo


    init {
        loadMemberInfo()
    }

    private fun loadMemberInfo() {
        viewModelScope.launch {
            _memberInfo.value = userRepository.getMemberInfo()
        }
    }
}