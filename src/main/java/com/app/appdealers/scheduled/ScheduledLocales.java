package com.app.appdealers.scheduled;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.app.appdealers.entity.Local;
import com.app.appdealers.repository.LocalRepository;

@Component
public class ScheduledLocales {
    
    @Autowired
    private LocalRepository localRepository;

    @Scheduled(fixedRate = 10000) // Se ejecuta a las 0:25 am
    public void crearGruposDeLocales() {

        // Esta funcion lo que debe hacer es
        // 1. Seleccionar a todos los locales que no cuenten con un grupo [Atraves de un SELECT]
        // 2. Utilizar un algoritmo para agrupar estos locales
        // 3. Guardar los locales, ahora ya con un grupo, en la base de datos nuevamente

        List<Local> localWithoutGroup = localRepository.getAllLocalWithoutGroup();

        for(Local local : localWithoutGroup) {
            System.out.println(local.getNombre());
        }

        System.out.println("Agrupación de locales completa");
    }


}
