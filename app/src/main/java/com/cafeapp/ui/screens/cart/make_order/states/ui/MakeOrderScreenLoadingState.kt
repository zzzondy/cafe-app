package com.cafeapp.ui.screens.cart.make_order.states.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.cafeapp.ui.theme.CafeAppTheme
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.material.shimmer
import com.google.accompanist.placeholder.placeholder

@Composable
fun MakeOrderScreenLoadingState(modifier: Modifier = Modifier) {
    Column(modifier = modifier) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .weight(1f)
        ) {
            item {
                RadioButtonsSection(
                    radioButtonSectionSpecies = RadioButtonSectionSpecies.DeliveryMethods,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                )
            }

            item {
                Spacer(modifier = Modifier.height(32.dp))
            }

            item {
                RadioButtonsSection(
                    radioButtonSectionSpecies = RadioButtonSectionSpecies.PaymentMethods,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                )
            }

            item {
                Spacer(modifier = Modifier.height(32.dp))
            }

            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                ) {
                    Text(
                        text = "",
                        modifier = Modifier
                            .fillMaxWidth(0.5f)
                            .placeholder(
                                visible = true,
                                color = MaterialTheme.colorScheme.surfaceColorAtElevation(3.dp),
                                shape = MaterialTheme.shapes.extraLarge,
                                highlight = PlaceholderHighlight.shimmer()
                            )
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "",
                        modifier = Modifier
                            .fillMaxWidth(0.25f)
                            .placeholder(
                                visible = true,
                                color = MaterialTheme.colorScheme.surfaceColorAtElevation(3.dp),
                                shape = MaterialTheme.shapes.extraLarge,
                                highlight = PlaceholderHighlight.shimmer()
                            )
                    )
                }
            }
        }

        Button(
            enabled = false,
            onClick = {},
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp, bottom = 32.dp)
                .placeholder(
                    visible = true,
                    color = MaterialTheme.colorScheme.surfaceColorAtElevation(3.dp),
                    shape = MaterialTheme.shapes.extraLarge,
                    highlight = PlaceholderHighlight.shimmer()
                )
        ) {

        }
    }
}

@Composable
private fun RadioButtonsSection(
    radioButtonSectionSpecies: RadioButtonSectionSpecies,
    modifier: Modifier = Modifier
) {
    ElevatedCard(
        modifier = modifier
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = "",
                modifier = Modifier
                    .fillMaxWidth()
                    .placeholder(
                        visible = true,
                        color = MaterialTheme.colorScheme.surfaceColorAtElevation(3.dp),
                        shape = MaterialTheme.shapes.extraLarge,
                        highlight = PlaceholderHighlight.shimmer()
                    )
            )

            Spacer(modifier = Modifier.height(16.dp))

            for (index in 0 until PLACEHOLDER_ITEMS) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(
                        selected = false,
                        onClick = {},
                        modifier = Modifier
                            .placeholder(
                                visible = true,
                                color = MaterialTheme.colorScheme.surfaceColorAtElevation(3.dp),
                                shape = MaterialTheme.shapes.extraLarge,
                                highlight = PlaceholderHighlight.shimmer()
                            )
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    Text(
                        text = "",
                        modifier = Modifier
                            .fillMaxWidth(0.75f)
                            .placeholder(
                                visible = true,
                                color = MaterialTheme.colorScheme.surfaceColorAtElevation(3.dp),
                                shape = MaterialTheme.shapes.extraLarge,
                                highlight = PlaceholderHighlight.shimmer()
                            )
                    )
                }

                if (radioButtonSectionSpecies == RadioButtonSectionSpecies.DeliveryMethods) {
                    Row(modifier = Modifier.padding(start = 16.dp, top = 8.dp, bottom = 8.dp)) {
                        Text(
                            text = "",
                            modifier = Modifier
                                .fillMaxWidth(0.3f)
                                .placeholder(
                                    visible = true,
                                    color = MaterialTheme.colorScheme.surfaceColorAtElevation(3.dp),
                                    shape = MaterialTheme.shapes.extraLarge,
                                    highlight = PlaceholderHighlight.shimmer()
                                )
                        )

                        Spacer(modifier = Modifier.width(8.dp))

                        Text(
                            text = "",
                            modifier = Modifier
                                .fillMaxWidth()
                                .placeholder(
                                    visible = true,
                                    color = MaterialTheme.colorScheme.surfaceColorAtElevation(3.dp),
                                    shape = MaterialTheme.shapes.extraLarge,
                                    highlight = PlaceholderHighlight.shimmer()
                                )
                        )
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}


private const val PLACEHOLDER_ITEMS = 2


private enum class RadioButtonSectionSpecies {
    DeliveryMethods, PaymentMethods
}


@Preview
@Composable
private fun MakeOrderScreenLoadingStatePreview() {
    CafeAppTheme() {
        Box(modifier = Modifier.fillMaxSize()) {
            MakeOrderScreenLoadingState()
        }
    }
}