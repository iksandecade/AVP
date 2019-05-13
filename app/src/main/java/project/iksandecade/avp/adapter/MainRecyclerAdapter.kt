package project.iksandecade.avp.adapter

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_news.view.*
import project.iksandecade.avp.R
import project.iksandecade.avp.adapter.view.NewsRowView
import project.iksandecade.avp.view.main.MainPresenter

class MainRecyclerAdapter(private val presenter: MainPresenter, recyclerView: RecyclerView) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var onLoadMoreListener: OnLoadMoreListener? = null

    private val typeItem = 0
    private val typeLoading = 1
    private val visibleThreshold = 5
    private var lastVisibleItem = 0
    private var totalItemCount = 0
    private var isLoading = false

    init {
        val linearLayoutManager = recyclerView.layoutManager as LinearLayoutManager
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                totalItemCount = linearLayoutManager.itemCount
                lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition()

                if (!isLoading && totalItemCount <= (lastVisibleItem + visibleThreshold)) {
                    if (onLoadMoreListener != null) {
                        onLoadMoreListener?.onLoadMore()
                    }
                    isLoading = true
                }
            }
        })
    }

    override fun getItemViewType(position: Int): Int {
        return if (presenter.getItemPosition(position) == null) {
            typeLoading
        } else {
            typeItem
        }
    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): RecyclerView.ViewHolder {
        return if (p1 == typeLoading) {
            LoadingViewHolder(LayoutInflater.from(p0.context).inflate(R.layout.loading, p0, false))
        } else {
            Holder(LayoutInflater.from(p0.context).inflate(R.layout.item_news, p0, false))
        }
    }

    override fun getItemCount(): Int {
        return presenter.getNewsCount()
    }

    override fun onBindViewHolder(p0: RecyclerView.ViewHolder, p1: Int) {
        if (p0 is Holder) {
            presenter.onBindNewsRowAtPosition(p0, p1)
        }
    }

    fun setOnLoadMoreListener(onLoadMoreListener: OnLoadMoreListener) {
        this.onLoadMoreListener = onLoadMoreListener
    }

    fun setLoaded() {
        isLoading = false
    }

    class Holder(itemView: View) : RecyclerView.ViewHolder(itemView), NewsRowView {
        override fun setData(title: String?, desc: String?, image: String?, date: String, sources: String) {
            itemView.tvTitle.text = title
            itemView.tvDesc.text = desc
            itemView.tvDate.text = date
            itemView.tvSources.text = sources
            Glide.with(itemView).load(image).into(itemView.ivNews)
        }

    }

}
