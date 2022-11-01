package com.harera.hayat.ui.chats

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.harera.hayat.databinding.FragmentChatsBinding

class ChatsFragment : Fragment() {

    private lateinit var chatsViewModel: ChatsViewModel
    private lateinit var bind: FragmentChatsBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        bind = FragmentChatsBinding.inflate(layoutInflater)

        chatsViewModel =
            ViewModelProvider(this).get(ChatsViewModel::class.java)


        return bind.root
    }
}