package br.com.alunoonline.api.service;

import br.com.alunoonline.api.enums.MatriculaAlunoStatusEnum;
import br.com.alunoonline.api.model.MatriculaAluno;
import br.com.alunoonline.api.repository.MatriculaAlunoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class MatriculaAlunoService {
    @Autowired
    MatriculaAlunoRepository matriculaAlunoRepository;

    public void criarMatricula(MatriculaAluno matriculaAluno){
        matriculaAluno.setStatus(MatriculaAlunoStatusEnum.MATRICULADO);
        matriculaAlunoRepository.save(matriculaAluno);
    }

    public void trancarMatricula(Long matriculaAlunoId){
        MatriculaAluno matriculaAluno =
                matriculaAlunoRepository.findById(matriculaAlunoId)
                        .orElseThrow(()->
                                new ResponseStatusException(HttpStatus.NOT_FOUND,
                                        "Matricula do Aluno não encontrada !"));
    }
}
