package com.ejemplo.rxjava;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import java.util.Arrays;
import java.util.List;

public class InscripcionMaterias {

    public static void main(String[] args) {
        // Lista de materias (algunas con cupo, otras sin cupo)
        List<Materia> materias = Arrays.asList(
                new Materia("Matemáticas", true),
                new Materia("Física", false),
                new Materia("Química", true),
                new Materia("Historia", true),
                new Materia("Arte", false)
        );

        // Lista de estudiantes que desean inscribirse
        List<Estudiante> estudiantes = Arrays.asList(
                new Estudiante("Alice"),
                new Estudiante("Bob"),
                new Estudiante("Carlos")
        );

        // Observable de materias disponibles (filtrando solo las que tienen cupo)
        Observable<Materia> materiasDisponibles = Observable.fromIterable(materias)
                .filter(Materia::tieneCupo); // Filtra solo las materias con cupo

        // Observable de inscripciones, usando flatMap para simular la inscripción de cada estudiante en materias disponibles
        Observable<String> inscripciones = Observable.fromIterable(estudiantes)
                .flatMap(estudiante -> 
                    materiasDisponibles
                        .toList() // Colecta las materias disponibles para cada estudiante
                        .toObservable() // Convierte la lista a un observable
                        .map(materiasList -> {
                            StringBuilder inscripcion = new StringBuilder();
                            for (Materia materia : materiasList) {
                                inscripcion.append("Estudiante: ").append(estudiante.getNombre())
                                           .append(" inscrito en ").append(materia.getNombre()).append("\n");
                            }
                            return inscripcion.toString();
                        })
                );

        // Observable combinando mensajes adicionales con zip para mostrar detalles de inscripción
        Observable<String> mensajesFinales = Observable.zip(
                inscripciones,
                Observable.just("¡Inscripción exitosa!", "¡Listo para la clase!", "Revisa tu horario"),
                (inscripcion, mensaje) -> inscripcion + " - " + mensaje
        );

        // Observable que mezcla listas de materias obligatorias y optativas usando merge
        Observable<String> materiasCombinadas = Observable.merge(
                Observable.just("Matemáticas", "Física", "Química"), // Materias obligatorias
                Observable.just("Historia", "Arte") // Materias optativas
        );

        // Suscripciones
        System.out.println("----- Inscripciones -----");
        mensajesFinales.subscribe(
                System.out::println,
                Throwable::printStackTrace,
                () -> System.out.println("¡Todas las inscripciones completadas!")
        );

        System.out.println("\n----- Lista de materias -----");
        materiasCombinadas.subscribe(
                System.out::println,
                Throwable::printStackTrace,
                () -> System.out.println("¡Lista de materias combinada!")
        );
    }

}
