package com.aslnstbk.unsplash.home.data

import com.aslnstbk.unsplash.common.data.models.Image
import com.aslnstbk.unsplash.common.data.models.api.ImageApiData
import com.aslnstbk.unsplash.home.data.models.SearchResult
import com.aslnstbk.unsplash.home.data.models.SearchResultApiData
import com.aslnstbk.unsplash.home.domain.HomeRepository
import com.aslnstbk.unsplash.utils.mappers.ImageApiDataMapper
import com.aslnstbk.unsplash.utils.mappers.SearchResultApiDataMapper
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DefaultHomeRepository(
    private val homeDataSource: HomeDataSource,
    private val imageApiDataMapper: ImageApiDataMapper,
    private val searchResultApiDataMapper: SearchResultApiDataMapper
) : HomeRepository {

    override fun getImages(
        result: (List<Image>) -> Unit,
        fail: (String?) -> Unit
    ) {
        homeDataSource.getImages().enqueue(object : Callback<List<ImageApiData>> {
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

    override fun searchImages(
        query: String,
        result: (SearchResult) -> Unit,
        fail: (String?) -> Unit
    ) {
        homeDataSource.searchImages(query = query).enqueue(object : Callback<SearchResultApiData> {
            override fun onResponse(
                call: Call<SearchResultApiData>,
                response: Response<SearchResultApiData>
            ) {
                if (response.isSuccessful){
                    val searchResult: SearchResult = searchResultApiDataMapper.map(response.body())

                    result(searchResult)
                } else {
                    fail(response.message())
                }
            }

            override fun onFailure(call: Call<SearchResultApiData>, t: Throwable) {
                fail(t.localizedMessage)
            }
        })
    }
}