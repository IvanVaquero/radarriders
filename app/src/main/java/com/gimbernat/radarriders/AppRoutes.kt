package com.gimbernat.radarriders

//Each value in the enum class is associated with a string value that represents the route for
//a destination in the application's navigation graph.
enum class AppRoutes(val value: String) {
    WELCOME("Welcome"),
    LOGIN("Login"),
    MAP("map"),
    EDITUSER("EditUser"),
    EDITRADAR("EditRadar"),
    REGISTRO("Registro"),
    NEWRADAR("Crear Radar"),
    NEWALERT("Crear Alerta"),
    // MAIN("Main")
}