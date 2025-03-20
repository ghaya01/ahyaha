package com.example.ahyaha.presentation.view
import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.ahyaha.presentation.viewmodel.AddDonorEvent
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import com.example.ahyaha.presentation.viewmodel.AddDonorViewModel
import com.example.ahyaha.ui.components.DropdownMenuComponent
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.platform.LocalLayoutDirection
@Composable
fun AddDonorView(
    navController: NavController,
    viewModel: AddDonorViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = ":Add a Donor",
            style = MaterialTheme.typography.headlineLarge,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // حقل الاسم
        state.error?.let {
            OutlinedTextField(
                value = state.name,
                onValueChange = { viewModel.onEvent(AddDonorEvent.NameChanged(it)) },
                label = { Text("Full Name") },
                modifier = Modifier.fillMaxWidth(),
                isError = it.containsKey("name")
            )
        }
        state.error?.get("name")?.let { Text(it, color = Color.Red) }

        // حقل البريد الإلكتروني
        state.error?.let {
            OutlinedTextField(
                value = state.email,
                onValueChange = { viewModel.onEvent(AddDonorEvent.EmailChanged(it)) },
                label = { Text("E-mail") },
                modifier = Modifier.fillMaxWidth(),
                isError = it.containsKey("email")
            )
        }
        state.error?.get("email")?.let { Text(it, color = Color.Red) }

        // حقل رقم الهاتف
        state.error?.let {
            OutlinedTextField(
                value = state.phoneNumber,
                onValueChange = { viewModel.onEvent(AddDonorEvent.PhoneNumberChanged(it)) },
                label = { Text("Phone Number") },
                modifier = Modifier.fillMaxWidth(),
                isError = it.containsKey("phoneNumber")
            )
        }
        state.error?.get("phoneNumber")?.let { Text(it, color = Color.Red) }

        // ✅ حقل فصيلة الدم (إدخال يدوي بدلاً من القائمة المنسدلة)
        OutlinedTextField(
            value = state.bloodGroup,
            onValueChange = { viewModel.onEvent(AddDonorEvent.BloodGroupChanged(it)) },
            label = { Text("Blood type") },
            modifier = Modifier.fillMaxWidth(),
            isError = state.error?.containsKey("bloodGroup") == true
        )
        state.error?.get("bloodGroup")?.let { Text(it, color = Color.Red) }

        // ✅ حقل عامل Rh (إدخال يدوي بدلاً من القائمة المنسدلة)
        OutlinedTextField(
            value = state.rh,
            onValueChange = { viewModel.onEvent(AddDonorEvent.RhChanged(it)) },
            label = { Text("Rh factor") },
            modifier = Modifier.fillMaxWidth(),
            isError = state.error?.containsKey("rh") == true
        )
        state.error?.get("rh")?.let { Text(it, color = Color.Red) }

        // حقل الموقع
        state.error?.let {
            OutlinedTextField(
                value = state.location,
                onValueChange = { viewModel.onEvent(AddDonorEvent.LocationChanged(it)) },
                label = { Text("Location") },
                modifier = Modifier.fillMaxWidth(),
                isError = it.containsKey("location")
            )
        }
        state.error?.get("location")?.let { Text(it, color = Color.Red) }

        // حقل رابط الصورة الشخصية
        OutlinedTextField(
            value = state.profilePicture,
            onValueChange = { viewModel.onEvent(AddDonorEvent.ProfilePictureChanged(it)) },
            label = { Text("Profile picture link") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        // زر الإضافة وحالة التحميل
        if (state.isLoading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
        } else {
            Button(
                onClick = { viewModel.onEvent(AddDonorEvent.Submit) },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Add donor")
            }
        }

        // عرض رسالة الخطأ العامة إن وجدت
        state.error?.get("general")?.let {
            Text(it, color = Color.Red, modifier = Modifier.padding(top = 15.dp))
        }

        // عرض رسالة النجاح

        if (state.isSuccess) {
            CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {
                Text(
                    text = "Donor added successfully!",
                    color = Color(0xFF006400), // أخضر غامق
                    modifier = Modifier.padding(top = 15.dp)
                )
            }
        }
    }
}