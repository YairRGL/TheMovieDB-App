package com.example.themoviedb.screens.user

import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Face
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.example.themoviedb.R
import com.example.themoviedb.utils.ComposeFileProvider
import com.example.themoviedb.widgets.TheMovieDBAppBar
import com.example.themoviedb.widgets.TheMovieDBBottomBar


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserScreen(navController: NavHostController, userViewModel: UserViewModel = hiltViewModel()) {
    Scaffold(
        topBar = {
            TheMovieDBAppBar(title = "USER", navController = navController)
        },
        bottomBar = {
            TheMovieDBBottomBar(navController = navController, selectedItemIndex = 1)
        }, containerColor = Color(0xFF000000)
    ) {
        Column(
            modifier = Modifier
                .padding(it)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Hola Yair!", color = Color.White)
            ProfileImageEditView(userViewModel)
        }
    }
}

@Composable
fun ProfileImageEditView(userViewModel: UserViewModel) {
    var isEditMenuVisible by remember { mutableStateOf(false) }
    var isImageSelected by remember { mutableStateOf(false) }
    var tempPhotoUri by remember { mutableStateOf(Uri.EMPTY) }
    val context = LocalContext.current

    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri ->
            uri?.let {
                userViewModel.updateSelectedImageUri(it)
                isImageSelected = true
            }
        }
    )

    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture(),
        onResult = { success ->
            if (success) {
                Log.d("TAG", tempPhotoUri.toString())
                Log.d("XD", userViewModel.selectedImageUri.toString())
                userViewModel.selectedImageUri = tempPhotoUri
                isImageSelected = true
            }
        }
    )


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        ProfileImage(
            modifier = Modifier
                .size(250.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.primary)
                .clickable {
                    isEditMenuVisible = !isEditMenuVisible
                },
            imageResource = if (isImageSelected) {
                rememberAsyncImagePainter(model = userViewModel.selectedImageUri)
            } else {
                painterResource(R.drawable.ic_launcher_foreground)
            }
        )

        if (isEditMenuVisible) {
            Spacer(modifier = Modifier.height(16.dp))
            EditMenu(
                onGalleryClick = {
                    isEditMenuVisible = false
                    galleryLauncher.launch("image/*")
                },
                onDeleteClick = {
                    isEditMenuVisible = false
                    isImageSelected = false
                    userViewModel.updateSelectedImageUri(null)
                },
                onCameraClick = {
                    tempPhotoUri = ComposeFileProvider.getImageUri(context)
                    isEditMenuVisible = false
                    cameraLauncher.launch(tempPhotoUri)
                }
            )
        } else {
            Box {

            }
        }
    }
}

@Composable
fun ProfileImage(
    modifier: Modifier = Modifier,
    imageResource: Painter
) {
    Image(
        painter = imageResource,
        contentDescription = "User profile pic",
        modifier = modifier,
        contentScale = ContentScale.Crop
    )
}

@Composable
fun EditMenu(
    onGalleryClick: () -> Unit,
    onDeleteClick: () -> Unit,
    onCameraClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        IconButton(onClick = onGalleryClick) {
            Icon(
                imageVector = Icons.Default.Edit,
                contentDescription = "Edit",
                tint = Color.DarkGray,
                modifier = Modifier.size(30.dp),
            )
        }

        IconButton(onClick = onDeleteClick) {
            Icon(
                imageVector = Icons.Default.Delete,
                contentDescription = "Delete",
                tint = Color.DarkGray,
                modifier = Modifier.size(30.dp)
            )
        }

        IconButton(onClick = onCameraClick) {
            Icon(
                imageVector = Icons.Default.Face,
                contentDescription = "Camera",
                tint = Color.DarkGray,
                modifier = Modifier.size(30.dp)
            )
        }
    }
}
