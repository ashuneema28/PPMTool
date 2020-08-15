package io.ashuproject.ppmtool.services;

import io.ashuproject.ppmtool.domain.Backlog;
import io.ashuproject.ppmtool.domain.ProjectTask;
import io.ashuproject.ppmtool.repositories.BacklogRepository;
import io.ashuproject.ppmtool.repositories.ProjectTaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProjectTaskService {


    @Autowired
    private BacklogRepository backlogRepository;

    @Autowired
    private ProjectTaskRepository projectTaskRepository;


    public ProjectTask addProjectTask(String projectIdentifier, ProjectTask projectTask){
        Backlog backlog = backlogRepository.findByProjectIdentifier(projectIdentifier);
        projectTask.setBacklog(backlog);
        Integer BacklogSequence = backlog.getPTSequence();
        BacklogSequence++;
        backlog.setPTSequence(BacklogSequence);
        projectTask.setProjectSequence(backlog.getProjectIdentifier()+"-"+BacklogSequence);
        projectTask.setProjectIdentifier(projectIdentifier);

        if(projectTask.getStatus()==""|| projectTask.getStatus()==null){
            projectTask.setStatus("TO_DO");
        }

        if(projectTask.getPriority()==null || projectTask.getPriority()==0){
            projectTask.setPriority(3);
        }

        return projectTaskRepository.save(projectTask);
    }
}
