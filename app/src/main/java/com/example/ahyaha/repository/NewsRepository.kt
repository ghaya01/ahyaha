package com.example.ahyaha.repository

import com.example.ahyaha.model.BloodType
import com.example.ahyaha.model.News

object NewsRepository {
    // TODO: Add methods to manage blood type data operations
    fun getAllBloodTypes(): List<News> {
        // Simulating fetching data from a data source
        return listOf(
          News("titel1", "Content1", "https://northcentralsurgical.com/wp-content/uploads/2023/06/reasons-why-you-should-give-blood-in-1685340565.jpg"),
            News("titel2", "Content1", "https://northcentralsurgical.com/wp-content/uploads/2023/06/reasons-why-you-should-give-blood-in-1685340565.jpg"),
            News("titel3", "Content1", "https://northcentralsurgical.com/wp-content/uploads/2023/06/reasons-why-you-should-give-blood-in-1685340565.jpg")
        )
    }
}
