package ind.lke.mywishlist

import android.content.Context
import androidx.room.Room
import ind.lke.mywishlist.Data.WishDatabase
import ind.lke.mywishlist.Data.WishRepository

object Graph {
    lateinit var database: WishDatabase

    val wishRepository by lazy {
        WishRepository(wishDao = database.wishDao())
    }

    fun provider(context: Context) {
        database = Room.databaseBuilder(context, WishDatabase::class.java, "wishlist.db").
                fallbackToDestructiveMigration().build()
    }
}