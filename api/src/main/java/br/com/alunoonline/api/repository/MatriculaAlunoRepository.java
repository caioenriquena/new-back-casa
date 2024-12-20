package br.com.alunoonline.api.repository;

import br.com.alunoonline.api.model.MatriculaAluno;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.function.LongBinaryOperator;

@Repository
public interface MatriculaAlunoRepository extends JpaRepository<MatriculaAluno, Long> {
    List<MatriculaAluno>findMatriculaAlunoByAluno_Id(Long alunoIid);
}
