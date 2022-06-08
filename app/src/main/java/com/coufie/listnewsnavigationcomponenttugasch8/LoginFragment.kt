@file:Suppress("MemberVisibilityCanBePrivate", "MemberVisibilityCanBePrivate", "UnusedImport",
    "UnusedImport", "UnusedImport", "CanBeVal", "CanBeVal", "CanBeVal"
)

package com.coufie.listnewsnavigationcomponenttugasch8

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.Navigation
import com.coufie.listnewsnavigationcomponenttugasch8.model.GetUserItem
import com.coufie.listnewsnavigationcomponenttugasch8.network.NewsApi
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Response


class LoginFragment : Fragment() {

    private lateinit var userLogin : List<GetUserItem>
    private lateinit var prefsLogin : SharedPreferences


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        prefsLogin = requireContext().getSharedPreferences("SF", Context.MODE_PRIVATE)
        var username = prefsLogin.getString("USERNAME", "")
        prefsLogin.edit().putString("USERNAME", username).apply()


            if(requireContext().getSharedPreferences("SF", Context.MODE_PRIVATE).contains("USERNAME") && requireContext().getSharedPreferences("SF", Context.MODE_PRIVATE).contains("PASSWORD")){
                Navigation.findNavController(requireView()).navigate(R.id.action_loginFragment_to_homeFragment)
            }

        login()

        tv_goto_register.setOnClickListener {
            Navigation.findNavController(requireView()).navigate(R.id.action_loginFragment_to_registerFragment)

        }
    }

    fun login(){
        btn_login.setOnClickListener {
            if(et_input_password.text.isNotEmpty() && et_input_username.text.isNotEmpty()){
                var username = et_input_username.text.toString()
                var password = et_input_password.text.toString()

                login(username, password)
            }else{
                Toast.makeText(requireContext(), "Mohon isi form login", Toast.LENGTH_SHORT).show()

            }
        }
    }

    fun login(username : String, password : String){
        NewsApi.instance.getUser(username)
            .enqueue(object : retrofit2.Callback<List<GetUserItem>>{
                override fun onResponse(
                    call: Call<List<GetUserItem>>,
                    response: Response<List<GetUserItem>>
                ) {
                    if(response.isSuccessful){
                        if(response.body()?.isEmpty() == true){
                            Toast.makeText(requireContext(), "User tidak ditemukan", Toast.LENGTH_SHORT).show()
                        }else{
                            when{
                                response.body()?.size!! > 1 -> {
                                    Toast.makeText(requireContext(), "Mohon masukan data yang benar", Toast.LENGTH_SHORT).show()
                                }
                                username!=response.body()!![0].username -> {
                                    Toast.makeText(requireContext(), "Username tidak terdaftar", Toast.LENGTH_SHORT).show()
                                }
                                password!=response.body()!![0].password -> {
                                    Toast.makeText(requireContext(), "Password Salah", Toast.LENGTH_SHORT).show()
                                }
                                else -> {
                                    userLogin = response.body()!!
                                    detailUser(userLogin)

                                    Navigation.findNavController(requireView()).navigate(R.id.action_loginFragment_to_homeFragment)

                                }
                            }
                        }
                    }else{
                        Toast.makeText(requireContext(), response.message(), Toast.LENGTH_SHORT).show()

                    }
                }

                override fun onFailure(call: Call<List<GetUserItem>>, t: Throwable) {
                    Toast.makeText(requireContext(), t.message, Toast.LENGTH_SHORT).show()
                }

            })
    }


    fun detailUser(listLogin : List<GetUserItem>){
        for(i in listLogin.indices){

            prefsLogin.edit().putString("PASSWORD", listLogin[i].password).apply()
            prefsLogin.edit().putString("USERNAME", listLogin[i].username).apply()
        }
    }

}