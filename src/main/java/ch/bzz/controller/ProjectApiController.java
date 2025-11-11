package ch.bzz.controller;

import ch.bzz.Project;
import ch.bzz.ProjectRepository;
import ch.bzz.generated.api.ProjectApi;
import ch.bzz.generated.model.LoginProject200Response;
import ch.bzz.generated.model.LoginRequest;
import ch.bzz.util.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.util.StringUtils;

import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("api")
public class ProjectApiController implements ProjectApi {
    private ProjectRepository projectRepository;
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
    private final JwtUtil jwtUtil;

    @Autowired
    public ProjectApiController(ProjectRepository projectRepository, JwtUtil jwtUtil) {
        this.projectRepository = projectRepository;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public ResponseEntity<Void> createProject(LoginRequest loginRequest) {
        if (loginRequest == null || !StringUtils.hasText(loginRequest.getProjectName()) || !StringUtils.hasText(loginRequest.getPassword())) {
            log.error("no ProjectName given");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        String projectName =  loginRequest.getProjectName().trim();
        String password = loginRequest.getPassword();
        Optional<Project> optionalProject = projectRepository.findById(loginRequest.getProjectName());
        if (optionalProject.isPresent()) {
            log.error("ProjectName already exists: '{}'", projectName);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        } else {
            Project newProject = new Project();
            newProject.setProjectName(projectName);
            String encodedPassword = encoder.encode(password);
            newProject.setPasswordHash(encodedPassword);
            projectRepository.save(newProject);
            log.info("Successfully executed project");
            return  ResponseEntity.status(200).build();
        }
    }

    @Override
    public ResponseEntity<LoginProject200Response> loginProject(LoginRequest loginRequest) {
        Project project = projectRepository.findByProjectName(loginRequest.getProjectName()).get(0);
        if (project != null && encoder.matches(loginRequest.getPassword(), project.getPasswordHash())) {
            String token =  jwtUtil.generateToken(loginRequest.getProjectName());
            LoginProject200Response loginProject200Response = new LoginProject200Response();
            loginProject200Response.setAccessToken(token);
            log.info("Successfully logged in project");
            return ResponseEntity.ok(loginProject200Response);
        }
        log.error("Invalid login");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
}