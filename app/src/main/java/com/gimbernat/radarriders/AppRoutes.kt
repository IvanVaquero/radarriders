package com.gimbernat.radarriders

//Each value in the enum class is associated with a string value that represents the route for
//a destination in the application's navigation graph.
enum class AppRoutes(val value: String) {
    EDITUSER("EditUser"),
    WELCOME("Welcome"),
    LOGIN("Login"),
    MAP("map"),
//    MAIN("Main")
}