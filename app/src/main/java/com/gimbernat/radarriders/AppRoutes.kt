package com.gimbernat.radarriders

//Each value in the enum class is associated with a string value that represents the route for
//a destination in the application's navigation graph.
enum class AppRoutes(val value: String) {
    WELCOME("Welcome"),
    EDITUSER("EditUser"),
    EDITRADAR("EditRadar"),
    EDITALERT("EditAlert"),
    REGISTRO("Registro"),
    NEWRADAR("Crear Radar"),
    COMMENT("Comentarios"),
    NEWALERT("Crear Alerta"),
    DELETEUSER("Eliminar Usuario"),
    DELETERADAR("Eliminar Radar"),
    DELETEALERT("Eliminar Alerta"),
    MAP("map"),
    // LOGIN("Login"),
    // MAIN("Main")
    // CAPSULE_DETAIL("CapsuleDetail")
}