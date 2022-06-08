@file:Suppress("MemberVisibilityCanBePrivate", "MemberVisibilityCanBePrivate",
    "MemberVisibilityCanBePrivate", "UnusedImport"
)

package com.coufie.listnewsnavigationcomponenttugasch8

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.Navigation
import com.coufie.listnewsnavigationcomponenttugasch8.model.GetUserItem
import com.coufie.listnewsnavigationcomponenttugasch8.model.PostUser
import com.coufie.listnewsnavigationcomponenttugasch8.network.NewsApi
import kotlinx.android.synthetic.main.fragment_register.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_register, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        register()
        login()
    }


    fun register(){

        btn_register.setOnClickListener {

            if(et_register_password.text.isEmpty() || et_register_username.text.isEmpty()){
                Toast.makeText(requireContext(), "Data belum lengkap", Toast.LENGTH_SHORT).show()
            }else{
                if(et_register_password.text.toString() != et_confirm_password.text.toString()){
                    Toast.makeText(requireContext(), "Password tidak samaa", Toast.LENGTH_SHORT).show()
                }else{
                    val password = et_register_password.text.toString()
                    val username = et_register_username.text.toString()
                    val address = "alamat mu1"
                    val image = "http://loremflickr.com/640/480"
                    val name = "tes mu1"
                    val umur = 25
                    registerUser(username,password, address, image, name, umur)
                    Navigation.findNavController(requireView()).navigate(R.id.action_registerFragment_to_loginFragment)

                }
            }

        }
    }

    fun registerUser(username: String,password: String, address: String, image:String, name:String, umur:Int) : Boolean{
        var messageResponse = false
        NewsApi.instance.postUser(
            PostUser(username, password, address, image, name, umur)
        ).enqueue(object : Callback<GetUserItem> {
            override fun onResponse(call: Call<GetUserItem>, response: Response<GetUserItem>) {
                messageResponse = response.isSuccessful
            }

            override fun onFailure(call: Call<GetUserItem>, t: Throwable) {
                messageResponse = false
            }

        })
        return messageResponse
    }

    fun login(){
        tv_goto_login.setOnClickListener {
            Navigation.findNavController(requireView()).navigate(R.id.action_registerFragment_to_loginFragment)
        }
    }
}