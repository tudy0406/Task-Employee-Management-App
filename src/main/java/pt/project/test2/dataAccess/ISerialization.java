package pt.project.test2.dataAccess;

import pt.project.test2.dataModel.Task;
import pt.project.test2.dataModel.Employee;

import java.io.*;
import java.util.List;
import java.util.Map;

public class ISerialization implements Serializable {
    private final String fileName;

    public ISerialization(String fileName) {
        this.fileName = fileName;
    }

    public void saveData(Map<Employee, List<Task>> map, List<Task> tasks) throws Exception {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(fileName))) {
            out.writeObject(map);
            out.writeObject(tasks);
            System.out.println("Data saved successfully!");
        } catch (IOException e) {
            throw new Exception(e.getCause());
        }
    }

    @SuppressWarnings("unchecked")
    public Object[] loadData() throws Exception {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(fileName))) {
            Map<Employee, List<Task>> map = (Map<Employee, List<Task>>) in.readObject();
            List<Task> tasks = (List<Task>) in.readObject();
            return new Object[]{map, tasks};
        }catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

}
