package feature.home.data.repository

import feature.home.data.service.HomeService
import feature.home.domain.repository.HomeRepository

class HomeRepositoryImpl(

): HomeRepository {
    override suspend fun getData(): String {
        TODO("Not yet implemented")
    }

}