package ind.lke.mywishlist

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import ind.lke.mywishlist.Data.DummyData
import ind.lke.mywishlist.Data.Wish

@Composable
fun HomeView() {
    val context = LocalContext.current
    Scaffold(
        topBar = { AppBarView(title = "WishList", {
            mMakeToast("navigation button working", context)
        })},
        floatingActionButton = {
            FloatingActionButton(modifier = Modifier.padding(20.dp),
                contentColor = Color.White,
                containerColor = Color.Black,
                onClick = {
                    mMakeToast(message = "floating action button working", context)
                }) {
                Icon(imageVector = Icons.Default.Add, contentDescription = null)
            }
        }
    ) {
        LazyColumn(modifier = Modifier
            .fillMaxSize()
            .padding(it)) {
            items(DummyData.wishList) {
                WishItem(wish = it) {
                    
                }
            }
        }
    }
}
@Composable
fun WishItem(wish: Wish, onClick: () -> Unit) {
    Card(modifier = Modifier
        .fillMaxWidth()
        .padding(top = 8.dp, start = 8.dp, end = 8.dp)
        .clickable { onClick() },
        elevation = CardDefaults.cardElevation(10.dp),
        colors = CardDefaults.cardColors(Color.White),
        border = BorderStroke(1.dp, Color.Black)
    ) {
        Column(modifier = Modifier.padding(10.dp)) {
            Text(text = wish.title, fontWeight = FontWeight.ExtraBold, color = Color.Black)
            Text(text = wish.description, color = Color.Black)
        }
    }
}

private fun mMakeToast(message: String, context: Context) {
    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
}