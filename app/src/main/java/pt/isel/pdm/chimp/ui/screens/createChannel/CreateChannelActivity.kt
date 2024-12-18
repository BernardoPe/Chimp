package pt.isel.pdm.chimp.ui.screens.createChannel

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import pt.isel.pdm.chimp.ui.screens.ChannelsActivity
import pt.isel.pdm.chimp.ui.theme.ChIMPTheme

class CreateChannelActivity : ChannelsActivity() {
    val createChannelViewModel by initializeViewModel { dependencies ->
        CreateChannelViewModel(
            dependencies.chimpService,
            dependencies.sessionManager,
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ChIMPTheme {
                val state by createChannelViewModel.state.collectAsState(initial = CreateChannelScreenState.CreatingChannel())
                CreateChannelScreen(
                    state = state,
                    onCreateChannel = createChannelViewModel::createChannel,
                    onBack = { finish() },
                )
            }
        }
    }
}
