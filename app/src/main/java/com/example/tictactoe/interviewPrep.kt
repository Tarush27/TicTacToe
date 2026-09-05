package com.example.tictactoe

class MyActivity(){

    // initialize view model
    // use view model provider, passing instance of repository, retrofit service and view model class
    //TextView()
    // use observe() of live data from View model to observe the UI and map the deserialized response to the text view

}

class Retrofit(){
    // create retrofit builder object
    // use coroutine to run in the background
    // provide the implementation of the service here which would be hit by the base URL
}

interface RetrofitService{
    // function to fetch response annotated with @Get
}

class ViewModel(/* pass repository variable here*/){
    // uses viewModelScope as coroutine scope to fetch the data from the repo in background
    // fetch and store the live data value here
}

class Repo(/* pass the service variable here*/){
    // fetch the remote response here and return as states which would show success and error
    // live data, flow -> to store the state observable to UI
}

class states(){
    // sealed class to define states such as success error loading
}

class ViewModelFactory(/* pass the repos variable here*/){
    // return instance of view model
}
