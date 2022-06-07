@file:Suppress("RemoveRedundantCallsOfConversionMethods", "RemoveRedundantCallsOfConversionMethods",
    "RemoveRedundantCallsOfConversionMethods", "KotlinDeprecation", "KotlinDeprecation",
    "KotlinDeprecation", "KotlinDeprecation"
)

package com.coufie.listnewsnavigationcomponenttugasch8

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.coufie.listnewsnavigationcomponenttugasch8.model.GetNewsItem
import kotlinx.android.synthetic.main.fragment_detail.*


class DetailFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        val newsData = arguments?.getSerializable("NEWSDETAIL") as GetNewsItem?
        val newsDataa = arguments?.getParcelable<GetNewsItem>("NEWSDETAIL") as GetNewsItem


        Glide.with(this).load(newsDataa!!.image).into(iv_imagedetail)

        tv_author_detail.text = newsDataa!!.author.toString()
        tv_title_detail.text = newsDataa!!.title.toString()
        tv_description_detail.text = newsDataa!!.description.toString()
    }

}