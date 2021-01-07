package com.ribeiro.spbeventos.controllers;

import javax.validation.Valid;

import com.ribeiro.spbeventos.models.Convidado;
import com.ribeiro.spbeventos.models.Evento;
import com.ribeiro.spbeventos.repository.ConvidadoRepository;
import com.ribeiro.spbeventos.repository.EventoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class EventoController {
    // Injeta dependencias de Evento
    @Autowired
    private EventoRepository eventoRepository;
    // Injeta dependencias de Convidado
    @Autowired
    private ConvidadoRepository convidadoRepository;

    // ------ Lista de Eventos -----------
    @RequestMapping("/eventos")
    public ModelAndView ListaEventos(){
        // Busca lista de eventos
        Iterable<Evento> eventos = eventoRepository.findAll();
        // Prepara objeto e passa para a view
        ModelAndView modelAndView = new ModelAndView("index");
        modelAndView.addObject("eventos", eventos);
        return modelAndView;
    }

    // ------ Evoca view cadastar ---------
    @RequestMapping(value = "/cadastrarEvento", method = RequestMethod.GET)
    public String view(){
        return "evento/cadastrar";
    }

    // ----- Cadastrar Evento ------------
    @RequestMapping(value = "/cadastrarEvento", method = RequestMethod.POST)
    public String eventoPost(@Valid Evento evento,
    BindingResult result, RedirectAttributes attributes){
        if(result.hasErrors()){
            attributes.addFlashAttribute("mensagem", "Verifque os Campos!");
        }else{
        // Salva Registro
        eventoRepository.save(evento);
        attributes.addFlashAttribute("mensagem", "Evento Salvo com Sucesso!");
        }
        // Retona a view cadastrar
        return "redirect:/cadastrarEvento";
    }

    // Deletar Evento
    @RequestMapping("/deletarEvento")
    public String deletarEvento(long id){
        // Busca evento pelo id
        Evento evento = eventoRepository.findById(id);
        // Deleta Evento
        eventoRepository.delete(evento);
        return "redirect:eventos";
    }

    // ------ Evoca view detalhes --------
    @RequestMapping(value="/{id}", method=RequestMethod.GET)
    public ModelAndView detalhesEvento(@PathVariable("id") long id){
        // Busca o evento pelo id
        Evento evento = eventoRepository.findById(id);
        // Referencia a view destino
        ModelAndView modelAndView = new ModelAndView("evento/detalhes");
        // Passa objeto para a view destino
        modelAndView.addObject("evento", evento);
        // System.out.println("evento " + evento);
        // Busca lista convidados por evento
        Iterable<Convidado> convidados = convidadoRepository.findByEvento(evento);
        // Passa objeto para a view destino
        modelAndView.addObject("convidados", convidados);
        // Evoca a view destino com os objetos
        return modelAndView;
    }

    // ------ Cadastrar Convidado associado a Evento --------
    @RequestMapping(value="/{id}", method=RequestMethod.POST)
    public String convidadoPost(@PathVariable("id") long id, 
        @Valid  Convidado convidado,
        BindingResult result, RedirectAttributes attributes){
        
            if (result.hasErrors()){
            attributes.addFlashAttribute("mensagem", "Verifque os Campos!");
        }else{
            // Busca o evento pelo id
            Evento evento = eventoRepository.findById(id);
            // Relaciona o Convidado com o Evento
            convidado.setEvento(evento);
            // Salva o Convidado mais Evento relacionado
            convidadoRepository.save(convidado);
            attributes.addFlashAttribute("mensagem", "Registro Salvo com Sucesso!");
        }    
        return "redirect:/{id}";
    }

    // Deletar Convidado
    @RequestMapping("/deletarConvidado")
    public String deletarConvidado(String rg){
        // Busca convidado pelo rg
        Convidado convidado = convidadoRepository.findByRg(rg);
        // Deleta Convidado
        convidadoRepository.delete(convidado);
        // Recupera o Evento relacionado
        Evento evento = convidado.getEvento();
        // Recupera o codigo do Evento
        long idLong = evento.getId();
        // Converte para String
        String id = "" + idLong;
        // Evoca o metodo detalhesEvento que chama a View detalhesEvento
        return "redirect:/" + id;
    }
}
