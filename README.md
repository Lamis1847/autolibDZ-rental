# AutolibDZ rental app



## General project structure
- data
    - api
    - model
    - repositories


- ui
    - adapter
    - view
    - viewmodel


- utils

## Packages details
- data
(_All data and business logic related files should go in here:_)
    - api (Check [the example of Sayara](https://github.com/NadjibSb/SayaraDz-EE-Mobile/tree/master/app/src/main/java/sayaradz/api) to better understand the use of these files as well as other parts of the architecture)
        - ServiceBuilder.kt
        - ServiceProvider.kt
        - Api.kt
    - model
    - repositories

- ui
(_All UI related elements and their view models should go in here:_)
    - adapter
    - view
        - activity (_contains the app activities: SignIn, SignUp, MainActivity_)
        - fragment (_All fragments accessed by drawer menu_)
    - viewmodel

- utils
(_Contains constants, wrappers and functions which are a utility for the project._)

___


We are using Retrofit to consume the services:
- [Details here](https://square.github.io/retrofit/)

- [Example here](https://blog.mindorks.com/using-retrofit-with-kotlin-coroutines-in-android)



> Note that you should keep a coherent name system for your files. Example: 
* SearchActivity.kt
* SearchViewModel.kt
* SearchAdapter.kt
* SearchRepository.kt

Please do not forget to check the coding conventions (charte de codage) before you start coding.

