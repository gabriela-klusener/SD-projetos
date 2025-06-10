package com.microservice.clientes.Clientes_microservice_curse.services;

import com.microservice.clientes.Clientes_microservice_curse.Clients.GroupServiceClient;
import com.microservice.clientes.Clientes_microservice_curse.Clients.UserServiceClient;
import com.microservice.clientes.Clientes_microservice_curse.dto.ProjetoDTO;
import com.microservice.clientes.Clientes_microservice_curse.enums.StatusProjetoModel;
import com.microservice.clientes.Clientes_microservice_curse.model.ProjetoModel;
import com.microservice.clientes.Clientes_microservice_curse.repository.ProjetoRepository;
import com.microservice.clientes.Clientes_microservice_curse.service.GestaoProjetoService;
import com.microservice.clientes.Clientes_microservice_curse.tests.ProjetoFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ExtendWith(SpringExtension.class)
public class GestaoProjetoServiceTests {

    @Mock
    private UserServiceClient userServiceClient;

    @Mock
    private GroupServiceClient groupServiceClient;

    @Mock
    private ProjetoRepository projetoRepository;

    @InjectMocks
    private GestaoProjetoService service;

    private long existingProjetoId, nonExistingProjetoId;

    private String existingProjetoName, nonExistingProjetoName;

    private ProjetoModel projeto;
    private ProjetoDTO projetoDTO;

    private List<ProjetoModel> projetoModelList = new ArrayList<>();


    @BeforeEach
    void setUp() throws Exception {
        existingProjetoId = 1L;
        nonExistingProjetoId = 1000L;
        existingProjetoName = "UCSAL";
        nonExistingProjetoName = "NON";

        projeto = ProjetoFactory.createProjeto(existingProjetoName);
        projetoDTO = new ProjetoDTO(
                projeto.getId(),
                projeto.getNome(),
                projeto.getObjetivo(),
                projeto.getEscopoResumo(),
                projeto.getPublicoAlvo(),
                projeto.getDataInicio(),
                StatusProjetoModel.EM_ANALISE,
                projeto.getProfessorCriadorId(),
                "Professor",
                projeto.getGrupoId(),
                "grupo"
        );

        projetoModelList.add(projeto);

        Mockito.when(projetoRepository.findAll()).thenReturn(projetoModelList);
        Mockito.when(projetoRepository.findById(existingProjetoId)).thenReturn(Optional.of(projeto));
        Mockito.when(projetoRepository.findById(nonExistingProjetoId)).thenReturn(Optional.empty());
        Mockito.when(projetoRepository.findByNome(existingProjetoName)).thenReturn(Optional.of(projeto));
        Mockito.when(projetoRepository.findByNome(nonExistingProjetoName)).thenReturn(Optional.empty());
        Mockito.when(projetoRepository.findByStatus(StatusProjetoModel.EM_ANALISE)).thenReturn(projetoModelList);
    }

    @Test
    public void findByIdShouldReturnProjectDTOWhenIdExists() {

        Optional<ProjetoDTO> result = service.buscarPorId(existingProjetoId);

        Assertions.assertTrue(result.isPresent());
        Assertions.assertEquals(projeto.getId(), result.get().getId());
        Assertions.assertEquals(projeto.getNome(), result.get().getNome());
    }

    @Test
    public void findByIdShouldReturnOptinalOfEmpityWhenIdNonExists() {

        Optional<ProjetoDTO> result = service.buscarPorId(nonExistingProjetoId);

        Assertions.assertFalse(result.isPresent());
    }

    @Test
    public void findByNameShouldReturnProjectDTOWhenIdExists() {

        Optional<ProjetoDTO> result = service.buscarPorNome(existingProjetoName);

        Assertions.assertTrue(result.isPresent());
        Assertions.assertEquals(projeto.getId(), result.get().getId());
        Assertions.assertEquals(projeto.getNome(), result.get().getNome());
    }

    @Test
    public void findByNameShouldReturnOptinalOfEmpityWhenIdNonExists() {

        Optional<ProjetoDTO> result = service.buscarPorNome(nonExistingProjetoName);

        Assertions.assertFalse(result.isPresent());
    }

    @Test
    public void findByStatusShouldReturnListProductWhenIdExists() {

        List<ProjetoDTO> result = service.buscarPorStatus(StatusProjetoModel.EM_ANALISE);

        Mockito.verify(projetoRepository).findByStatus(StatusProjetoModel.EM_ANALISE);

        ProjetoDTO dto = result.get(0);
        Assertions.assertEquals(projeto.getId(), dto.getId());
        Assertions.assertEquals(projeto.getNome(), dto.getNome());
        Assertions.assertEquals(projeto.getObjetivo(), dto.getObjetivo());
        Assertions.assertEquals(projeto.getEscopoResumo(), dto.getEscopoResumo());
        Assertions.assertEquals(projeto.getPublicoAlvo(), dto.getPublicoAlvo());
        Assertions.assertEquals(projeto.getDataInicio(), dto.getDataInicio());
        Assertions.assertEquals(StatusProjetoModel.EM_ANALISE, dto.getStatus());
    }
}
