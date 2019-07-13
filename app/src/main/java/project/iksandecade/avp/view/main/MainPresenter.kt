package project.iksandecade.avp.view.main

import project.iksandecade.avp.adapter.view.NewsRowView
import project.iksandecade.avp.base.BasePresenter
import project.iksandecade.avp.network.RequestCallback
import project.iksandecade.avp.response.NewsResponse
import project.iksandecade.avp.util.AVPUtils
import project.iksandecade.avp.util.Constant
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

class MainPresenter(activity: MainActivity) : BasePresenter<MainView>() {
    private val newsList: MutableList<NewsResponse.Article?> = mutableListOf()

    private var itemSize = 0
    private var page = 1
    private val pageSize = 10

    fun getItemPosition(position: Int): NewsResponse.Article? {
        return newsList[position]
    }

    fun getNewsCount(): Int {
        return newsList.size
    }

    fun onBindNewsRowAtPosition(newsRowView: NewsRowView, position: Int) {
        val data = newsList[position]
        val title = data?.title
        val desc = data?.description
        val image = data?.urlToImage
        val date = AVPUtils.getSimplifiedDate(data?.publishedAt)
        val sources = "Sources : " + data?.source?.name

        newsRowView.setData(title, desc, image, date, sources)
    }

    fun loadNews() {
        if (page == 1) {
            newsList.clear()
        }

        networkService?.getTopHeadlines(Constant.API_KEY, "ID", pageSize, page)
                ?.subscribeOn(Schedulers.io())
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.subscribe(object : RequestCallback<NewsResponse>() {
                    override fun onSuccess(model: NewsResponse) {
                        if (page != 1) {
                            removeNullItem()
                            view?.notifyItemRemove()
                        }
                        itemSize = model.totalResults
                        model.articles?.let {
                            newsList.addAll(it)
                        }

                        updatePage()
                        view?.refreshList()
                    }

                    override fun onFailure(message: String, code: Int) {
                    }

                    override fun onFinish() {
                    }

                })
    }

    fun resetPage() {
        page = 1
    }

    fun isItemMore(): Boolean {
        return page != 1
    }

    fun removeNullItem() {
        newsList.removeAt(newsList.size - 1)
    }


    fun addNullList() {
        newsList.add(null)
    }

    fun updatePage() {
        val totalPage = Math.ceil(itemSize.toDouble() / pageSize.toDouble()).toInt()
        if ((page + 1) <= totalPage) {
            page++
        } else {
            page = 1
        }
    }

    init {
        super.attachView(activity as MainView, activity)
    }
}
