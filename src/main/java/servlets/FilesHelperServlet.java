package servlets;

import models.MyFile;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@WebServlet(urlPatterns = "/files")
public class FilesHelperServlet extends HttpServlet {

    Comparator<MyFile> fileDirComparator;
    File[] roots;
    String firstRootName;

    @Override
    public void init() throws ServletException {
        super.init();
        fileDirComparator = initComparator();
        roots = File.listRoots();
        firstRootName = roots[0].getAbsolutePath();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Map<String, String[]> params = req.getParameterMap();

        String path = params.getOrDefault("path", new String[]{firstRootName})[0];
        File file = new File(path);
        List<MyFile> files = Arrays.stream(file.listFiles())
                .map(MyFile::new)
                .sorted(this.fileDirComparator.thenComparing(fieldComparator("size", true)))
                .collect(Collectors.toList());

        req.setAttribute("roots", this.roots);
        req.setAttribute("files", files);
        req.setAttribute("currentFolder", file.getAbsolutePath());
        req.setAttribute("parentFolder", file.getParent());
        req.setAttribute("fileDate", new MyFile(file).formatDate());
        getServletContext().getRequestDispatcher("/files.jsp").forward(req, resp);
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
