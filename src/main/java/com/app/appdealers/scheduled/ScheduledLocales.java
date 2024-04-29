package com.app.appdealers.scheduled;

import java.util.List;

import org.apache.commons.math3.ml.clustering.CentroidCluster;
import org.apache.commons.math3.ml.clustering.Cluster;
import org.apache.commons.math3.ml.clustering.KMeansPlusPlusClusterer;
import org.apache.commons.math3.ml.distance.EuclideanDistance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.app.appdealers.entity.Local;
import com.app.appdealers.repository.LocalRepository;

@Component
public class ScheduledLocales {
    
    @Autowired
    private LocalRepository localRepository;

    @Scheduled(fixedRate = 30000) // Se ejecuta a las 0:25 am
    public void crearGruposDeLocales() {

        // Esta funcion lo que debe hacer es
        // 1. Seleccionar a todos los locales que no cuenten con un grupo [Atraves de un SELECT]
        // 2. Utilizar un algoritmo para agrupar estos locales en grupos de 5 estos grupos estaran formados por los comercios mas cercanos entre si
        // 3. Guardar los locales, ahora ya con un grupo, en la base de datos nuevamente

        List<Local> localWithoutGroup = localRepository.getAllLocalWithoutGroup();
        
        // Creamos un Cluster de K-Means
        int numeroGrupos = 3;
        // int numeroGrupos = localWithoutGroup.size() / 10;

        KMeansPlusPlusClusterer<Local> clusterer = new KMeansPlusPlusClusterer<>(numeroGrupos, -1, new EuclideanDistance());

        List<CentroidCluster<Local>> clusters = clusterer.cluster(localWithoutGroup);
        
        guardarGruposEnLaDB(clusters);

        System.out.println("Agrupación de locales completa");
    }

    private void guardarGruposEnLaDB(List<CentroidCluster<Local>> clusters) {
        for (int i = 0; i < clusters.size(); i++) {
            List<Local> localesDelGrupo = clusters.get(i).getPoints();
            for (Local local : localesDelGrupo) {
                System.out.println(local.getId() + " " + local.getNombre() + " - Grupo " + (i + 1));
            }
        }
    }


}
