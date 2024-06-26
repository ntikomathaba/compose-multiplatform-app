package feature.home.domain.repository

interface HomeRepository {
    suspend fun getData(): String
}