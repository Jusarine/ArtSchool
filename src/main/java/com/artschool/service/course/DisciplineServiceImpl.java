package com.artschool.service.course;

import com.artschool.model.entity.Discipline;
import com.artschool.repository.DisciplineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;

@Service
public class DisciplineServiceImpl implements DisciplineService{

    private final DisciplineRepository disciplineRepository;

    @Autowired
    public DisciplineServiceImpl(DisciplineRepository disciplineRepository) {
        this.disciplineRepository = disciplineRepository;
    }

    @Override
    @Transactional
    public void addDiscipline(String discipline){
        disciplineRepository.save(new Discipline(discipline));
    }

    @Override
    @Transactional
    public void addDiscipline(String... disciplines){
        for (String d : disciplines) {
            addDiscipline(d);
        }
    }

    @Override
    @Transactional
    public Discipline getDiscipline(String discipline){
        return disciplineRepository.findDisciplineByName(discipline);
    }

    @Override
    @Transactional
    public List<Discipline> getDisciplines(String... disciplines){
        List<Discipline> list = new LinkedList<>();
        for (String discipline : disciplines) {
            list.add(disciplineRepository.findDisciplineByName(discipline));
        }
        return list;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Discipline> findDisciplines(){
        return disciplineRepository.findAll();
    }
}
