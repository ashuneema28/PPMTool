package io.ashuproject.ppmtool.services;

import io.ashuproject.ppmtool.domain.Backlog;
import io.ashuproject.ppmtool.domain.Project;
import io.ashuproject.ppmtool.exceptions.ProjectIdException;
import io.ashuproject.ppmtool.repositories.BacklogRepository;
import io.ashuproject.ppmtool.repositories.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProjectService {

    @Autowired
    private BacklogRepository backlogRepository;

    @Autowired
    private ProjectRepository projectRepository;

    public Project saveOrUpdateProject(Project project){
        try {
            project.setProjectIdentifier(project.getProjectIdentifier().toUpperCase());
            if(project.getId()==null){
                Backlog backlog = new Backlog();
                project.setBacklog(backlog);
                backlog.setProject(project);
                backlog.setProjectIdentifier(project.getProjectIdentifier().toUpperCase());
            }

            if(project.getId()!=null){
                project.setBacklog(backlogRepository.findByProjectIdentifier(project.getProjectIdentifier().toUpperCase()));
            }
            return projectRepository.save(project);
        }
        catch (Exception ex){
            throw new ProjectIdException("Project Id '"+project.getProjectIdentifier().toUpperCase() + "' alresdy exists");
        }
    }

    public Project findProjectByIdentifier(String projectId){

        Project project  = projectRepository.findByProjectIdentifier(projectId.toUpperCase());

        if(project==null){
            throw new ProjectIdException("Project Id '"+projectId.toUpperCase() + "' does not exist");
        }

        return project;
    }

    public Iterable<Project> findAllProjects(){
        return projectRepository.findAll();
    }

    public void deleteProjectByIdentifier(String projectId){
        Project project = projectRepository.findByProjectIdentifier(projectId.toUpperCase());

        if(project==null){
            throw new ProjectIdException("Cannot delete project  '"+projectId.toUpperCase() + "' as it does not exist");
        }

        projectRepository.delete(project);
    }
}
