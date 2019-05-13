package project.iksandecade.avp.view.main

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import project.iksandecade.avp.R
import project.iksandecade.avp.adapter.MainRecyclerAdapter
import project.iksandecade.avp.adapter.OnLoadMoreListener

class MainActivity : AppCompatActivity(), MainView {
    private lateinit var presenter: MainPresenter
    private lateinit var adapter: MainRecyclerAdapter

    private var isLoading = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        presenter = MainPresenter(this)
        rvMain.layoutManager = LinearLayoutManager(applicationContext)
        adapter = MainRecyclerAdapter(presenter, rvMain)
        rvMain.adapter = adapter

        initListener()

        swlMain.isRefreshing = true
        presenter.loadNews()
    }

    private fun initListener() {
        swlMain.setOnRefreshListener {
            isLoading = true
            presenter.resetPage()
            presenter.loadNews()
        }

        adapter.setOnLoadMoreListener(object : OnLoadMoreListener {
            override fun onLoadMore() {
                if (presenter.isItemMore()) {
                    presenter.addNullList()
                    adapter.notifyItemInserted(presenter.getNewsCount() - 1)
                    presenter.loadNews()
                }
            }
        })
    }

    override fun notifyItemRemove() {
        adapter.notifyItemRemoved(presenter.getNewsCount())
    }

    override fun refreshList() {
        swlMain.isRefreshing = false
        adapter.setLoaded()
        adapter.notifyDataSetChanged()
    }
}
