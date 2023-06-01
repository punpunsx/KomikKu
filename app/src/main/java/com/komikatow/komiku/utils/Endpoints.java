package com.komikatow.komiku.utils;

public final class Endpoints {

    //Server utama
    public static final String KOMIK_TERBARU = "https://komi.katowproject.app/api/komikindo/komik-terbaru/page/1/";
    public static final String KOMIK_TYPE_MANGA = "https://komi.katowproject.app/api/komikindo/komikk/manga/page/1/";
    public static final String KOMIK_TYPE_MANHWA= "https://komi.katowproject.app/api/komikindo/komikk/manhwa/page/1/";
    public static final String KOMIK_TYPE_MANHUA = "https://komi.katowproject.app/api/komikindo/komikk/manhua/page/1/";
    public static final String KOMIK_ALL_SEARCH = "https://komi.katowproject.app/api/komikindo/search/";
    public static final String KOMIK_DETAIL = "https://komi.katowproject.app/api/komikindo/";
    public static final String KOMIK_DETAIL_CHAPTER = "https://komi.katowproject.app/api/komikindo/chapter/";
    public static final String KOMIK_LIST_GENRE = "https://komi.katowproject.app/api/komikindo/genres/";
    public static final String KOMIK_LIST_DETAIL_GENRE = "https://komi.katowproject.app/api/komikindo/";//action/page/1
    public static final String KOMIK_LIST_DETAIL_GENRE_FROM_DETAIL = "https://komi.katowproject.app/api/komikindo";// /action/page/1

    //Server backup
    public static final String KOMIK_TYPE_MANGA_BACKUP = "https://komiku-api.fly.dev/api/comic/list?filter=manga";
    public static final String KOMIK_TYPE_MANHWA_BACKUP = "https://komiku-api.fly.dev/api/comic/list?filter=manhwa";
    public static final String KOMIK_TYPE_MANHUA_BACKUP = "https://komiku-api.fly.dev/api/comic/list?filter=manhua";
    public static final String KOMIK_TERBARU_BACKUP = "https://komiku-api.fly.dev/api/comic/recommended/page/1";
    public static final String KOMIK_SEARCH_BACKUP = "https://komiku-api.fly.dev/api/comic/search/";
    public static final String KOMIK_POPULER_BACKUP = "https://komiku-api.fly.dev/api/comic/popular/page/1";
    public static final String KOMIK_DETAIL_BACKUP = "https://komiku-api.fly.dev/api/comic/info/";
    public static final String KOMIK_DETAIL_CHAPTER_BACKUP = "https://komiku-api.fly.dev/api/comic/chapter";

    // API KEY
    public static final String BASE_UPDATE_API = "https://api.jsonbin.io/v3/b/6472b6919d312622a36693d4/";
    public static final String API_KEY = "$2b$10$GmrXGgbr/1CCr3ia8PB1N.I3EsbBHVjuwp7LBiFASPbOs.ctQEHdi";

}
