package br.com.alunoonline.api.service;

import br.com.alunoonline.api.model.Aluno;
import br.com.alunoonline.api.repository.AlunoRepository;
import jakarta.persistence.Id;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class AlunoService {

    @Autowired
    AlunoRepository alunoRepository;

    public void criarAluno(Aluno aluno){
        alunoRepository.save(aluno);
    }

    public List<Aluno> listarTodosAlunos (){
         return alunoRepository.findAll();
    }

    public Optional<Aluno> buscarAlunoPorId(Long id){
        return alunoRepository.findById(id);
    }

    public void deletarAlunoPorId(Long id){
        alunoRepository.deleteById(id);
    }

    public void atualizarAlunoPorId( Aluno aluno){
        //Verificando se o aluno existe no banco de dados
        Optional<Aluno> alunoDoBancoDeDados = buscarAlunoPorId(aluno.getId());

        // E se nao tiver? fazemos a verificação com o if

        if (alunoDoBancoDeDados.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Aluno não encontrado no banco de dados");
        }
        // se passar pelo if, devemos aramazenar o aluno  numa variavel
        Aluno alunoEditado =  alunoDoBancoDeDados.get();
        // Com o aluno editado, vou fazer os sets para atualizar os atributos dele.
        alunoEditado.setNome(aluno.getNome());
        alunoEditado.setCpf(aluno.getCpf());
        alunoEditado.setEmail(aluno.getEmail());
        /* apos atualizar os atributos, salvo o aluno editado no banco de dados atraves da
        injeçao de dependência que eu tenho com o repositorio */
        alunoRepository.save(alunoEditado);
    }
}
