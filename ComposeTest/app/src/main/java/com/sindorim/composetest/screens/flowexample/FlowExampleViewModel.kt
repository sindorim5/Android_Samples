package com.sindorim.composetest.screens.flowexample

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.*
import kotlin.random.Random

class FlowExampleViewModel(
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {
    //    private val _color = MutableStateFlow(0xFFFFFFFF)
//    val color = _color.asStateFlow()
    val color = savedStateHandle.getStateFlow("color", 0xFFFFFFFF)

//    var composeColor by mutableStateOf(0xFFFFFFFF)
//        private set
//    var composeColor by mutableStateOf(
//        savedStateHandle.get<Long>("color") ?: 0xFFFFFFFF
//    )
//        private set

    fun generateNewColor() {
        val color = Random.nextLong(0xFFFFFFFF)
//        _color.value = color
        savedStateHandle["color"] = color
//        composeColor = color
    }

    ////////////////////////////////////////////////////////////////

    private val _users = MutableStateFlow<List<User>>(emptyList())
    val users = _users.asStateFlow()

    private val isLoggedIn = MutableStateFlow(true)

    val test = combine(_users, isLoggedIn) { users, isLoggedIn ->
        // do something with users, isLoggedIn
    }

//    private val _localUser = MutableStateFlow<User?>(null)
//    val localUser = _localUser.asStateFlow()

    // users가 변할 때마다 localuser도 업데이트 해야함 -> 쓰레기
//    fun onUserJoined(user: User) {
//        _users.update { it + user }
//        if (user.id == "local") {
//            _localUser.update { user }
//        }
//    }

    // users의 변화를 감지하여 StateFlow를 생성
    val localUser = users.map { users ->
        users.find { it.id == "local" }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = null
    )
    // users가 update되면 localUser도 변화한다.
    fun onUserJoined(user: User) {
        _users.update { it + user }
    }


}

data class User(
    val id: String,
    val email: String,
    val name: String,
    val avatarUrl: String
)