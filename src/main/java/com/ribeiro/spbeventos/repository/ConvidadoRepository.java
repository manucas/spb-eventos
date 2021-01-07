package com.ribeiro.spbeventos.repository;

import com.ribeiro.spbeventos.models.Convidado;
import com.ribeiro.spbeventos.models.Evento;

import org.springframework.data.repository.CrudRepository;

public interface ConvidadoRepository extends CrudRepository<Convidado, String> {
    
    // Lista de convidados por evento
    Iterable<Convidado> findByEvento(Evento evento);

    // Busca Convidado por rg
    Convidado findByRg(String rg);

}
