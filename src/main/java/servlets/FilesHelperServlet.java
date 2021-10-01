package servlets;

import models.MyFile;
import models.UserProfile;
import services.AccountService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystem;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@WebServlet(urlPatterns = "/files")
public class FilesHelperServlet extends HttpServlet {

    Comparator<MyFile> fileDirComparator;

    @Override
    public void init() throws ServletException {
        super.init();
        fileDirComparator = initComparator();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Map<String, String[]> params = req.getParameterMap();
        UserProfile user = AccountService.getUserBySessionId(req.getSession().getId());
        String path = params.getOrDefault("path", new String[]{""})[0];

        File dirFile = path.contains(user.getLogin()) ? new File(path) : getUserDirFile(user.getLogin());

        List<MyFile> files = getSortedFiles(dirFile);

        req.setAttribute("files", files);
        req.setAttribute("currentFolder", dirFile.getAbsolutePath());
        req.setAttribute("parentFolder", dirFile.getParent());
        req.setAttribute("fileDate", new MyFile(dirFile).formatDate());
        getServletContext().getRequestDispatcher("/view/files.jsp").forward(req, resp);
    }

    private List<MyFile> getSortedFiles(File dirFile) {
        return Arrays.stream(dirFile.listFiles())
                .map(MyFile::new)
                .sorted(this.fileDirComparator)//.thenComparing(fieldComparator("size", true)))
                .collect(Collectors.toList());
    }

    private File getUserDirFile(String login){
        String path = String.join(File.separator, System.getProperty("user.home"), "user files", login);
        File file = new File(path);
        if (!file.exists()){
            file.mkdirs();
        }
        return file;
    }



    private Comparator<MyFile> initComparator() {
        return (a, b) -> a.getFile().isDirectory() && !b.getFile().isDirectory() ? -1 :
                !a.getFile().isDirectory() && b.getFile().isDirectory() ? 1 : 0;
    }

    private Comparator<MyFile> fieldComparator(String field, boolean ascending){
        Comparator<MyFile> comp = null;
        if (field.equals("fileName")) {
            comp = (a, b) -> a.getFile().getName().compareToIgnoreCase(b.getFile().getName());
        } else if (field.equals("size")){
            comp = Comparator.comparing(a -> a.getFile().length());
        } else if (field.equals("date")) {
            comp = Comparator.comparing(d -> d.getFile().lastModified());
        }
        if (!ascending){
            comp.reversed();
        }
        return comp;

    }
}
