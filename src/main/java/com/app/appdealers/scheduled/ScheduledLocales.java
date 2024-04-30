package com.app.appdealers.scheduled;

import java.util.List;

import org.apache.commons.math3.ml.clustering.CentroidCluster;
import org.apache.commons.math3.ml.clustering.KMeansPlusPlusClusterer;
import org.apache.commons.math3.ml.distance.EuclideanDistance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.app.appdealers.entity.Grupo;
import com.app.appdealers.entity.Local;
import com.app.appdealers.repository.GrupoRepository;
import com.app.appdealers.repository.LocalRepository;

@Component
public class ScheduledLocales {
    
    @Autowired
    private LocalRepository localRepository;

    @Autowired
    private GrupoRepository grupoRepository;

    @Scheduled(cron = "0 0 0 * * ?") // Se ejecuta a media noche
    // @Scheduled(fixedRate = 30000)
    public void crearGruposDeLocales() {

        // Esta funcion lo que debe hacer es
        // 1. Seleccionar a todos los locales que no cuenten con un grupo [Atraves de un SELECT]
        // 2. Utilizar un algoritmo para agrupar estos locales en grupos de 5 estos grupos estaran formados por los comercios mas cercanos entre si
        // 3. Guardar los locales, ahora ya con un grupo, en la base de datos nuevamente

        List<Local> localWithoutGroup = localRepository.getAllLocalWithoutGroup();
        
        // Creamos un Cluster de K-Means
        int numeroGrupos = 5; // Cambiarlo cuando se tengan mas comercios
        // int numeroGrupos = localWithoutGroup.size() / 10;

        KMeansPlusPlusClusterer<Local> clusterer = new KMeansPlusPlusClusterer<>(numeroGrupos, -1, new EuclideanDistance());

        List<CentroidCluster<Local>> clusters = clusterer.cluster(localWithoutGroup);
        
        guardarGruposEnLaDB(clusters);

        System.out.println("Agrupación de locales completa");
    }

    private void guardarGruposEnLaDB(List<CentroidCluster<Local>> clusters) {
        for (int i = 0; i < clusters.size(); i++) {
            List<Local> localesDelGrupo = clusters.get(i).getPoints();
            // Crear nuevo grupo
            Grupo nuevoGrupo = new Grupo("Grupo número " + (i + 1), localesDelGrupo);

            grupoRepository.save(nuevoGrupo);

            for (Local local : localesDelGrupo) {
                // Guardar los locales en el nuevo grupo
                local.setGrupo(nuevoGrupo);
                localRepository.save(local);
                System.out.println(local.getId() + " " + local.getNombre() + " - Grupo " + (i + 1));
            }
        }
    }


}
