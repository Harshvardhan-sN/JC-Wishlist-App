package ind.lke.mywishlist

import android.app.Application

class WishListApp: Application() {
    override fun onCreate() {
        super.onCreate()
        Graph.provider(this@WishListApp)
    }
}