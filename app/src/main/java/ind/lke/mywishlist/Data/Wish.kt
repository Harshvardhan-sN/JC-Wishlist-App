package ind.lke.mywishlist.Data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "wish-table")
data class Wish(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    @ColumnInfo(name = "wish-title")
    val title: String = "",
    @ColumnInfo(name = "wish-desc")
    val description: String = "",
    @ColumnInfo(name = "wish-date")
    val date: String = ""
)

object DummyData {
    val wishList = listOf(
        Wish(title = "1st", description = "In this example, the shape parameter of the FloatingActionButton composable is set to CircleShape, which creates a circular floating action button. You can change this shape to any other shape provided by the Compose library or create a custom shape if needed."),
        Wish(title = "2nd", description = "Working"),
        Wish(title = "3rd", description = "Working"),
        Wish(title = "4th", description = "Working"),
        Wish(title = "5th", description = "In this example, the shape parameter of the FloatingActionButton composable is set to CircleShape, which creates a circular floating action button. You can change this shape to any other shape provided by the Compose library or create a custom shape if needed.")
    )
}