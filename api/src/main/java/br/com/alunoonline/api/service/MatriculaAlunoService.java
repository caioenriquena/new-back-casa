package br.com.alunoonline.api.service;

import br.com.alunoonline.api.dtos.AtualizarNotasRequest;
import br.com.alunoonline.api.dtos.DisciplinasAlunoResponse;
import br.com.alunoonline.api.dtos.HistoricoAlunoResponse;
import br.com.alunoonline.api.enums.MatriculaAlunoStatusEnum;
import br.com.alunoonline.api.model.MatriculaAluno;
import br.com.alunoonline.api.repository.MatriculaAlunoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Service
public class MatriculaAlunoService {

    public static final double MEDIA_PARA_APROVACAO = 7.0;

    @Autowired
    MatriculaAlunoRepository matriculaAlunoRepository;

    public void criarMatricula(MatriculaAluno matriculaAluno) {
        matriculaAluno.setStatus(MatriculaAlunoStatusEnum.MATRICULADO);
        matriculaAlunoRepository.save(matriculaAluno);
    }

    // regra de negocio que o aluno tranca a disciplina

    public void trancarMatricula(Long matriculaAlunoId) {
        MatriculaAluno matriculaAluno =
                matriculaAlunoRepository.findById(matriculaAlunoId)
                        .orElseThrow(() ->
                                new ResponseStatusException(HttpStatus.NOT_FOUND,
                                        "Matricula do Aluno não encontrada !"));
        if (!MatriculaAlunoStatusEnum.MATRICULADO.equals(matriculaAluno.getStatus())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Só é possivel trancar uma matricula com o status MATRICULADO");
        }
        matriculaAluno.setStatus(MatriculaAlunoStatusEnum.TRANCADO);

        matriculaAlunoRepository.save(matriculaAluno);
    }

    public void atualizaNotas(Long matriculaAlunoId,
                              AtualizarNotasRequest atualizarNotasRequest) {
        MatriculaAluno matriculaAluno =
                matriculaAlunoRepository.findById(matriculaAlunoId)
                        .orElseThrow(() ->
                                new ResponseStatusException(HttpStatus.NOT_FOUND,
                                        "Matricula do Aluno não encontrada !"));


        if (matriculaAluno.getStatus().equals(MatriculaAlunoStatusEnum.TRANCADO)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Operação nao permitida para matricula com status trancada");

        }
        // Verifica se o front ta mandando a nota 1
        // atualizarNotasRequest.getNota1() :Traz a nota que vem do front
        if (atualizarNotasRequest.getNota1() != null) {
            // atualizarNotasRequest.getNota1() : Atualiza a nota 1 que vem atualmente do Banco
            matriculaAluno.setNota1(atualizarNotasRequest.getNota1());
        }
        // Verifica se o front ta mandando a nota 2
        // atualizarNotasRequest.getNota2() :Traz a nota que vem do front
        if (atualizarNotasRequest.getNota2() != null) {
            // atualizarNotasRequest.getNota2() : Atualiza a nota 2 que vem atualmente do Banco
            matriculaAluno.setNota2(atualizarNotasRequest.getNota2());
        }

        calculaMedia(matriculaAluno);
        matriculaAlunoRepository.save(matriculaAluno);
    }

    private Double calculaMedia(MatriculaAluno matriculaAluno) {
        Double nota1 = matriculaAluno.getNota1();
        Double nota2 = matriculaAluno.getNota2();
        Double media = 0.0;
        if (nota1 != null && nota2 != null) {
             media = (nota1 + nota2) / 2;
            matriculaAluno.setStatus(media >= MEDIA_PARA_APROVACAO ? MatriculaAlunoStatusEnum.APROVADO : MatriculaAlunoStatusEnum.REPROVADO);
        }
        return media;
    }

    public HistoricoAlunoResponse emitirHistorico(Long alunoId){
        List<MatriculaAluno> matriculasDoAluno = matriculaAlunoRepository.findMatriculaAlunoByAluno_Id(alunoId);


        if(matriculasDoAluno.isEmpty()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Esse Aluno não possui matriculas");
        }
        HistoricoAlunoResponse historicoAluno = new HistoricoAlunoResponse();
        historicoAluno.setNomeAluno(matriculasDoAluno.get(0).getAluno().getNome());
        historicoAluno.setCpfAluno(matriculasDoAluno.get(0).getAluno().getCpf());
        historicoAluno.setEmailAluno(matriculasDoAluno.get(0).getAluno().getEmail());

        List<DisciplinasAlunoResponse> disciplinasList = new ArrayList<>();

        for (MatriculaAluno matriculaAluno : matriculasDoAluno) {
            DisciplinasAlunoResponse disciplinasAlunoResponse = new DisciplinasAlunoResponse();
            disciplinasAlunoResponse.setNomeDisciplina(matriculaAluno.getDisciplina().getNome());
            disciplinasAlunoResponse.setNomeProfessor(matriculaAluno.getDisciplina().getProfessor().getNome());
            disciplinasAlunoResponse.setNota1(matriculaAluno.getNota1());
            disciplinasAlunoResponse.setNota2(matriculaAluno.getNota2());

            if (matriculaAluno.getNota1() != null && matriculaAluno.getNota2() != null) {

                disciplinasAlunoResponse.setMedia(calculaMedia(matriculaAluno));
            }else {
                disciplinasAlunoResponse.setMedia(null);
            }
            disciplinasAlunoResponse.setStatus(matriculaAluno.getStatus());

            disciplinasList.add(disciplinasAlunoResponse);
        }
        historicoAluno.setDisciplinasAlunoResponses(disciplinasList);

        return historicoAluno;

    }
}
