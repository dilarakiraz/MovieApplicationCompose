package com.example.movieapplicationcompose.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.movieapplicationcompose.R
import com.example.movieapplicationcompose.viewModel.MovieViewModel

@Composable
fun BannerScreen(navController: NavHostController) {

    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.splash), contentDescription = "",
            Modifier.fillMaxSize(), contentScale = ContentScale.Crop
        )

        Column(
            Modifier
                .wrapContentHeight()
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .background(color = Color.White.copy(0.4f), RoundedCornerShape(20.dp))
                .border(0.5.dp, Color.White, RoundedCornerShape(topEnd = 20.dp, topStart = 20.dp)),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Bottom
        ) {
            val offset = Offset(10.0f, 10f)
            Text(
                text = "Enjoy the world of movies",
                Modifier.padding(vertical = 25.dp),
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                style = TextStyle(
                    fontSize = 34.sp,
                    shadow = Shadow(
                        color = Color(0xFFFC5A03),
                        offset = offset, blurRadius = 3f
                    ),
                    fontFamily = FontFamily(Font(R.font.cinzel_decorative)),
                    textAlign = TextAlign.Center
                )
            )
            val linearGradientBrush = Brush.linearGradient(
                colors = listOf(
                    Color(0xFFB226E1),
                    Color(0xFFFC6603),
                    Color(0xFF5995EE),
                    Color(0xFF3D3535)
                ),
                start = Offset(Float.POSITIVE_INFINITY, 0f),
                end = Offset(0f, Float.POSITIVE_INFINITY),
            )
            Button(
                onClick = { navController.navigate("Home Screen") },
                Modifier
                    .padding(bottom = 55.dp, start = 20.dp, end = 20.dp)
                    .fillMaxWidth()
                    .background(color = Color.Gray.copy(0.8f), RoundedCornerShape(16.dp))
                    .border(
                        BorderStroke(
                            3.dp,
                            linearGradientBrush
                        ),
                        shape = RoundedCornerShape(16.dp)
                    ),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Transparent
                )
            ) {
                Text(
                    text = "Get In", style = TextStyle(
                        fontSize = 30.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black,
                        fontFamily = FontFamily(Font(R.font.cinzel_decorative)),
                        textAlign = TextAlign.Center
                    )
                )
            }
        }
    }
}