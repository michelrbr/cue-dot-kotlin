package br.com.mxel.cuedot.data.remote

import okhttp3.*

class TestInterceptor: Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {

        return Response.Builder()
                .code(200)
                .message(responseListString)
                .request(chain.request())
                .protocol(Protocol.HTTP_1_0)
                .body(ResponseBody.create(MediaType.parse("application/json"), responseListString))
                .addHeader("content-type", "application/json")
                .build()
    }

    companion object {
        private const val responseListString: String = "{\n" +
                "  \"page\": 1,\n" +
                "  \"total_results\": 6879,\n" +
                "  \"total_pages\": 344,\n" +
                "  \"results\": [\n" +
                "    {\n" +
                "      \"vote_count\": 1973,\n" +
                "      \"id\": 19404,\n" +
                "      \"video\": false,\n" +
                "      \"vote_average\": 9.1,\n" +
                "      \"title\": \"Dilwale Dulhania Le Jayenge\",\n" +
                "      \"popularity\": 20.11,\n" +
                "      \"poster_path\": \"\\/uC6TTUhPpQCmgldGyYveKRAu8JN.jpg\",\n" +
                "      \"original_language\": \"hi\",\n" +
                "      \"original_title\": \"दिलवाले दुल्हनिया ले जायेंगे\",\n" +
                "      \"genre_ids\": [\n" +
                "        35,\n" +
                "        18,\n" +
                "        10749\n" +
                "      ],\n" +
                "      \"backdrop_path\": \"\\/nl79FQ8xWZkhL3rDr1v2RFFR6J0.jpg\",\n" +
                "      \"adult\": false,\n" +
                "      \"overview\": \"Raj is a rich, carefree, happy-go-lucky second generation NRI. Simran is the daughter of Chaudhary Baldev Singh, who in spite of being an NRI is very strict about adherence to Indian values. Simran has left for India to be married to her childhood fiancé. Raj leaves for India with a mission at his hands, to claim his lady love under the noses of her whole family. Thus begins a saga.\",\n" +
                "      \"release_date\": \"1995-10-20\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"vote_count\": 12158,\n" +
                "      \"id\": 278,\n" +
                "      \"video\": false,\n" +
                "      \"vote_average\": 8.6,\n" +
                "      \"title\": \"The Shawshank Redemption\",\n" +
                "      \"popularity\": 33.988,\n" +
                "      \"poster_path\": \"\\/9O7gLzmreU0nGkIB6K3BsJbzvNv.jpg\",\n" +
                "      \"original_language\": \"en\",\n" +
                "      \"original_title\": \"The Shawshank Redemption\",\n" +
                "      \"genre_ids\": [\n" +
                "        18,\n" +
                "        80\n" +
                "      ],\n" +
                "      \"backdrop_path\": \"\\/j9XKiZrVeViAixVRzCta7h1VU9W.jpg\",\n" +
                "      \"adult\": false,\n" +
                "      \"overview\": \"Framed in the 1940s for the double murder of his wife and her lover, upstanding banker Andy Dufresne begins a new life at the Shawshank prison, where he puts his accounting skills to work for an amoral warden. During his long stretch in prison, Dufresne comes to be admired by the other inmates -- including an older prisoner named Red -- for his integrity and unquenchable sense of hope.\",\n" +
                "      \"release_date\": \"1994-09-23\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"vote_count\": 9333,\n" +
                "      \"id\": 238,\n" +
                "      \"video\": false,\n" +
                "      \"vote_average\": 8.6,\n" +
                "      \"title\": \"The Godfather\",\n" +
                "      \"popularity\": 30.657,\n" +
                "      \"poster_path\": \"\\/rPdtLWNsZmAtoZl9PK7S2wE3qiS.jpg\",\n" +
                "      \"original_language\": \"en\",\n" +
                "      \"original_title\": \"The Godfather\",\n" +
                "      \"genre_ids\": [\n" +
                "        18,\n" +
                "        80\n" +
                "      ],\n" +
                "      \"backdrop_path\": \"\\/6xKCYgH16UuwEGAyroLU6p8HLIn.jpg\",\n" +
                "      \"adult\": false,\n" +
                "      \"overview\": \"Spanning the years 1945 to 1955, a chronicle of the fictional Italian-American Corleone crime family. When organized crime family patriarch, Vito Corleone barely survives an attempt on his life, his youngest son, Michael steps in to take care of the would-be killers, launching a campaign of bloody revenge.\",\n" +
                "      \"release_date\": \"1972-03-14\"\n" +
                "    }\n" +
                "  ]\n" +
                "}"
    }
}