package com.example.carsapp.ui.composables

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.carsapp.R
import com.example.carsapp.domain.model.Car

@Composable
fun CarItem(car: Car) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(dimensionResource(R.dimen.medium))
    ) {
        Text(
            text = stringResource(R.string.model) + " ${car.model}",
            style = MaterialTheme.typography.titleLarge
        )
        Text(
            text = stringResource(R.string.brand) + " ${car.brand}",
            style = MaterialTheme.typography.titleMedium
        )
    }
}

@Preview(showBackground = true)
@Composable
fun CarItemPreview() {
    CarItem(Car("Clio", "Renault"))
}