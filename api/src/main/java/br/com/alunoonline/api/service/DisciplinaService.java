package br.com.alunoonline.api.service;

import br.com.alunoonline.api.model.Aluno;
import br.com.alunoonline.api.model.Disciplina;
import br.com.alunoonline.api.repository.DisciplinaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class DisciplinaService {
    @Autowired
    private DisciplinaRepository disciplinaRepository;

    public void criarDisciplina(Disciplina disciplina) {
        disciplinaRepository.save(disciplina);
    }

    public List<Disciplina> listarTodasDisciplinas() {
        return disciplinaRepository.findAll();
    }

    public Optional<Disciplina> buscarDisciplinaPorId(Long id) {
        return disciplinaRepository.findById(id);
    }

    public void deletarDisciplinaPorId(Long id) {
        disciplinaRepository.deleteById(id);
    }

    public void atualizarDisciplinaPorId(Disciplina disciplina) {

        Optional<Disciplina> disciplinaDoBancoDeDados = buscarDisciplinaPorId(disciplina.getId());

        if (disciplinaDoBancoDeDados.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Disciplina não encontrada no banco de dados");
        }
        Disciplina disciplinaEditada = disciplinaDoBancoDeDados.get();

        disciplinaEditada.setNome(disciplina.getNome());
        disciplinaEditada.setProfessor(disciplina.getProfessor());

        disciplinaRepository.save(disciplinaEditada);
    }

}
