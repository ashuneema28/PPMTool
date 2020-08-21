package io.ashuproject.ppmtool.services;

import io.ashuproject.ppmtool.domain.Backlog;
import io.ashuproject.ppmtool.domain.Project;
import io.ashuproject.ppmtool.domain.ProjectTask;
import io.ashuproject.ppmtool.exceptions.ProjectNotFoundException;
import io.ashuproject.ppmtool.repositories.BacklogRepository;
import io.ashuproject.ppmtool.repositories.ProjectRepository;
import io.ashuproject.ppmtool.repositories.ProjectTaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectTaskService {


    @Autowired
    private BacklogRepository backlogRepository;

    @Autowired
    private ProjectTaskRepository projectTaskRepository;

    @Autowired
    private ProjectRepository projectRepository;


    public ProjectTask addProjectTask(String projectIdentifier, ProjectTask projectTask){

        try {
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
        catch (Exception ex){
            throw new ProjectNotFoundException("Project Not Found");
        }
    }

    public Iterable<ProjectTask> findBacklogByID(String id) {

        Project project = projectRepository.findByProjectIdentifier(id);
        if(project==null){
            throw new ProjectNotFoundException("Project with ID: '"+ id + "' does not exist");
        }

        return projectTaskRepository.findByProjectIdentifierOrderByPriority(id);
    }

    public ProjectTask findPTByProjectSequence(String backlog_id,String pt_id){

        Backlog backlog = backlogRepository.findByProjectIdentifier(backlog_id);
        if(backlog==null){
            throw new ProjectNotFoundException("Project with ID: '"+ backlog_id + "' does not exist");
        }

        ProjectTask projectTask = projectTaskRepository.findByProjectSequence(pt_id);
        if(projectTask==null){
            throw new ProjectNotFoundException("Project Task: '"+pt_id+"' not Found");
        }

        if(!projectTask.getProjectIdentifier().equals(backlog_id)){
            throw new ProjectNotFoundException("Project Task: '"+pt_id+"' does not exixst in project: '"+backlog_id+"'");
        }

        return projectTask;
    }

    public ProjectTask updateByProjectSequence(ProjectTask updatedTask, String backlog_id, String pt_id){
        ProjectTask projectTask = findPTByProjectSequence(backlog_id,pt_id);

        projectTask = updatedTask;

        return projectTaskRepository.save(projectTask);
    }

    public void deletePTbyProjectSequence(String backlog_id, String pt_id){
        ProjectTask projectTask = findPTByProjectSequence(backlog_id,pt_id);

        projectTaskRepository.delete(projectTask);
    }
}
