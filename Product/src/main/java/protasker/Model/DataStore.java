package protasker.Model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class DataStore {
    private List<User> users;
    private List<Project> projects;
    private List<UserProject> userProjects;
    private List<Task> tasks;

    public DataStore() {
        this.users = new ArrayList<>();
        this.projects = new ArrayList<>();
        this.userProjects = new ArrayList<>();
        this.tasks = new ArrayList<>();
    }

    public List<User> getUsers() {
        if (users == null) {
            users = new ArrayList<>();
        }
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public List<Project> getProjects() {
        if (projects == null) {
            projects = new ArrayList<>();
        }
        return projects;
    }

    public void setProjects(List<Project> projects) {
        this.projects = projects;
    }

    public List<UserProject> getUserProjects() {
        if (userProjects == null) {
            userProjects = new ArrayList<>();
        }
        return userProjects;
    }

    public void setUserProjects(List<UserProject> userProjects) {
        this.userProjects = userProjects;
    }

    public List<Task> getTasks() {
        if (tasks == null) {
            tasks = new ArrayList<>();
        }
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

    public User findUserById(String userId) {
        return getUsers().stream()
                .filter(u -> u.getUserId().equals(userId))
                .findFirst()
                .orElse(null);
    }

    public User findUserByUsername(String username) {
        return getUsers().stream()
                .filter(u -> u.getUsername().equals(username))
                .findFirst()
                .orElse(null);
    }

    public Project findProjectById(String projectId) {
        return getProjects().stream()
                .filter(p -> p.getProjectId().equals(projectId))
                .findFirst()
                .orElse(null);
    }

    public List<Project> getUserProjects(String userId) {
        return getUserProjects().stream()
                .filter(up -> up.getUserId().equals(userId))
                .map(up -> findProjectById(up.getProjectId()))
                .filter(p -> p != null)
                .collect(Collectors.toList());
    }

    public List<User> getProjectMembers(String projectId) {
        return getUserProjects().stream()
                .filter(up -> up.getProjectId().equals(projectId))
                .map(up -> findUserById(up.getUserId()))
                .filter(u -> u != null)
                .collect(Collectors.toList());
    }

    public List<Task> getProjectTasks(String projectId) {
        return getTasks().stream()
                .filter(t -> t.getProjectId().equals(projectId))
                .collect(Collectors.toList());
    }

    public List<Task> getUserTasks(String userId) {
        return getTasks().stream()
                .filter(t -> t.getAssignedUserId().equals(userId))
                .collect(Collectors.toList());
    }

    public void addUserToProject(String userId, String projectId, String role) {
        getUserProjects().add(new UserProject(userId, projectId, role));
    }

    public void removeUserFromProject(String userId, String projectId) {
        getUserProjects().removeIf(up -> up.getUserId().equals(userId) && up.getProjectId().equals(projectId));
    }

    public boolean isUserInProject(String userId, String projectId) {
        return getUserProjects().stream()
                .anyMatch(up -> up.getUserId().equals(userId) && up.getProjectId().equals(projectId));
    }

    public String getUserRole(String userId, String projectId) {
        return getUserProjects().stream()
                .filter(up -> up.getUserId().equals(userId) && up.getProjectId().equals(projectId))
                .map(UserProject::getRole)
                .findFirst()
                .orElse(null);
    }

    public boolean isProjectLeader(String userId, String projectId) {
        String role = getUserRole(userId, projectId);
        return "owner".equals(role);
    }

    public List<User> getAllUsersExceptInProject(String projectId) {
        Set<String> projectMemberIds = getUserProjects().stream()
                .filter(up -> up.getProjectId().equals(projectId))
                .map(UserProject::getUserId)
                .collect(Collectors.toCollection(HashSet::new));

        return getUsers().stream()
                .filter(u -> !projectMemberIds.contains(u.getUserId()))
                .collect(Collectors.toList());
    }

    // Delete a project with all related tasks and memberships.
    public void deleteProject(String projectId) {
        getTasks().removeIf(t -> t.getProjectId().equals(projectId));
        getUserProjects().removeIf(up -> up.getProjectId().equals(projectId));
        getProjects().removeIf(p -> p.getProjectId().equals(projectId));
    }

    public void deleteTask(String taskId) {
        getTasks().removeIf(t -> t.getTaskId().equals(taskId));
    }
}
