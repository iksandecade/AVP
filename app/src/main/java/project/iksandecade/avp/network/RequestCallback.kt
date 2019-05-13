package project.iksandecade.avp.network

import rx.Subscriber

abstract class RequestCallback<M> : Subscriber<M>() {
    abstract fun onSuccess(model: M)

    abstract fun onFailure(message: String, code: Int)

    abstract fun onFinish()

    override fun onCompleted() {
        onFinish()
    }

    override fun onError(e: Throwable?) {
        var message = "Opps, failed to get error on apps. Please contact our office"
        var code = 0
        when (e) {
            is retrofit2.adapter.rxjava.HttpException -> {
                code = e.code()
                when (code) {
                    500 -> message = "Sorry, there's something error on server. Please contact our office"
                    400 -> message = "Sorry, there's something error on apps. Please contact our office"
                    401 -> message = "Sorry, your account can't access this action. Pelase contact our office"
                    404 -> message = "Sorry, can't reach the server. Please contact our office"
                }
            }
            is javax.net.ssl.SSLHandshakeException -> message = "Opps, connection timed out. Plase check your connection to internet"
            is java.net.SocketTimeoutException -> message = "Opps, can't connect to server to server. Please check your connection to internet or contact our office"
        }

        e?.printStackTrace()
        onFailure(message, code)
        onFinish()
    }

    override fun onNext(t: M) {
        onSuccess(t)
    }
}