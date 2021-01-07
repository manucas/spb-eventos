package com.ribeiro.spbeventos.repository;

import com.ribeiro.spbeventos.models.Evento;

import org.springframework.data.repository.CrudRepository;

public interface EventoRepository extends CrudRepository<Evento, String>{
    
    Evento findById(long id);
}
