package io.ashuproject.ppmtool.web;


import io.ashuproject.ppmtool.domain.Project;
import io.ashuproject.ppmtool.domain.ProjectTask;
import io.ashuproject.ppmtool.services.MapValidationErrorService;
import io.ashuproject.ppmtool.services.ProjectTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/backlog")
@CrossOrigin
public class BacklogController {

    @Autowired
    private ProjectTaskService projectTaskService;

    @Autowired
    private MapValidationErrorService mapValidationErrorService;


    @PostMapping("/{backlog_id}")
    public ResponseEntity<?> addPTtoBacklog(@Valid @RequestBody ProjectTask projectTask,
                                            BindingResult result, @PathVariable String backlog_id, Principal principal){

        ResponseEntity<?> erroMap = mapValidationErrorService.MapValidationService(result);
        if (erroMap != null) return erroMap;

        ProjectTask projectTask1 = projectTaskService.addProjectTask(backlog_id, projectTask, principal.getName());

        return new ResponseEntity<ProjectTask>(projectTask1, HttpStatus.CREATED);

    }

    @GetMapping("/{backlog_id}")
    public Iterable<ProjectTask> getProjectBacklog(@PathVariable String backlog_id,Principal principal){

        return projectTaskService.findBacklogByID(backlog_id,principal.getName());
    }

    @GetMapping("/{backlog_id}/{pt_id}")
    public ResponseEntity<?> getProjectTask(@PathVariable String backlog_id, @PathVariable String pt_id, Principal principal){

        ProjectTask projectTask=projectTaskService.findPTByProjectSequence(backlog_id,pt_id, principal.getName());
        return new ResponseEntity<ProjectTask>(projectTask, HttpStatus.OK);
    }

    @PatchMapping("/{backlog_id}/{pt_id}")
    public ResponseEntity<?> updateProjecTask(@Valid @RequestBody ProjectTask projectTask,BindingResult result,@PathVariable String backlog_id, @PathVariable String pt_id, Principal principal){

        ResponseEntity<?> erroMap = mapValidationErrorService.MapValidationService(result);
        if (erroMap != null) return erroMap;

        ProjectTask updatesTask = projectTaskService.updateByProjectSequence(projectTask,backlog_id,pt_id, principal.getName());

        return new ResponseEntity<ProjectTask>(updatesTask,HttpStatus.OK);
    }

    @DeleteMapping("/{backlog_id}/{pt_id}")
    public ResponseEntity<?> deleteProjectTask(@PathVariable String backlog_id, @PathVariable String pt_id, Principal principal){

        projectTaskService.deletePTbyProjectSequence(backlog_id,pt_id, principal.getName());

        return new ResponseEntity<String>("Project task : '"+pt_id+"' was deleted successfully",HttpStatus.OK);
    }

}
