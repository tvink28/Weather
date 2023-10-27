package com.tvink28.weather.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tvink28.weather.R
import com.tvink28.weather.WeatherViewModel
import com.tvink28.weather.common.DateTimeConversion
import com.tvink28.weather.data.remote.GeoResponseItem
import com.tvink28.weather.ui.theme.InclusiveSans
import com.tvink28.weather.ui.theme.LightGrey

@Composable
fun WeatherScreen(
    viewModel: WeatherViewModel,
    onClickSync: () -> Unit,
    onClickSearch: () -> Unit,
) {

    val weatherData by viewModel.weatherData.collectAsState()
    val forecastData by viewModel.selectedCity.collectAsState()

    val nameCity = forecastData?.name
    val temp = weatherData?.current?.temp?.toInt()
    val date = weatherData?.let {
        DateTimeConversion.formattedDateTime(
            it.current.date,
            it.timezoneOffset
        )
    }
    val condition = weatherData?.current?.weather?.firstOrNull()?.main

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 5.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        IconButton(onClick = {
            onClickSearch.invoke()
        }) {
            Icon(
                painter = painterResource(id = R.drawable.ic_search),
                contentDescription = "ic_refresh",
                tint = Color.White
            )
        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                modifier = Modifier.padding(top = 5.dp),
                text = "$nameCity",
                style = TextStyle(fontSize = 22.sp, fontFamily = InclusiveSans),
                color = Color.White
            )

            Text(
                modifier = Modifier.padding(bottom = 20.dp),
                text = "$date",               // Date
                style = TextStyle(fontSize = 15.sp, fontFamily = InclusiveSans),
                color = Color.White
            )

            Image(
                painter = painterResource(id = R.drawable.cloudy),
                contentDescription = "condition",
                modifier = Modifier.sizeIn(maxHeight = 150.dp, maxWidth = 150.dp)
            )

            Text(
//                modifier = Modifier.padding(bottom = 10.dp),
                text = "${temp}°С",
                style = TextStyle(fontSize = 55.sp, fontFamily = InclusiveSans),
                color = Color.White,
            )

            Text(
                text = "$condition",
                style = TextStyle(fontSize = 15.sp, fontFamily = InclusiveSans),
                color = Color.White
            )
//            Text(
//                modifier = Modifier.padding(bottom = 5.dp),
//                text = "Wide 4 m/s, NE",
//                style = TextStyle(fontSize = 15.sp, fontFamily = InclusiveSans),
//                color = Color.White
//            )
        }

        IconButton(onClick = {
            onClickSync.invoke()
        }) {
            Icon(
                painter = painterResource(id = R.drawable.ic_refresh),
                contentDescription = "ic_refresh",
                tint = Color.White
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DialogSearch(
    dialogState: MutableState<Boolean>,
    viewModel: WeatherViewModel,
    onSubmit: (String) -> Unit
) {

    val dialogText = remember {
        mutableStateOf("")
    }

    LaunchedEffect(dialogText.value) {
        viewModel.fetchGeo(dialogText.value)
    }

    AlertDialog(modifier = Modifier
        .fillMaxWidth(),
        onDismissRequest = { dialogState.value = false },
        confirmButton = {
            TextButton(onClick = {
                onSubmit(dialogText.value)
                dialogState.value = false
            }) {
                Text(text = "OK")
            }
        }, dismissButton = {
            TextButton(onClick = { dialogState.value = false }) {
                Text(text = "Cancel")
            }
        }, title = {
            Column {
                Text(
                    modifier = Modifier.padding(bottom = 5.dp),
                    text = "Select city"
                )
                TextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = dialogText.value,
                    onValueChange = {
                        dialogText.value = it
                    })
                CityList(viewModel, dialogState)
            }
        }
    )
}

@Composable
fun CityList(
    viewModel: WeatherViewModel,
    dialogState: MutableState<Boolean>
) {
    val geoData by viewModel.geoData.collectAsState(null)

    LazyColumn {
        itemsIndexed(
            items = geoData ?: viewModel.defaultGeoList
        ) { _, item ->
            ListItem(item, viewModel, dialogState)
        }
    }
}

@Composable
fun ListItem(
    item: GeoResponseItem,
    viewModel: WeatherViewModel,
    dialogState: MutableState<Boolean>
) {
    Column(modifier = Modifier
        .clickable {
            viewModel.selectedCity.value = item
            viewModel.fetchForecast()
            dialogState.value = false
        }
        .padding(horizontal = 3.dp, vertical = 10.dp)) {
        Text(text = item.name, style = TextStyle(fontSize = 18.sp))
        Text(
            text = item.country + " " + item.state,
            style = TextStyle(fontSize = 15.sp, color = LightGrey)
        )
    }
}
