package com.app.appdealers.scheduled;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.app.appdealers.repository.FaseRepository;
import org.apache.commons.math3.ml.clustering.CentroidCluster;
import org.apache.commons.math3.ml.clustering.KMeansPlusPlusClusterer;
import org.apache.commons.math3.ml.distance.EuclideanDistance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.app.appdealers.entity.Grupo;
import com.app.appdealers.entity.Comercio;
import com.app.appdealers.repository.GrupoRepository;
import com.app.appdealers.repository.ComercioRepository;

@Component
public class ScheduledLocales {
    
    @Autowired
    private ComercioRepository comercioRepository;

    @Autowired
    private GrupoRepository grupoRepository;

    @Autowired
    private FaseRepository faseRepository;

//    @Scheduled(cron = "0 0 0 * * ?") // Se ejecuta a media noche
        @Scheduled(fixedRate = 30000)
    public void crearGruposDeLocales() {

        // Esta funcion lo que debe hacer es
        // 1. Seleccionar a todos los locales que no cuenten con un grupo [Atraves de un SELECT]
        // 2. Utilizar un algoritmo para agrupar estos locales en grupos de 5 estos grupos estaran formados por los comercios mas cercanos entre si
        // 3. Guardar los locales, ahora ya con un grupo, en la base de datos nuevamente
        int localesXGrupo = 5;

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, -1);
        Date oneMonthAgo = calendar.getTime();

        // eliminarGruposAsignadosALosComercios(comercioRepository.findAll());
        comercioRepository.setGrupoNullWhereFaseIsOneOrThree();
        grupoRepository.deleteAllGroupsiWithIdFaseOne();

        List<Comercio> comercioWithoutGroup = comercioRepository.findComerciosWithoutRecentVisita(oneMonthAgo);

        if(comercioWithoutGroup.size() < localesXGrupo) {
            System.out.println("No hay suficientes locales para hacer la agrupación");
            return;
        }
        // Creamos un Cluster de K-Means
        int numeroGrupos = comercioWithoutGroup.size() / localesXGrupo; //[5] Cambiarlo cuando se tengan mas comercios
        // int numeroGrupos = localWithoutGroup.size() / 10;

        KMeansPlusPlusClusterer<Comercio> clusterer = new KMeansPlusPlusClusterer<>(numeroGrupos, -1, new EuclideanDistance());

        List<CentroidCluster<Comercio>> clusters = clusterer.cluster(comercioWithoutGroup);

        guardarGruposEnLaDB(clusters);

        System.out.println("Agrupación de locales completa");
    }

    private void eliminarGruposAsignadosALosComercios(List<Comercio> comercios) {
        for (Comercio listaComercio : comercios) {
            listaComercio.setGrupo(null);
        }
        comercioRepository.saveAll(comercios);
    }

    private void guardarGruposEnLaDB(List<CentroidCluster<Comercio>> clusters) {
        for (int i = 0; i < clusters.size(); i++) {
            List<Comercio> localesDelGrupo = clusters.get(i).getPoints();
            // Create new group
            Grupo nuevoGrupo = new Grupo("Group number " + (i + 1), localesDelGrupo);
            nuevoGrupo.setFase(faseRepository.findById(1).orElseThrow(null)); // Sin tomar
            nuevoGrupo.setFechaCreacion(new Date());

            grupoRepository.save(nuevoGrupo);

            for (Comercio comercio : localesDelGrupo) {
                // Save locals in new group
                comercio.setGrupo(nuevoGrupo);
                comercioRepository.save(comercio);
                System.out.println(comercio.getId() + " " + comercio.getRazonSocial() + " - Group " + (i + 1));
            }
        }
    }


}
