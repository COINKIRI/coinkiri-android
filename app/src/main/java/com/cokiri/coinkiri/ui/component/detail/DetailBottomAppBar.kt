package com.cokiri.coinkiri.ui.component.detail

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.cokiri.coinkiri.R
import com.cokiri.coinkiri.ui.theme.CoinkiriWhite

/**
 * 게시글(분석,커뮤니티) 상세화면의 BottomAppBar
 */
@Composable
fun DetailBottomAppBar(
    clickComment: () -> Unit,
    clickLike: () -> Unit,
    isLiked: Boolean
) {
    BottomAppBar(
        modifier = Modifier
            .height(50.dp)
            .fillMaxWidth(),
        containerColor = CoinkiriWhite,
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = clickLike
            ) {
                Icon(
                    painter = painterResource(
                        id = if (isLiked) R.drawable.ic_post_filled_thumb_up else R.drawable.ic_post_baseline_thumb_up
                    ),
                    contentDescription = if (isLiked) "좋아요 취소" else "좋아요"
                )
            }
            IconButton(
                onClick = clickComment
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_chat),
                    contentDescription = "댓글"
                )
            }
        }
    }
}