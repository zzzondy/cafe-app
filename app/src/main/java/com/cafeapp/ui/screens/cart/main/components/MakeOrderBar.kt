package com.cafeapp.ui.screens.cart.main.components

import androidx.compose.foundation.background
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import com.cafeapp.R
import com.cafeapp.core.util.UiText

@Composable
fun MakeOrderBar(
    currentTotal: Int,
    modifier: Modifier = Modifier,
    onMakeOrder: () -> Unit = {}
) {
    ConstraintLayout(
        constraintSet = ConstraintSet {
            val totalLabel = createRefFor(MakeOrderBarTags.totalLabel)
            val currentTotalLabel = createRefFor(MakeOrderBarTags.currentTotal)
            val makeOrderButton = createRefFor(MakeOrderBarTags.makeOrderButton)

            constrain(totalLabel) {
                start.linkTo(parent.start, 16.dp)
                top.linkTo(parent.top, 16.dp)
            }

            constrain(currentTotalLabel) {
                start.linkTo(totalLabel.start)
                top.linkTo(totalLabel.bottom, 8.dp)
                bottom.linkTo(parent.bottom, 16.dp)
            }

            constrain(makeOrderButton) {
                end.linkTo(parent.end, 16.dp)
                top.linkTo(parent.top, 16.dp)
                bottom.linkTo(parent.bottom, 16.dp)
            }
        },
        modifier = modifier
            .background(color = MaterialTheme.colorScheme.surfaceColorAtElevation(3.dp))
    ) {
        Text(
            text = UiText.StringResource(R.string.total).asString(),
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.layoutId(MakeOrderBarTags.totalLabel)
        )

        Text(
            text = UiText.StringResource(R.string.rubles, currentTotal).asString(),
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.layoutId(MakeOrderBarTags.currentTotal)
        )

        Button(
            onClick = onMakeOrder,
            modifier = Modifier.layoutId(MakeOrderBarTags.makeOrderButton),
            enabled = currentTotal != 0
        ) {
            Text(text = UiText.StringResource(R.string.make_order).asString())
        }
    }
}

private object MakeOrderBarTags {
    const val totalLabel = "totalLabel"
    const val currentTotal = "currentTotal"
    const val makeOrderButton = "makeOrderButton"
}