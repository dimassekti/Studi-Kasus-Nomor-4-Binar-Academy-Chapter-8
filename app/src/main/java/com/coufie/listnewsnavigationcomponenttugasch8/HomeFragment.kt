@file:Suppress("MemberVisibilityCanBePrivate", "MemberVisibilityCanBePrivate", "unused",
    "UsePropertyAccessSyntax", "CanBeVal", "CanBeVal", "CanBeVal", "CanBeVal"
)

package com.coufie.listnewsnavigationcomponenttugasch8

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.coufie.listnewsnavigationcomponenttugasch8.adapter.NewsAdapter
import com.coufie.listnewsnavigationcomponenttugasch8.model.GetNewsItem
import com.coufie.listnewsnavigationcomponenttugasch8.network.NewsApi
import kotlinx.android.synthetic.main.fragment_home.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class HomeFragment : Fragment() {

    val news : GetNewsItem? = null
    private lateinit var prefsLogin : SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        prefsLogin = requireContext().getSharedPreferences("SF", Context.MODE_PRIVATE)
        var username = prefsLogin.getString("USERNAME", "")
        tv_header.setText(username)

        getNewsData()
        logout()
    }

    fun logout(){
        tv_logout.setOnClickListener {
            prefsLogin.edit().clear().apply()
            Navigation.findNavController(requireView()).navigate(R.id.action_homeFragment_to_loginFragment)
        }
    }

    fun getNewsData(){
        NewsApi.instance.getNews()
            .enqueue(object : Callback<List<GetNewsItem>>{
                override fun onResponse(
                    call: Call<List<GetNewsItem>>,
                    response: Response<List<GetNewsItem>>
                ) {

                    if(response.isSuccessful){
                        val newsData = response.body()
                        val newsAdapter = NewsAdapter(newsData!!){

                            val data = bundleOf("NEWSDETAIL" to it)
                            Navigation.findNavController(requireView()).navigate(R.id.action_homeFragment_to_detailFragment, data)

                        }
                        val lm = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
                        rv_news.layoutManager = lm
                        rv_news.adapter = newsAdapter
                    }else{
                        Toast.makeText(requireContext(), response.message(), Toast.LENGTH_SHORT).show()

                    }
                }

                override fun onFailure(call: Call<List<GetNewsItem>>, t: Throwable) {
                    Toast.makeText(requireContext(), t.message, Toast.LENGTH_SHORT).show()
                }

            })
    }


}