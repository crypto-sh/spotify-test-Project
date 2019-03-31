# Spotify Test Project

This project created base on [VOI Technology](https://www.voiscooters.com) assignment for hiring process.


It is base on Kotlin and Spotify webService with most the new Android architecture components and another useful library on android such as
  - viewModel
  - liveData
  - Retrofit
  - Dagger 2
  - data Binding
  - MVVM
  - Room
  - Kotlin Coroutines
  - Glide
  - androidTest with espresso and mock

# Description

- SplashActivity is the launcher activity.It's just contains of Kotlin Coroutines for start another activity.

- MainActivity : It's contains paging list RecyclerView which just show vertical list of  
    - MainActivityViewModel viewModel of MainActivity, handle Data Request and action. Request to DataSource.Factory is based on changing query.
    - MainDataSourceFactory DataSource Factory for Handle PageKeyedDataSource data request.
    - PageKeyedDataSource handle request by paging parameters offset and pageSize. I've implemented Room but It's not connected to this part.
    - BoundaryCallback is not implemented because we just fetch data from network but if we're going to add Room in the process that would be necessarily too.

- ListWithoutPaging : It' contains vertical RecyclerView and in the RecyclerView I've implemented horizontal RecyclerView and that's contains Artist, Album, Playlist and Track.
    - ListWithoutPagingViewModel : viewModel of ListWithoutPaging, handle Data Request and action. Request to DataSource.Factory is based on changing query.
    - DataRepository implemented for Handle request data.  

- BaseApp is Application class and the link between Kotlin with JNI. I've two String parameters in JNI for clientId and ClientSecret.

- RestRequest is main Interface of Retrofit and It can handle development and test mode. in this part we have 2 webService getAccessToken for request token Oath2 form server and searchArtist for get list data. All the class related to Retrofit is in api package.

- BindingAdapter class used for custom attribute we need for dataBinding.

- SpotifyDb is Room database for save Artist list data, ArtistDao is contains query should be run on database. and for saving Image object in database I used DataConvertor class to change the format from object to Json and Json to images.   

- BaseActivity is a general class and all activities extends from it. Dagger is in this class and it also had some useful module.

- AppExecuter is responsible for execute all task in the project on Network, IO and Main Thread.

- LogHelper is a class for writing all Log in the hole project.


- Test Class
    - androidTest
            - webServiceTest for mocking web Service and test result.(It need to be complete)
            - ArtistDaoTest  for test database and Artist Table.
            - ListPageInstrumentedTest for test ListWithoutPaging and adapter.


Prerequisites
=============

    - Android Studio 3.3.2
    - Gradle version 4.10.1+
    - Kotlin 1.3.21



## Author

[Ali Shatergholi](https://github.com/alishatergholi)
