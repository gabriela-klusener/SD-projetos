package com.microservice.clientes.Clientes_microservice_curse.tests;

import com.microservice.clientes.Clientes_microservice_curse.model.ProjetoModel;

import java.time.Instant;
import java.util.Date;

public class ProjetoFactory {

    public static ProjetoModel createProjeto() {
        Date dataInicioFixa = Date.from(Instant.now());

        ProjetoModel projeto = new ProjetoModel(
                "Projeto de Pesquisa",
                "Analisar dados clínicos",
                "Escopo resumido do projeto aqui",
                "Estudantes de medicina",
                dataInicioFixa,
                42L
        );

        return projeto;
    }

    public static ProjetoModel createProjeto(String name) {
        ProjetoModel projetoModel = createProjeto();
        projetoModel.setNome(name);
        return projetoModel;
    }
}
