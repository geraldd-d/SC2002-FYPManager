package controllers;

public class ServiceController {
	private static final ServiceController svc = new ServiceController();

    private final ProjectDataController projectDataController;
    private final ProjectRepository projectRepository;
    private final RequestDataController requestDataController;
    private final RequestRepository requestRepository;
    private final StudentService studentService;
    private final FacultyService facultyService;

    private ServiceController() {
    	this.studentService = StudentService.getInstance();
        this.facultyService = FacultyService.getInstance();
        this.projectDataController = ProjectDataController.getInstance();
        this.projectRepository = ProjectRepository.getInstance();
        this.requestDataController = RequestDataController.getInstance();
        this.requestRepository = RequestRepository.getInstance();
    }

    public static ServiceController getInstance() {
        return svc;
    }

    public ProjectDataController getProjectDataController() {
        return projectDataController;
    }

    public ProjectRepository getProjectRepository() {
        return projectRepository;
    }

    public RequestDataController getRequestDataController() {
        return requestDataController;
    }

    public RequestRepository getRequestRepository() {
        return requestRepository;
    }

    public StudentService getStudentService() {
        return studentService;
    }

    public FacultyService getFacultyService() {
        return facultyService;
    }

}
