package com.aslnstbk.unsplash.home.data

import com.aslnstbk.unsplash.common.data.models.Image
import com.aslnstbk.unsplash.common.data.models.api.ImageApiData
import com.aslnstbk.unsplash.home.domain.HomeRepository
import com.aslnstbk.unsplash.utils.mappers.ImageApiDataMapper
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DefaultHomeRepository(
    private val homeApiClient: HomeApiClient,
    private val imageApiDataMapper: ImageApiDataMapper
) : HomeRepository {

    override fun getImages(
        result: (List<Image>) -> Unit,
        fail: (String?) -> Unit
    ) {
        homeApiClient.getImages().enqueue(object : Callback<List<ImageApiData>> {
            override fun onResponse(
                call: Call<List<ImageApiData>>,
                response: Response<List<ImageApiData>>
            ) {
                if (response.isSuccessful) {
                    val imageList: List<Image> = imageApiDataMapper.map(response.body())
                    result(imageList)
                } else {
                    fail(response.message())
                }
            }

            override fun onFailure(
                call: Call<List<ImageApiData>>,
                t: Throwable
            ) {
                fail(t.localizedMessage)
            }
        })
    }
}