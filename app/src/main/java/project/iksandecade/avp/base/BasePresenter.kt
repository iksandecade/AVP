package project.iksandecade.avp.base

import android.app.Activity
import project.iksandecade.avp.network.NetworkClient
import project.iksandecade.avp.network.NetworkService
import rx.Observable
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

open class BasePresenter<V> {

    open var view: V? = null
    open lateinit var activity: Activity
    protected var networkService: NetworkService? = null

    fun attachView(view: V?, activity: Activity) {
        this.view = view
        this.activity = activity
        val retrofit = NetworkClient.request()
        networkService = retrofit?.create(NetworkService::class.java)
    }

}