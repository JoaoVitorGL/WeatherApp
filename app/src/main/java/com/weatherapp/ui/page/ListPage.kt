package com.weatherapp.ui.page

import android.annotation.SuppressLint
import android.app.Activity
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.rounded.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.weatherapp.R
import com.weatherapp.model.City
import com.weatherapp.ui.nav.Route
import com.weatherapp.viewmodel.MainViewModel

@SuppressLint("ContextCastToActivity")
@Composable
fun ListPage(
    viewModel: MainViewModel,
    modifier: Modifier = Modifier
) {
    val activity = LocalContext.current as? Activity
    val cityList = viewModel.cities
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(8.dp)
    ) {
        items(cityList) { city ->
            LaunchedEffect(city.name) {
                if (city.weather == null) {
                    viewModel.loadWeather(city.name)
                }
            }
            CityItem(city = city, onClose = {
                viewModel.remove(city)
                Toast.makeText(activity, "${city.name} removida", Toast.LENGTH_SHORT).show()
            }, onClick = {
                viewModel.city = city
                viewModel.page = Route.Home
            })
        }
    }
}

@Composable
fun CityItem(
    city: City,
    onClick: () -> Unit,
    onClose: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onClick() },
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            model = city.weather?.imgUrl,
            modifier = Modifier.size(75.dp),
            error = painterResource(id = R.drawable.loading),
            contentDescription = "Imagem"
        )
        Spacer(modifier = Modifier.size(12.dp))
        Column(modifier = modifier.weight(1f)) {
            Text(modifier = Modifier,
                text = city.name,
                fontSize = 24.sp)
            Text(modifier = Modifier,
                text = city.weather?.desc?:"carregando...",
                fontSize = 16.sp)
        }
        IconButton(onClick = onClose) {
            Icon(Icons.Filled.Close, contentDescription = "Close")
        }
    }
}