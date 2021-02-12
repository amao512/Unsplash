package com.aslnstbk.unsplash.home.data

import com.aslnstbk.unsplash.common.data.models.Image
import com.aslnstbk.unsplash.common.data.models.api.ImageApiData
import com.aslnstbk.unsplash.home.domain.HomeRepository
import com.aslnstbk.unsplash.utils.mappers.ImageApiDataMapper
import retrofit2.Response
import retrofit2.awaitResponse

class DefaultHomeRepository(
    private val homeApiClient: HomeApiClient,
    private val imageApiDataMapper: ImageApiDataMapper
) : HomeRepository {

    override suspend fun getImages(
        result: (List<Image>) -> Unit,
        fail: (String?) -> Unit
    ) {
        try {
            val response: Response<List<ImageApiData>> = homeApiClient.getImages().awaitResponse()

            if (response.isSuccessful) {
                val imageList: List<Image> = imageApiDataMapper.map(response.body())

                result(imageList)
            } else {
                fail(response.message())
            }
        } catch (e: Exception) {
            fail(e.localizedMessage)
        }
    }
}