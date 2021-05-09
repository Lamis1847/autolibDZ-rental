# autolib rental app

Check out [this blog post](https://blog.mindorks.com/mvvm-architecture-android-tutorial-for-beginners-step-by-step-guide) to better understand the architecture elements and to have an example of it

We are using Retrofit to consume the services:
- [Details here](https://square.github.io/retrofit/)

- [Example here](https://blog.mindorks.com/using-retrofit-with-kotlin-coroutines-in-android)
___

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
    - api
    - model
    - repositories

- ui
(_All UI related elements and their view models should go in here:_)
    - adapter
    - view
    - viewmodel

- utils
(_Contains constants, wrappers and functions which are a utility for the project._)


> Note that you should keep a coherent name system for your files. Example: 
* SearchActivity.kt
* SearchViewModel.kt
* SearchAdapter.kt
* SearchRepository.kt

Please do not forget to check the coding conventions (charte de codage) before you start coding.

