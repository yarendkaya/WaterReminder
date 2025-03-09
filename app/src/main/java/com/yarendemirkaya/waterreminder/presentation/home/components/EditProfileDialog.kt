package com.yarendemirkaya.waterreminder.presentation.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.yarendemirkaya.waterreminder.R
import com.yarendemirkaya.waterreminder.R.color.dark_gray
import com.yarendemirkaya.waterreminder.presentation.home.HomeContract

@Composable
fun EditProfileDialog(onAction: (HomeContract.HomeUiAction) -> Unit) {
    Dialog(onDismissRequest = { }) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White, shape = RoundedCornerShape(16.dp))
                .padding(16.dp)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(fontSize = 18.sp, text = stringResource(id = R.string.edit_profile_dialog))
                Spacer(modifier = Modifier.height(10.dp))
                Button(
                    onClick = {
                        onAction(HomeContract.HomeUiAction.OnClickEditProfile)
                    }, colors = ButtonDefaults.buttonColors(
                        containerColor = colorResource(id = dark_gray),
                        contentColor = Color.White
                    )
                ) {
                    Text(text = stringResource(id = R.string.edit_profile_btn))
                }
            }
        }
    }
}