package pt.isel.pdm.chimp.ui.screens.home.inviteUser

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import pt.isel.pdm.chimp.R
import pt.isel.pdm.chimp.domain.sessions.Session
import pt.isel.pdm.chimp.ui.components.TopBar
import pt.isel.pdm.chimp.ui.components.inputs.ExpirationOptions
import pt.isel.pdm.chimp.ui.screens.home.inviteUser.views.InviteUserForm
import pt.isel.pdm.chimp.ui.theme.ChIMPTheme
import pt.isel.pdm.chimp.ui.utils.SnackBarVisuals
import pt.isel.pdm.chimp.ui.utils.getMessage

@Composable
fun CreateUserInviteScreen(
    state: InviteUserScreenState,
    session: Session?,
    onCreateInvite: (ExpirationOptions) -> Unit,
    onBack: () -> Unit,
) {
    if (session == null) {
        onBack()
        return
    }
    val snackBarHostState = remember { SnackbarHostState() }
    ChIMPTheme {
        Scaffold(
            snackbarHost = { SnackbarHost(snackBarHostState) },
            topBar = {
                TopBar(
                    content = {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            IconButton(onClick = onBack) {
                                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                            }
                            Text(stringResource(id = R.string.create_user_invite))
                        }
                    },
                )
            },
        ) { innerPadding ->
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.fillMaxSize().padding(innerPadding),
            ) {
                when (state) {
                    is InviteUserScreenState.CreatingInvite,
                    is InviteUserScreenState.InviteCreationError,
                    -> {
                        InviteUserForm(
                            expirationOption = state.expirationDate,
                            onCreateInvite = onCreateInvite,
                        )
                    }
                    is InviteUserScreenState.Loading -> {
                        InviteUserForm(
                            expirationOption = state.expirationDate,
                            onCreateInvite = onCreateInvite,
                            loading = true,
                        )
                    }
                    is InviteUserScreenState.InviteCreated -> {
                        InviteUserForm(
                            expirationOption = state.expirationDate,
                            onCreateInvite = onCreateInvite,
                            createdInvite = state.invitation,
                        )
                    }
                }
            }
        }
    }

    LaunchedEffect(state) {
        if (state is InviteUserScreenState.InviteCreationError) {
            snackBarHostState.showSnackbar(
                SnackBarVisuals(
                    message = state.problem.getMessage(),
                ),
            )
        }
    }
}
