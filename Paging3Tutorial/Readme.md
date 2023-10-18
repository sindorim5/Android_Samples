# Paging 3 Tutorial

### Paginate the Data from DataSource
- PagingSource<Int, UnsplashImage>()
    - KEY : Identifier used to load the data. Usually a Page Number.
    - VALUE : Type of the data that will be loaded.

### Room supports Paging3 by default.
- If you want to paginate the data from the local DB, You don’t have to implement your own paging source class.
- Instead, everything is handled for you.
- Only thing you have to do is to add a PagingSource wrapper class as a return type of the function that is reading your db.

### Paging Data
- a container for Paged data from a single generation of loads.

### Paging Config
- Page Size
- Initial Load Size (Default = Page Size * 3)
- Max Size

### RemoteMediator
- To combine local storage with a remote queries in order to provide a consistent data flow to the users regardless if your network is available or not.
- Help us load the page data from the network into our DB. NOT TO our UI.

----

- 참고자료
  - [Paging 3 Stevdza-San Youtube] (https://www.youtube.com/playlist?list=PLSrm9z4zp4mGxiCHqcJybVL7GoSJ6lTTY)
  - [Paging 라이브러리 개요] (https://developer.android.com/topic/libraries/architecture/paging/v3-overview?hl=ko)