package com.ribeiro.spbpostgreseventos.repository;

import com.ribeiro.spbpostgreseventos.models.Evento;

import org.springframework.data.repository.CrudRepository;

public interface EventoRepository extends CrudRepository<Evento, String>{
    
    Evento findById(long id);
}
