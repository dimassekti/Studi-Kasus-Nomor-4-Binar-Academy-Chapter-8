@file:Suppress("LiftReturnOrAssignment", "RemoveEmptyClassBody", "RemoveRedundantQualifierName",
    "RemoveRedundantQualifierName", "LocalVariableName"
)

package com.coufie.listnewsnavigationcomponenttugasch8.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.coufie.listnewsnavigationcomponenttugasch8.R
import com.coufie.listnewsnavigationcomponenttugasch8.model.GetNewsItem
import kotlinx.android.synthetic.main.item_news.view.*

class NewsAdapter (private var newsData : List<GetNewsItem>, private var onClick: (GetNewsItem)->Unit) : RecyclerView.Adapter<NewsAdapter.ViewHolder>(){


    class ViewHolder (itemView : View) : RecyclerView.ViewHolder(itemView){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsAdapter.ViewHolder {

        val NewsUi = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_news , parent, false)

        return ViewHolder(NewsUi)
    }

    override fun onBindViewHolder(holder: NewsAdapter.ViewHolder, position: Int) {
        holder.itemView.tv_author.text = "Author : ${newsData[position].author}"
        holder.itemView.tv_title.text = "Title : ${newsData[position].title}"

        this.let {
            Glide.with(holder.itemView.context).load(newsData[position].image).into(holder.itemView.iv_image)
        }

        holder.itemView.newsCard.setOnClickListener {
            onClick(newsData[position])
        }
    }

    override fun getItemCount(): Int {
        if(newsData == null){
            return 0
        }else{
            return newsData.size
        }
    }
}