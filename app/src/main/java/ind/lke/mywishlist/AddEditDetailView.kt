package ind.lke.mywishlist

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Scaffold
import androidx.compose.material.SnackbarDuration
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import ind.lke.mywishlist.Data.Wish
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

@Composable
fun AddEditDetailView(
    id: Long,
    viewModel: WishViewModel,
    navController: NavController
) {
    var message by remember {
        mutableStateOf("")
    }
    val scope = rememberCoroutineScope()
    val scaffoldState = rememberScaffoldState()
    val context = LocalContext.current
    val currentDate = getCurrentData()

    if(id != 0L) { // update wish
        val wish = viewModel.getWishByID(id).collectAsState(initial = Wish(0L, "", ""))
        viewModel.wishTitleState = wish.value.title
        viewModel.wishDescriptionState = wish.value.description
    } else {
        viewModel.wishTitleState = ""
        viewModel.wishDescriptionState = ""
    }
    Scaffold(
        backgroundColor = colorResource(id = R.color.app_bar_color),
        topBar = { AppBarView(
            title =
            if (id != 0L) stringResource(id = R.string.update_wish)
            else stringResource(id = R.string.add_wish),
            onBackNavClicked = { navController.navigateUp() }
            ) },
        scaffoldState = scaffoldState
    ) {
        Column(
            modifier = Modifier
                .padding(it)
                .wrapContentSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Spacer(modifier = Modifier.height(10.dp))
            WishTextField(
                label = "Title",
                value = viewModel.wishTitleState,
                onValueChanged = {
                    viewModel.onWishTitleChanged(it)
                }
            )

            Spacer(modifier = Modifier.height(10.dp))
            WishTextField(
                label = "Description",
                value = viewModel.wishDescriptionState,
                onValueChanged = {
                    viewModel.onWishDescriptionChanged(it)
                }
            )
            
            Spacer(modifier = Modifier.height(10.dp))
            Button(colors = ButtonDefaults.buttonColors(backgroundColor = colorResource(id = R.color.button_lavender)),
                onClick = {
                if(viewModel.wishTitleState.trim().isNotEmpty() &&
                    viewModel.wishDescriptionState.trim().isNotEmpty()) {
                    message = if(id == 0L) { // add wish
                        viewModel.addWish(Wish(title = viewModel.wishTitleState.trim(),
                            description = viewModel.wishDescriptionState.trim(),
                            date = currentDate)
                        )
                        "Wish Added"
                    } else {    // update wish
                        viewModel.updateWish(Wish(id = id, title = viewModel.wishTitleState.trim(),
                            description = viewModel.wishDescriptionState.trim(),
                            date = currentDate)
                        )
                        "Wish Updated"
                    }
                    scope.launch {
                        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                        navController.navigateUp()
                    }
                } else {
                    // invalid entry
                    message = "Invalid Entries"
                    scope.launch {
                        scaffoldState.snackbarHostState.showSnackbar(message, duration = SnackbarDuration.Short)
                    }
                }
            }) {
                Text(
                    text = if(id != 0L) "Update Wish" else "Add Wish",
                    style = TextStyle(fontSize = 18.sp)
                )
            }
        }
    }
}

@Composable
fun WishTextField(
    label: String,
    value: String,
    onValueChanged: (String) -> Unit
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChanged,
        label = { Text(text = label, color = Color.Black) },
        modifier = Modifier.fillMaxWidth(),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            textColor = Color.Black,
            focusedBorderColor = Color.Black,
            unfocusedBorderColor = Color.Black,
            cursorColor = Color.Black,
            focusedLabelColor = Color.Black,
            unfocusedLabelColor = Color.Black,
        )
    )
}

@Preview
@Composable
fun WishTextFieldPreview() {
    WishTextField(label = "Check", value = "Check", onValueChanged = {})
}

fun getCurrentData(): String {
    val currentData = LocalDate.now()
    val formatter = DateTimeFormatter.ofPattern("dd MMM yyyy", Locale.ENGLISH)
    return currentData.format(formatter)
}