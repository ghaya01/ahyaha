package com.example.ahyaha.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.ahyaha.model.BloodType
import com.example.ahyaha.model.Donor
import com.example.ahyaha.model.News
import com.example.ahyaha.viewmodel.BloodTypeViewModel
import com.example.ahyaha.viewmodel.DonorViewModel
import com.example.ahyaha.viewmodel.NewsViewModel

@Composable
fun MainScreen(

    NewsViewModel: NewsViewModel,
    donorViewModel: DonorViewModel,
    bloodTypeViewModel: BloodTypeViewModel,
    modifier: Modifier = Modifier
) {
    val donorState by donorViewModel.uiState.collectAsState()
    val bloodTypeState by bloodTypeViewModel.bloodTypeState.collectAsState()
    val NewsState by NewsViewModel.uiState.collectAsState()

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(vertical = 8.dp)
    ) {
        item {
            // Header with Icons
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search",
                    modifier = Modifier.clickable { /* Handle search click */ }
                )
                Icon(
                    imageVector = Icons.Default.Notifications,
                    contentDescription = "Notifications",
                    modifier = Modifier.clickable { /* Handle notifications click */ }
                )
                Icon(
                    imageVector = Icons.Default.Settings,
                    contentDescription = "Settings",
                    modifier = Modifier.clickable { /* Handle settings click */ }
                )
            }
        }
        
        // Loading State
        if (donorState.isLoading) {
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
        } else {
            // Blood Types Section
            item {
                Section(
                    title = "Blood Types",
                    content = {
                        LazyRow(
                            contentPadding = PaddingValues(horizontal = 16.dp),
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            items(bloodTypeState.bloodTypes) { bloodType ->
                                BloodTypeCard(bloodType = bloodType)
                            }
                        }
                    }
                )
            }


            //News Section
            item {
                Section(
                    title = "News",
                    content = {
                        LazyRow(
                            contentPadding = PaddingValues(horizontal = 16.dp),
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            items(NewsState.newsList) { News ->
                                NewsCard(news = News)
                            }
                        }
                    }
                )
            }


            // Donors Section
            item {
                Section(
                    title = "Donors",
                    content = {
                        LazyRow(
                            contentPadding = PaddingValues(horizontal = 16.dp),
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            items(donorState.donors) { donor ->
                                DonorCard(donor = donor)
                            }
                        }
                    }
                )
            }
        }
    }
}

@Composable
fun Section(
    title: String,
    content: @Composable () -> Unit
) {
    Column(modifier = Modifier.padding(vertical = 16.dp)) {
        Text(
            text = title,
            style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
        )
        content()
    }
}

@Composable
fun DonorCard(donor: Donor) {
    Card(
        modifier = Modifier
            .width(180.dp)
            .padding(bottom = 8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Profile Picture
            AsyncImage(
                model = donor.profilePicture,
                contentDescription = "Profile picture of ${donor.name}",
                modifier = Modifier
                    .size(80.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Name
            Text(
                text = donor.name,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )

            Spacer(modifier = Modifier.height(6.dp))

            // Blood Type Chip
            BloodTypeChip(bloodGroup = donor.bloodGroup, rh = donor.Rh)

            Spacer(modifier = Modifier.height(10.dp))

            // Location
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Icon(
                    imageVector = Icons.Default.LocationOn,
                    contentDescription = "Location icon",
                    modifier = Modifier.size(16.dp),
                    tint = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = donor.location,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}




@Composable
fun NewsCard(news: News) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            // Large News Image
            AsyncImage(
                model = news.image,
                contentDescription = "News Image - ${news.title}",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp)
                    .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Title
            Text(
                text = news.title,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(horizontal = 16.dp),
                color = MaterialTheme.colorScheme.onSurface
            )

            Spacer(modifier = Modifier.height(6.dp))

            // Content Preview
            Text(
                text = news.content,
                style = MaterialTheme.typography.bodyMedium,
                maxLines = 3,

                modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp),
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(12.dp))
        }
    }
}


@Composable
fun BloodTypeCard(bloodType: BloodType) {
    Card(
        modifier = Modifier
            .size(100.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(
            containerColor = when(bloodType.bloodGroup) {
                "A" -> Color(0xFFFFE0E0)  
                "B" -> Color(0xFFE0E0FF)
                "AB" -> Color(0xFFE0FFE0)
                "O" -> Color(0xFFFFE0C0)
                else -> MaterialTheme.colorScheme.surfaceVariant
            }
        ),
        shape = RoundedCornerShape(16.dp)
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "${bloodType.bloodGroup}${bloodType.Rh}",
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.ExtraBold
            )
        }
    }
}

@Composable
fun BloodTypeChip(bloodGroup: String, rh: String) {
    Surface(
        shape = RoundedCornerShape(16.dp),
        color = when(bloodGroup) {
            "A" -> Color(0xFFFFE0E0)
            "B" -> Color(0xFFE0E0FF)
            "AB" -> Color(0xFFE0FFE0)
            "O" -> Color(0xFFFFE0C0)
            else -> MaterialTheme.colorScheme.surfaceVariant
        },
        modifier = Modifier.padding(vertical = 4.dp)
    ) {
        Text(
            text = "$bloodGroup$rh",
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp)
        )
    }
}