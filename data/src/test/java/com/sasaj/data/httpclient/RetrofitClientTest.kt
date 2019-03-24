package com.sasaj.data.httpclient

import com.sasaj.data.entities.LocationDto
import io.reactivex.observers.TestObserver
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.net.SocketTimeoutException
import java.util.concurrent.TimeUnit

@RunWith(JUnit4::class)
class RetrofitClientTest {

    lateinit var mockServer : MockWebServer

    lateinit var weatherService: OWMService

    @Before
    fun setUp() {
        mockServer = MockWebServer()
        mockServer.start()
        val baseUrl = mockServer.url("/")


        // Get an okhttp client
        val okHttpClient = OkHttpClient.Builder()
                .connectTimeout(5, TimeUnit.SECONDS)
                .build()

        // Get an instance of Retrofit
        val retrofit = Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build()

        // Get an instance of blogService
        weatherService = retrofit.create(OWMService::class.java)
    }

    @After
    fun tearDown() {
        mockServer.shutdown()
    }

    @Test
    fun getWeatherHappyFlow() {
        val testObserver = TestObserver<LocationDto>()

        // Mock a response with status 200 and sample JSON output
        val mockResponse = MockResponse()
                .setResponseCode(200)
                .setBody(getJson("json/locationResponse.json"))

        // Enqueue request
        mockServer.enqueue(mockResponse)

        // Call the API
        weatherService.getWeatherForCityId(1,"metric","en", "123").subscribe(testObserver)
        testObserver.awaitTerminalEvent(2, TimeUnit.SECONDS)

        // No errors
        testObserver.assertNoErrors()
        // One list emitted
        testObserver.assertValueCount(1)

        // Get the request that was just made
        val request = mockServer.takeRequest()
        // Make sure we made the request to the required path
        Assert.assertEquals("/data/2.5/weather?id=1&units=metric&lang=en&appid=123", request.path)
    }


    @Test
    fun getWeatherServerError() {
        val testObserver = TestObserver<LocationDto>()

        // Mock a response with status 200 and sample JSON output
        val mockResponse = MockResponse()
                .setResponseCode(500)

        // Enqueue request
        mockServer.enqueue(mockResponse)

        // Call the API
        weatherService.getWeatherForCityId(1,"metric","en", "123").subscribe(testObserver)
        testObserver.awaitTerminalEvent(2, TimeUnit.SECONDS)

        testObserver.assertError(Exception::class.java)
    }

    /**
     * Helper function which will load JSON from
     * the path specified
     *
     * @param path : Path of JSON file
     * @return json : JSON from file at given path
     */
    fun getJson(path : String) : String {
        // Load the JSON response
        val uri = this.javaClass.classLoader.getResource(path)
        val file = File(uri.path)
        return String(file.readBytes())
    }
}