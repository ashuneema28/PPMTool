package io.ashuproject.ppmtool.services;

import io.ashuproject.ppmtool.domain.Project;
import io.ashuproject.ppmtool.exceptions.ProjectIdException;
import io.ashuproject.ppmtool.repositories.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    public Project saveOrUpdateProject(Project project){
        try {
            project.setProjectIdentifier(project.getProjectIdentifier().toUpperCase());
            return projectRepository.save(project);
        }
        catch (Exception ex){
            throw new ProjectIdException("Project Id '"+project.getProjectIdentifier().toUpperCase() + "' alresdy exists");
        }
    }
}
