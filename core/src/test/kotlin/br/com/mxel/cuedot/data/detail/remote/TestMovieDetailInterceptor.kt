package br.com.mxel.cuedot.data.detail.remote

import okhttp3.*

class TestMovieDetailInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        return Response.Builder()
                .code(200)
                .message("OK")
                .request(chain.request())
                .protocol(Protocol.HTTP_1_0)
                .body(ResponseBody.create(MediaType.parse("application/json"), SUCCESS_MOVIE))
                .addHeader("content-type", "application/json")
                .build()
    }

    companion object {
        private const val SUCCESS_MOVIE: String = "{\n" +
                "  \"adult\": false,\n" +
                "  \"backdrop_path\": \"/kzeR7BA0htJ7BeI6QEUX3PVp39s.jpg\",\n" +
                "  \"belongs_to_collection\": null,\n" +
                "  \"budget\": 1350000,\n" +
                "  \"genres\": [\n" +
                "    {\n" +
                "      \"id\": 35,\n" +
                "      \"name\": \"Comedy\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"id\": 80,\n" +
                "      \"name\": \"Crime\"\n" +
                "    }\n" +
                "  ],\n" +
                "  \"homepage\": \"http://www.universalstudiosentertainment.com/lock-stock-and-two-smoking-barrels/\",\n" +
                "  \"id\": 100,\n" +
                "  \"imdb_id\": \"tt0120735\",\n" +
                "  \"original_language\": \"en\",\n" +
                "  \"original_title\": \"Lock, Stock and Two Smoking Barrels\",\n" +
                "  \"overview\": \"A card shark and his unwillingly-enlisted friends need to make a lot of cash quick after losing a sketchy poker match. To do this they decide to pull a heist on a small-time gang who happen to be operating out of the flat next door.\",\n" +
                "  \"popularity\": 8.69,\n" +
                "  \"poster_path\": \"/qV7QaSf7f7yC2lc985zfyOJIAIN.jpg\",\n" +
                "  \"production_companies\": [\n" +
                "    {\n" +
                "      \"id\": 491,\n" +
                "      \"logo_path\": \"/rUp0lLKa1pr4UsPm8fgzmnNGxtq.png\",\n" +
                "      \"name\": \"Summit Entertainment\",\n" +
                "      \"origin_country\": \"US\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"id\": 21920,\n" +
                "      \"logo_path\": null,\n" +
                "      \"name\": \"The Steve Tisch Company\",\n" +
                "      \"origin_country\": \"\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"id\": 13419,\n" +
                "      \"logo_path\": null,\n" +
                "      \"name\": \"SKA Films\",\n" +
                "      \"origin_country\": \"\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"id\": 1382,\n" +
                "      \"logo_path\": \"/sOg7LGESPH5vCTOIdbMhLuypoLL.png\",\n" +
                "      \"name\": \"PolyGram Filmed Entertainment\",\n" +
                "      \"origin_country\": \"US\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"id\": 20076,\n" +
                "      \"logo_path\": null,\n" +
                "      \"name\": \"HandMade Films\",\n" +
                "      \"origin_country\": \"\"\n" +
                "    }\n" +
                "  ],\n" +
                "  \"production_countries\": [\n" +
                "    {\n" +
                "      \"iso_3166_1\": \"GB\",\n" +
                "      \"name\": \"United Kingdom\"\n" +
                "    }\n" +
                "  ],\n" +
                "  \"release_date\": \"1998-03-05\",\n" +
                "  \"revenue\": 28356188,\n" +
                "  \"runtime\": 105,\n" +
                "  \"spoken_languages\": [\n" +
                "    {\n" +
                "      \"iso_639_1\": \"en\",\n" +
                "      \"name\": \"English\"\n" +
                "    }\n" +
                "  ],\n" +
                "  \"status\": \"Released\",\n" +
                "  \"tagline\": \"A Disgrace to Criminals Everywhere.\",\n" +
                "  \"title\": \"Lock, Stock and Two Smoking Barrels\",\n" +
                "  \"video\": false,\n" +
                "  \"vote_average\": 8.0,\n" +
                "  \"vote_count\": 2939\n" +
                "}"
    }
}