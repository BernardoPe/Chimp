package pt.isel.pdm.chimp.ui.screens.credentials.views

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import pt.isel.pdm.chimp.domain.Either
import pt.isel.pdm.chimp.domain.Success
import pt.isel.pdm.chimp.domain.success
import pt.isel.pdm.chimp.domain.wrappers.email.EmailValidationError
import pt.isel.pdm.chimp.domain.wrappers.email.EmailValidator
import pt.isel.pdm.chimp.domain.wrappers.name.NameValidationError
import pt.isel.pdm.chimp.domain.wrappers.name.NameValidator
import pt.isel.pdm.chimp.domain.wrappers.password.PasswordValidationError
import pt.isel.pdm.chimp.domain.wrappers.password.PasswordValidator
import pt.isel.pdm.chimp.ui.screens.credentials.components.EmailTextField
import pt.isel.pdm.chimp.ui.screens.credentials.components.PasswordTextField
import pt.isel.pdm.chimp.ui.screens.credentials.components.TokenTextField
import pt.isel.pdm.chimp.ui.screens.credentials.components.UsernameTextField
import pt.isel.pdm.chimp.ui.screens.credentials.components.validateToken
import pt.isel.pdm.chimp.ui.theme.ChIMPTheme

@Composable
fun RegisterView(
    usernameInitialValue: String = "",
    emailInitialValue: String = "",
    passwordInitialValue: String = "",
    onLoginClick: () -> Unit,
    onRegister: (email: String, username: String, password: String, token: String) -> Unit,
) {
    val (emailValidation, setEmailValidation) = remember { mutableStateOf<Either<List<EmailValidationError>, Unit>>(success(Unit)) }
    val (usernameValidation, setUsernameValidation) = remember { mutableStateOf<Either<List<NameValidationError>, Unit>>(success(Unit)) }
    val (passwordValidation, setPasswordValidation) =
        remember {
            mutableStateOf<Either<List<PasswordValidationError>, Unit>>(
                success(Unit),
            )
        }
    val (tokenValidation, setTokenValidation) = remember { mutableStateOf<Either<List<String>, Unit>>(success(Unit)) }
    val (email, setEmail) = remember { mutableStateOf(TextFieldValue(emailInitialValue)) }
    val (username, setUsername) = remember { mutableStateOf(TextFieldValue(usernameInitialValue)) }
    val (password, setPassword) = remember { mutableStateOf(TextFieldValue(passwordInitialValue)) }
    val (token, setToken) = remember { mutableStateOf(TextFieldValue("")) }
    val passwordValidator = PasswordValidator()
    val emailValidator = EmailValidator()
    val usernameValidator = NameValidator()
    ChIMPTheme {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.padding(16.dp),
        ) {
            Text(
                text = "Register",
                style = MaterialTheme.typography.titleMedium,
            )
            EmailTextField(
                email = email,
                onEmailChange = {
                    setEmail(it)
                    setEmailValidation(emailValidator.validate(it.text))
                },
                emailValidation = emailValidation,
            )
            UsernameTextField(
                username = username,
                onUsernameChange = {
                    setUsername(it)
                    setUsernameValidation(usernameValidator.validate(it.text))
                },
                usernameValidation = usernameValidation,
            )
            PasswordTextField(
                password = password,
                onPasswordChange = {
                    setPassword(it.copy(text = it.text.replace("\\s".toRegex(), "")))
                    setPasswordValidation(passwordValidator.validate(it.text))
                },
                passwordValidation = passwordValidation,
            )
            TokenTextField(
                token = token,
                onTokenChange = {
                    setToken(it)
                    setTokenValidation(validateToken(it.text))
                },
                tokenValidation = tokenValidation,
            )
            Text(
                text = "Already have an account? Login",
                modifier = Modifier.clickable { onLoginClick() },
                style = MaterialTheme.typography.titleSmall.copy(color = MaterialTheme.colorScheme.onBackground),
            )
            Button(
                onClick = {
                    onRegister(email.text, username.text, password.text, token.text)
                },
                modifier = Modifier.align(Alignment.CenterHorizontally),
                enabled =
                    listOf(emailValidation, usernameValidation, passwordValidation, tokenValidation).all { it is Success } &&
                        listOf(email, username, password, token).all { it.text.isNotBlank() },
            ) {
                Text("Register", style = MaterialTheme.typography.labelMedium)
            }
        }
    }
}

@Composable
@Preview
fun RegisterViewPreview() {
    RegisterView(onLoginClick = {}, onRegister = { _, _, _, _ -> })
}
