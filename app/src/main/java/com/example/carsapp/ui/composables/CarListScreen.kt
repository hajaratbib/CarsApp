@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.carsapp.ui.composables

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.carsapp.R
import com.example.carsapp.domain.model.Car
import com.example.carsapp.ui.CarState
import eu.bambooapps.material3.pullrefresh.PullRefreshIndicator
import eu.bambooapps.material3.pullrefresh.PullRefreshState
import eu.bambooapps.material3.pullrefresh.pullRefresh
import eu.bambooapps.material3.pullrefresh.rememberPullRefreshState

@Composable
fun CarListScreen(
    modifier: Modifier = Modifier,
    state: CarState = CarState(),
    brand: String = "",
    onSearchClick: () -> Unit = {},
    isButtonEnabled: Boolean = false,
    onValueChanged: (String) -> Unit = {},
    isRefreshLoading: Boolean = false,
    pullRefreshState: PullRefreshState
) {

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(dimensionResource(R.dimen.medium))
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(IntrinsicSize.Min),
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextField(
                value = brand,
                onValueChange = onValueChanged,
                label = { Text(stringResource(R.string.label)) },
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight(),
                singleLine = true,
                shape = RectangleShape,
                colors = TextFieldDefaults.colors(
                    focusedTextColor = Color.Black,
                    unfocusedTextColor = Color.Black,
                    disabledTextColor = Color.Black,
                    focusedLabelColor = Color.Gray,
                    unfocusedLabelColor = Color.Gray,
                    disabledLabelColor = Color.Gray,
                    focusedContainerColor = Color.LightGray,
                    unfocusedContainerColor = Color.LightGray,
                    disabledContainerColor = Color.LightGray,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent,
                ),
            )

            Button(
                onClick = onSearchClick,
                enabled = isButtonEnabled,
                modifier = Modifier
                    .fillMaxHeight(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Gray,
                    contentColor = Color.Black
                ),
                shape = RectangleShape
            ) {
                Text(stringResource(R.string.search))
            }
        }

        Spacer(modifier = Modifier.height(dimensionResource(R.dimen.medium)))

        when {
            state.isLoading -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }

            state.error != null -> {
                Text(
                    stringResource(R.string.error_message) + state.error.ifEmpty { stringResource(R.string.unknown_error) },
                    color = Color.Red
                )
            }

            else -> {
                if (state.cars.isEmpty()) {
                    Text(stringResource(R.string.no_data), color = Color.Red)
                } else {

                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .pullRefresh(pullRefreshState)
                    ) {
                        LazyColumn {
                            items(state.cars) { car ->
                                CarItem(car)
                            }
                        }

                        PullRefreshIndicator(
                            refreshing = isRefreshLoading,
                            state = pullRefreshState,
                            modifier = Modifier.align(Alignment.TopCenter)
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CarListPreview() {
    CarListScreen(pullRefreshState = rememberPullRefreshState(refreshing = false, onRefresh = {}))
}

@Preview(showBackground = true)
@Composable
fun CarListLoadingPreview() {
    CarListScreen(
        state = CarState(true),
        brand = "Renault",
        pullRefreshState = rememberPullRefreshState(refreshing = false, onRefresh = {})
    )
}

@Preview(showBackground = true)
@Composable
fun CarListErrorPreview() {
    CarListScreen(
        state = CarState(error = "Something wrong happened"),
        brand = "Renault",
        pullRefreshState = rememberPullRefreshState(refreshing = false, onRefresh = {})
    )
}

@Preview(showBackground = true)
@Composable
fun CarListSuccessPreview() {
    CarListScreen(
        state = CarState(cars = listOf(Car("Clio", "Renault"))),
        brand = "Renault",
        pullRefreshState = rememberPullRefreshState(refreshing = false, onRefresh = {})
    )
}


