package seedu.address.model.module;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.model.module.student.Student;
import seedu.address.model.module.student.UniqueStudentList;
import seedu.address.model.task.Task;
import seedu.address.model.task.TaskId;
import seedu.address.model.task.UniqueTaskList;

/**
 * Represents a Module in the TAB.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Module {

    private final UniqueStudentList students;
    private final FilteredList<Student> filteredStudents;
    private final UniqueTaskList tasks;
    // Identity fields
    private final ModuleName moduleName;

    /**
     * Every field must be present and not null.
     */
    public Module(ModuleName moduleName) {
        requireAllNonNull(moduleName);
        this.moduleName = moduleName;
        students = new UniqueStudentList();
        filteredStudents = new FilteredList<>(this.getStudentList());
        tasks = new UniqueTaskList();
    }

    public ModuleName getName() {
        return moduleName;
    }

    /**
     * Returns true if both modules have the same name.
     * This defines a weaker notion of equality between two modules.
     */
    public boolean isSameModule(Module otherModule) {
        if (otherModule == this) {
            return true;
        }

        return otherModule != null
                && otherModule.getName().equals(getName());
    }

    /**
     * Returns true if both modules have the same identity and data fields.
     * This defines a stronger notion of equality between two persons.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Module)) {
            return false;
        }
        Module otherModule = (Module) other;
        return otherModule.getName().equals(getName());
    }

    /**
     * Replaces the contents of the student list with {@code students}.
     * {@code students} must not contain duplicate students.
     */
    public void setStudents(List<Student> students) {
        this.students.setStudents(students);
    }


    /**
     * Returns true if a student with the same identity as {@code student} exists in TAB.
     */
    public boolean hasStudent(Student student) {
        requireNonNull(student);
        return students.contains(student);
    }

    /**
     * Adds a student to TAB.
     * The student must not already exist in TAB.
     */
    public void addStudent(Student student) {
        students.add(student);
    }

    /**
     * Replaces the given student {@code target} in the list with {@code editedStudent}.
     * {@code target} must exist in TAB.
     * The student identity of {@code editedStudent} must not be the same
     * as another existing student in TAB.
     */
    public void setStudent(Student target, Student editedStudent) {
        requireNonNull(editedStudent);

        students.setStudent(target, editedStudent);
    }

    /**
     * Returns an unmodifiable view of the list of {@code Person} backed by the internal list of
     * {@code versionedAddressBook}
     */
    public ObservableList<Student> getFilteredStudentList() {
        return filteredStudents;
    }

    /**
     * Updates the filtered student list.
     *
     * @param predicate The predicate to filter students.
     */
    public void updateFilteredStudentList(Predicate<Student> predicate) {
        requireNonNull(predicate);
        filteredStudents.setPredicate(predicate);
    }

    /**
     * Removes {@code key} from this {@code TeachingAssistantBuddy}.
     * {@code key} must exist in TAB.
     */
    public void removeStudent(Student key) {
        students.remove(key);
    }

    public ObservableList<Student> getStudentList() {
        return students.asUnmodifiableObservableList();
    }

    /**
     * Adds a task to this module and add the task to all students in this module.
     * The task must not already exist in this module.
     * @param task The task to be added.
     */
    public void addTask(Task task) {
        tasks.add(task);
        students.forEach(s -> studentAddTask(s, task));
    }

    /**
     * Add a task to a given student.
     * @param student The student to receive the task.
     * @param task The task to be added to the student.
     */
    public void studentAddTask(Student student, Task task) {
        student.addTask(task);
    }

    /**
     * Edits the given student's old task (specified by oldTaskID) using a new task object.
     * @param student The student whose task will be modified.
     * @param oldTaskId The TaskId of the task to be modified.
     * @param newTask The new Task which will replace the old Task in the student's task list.
     */
    public void studentEditTask(Student student, TaskId oldTaskId, Task newTask) {
        UniqueTaskList studentTaskList = student.getTaskList();
        for (Task task : studentTaskList) {
            if (task.getTaskId().equals(oldTaskId)) {
                studentTaskList.setTask(task, newTask);
            }
        }
    }

    /**
     * Returns true if this module has task {@code task} exists in it.
     */
    public boolean hasTask(Task task) {
        requireNonNull(task);
        return tasks.contains(task);
    }

    /**
     * Returns the UniqueTaskList containing the tasks for this module.
     * @return UniqueTaskList containing the tasks for this module.
     */
    public UniqueTaskList getTaskList() {
        return this.tasks;
    }

    /**
     * Replaces the contents of the task list with {@code tasks}.
     * {@code tasks} must not contain duplicate tasks.
     */
    public void setTasks(List<Task> tasks) {
        this.tasks.setTasks(tasks);
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(moduleName);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName());

        return builder.toString();
    }

}
