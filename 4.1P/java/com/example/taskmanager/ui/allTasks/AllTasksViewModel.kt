package com.example.taskmanager.ui.allTasks

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class AllTasksViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is allNotes Fragment"
    }
    val text: LiveData<String> = _text
}