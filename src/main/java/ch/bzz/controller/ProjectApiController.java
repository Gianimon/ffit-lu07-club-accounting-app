package ch.bzz.controller;

import ch.bzz.Project;
import ch.bzz.ProjectRepository;
import ch.bzz.generated.api.ProjectApi;
import ch.bzz.generated.model.LoginProject200Response;
import ch.bzz.generated.model.LoginRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.util.StringUtils;

import java.util.Optional;

@RestController
@RequestMapping("api")
public class ProjectApiController implements ProjectApi {
    private ProjectRepository projectRepository;
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
    @Autowired
    public ProjectApiController(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    @Override
    public ResponseEntity<Void> createProject(LoginRequest loginRequest) {
        if (loginRequest == null || !StringUtils.hasText(loginRequest.getProjectName()) || !StringUtils.hasText(loginRequest.getPassword())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        String projectName =  loginRequest.getProjectName().trim();
        String password = loginRequest.getPassword();
        Optional<Project> optionalProject = projectRepository.findById(loginRequest.getProjectName());
        if (optionalProject.isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } else {
            Project newProject = new Project();
            newProject.setProjectName(projectName);
            String encodedPassword = encoder.encode(password);
            newProject.setPasswordHash(encodedPassword);
            projectRepository.save(newProject);
            return ResponseEntity.status(HttpStatus.OK).build();

        }
    }

    @Override
    public ResponseEntity<LoginProject200Response> loginProject(LoginRequest loginRequest) {
        return null;
    }
}